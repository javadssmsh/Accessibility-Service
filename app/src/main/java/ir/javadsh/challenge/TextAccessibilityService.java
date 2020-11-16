package ir.javadsh.challenge;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;


public class TextAccessibilityService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        AccessibilityNodeInfo info = event.getSource();

        if (info == null || info.getText() == null || info.getClassName() == null || !event.getClassName().equals("android.widget.EditText")
                || event.getPackageName().equals(getPackageName())) {

            return;
        } else {
            String inputText = event.getText().toString();
            Log.d("TAG","string is : " + inputText);
            // process text here
        }
    }

    @Override
    public void onInterrupt() {

    }
}