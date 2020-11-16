package ir.javadsh.challenge.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import ir.javadsh.challenge.ApplicationClass;

public class MyAccessibilityService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        Log.d(ApplicationClass.TAG, " MyAccessibilityService (onAccessibilityEvent is started ...)");
    }

    @Override
    public void onInterrupt() {
        Log.d(ApplicationClass.TAG, " MyAccessibilityService (onInterrupt is started ...)");
    }

    @Override
    public void onServiceConnected() {
        Log.d(ApplicationClass.TAG, " MyAccessibilityService (onServiceConnected is started ...)");

        // Set the type of events that this service wants to listen to. Others
        // won't be passed to this service.
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED |
                AccessibilityEvent.TYPE_VIEW_FOCUSED;

        // If you only want this service to work with specific applications, set their
        // package names here. Otherwise, when the service is activated, it will listen
        // to events from all applications.
        info.packageNames = new String[]
                {"com.example.android.myFirstApp", "com.example.android.mySecondApp", "ir.javadsh.challenge"};

        // Set the type of feedback your service will provide.
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;

        // Default services are invoked only if no package-specific ones are present
        // for the type of AccessibilityEvent generated. This service *is*
        // application-specific, so the flag isn't necessary. If this was a
        // general-purpose service, it would be worth considering setting the
        // DEFAULT flag.

        // info.flags = AccessibilityServiceInfo.DEFAULT;

        info.notificationTimeout = 100;

        this.setServiceInfo(info);

    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(ApplicationClass.TAG, " MyAccessibilityService (onUnbind is started ...)");
        return super.onUnbind(intent);
    }
}
