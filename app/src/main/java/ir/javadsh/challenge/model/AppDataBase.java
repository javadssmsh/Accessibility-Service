package ir.javadsh.challenge.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = ReportLog.class, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public static String DB_NAME = "my_db";
    public static AppDataBase instance;

    public abstract ReportLogDao getReportLogDao();

    public static synchronized AppDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

}
