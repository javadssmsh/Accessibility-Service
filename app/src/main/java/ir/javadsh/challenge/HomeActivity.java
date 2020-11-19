package ir.javadsh.challenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.javadsh.challenge.adapter.ShowLogAdapter;
import ir.javadsh.challenge.model.Log;
import ir.javadsh.challenge.service.ServiceListener;
import ir.javadsh.challenge.service.TextAccessibilityService;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements ServiceListener {

    private TextView tv;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tv = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
            }
        });

        //
        List<Log> logs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Log log = new Log();
            log.setBrowserName("کروم" + i);
            log.setCreatedDate("" + i + ":" + i);
            log.setImgUrl("");
            log.setUrl("javadssmh@gmail.com");
            logs.add(log);
        }

        RecyclerView showLogRecyclerView = findViewById(R.id.show_log_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        showLogRecyclerView.setHasFixedSize(true);
        ShowLogAdapter adapter = new ShowLogAdapter(this, logs);
        showLogRecyclerView.setLayoutManager(linearLayoutManager);
        showLogRecyclerView.setAdapter(adapter);

    }

    protected void onResume() {
        super.onResume();
        if(!isAccessibilityServiceEnabled(this, TextAccessibilityService.class)) {
            tv.setText("Accessibility Service is NOT enabled");
            button.setText("Enable");
        }else{
            tv.setText("Accessibility Service is ENABLED");
            button.setText("Disable");
        }
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


    @Override
    public void sendToDB(String url, String packageName, Long date) {
        
    }
}