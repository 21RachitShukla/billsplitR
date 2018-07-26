package rs21.billsplitr;

import android.app.Application;
import android.arch.persistence.room.Room;

public class MyApplication extends Application {
    static MyDatabase myDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        myDatabase = Room.databaseBuilder(getApplicationContext(), MyDatabase.class, "db").allowMainThreadQueries().build();
    }

    public static MyDatabase getDb() {
        return myDatabase;
    }
}
