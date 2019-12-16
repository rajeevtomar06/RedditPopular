package com.abbaqus.reddit.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.abbaqus.reddit.db.popular.PopularDao;
import com.abbaqus.reddit.popular.model.PopularModel;


@Database(entities = {PopularModel.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract PopularDao popularDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "Reddit.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
