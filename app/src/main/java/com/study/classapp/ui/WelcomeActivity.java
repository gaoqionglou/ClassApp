package com.study.classapp.ui;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.study.classapp.base.BaseActivity;
import com.study.classapp.base.ThemeActivity;
import com.study.classapp.databinding.ActivityMainBinding;
import com.study.classapp.databinding.ActivityWelcomeBinding;
import com.study.classapp.datebase.AppDatabase;
import com.study.classapp.util.DataCreator;

import java.lang.ref.WeakReference;

public class WelcomeActivity extends AppCompatActivity {
    ActivityWelcomeBinding activityWelcomeBinding;
    private MyHandler mHandler = new MyHandler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWelcomeBinding = ActivityWelcomeBinding.inflate(LayoutInflater.from(this));
        setContentView(activityWelcomeBinding.getRoot());
        DataCreator.makeClassData();
        mHandler.sendEmptyMessageDelayed(1, 1000);

        PermissionUtils.permission(PermissionConstants.STORAGE).request();
    }


    static class MyHandler extends Handler {
        WeakReference<Activity> mActivityReference;

        MyHandler(Activity activity) {
            mActivityReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final Activity activity = mActivityReference.get();
            if (activity != null) {
                Log.i("app", AppDatabase.getInstance().studentClassDao().getStudentClass().toString());
                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }
        }
    }
}
