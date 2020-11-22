package ir.javadsh.challenge.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import ir.javadsh.challenge.ApplicationClass;
import ir.javadsh.challenge.R;
import ir.javadsh.challenge.helper.MessageEvent;
import ir.javadsh.challenge.db.model.ReportLog;


public class TextAccessibilityService extends AccessibilityService {

    String lastUrl;
    WindowManager wm;
    LayoutInflater inflater;
    View myView;
    Boolean wmIsShowing = false;

    /*@Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        //Log.d(ApplicationClass.DEBUG_TAG, "TextAccessibilityService is onAccessibilityEventB");

        AccessibilityNodeInfo info = event.getSource();


        if (info != null && info.getText() != null) {
            String requiredText = info.getText().toString();
            if (requiredText.contains("https") | requiredText.contains("http")) {
                lastUrl = requiredText;
            }
            if (requiredText.contains("پرداخت")) {
*//*                Log.d(ApplicationClass.DEBUG_TAG, "event is : " + event.toString());
                Log.d(ApplicationClass.DEBUG_TAG, ":::::::::::::::::::::::::::::::::::::::::::::::: ");
                Log.d(ApplicationClass.DEBUG_TAG, "info is : " + info.toString());*//*
                if (info.getChildCount() > 0) {
                    for (int i = 0; i < info.getChildCount(); i++) {
                        Log.d(ApplicationClass.DEBUG_TAG, "info is : "+i + "isssss : " + info.toString());
                    }
                }
                ReportLog reportLog = new ReportLog();
                reportLog.setBrowserName(event.getPackageName().toString());
                reportLog.setCreatedDate(event.getEventTime());
                if (lastUrl != null) {
                    reportLog.setUrl(lastUrl);
                } else {
                    reportLog.setUrl(getString(R.string.not_founf_link));
                }
                reportLog.setImgUrl("");
                if (true*//*reportLog.getUrl() != null*//*) {
                    if (!wmIsShowing) {
                        showOverOtherApp(reportLog);
                    }
                }
            }
        }
    }
*/

    @Override
    public void onInterrupt() {
        Log.d(ApplicationClass.DEBUG_TAG, "TextAccessibilityService is onInterrupt");
    }

    public void showOverOtherApp(ReportLog reportLog) {
        // WindowManager
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT);

        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        myView = inflater.inflate(R.layout.item_interance_to_app, null);
        myView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(ApplicationClass.DEBUG_TAG, "touch me");
                Log.d(ApplicationClass.DEBUG_TAG, "View : =>" + v.toString());
                Log.d(ApplicationClass.DEBUG_TAG, "MotionEvent: =>" + event.toString());
                // launch the app
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("ir.javadsh.challenge");
                    if (launchIntent != null) {
                        EventBus.getDefault().postSticky(new MessageEvent(reportLog));
                        startActivity(launchIntent);
                    }
                    closeOverOtherApp();
                } else {
                    closeOverOtherApp();

                }
                return true;
            }
        });
        // Add layout to window manager
        wmIsShowing = true;
        wm.addView(myView, params);
    }

    public void closeOverOtherApp() {
        wmIsShowing = false;
        wm.removeView(myView);
    }


    // test
    @Override
    protected void onServiceConnected() {
        AccessibilityServiceInfo info = getServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED;
        info.packageNames = packageNames();
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_VISUAL;
        //throttling of accessibility event notification
        info.notificationTimeout = 300;
        //support ids interception
        info.flags = AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS |
                AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS;

        this.setServiceInfo(info);
    }

    private String captureUrl(AccessibilityNodeInfo info, SupportedBrowserConfig config) {
        List<AccessibilityNodeInfo> nodes = info.findAccessibilityNodeInfosByViewId(config.addressBarId);
        if (nodes == null || nodes.size() <= 0) {
            return null;
        }

        AccessibilityNodeInfo addressBarNodeInfo = nodes.get(0);
        String url = null;
        if (addressBarNodeInfo.getText() != null) {
            url = addressBarNodeInfo.getText().toString();
        }
        addressBarNodeInfo.recycle();
        return url;
    }

    @Override
    public void onAccessibilityEvent(@NonNull AccessibilityEvent event) {
        AccessibilityNodeInfo parentNodeInfo = event.getSource();
        if (parentNodeInfo == null) {
            return;
        }

        String packageName = event.getPackageName().toString();
        SupportedBrowserConfig browserConfig = null;
        for (SupportedBrowserConfig supportedConfig : getSupportedBrowsers()) {
            if (supportedConfig.packageName.equals(packageName)) {
                browserConfig = supportedConfig;
            }
        }
        //this is not supported browser, so exit
        if (browserConfig == null) {
            return;
        }

        String requiredText = "";
        if (parentNodeInfo.getText() != null) {
            requiredText = parentNodeInfo.getText().toString();
        }
        String capturedUrl = captureUrl(parentNodeInfo, browserConfig);
        parentNodeInfo.recycle();

        //we can't find a url. Browser either was updated or opened page without url text field
        if (capturedUrl == null) {
            return;
        } else {
            lastUrl = capturedUrl;
            Log.d(ApplicationClass.DEBUG_TAG, "capturedUrl is : " + capturedUrl);
        }

        long eventTime = event.getEventTime();
        if (requiredText.contains("پرداخت")) {
            ReportLog reportLog = new ReportLog();
            reportLog.setUrl(lastUrl);
            reportLog.setBrowserName(packageName);
            reportLog.setCreatedDate(eventTime);
            reportLog.setImgUrl("");
            if (!wmIsShowing) {
                showOverOtherApp(reportLog);
            }
        }

    }


    @NonNull
    private static String[] packageNames() {
        List<String> packageNames = new ArrayList<>();
        for (SupportedBrowserConfig config : getSupportedBrowsers()) {
            packageNames.add(config.packageName);
        }
        return packageNames.toArray(new String[0]);
    }

    private static class SupportedBrowserConfig {
        public String packageName, addressBarId;

        public SupportedBrowserConfig(String packageName, String addressBarId) {
            this.packageName = packageName;
            this.addressBarId = addressBarId;
        }
    }


    @NonNull
    private static List<SupportedBrowserConfig> getSupportedBrowsers() {
        List<SupportedBrowserConfig> browsers = new ArrayList<>();
        browsers.add(new SupportedBrowserConfig("com.android.chrome", "com.android.chrome:id/url_bar"));
        browsers.add(new SupportedBrowserConfig("org.mozilla.firefox", "org.mozilla.firefox:id/url_bar_title"));
        return browsers;
    }

}