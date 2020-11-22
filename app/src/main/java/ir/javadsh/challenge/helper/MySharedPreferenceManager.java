package ir.javadsh.challenge.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferenceManager {

    public static final String PREFERENCE_NAME = "my_preference";
    public static final String FIRST_ENTRANCE_CHECKING = "my_preference";
    private static MySharedPreferenceManager instance = null;

    private SharedPreferences sharedPreferences = null;
    private SharedPreferences.Editor editor = null;

    public static MySharedPreferenceManager getInstance(Context context) {

        if (instance == null) {
            instance = new MySharedPreferenceManager(context);
        }
        return instance;
    }

    private MySharedPreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public Boolean getFirstEntranceBoolean() {
        return sharedPreferences.getBoolean(FIRST_ENTRANCE_CHECKING, true);
    }

    public void setFirstEntranceBoolean(Boolean isFirst) {
        editor.putBoolean(FIRST_ENTRANCE_CHECKING, isFirst);
        editor.apply();
    }


}
