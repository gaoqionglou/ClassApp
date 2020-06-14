package com.study.classapp.datebase;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.study.classapp.MyApp;
import com.study.classapp.model.StudentClass;


//app数据库初始化
@Database(entities = {StudentClass.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "class_app.db";

    private static AppDatabase mAppDatabase;

    public abstract StudentClassDao studentClassDao();


    public static AppDatabase getInstance() {
        if (mAppDatabase == null) {
            synchronized (AppDatabase.class) {
                if (mAppDatabase == null) {
                    mAppDatabase = Room.databaseBuilder(MyApp.getContext(), AppDatabase.class, DB_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return mAppDatabase;
    }
}
