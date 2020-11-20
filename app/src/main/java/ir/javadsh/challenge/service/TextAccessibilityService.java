package ir.javadsh.challenge.service;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ir.javadsh.challenge.ApplicationClass;
import ir.javadsh.challenge.HomeActivity;
import ir.javadsh.challenge.helper.MessageEvent;
import ir.javadsh.challenge.model.ReportLog;


public class TextAccessibilityService extends AccessibilityService {

    String lastUrl;


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        //Log.d(ApplicationClass.DEBUG_TAG, "TextAccessibilityService is onAccessibilityEventB");

        AccessibilityNodeInfo info = event.getSource();

        if (info != null && info.getText() != null) {
            String requiredText = info.getText().toString();
            if (requiredText.contains("https") | requiredText.contains("http")) {
                lastUrl = requiredText;
            }
            if (requiredText.contains("پرداخت")) {
/*                Log.d(ApplicationClass.DEBUG_TAG, " Specific url is : =============>>>>>>>>>" + lastUrl);
                Log.d(ApplicationClass.DEBUG_TAG, " Specific info text is : =============>>>>>>>>>" + info.getText().toString());
                Log.d(ApplicationClass.DEBUG_TAG, " getEventType text is : =============>>>>>>>>>" + event.getEventType());
                Log.d(ApplicationClass.DEBUG_TAG, "whole event getClassName is : =============>>>>>>>>>" + event.getClassName().toString());
                Log.d(ApplicationClass.DEBUG_TAG, "whole event getPackageName is : =============>>>>>>>>>" + event.getPackageName().toString());
                Log.d(ApplicationClass.DEBUG_TAG, "whole event getClassName is : =============>>>>>>>>>" + event.getEventTime());*/
                Log.d(ApplicationClass.DEBUG_TAG, "::::::::::::::::::::");
                Log.d(ApplicationClass.DEBUG_TAG, "::::::::::::::::::::");
                Log.d(ApplicationClass.DEBUG_TAG, "::::::::::::::::::::");
                Log.d(ApplicationClass.DEBUG_TAG, "::::::::::::::::::::");
                ReportLog reportLog = new ReportLog();
                reportLog.setBrowserName(event.getPackageName().toString());
                reportLog.setCreatedDate(event.getEventTime());
                if (lastUrl != null) {
                    reportLog.setUrl(lastUrl);
                } else {
                    reportLog.setUrl("لینکی پیدا نشد");
                }
                reportLog.setImgUrl("");
                //HomeActivity.staticLogs.add(reportLog);
                EventBus.getDefault().postSticky(new MessageEvent(reportLog));
            }
        }
    }


    @Override
    public void onInterrupt() {
        Log.d(ApplicationClass.DEBUG_TAG, "TextAccessibilityService is onInterrupt");
    }


    @Override
    protected void onServiceConnected() {


    }
}