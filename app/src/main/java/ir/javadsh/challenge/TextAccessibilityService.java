package ir.javadsh.challenge;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;


public class TextAccessibilityService extends AccessibilityService {

    List<AccessibilityNodeInfo> list;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        //Log.d(ApplicationClass.DEBUG_TAG, "TextAccessibilityService is onAccessibilityEventB");

        AccessibilityNodeInfo info = event.getSource();
/*        Log.d(ApplicationClass.DEBUG_TAG, "::::::::::::::::::::");
        Log.d(ApplicationClass.DEBUG_TAG, "::::::::::::::::::::");
        Log.d(ApplicationClass.DEBUG_TAG, "::::::::::::::::::::");
        Log.d(ApplicationClass.DEBUG_TAG, "::::::::::::::::::::");*/
        if (info != null && info.getText() != null) {
            String requiredText = info.getText().toString();
            if (requiredText.contains("https") | requiredText.contains("http")) {
                Log.d(ApplicationClass.DEBUG_TAG, "whole info text is : =============>>>>>>>>>" + info.getText().toString());
                Log.d(ApplicationClass.DEBUG_TAG, " getEventType text is : =============>>>>>>>>>" + event.getEventType());
                Log.d(ApplicationClass.DEBUG_TAG, "whole event getClassName is : =============>>>>>>>>>" + event.getClassName().toString());
                Log.d(ApplicationClass.DEBUG_TAG, "whole event getClassName is : =============>>>>>>>>>" + event.getPackageName().toString());
            }
/*            if (requiredText.contains("پرداخت")) {
                Log.d(ApplicationClass.DEBUG_TAG, "whole info text is : =============>>>>>>>>>" + info.getText().toString());
                Log.d(ApplicationClass.DEBUG_TAG, " getEventType text is : =============>>>>>>>>>" + event.getEventType());
                Log.d(ApplicationClass.DEBUG_TAG, "whole event getClassName is : =============>>>>>>>>>" + event.getClassName().toString());
                Log.d(ApplicationClass.DEBUG_TAG, "whole event getClassName is : =============>>>>>>>>>" + event.getPackageName().toString());
            }*/
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