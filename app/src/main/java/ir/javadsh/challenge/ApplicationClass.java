package ir.javadsh.challenge;

import android.app.Application;

public class ApplicationClass extends Application {

    public static final String DEBUG_TAG = "myApp";
    public static final int DRAW_OTHER_APP_PERMISSION = 1342;

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
