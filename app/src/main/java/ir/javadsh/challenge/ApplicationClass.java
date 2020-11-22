package ir.javadsh.challenge;

import android.app.Application;
import android.content.Intent;

import ir.javadsh.challenge.activity.HomeActivity;
import ir.javadsh.challenge.activity.WizardActivity;
import ir.javadsh.challenge.helper.MySharedPreferenceManager;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class ApplicationClass extends Application {

    public static final String DEBUG_TAG = "myApp";
    public static final int DRAW_OTHER_APP_PERMISSION = 1342;

    @Override
    public void onCreate() {
        super.onCreate();

        if (MySharedPreferenceManager.getInstance(this).getFirstEntranceBoolean()) {
            //go to wizard
            Intent intent = new Intent(this, WizardActivity.class);
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            //go to
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
