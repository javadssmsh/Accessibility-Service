package ir.javadsh.challenge.activity;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.javadsh.challenge.ApplicationClass;
import ir.javadsh.challenge.R;
import ir.javadsh.challenge.adapter.ShowLogAdapter;
import ir.javadsh.challenge.helper.MessageEvent;
import ir.javadsh.challenge.db.AppDataBase;
import ir.javadsh.challenge.db.model.ReportLog;
import ir.javadsh.challenge.service.TextAccessibilityService;

public class HomeActivity extends AppCompatActivity {

    private TextView tv;
    List<ReportLog> reportLogs;
    AppDataBase dataBase;
    ShowLogAdapter adapter;
    public static List<ReportLog> staticLogs = new ArrayList<>();
    SwitchMaterial switchMaterialService;
    SwitchMaterial switchMaterialDraw;
    ConstraintLayout contentLayout;
    RelativeLayout emptyLayout;


    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //initialization
        tv = findViewById(R.id.accessibility_service_textView);
        contentLayout = findViewById(R.id.content_layout);
        emptyLayout = findViewById(R.id.empty_layout);
        tv = findViewById(R.id.accessibility_service_textView);
        RecyclerView showLogRecyclerView = findViewById(R.id.show_log_rv);
        switchMaterialService = findViewById(R.id.accessibility_service_switch_button);
        switchMaterialDraw = findViewById(R.id.draw_over_other_app_switch_button);
        dataBase = AppDataBase.getInstance(this);
        reportLogs = new ArrayList<>();
        adapter = new ShowLogAdapter(this, reportLogs);

        //permission
        switchMaterialService.setOnClickListener(view -> {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        });

        switchMaterialDraw.setOnClickListener(view -> {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        });


        //
        reportLogs = dataBase.getReportLogDao().getAllLogs();
        if (reportLogs.size() == 0) {
            changeViewState(true);
        } else {
            changeViewState(false);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            showLogRecyclerView.setHasFixedSize(true);
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);
            showLogRecyclerView.setLayoutManager(linearLayoutManager);
            showLogRecyclerView.setAdapter(adapter);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ApplicationClass.DRAW_OTHER_APP_PERMISSION) {
            Log.d(ApplicationClass.DEBUG_TAG, "result is :" + resultCode);
        }
    }

    void changeViewState(Boolean isEmpty) {
        if (isEmpty) {
            emptyLayout.setVisibility(View.VISIBLE);
            contentLayout.setVisibility(View.INVISIBLE);
        } else {
            emptyLayout.setVisibility(View.INVISIBLE);
            contentLayout.setVisibility(View.VISIBLE);
        }
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {
        //Toast.makeText(this, "Hey, my message" + event.getMessage(), Toast.LENGTH_SHORT).show();
        if (event != null) {
            String url = event.getMessage().getUrl();
            String imgUrl = event.getMessage().getImgUrl();
            Long time = event.getMessage().getCreatedDate();
            String packageName = event.getMessage().getBrowserName();
            ReportLog reportLog = new ReportLog();
            reportLog.setUrl(url);
            reportLog.setImgUrl(imgUrl);
            reportLog.setCreatedDate(time);
            reportLog.setBrowserName(packageName);

            android.util.Log.d(ApplicationClass.DEBUG_TAG, "bus event in home activity is " + reportLog.getUrl());

            //create class
            dataBase.getReportLogDao().saveLog(reportLog);
            adapter.addLogReport(reportLog);
            staticLogs.add(reportLog);
        }
    }


    protected void onResume() {
        super.onResume();
        if (!isAccessibilityServiceEnabled(this, TextAccessibilityService.class)) {
            switchMaterialService.setChecked(false);
        } else {
            switchMaterialService.setChecked(true);
        }
        Log.d(ApplicationClass.DEBUG_TAG, "state is : " + Settings.canDrawOverlays(this));
        if (!Settings.canDrawOverlays(this)) {
            switchMaterialDraw.setChecked(false);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (Settings.canDrawOverlays(HomeActivity.this)) {
                        switchMaterialDraw.setChecked(true);
                    }
                }
            }, 500);
            switchMaterialDraw.setChecked(false);
        } else {
            switchMaterialDraw.setChecked(true);
        }
        if (dataBase.getReportLogDao().getAllLogs().size() != 0) {
            changeViewState(false);
            adapter.notifyDataSetChanged();
        } else {
            changeViewState(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public static boolean isAccessibilityServiceEnabled(Context context, Class<? extends AccessibilityService> service) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> enabledServices = accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK);

        for (AccessibilityServiceInfo enabledService : enabledServices) {
            ServiceInfo enabledServiceInfo = enabledService.getResolveInfo().serviceInfo;
            if (enabledServiceInfo.packageName.equals(context.getPackageName()) && enabledServiceInfo.name.equals(service.getName()))
                return true;
        }
        return false;
    }


}