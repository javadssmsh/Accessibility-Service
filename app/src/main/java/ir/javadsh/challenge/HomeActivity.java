package ir.javadsh.challenge;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.javadsh.challenge.adapter.ShowLogAdapter;
import ir.javadsh.challenge.helper.MessageEvent;
import ir.javadsh.challenge.model.AppDataBase;
import ir.javadsh.challenge.model.ReportLog;
import ir.javadsh.challenge.service.TextAccessibilityService;

public class HomeActivity extends AppCompatActivity {

    private TextView tv;
    List<ReportLog> reportLogs;
    AppDataBase dataBase;
    ShowLogAdapter adapter;
    public static List<ReportLog> staticLogs = new ArrayList<>();
    SwitchMaterial switchMaterial;

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

        tv = findViewById(R.id.textView);
        RecyclerView showLogRecyclerView = findViewById(R.id.show_log_rv);
        switchMaterial = findViewById(R.id.switch_button);
        dataBase = AppDataBase.getInstance(this);
        reportLogs = new ArrayList<>();

        switchMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
            }
        });


        //
        reportLogs = dataBase.getReportLogDao().getAllLogs();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        showLogRecyclerView.setHasFixedSize(true);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        adapter = new ShowLogAdapter(this, reportLogs);
        showLogRecyclerView.setLayoutManager(linearLayoutManager);
        showLogRecyclerView.setAdapter(adapter);

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
            //tv.setText("Accessibility Service is NOT enabled");
            switchMaterial.setChecked(false);
        } else {
            //tv.setText("Accessibility Service is ENABLED");
            switchMaterial.setChecked(true);
        }
        adapter.notifyDataSetChanged();
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