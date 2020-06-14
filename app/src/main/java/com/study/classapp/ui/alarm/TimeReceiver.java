package com.study.classapp.ui.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.TimeUtils;
import com.study.classapp.MyApp;
import com.study.classapp.util.DateUtil;
import com.study.classapp.util.SharedPreferencesUtils;


import java.util.Date;

//定时设置静音
public class TimeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (SharedPreferencesUtils.getBoolean(MyApp.getMyApplication().getApplicationContext(), "silentSwitch", false)) {
                //开关打开了
                String start = SharedPreferencesUtils.getString(MyApp.getMyApplication().getApplicationContext(), "startTime", "");
                String end = SharedPreferencesUtils.getString(MyApp.getMyApplication().getApplicationContext(), "endTime", "");
                String className = SharedPreferencesUtils.getString(MyApp.getMyApplication().getApplicationContext(), "className", "");
                boolean classOver = SharedPreferencesUtils.getBoolean(MyApp.getMyApplication().getApplicationContext(), "classNameOver", false);
                if (TextUtils.isEmpty(start) && TextUtils.isEmpty(end) && TextUtils.isEmpty(className)) {
                    return;
                }
                Log.i("app", start + "/" + end);
                long current = TimeUtils.date2Millis(new Date());

                assert start != null;
                long startDate = TimeUtils.date2Millis(DateUtil.getDateFormat("yyyy-MM-dd HH:mm:ss").parse(start + ":00"));
                assert end != null;
                long endDate = TimeUtils.date2Millis(DateUtil.getDateFormat("yyyy-MM-dd HH:mm:ss").parse(end + ":00"));
                AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

                assert audioManager != null;
                if (current >= startDate && current <= endDate) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    Toast.makeText(context, "上课 " + className + " 时间【" + start + "至" + end + "】手机已静音", Toast.LENGTH_LONG).show();
                } else if (current > endDate) {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    if (!classOver) {
                        //下课的提示语只提示一次
                        SharedPreferencesUtils.saveBoolean(MyApp.getMyApplication().getApplicationContext(), "classNameOver", true);
                        Toast.makeText(context, className + " 下课了， 手机已解除静音", Toast.LENGTH_LONG).show();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
