package ir.javadsh.challenge.service;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import org.greenrobot.eventbus.EventBus;
import ir.javadsh.challenge.ApplicationClass;
import ir.javadsh.challenge.helper.MessageEvent;


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
                Log.d(ApplicationClass.DEBUG_TAG, " Specific url is : =============>>>>>>>>>" + lastUrl);
                Log.d(ApplicationClass.DEBUG_TAG, " Specific info text is : =============>>>>>>>>>" + info.getText().toString());
                Log.d(ApplicationClass.DEBUG_TAG, " getEventType text is : =============>>>>>>>>>" + event.getEventType());
                Log.d(ApplicationClass.DEBUG_TAG, "whole event getClassName is : =============>>>>>>>>>" + event.getClassName().toString());
                Log.d(ApplicationClass.DEBUG_TAG, "whole event getPackageName is : =============>>>>>>>>>" + event.getPackageName().toString());
                Log.d(ApplicationClass.DEBUG_TAG, "whole event getClassName is : =============>>>>>>>>>" + event.getEventTime());
                Log.d(ApplicationClass.DEBUG_TAG, "::::::::::::::::::::");
                Log.d(ApplicationClass.DEBUG_TAG, "::::::::::::::::::::");
                Log.d(ApplicationClass.DEBUG_TAG, "::::::::::::::::::::");
                Log.d(ApplicationClass.DEBUG_TAG, "::::::::::::::::::::");
                EventBus.getDefault().postSticky(new MessageEvent("javad "));
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