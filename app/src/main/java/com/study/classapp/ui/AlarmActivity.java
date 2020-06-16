package com.study.classapp.ui;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.TimeUtils;
import com.study.classapp.MyApp;
import com.study.classapp.R;
import com.study.classapp.base.ThemeActivity;
import com.study.classapp.databinding.ActivityAlarmBinding;
import com.study.classapp.datebase.AppDatabase;
import com.study.classapp.model.StudentClass;
import com.study.classapp.ui.alarm.AlarmManagerUtil;
import com.study.classapp.ui.alarm.TimeReceiver;
import com.study.classapp.util.DateUtil;
import com.study.classapp.util.SharedPreferencesUtils;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AlarmActivity extends ThemeActivity {
    private String action = "com.study.classapp.ui.alarm.TimeReceiver";

    private String time = "";
    private int cycle = -99;
    private int ring = -99;

    ActivityAlarmBinding alarmBinding;

    private String currentDate;
    private List<String> weekDays = Arrays.asList("周日", "周一", "周二", "周三", "周四", "周五", "周六");
    //课程开始和结束时间
    private String start, end, className;

    //获取Do not disturb权限,才可进行静音操作
    private void getDoNotDisturb() {
        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        assert notificationManager != null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && !notificationManager.isNotificationPolicyAccessGranted()) {

            Intent intent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alarmBinding = ActivityAlarmBinding.inflate(LayoutInflater.from(this));
        setContentView(alarmBinding.getRoot());

        getActionBarLayoutBinding().setting.setText("提交");
        getActionBarLayoutBinding().title.setText("设置提醒");
        getActionBarLayoutBinding().back.setVisibility(View.GONE);

        getDoNotDisturb();
        alarmBinding.switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    SharedPreferencesUtils.saveBoolean(MyApp.getMyApplication().getApplicationContext(), "silentSwitch", true);

                } else {
                    SharedPreferencesUtils.saveBoolean(MyApp.getMyApplication().getApplicationContext(), "silentSwitch", false);
                }
            }
        });
        alarmBinding.classChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String curretnDay = TimeUtils.getChineseWeek(System.currentTimeMillis());
                    //获取当日课程
                    List<StudentClass> currentDayClasses = AppDatabase.getInstance().studentClassDao().getStudentClassesByDay(curretnDay);
                    //当日可以上的课程
                    List<StudentClass> currentClasses = new ArrayList<>();
                    for (StudentClass studentClass : currentDayClasses) {
                        long currentDate = System.currentTimeMillis();
                        String startTimeStr = studentClass.getClassDate() + " " + studentClass.getClassStartTime();
                        long startDate = DateUtil.getDateFormat("yyyy-MM-dd HH:mm").parse(startTimeStr).getTime();
//                        if (startDate >= currentDate) {
                        currentClasses.add(studentClass);
//                        }
                    }
                    List<String> types = new ArrayList<>();
                    for (StudentClass studentClass : currentClasses) {
                        String str = "【" + studentClass.getClassDate() + "】-" + studentClass.getClassDay() + "-" + studentClass.getClassName();
                        types.add(str);
                    }


                    //条件选择器
                    OptionsPickerView pvOptions = new OptionsPickerBuilder(AlarmActivity.this, new OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int option2, int options3, View v) {
                            String type = types.get(options1);
                            alarmBinding.classTv.setText(type);
                            StudentClass selectedClass = currentClasses.get(options1);
                            alarmBinding.timeLabel.setText("设置闹钟（上课时间：" + selectedClass.getClassStartTime() + "）");
                            start = selectedClass.getClassDate() + " " + selectedClass.getClassStartTime();
                            end = selectedClass.getClassDate() + " " + selectedClass.getClassEndTime();
                            className = selectedClass.getClassName();
                        }
                    }).build();
                    pvOptions.setPicker(types);
                    pvOptions.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        alarmBinding.timeChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //时间选择器
                new TimePickerBuilder(AlarmActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        time = getTime(date);
                        alarmBinding.alarmTv.setText(time);
                    }
                }).setType(new boolean[]{false, false, false, true, true, false})
                        .build().show();
            }
        });

        alarmBinding.repeatChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> repeatType = new ArrayList<>();
                repeatType.add("每天");
                repeatType.add("只响一次");

                //条件选择器
                OptionsPickerView pvOptions = new OptionsPickerBuilder(AlarmActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        String type = repeatType.get(options1);
                        alarmBinding.repeatTv.setText(type);
                        if (options1 == 0) {
                            cycle = 0;
                        } else {
                            cycle = -1;
                        }
                    }
                }).build();
                pvOptions.setPicker(repeatType);
                pvOptions.show();
            }
        });


        alarmBinding.soundChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> soundType = new ArrayList<>();
                soundType.add("铃声");
                soundType.add("震动");
                //条件选择器
                OptionsPickerView pvOptions = new OptionsPickerBuilder(AlarmActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        String type = soundType.get(options1);
                        alarmBinding.soundTv.setText(type);
                        if (options1 == 0) {
                            ring = 1;
                        } else {
                            ring = 0;
                        }
                    }
                }).build();
                pvOptions.setPicker(soundType);
                pvOptions.show();
            }
        });

        getActionBarLayoutBinding().setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(time)) {
                    Toast.makeText(AlarmActivity.this, "请设置时间", Toast.LENGTH_LONG).show();
                    return;
                }
                if (cycle == -99) {

                    Toast.makeText(AlarmActivity.this, "请设置重复类型", Toast.LENGTH_LONG).show();
                    return;
                }
                if (ring == -99) {

                    Toast.makeText(AlarmActivity.this, "请设置提醒类型", Toast.LENGTH_LONG).show();
                    return;
                }
                setClock();
                startTimeReceiver();
            }
        });


    }

    //启动定时设置手机静音
    private void startTimeReceiver() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(AlarmActivity.this, TimeReceiver.class);
        SharedPreferencesUtils.saveString(MyApp.getMyApplication().getApplicationContext(),"startTime", start);
        SharedPreferencesUtils.saveString(MyApp.getMyApplication().getApplicationContext(),"endTime", end);
        SharedPreferencesUtils.saveString(MyApp.getMyApplication().getApplicationContext(),"className", className);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0,
                intent, 0);
        assert alarmManager != null;
        alarmManager.setInexactRepeating(AlarmManager.RTC, 0, 1 * 1000, pendingIntent);
    }

    public String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }

    private void setClock() {
        if (time != null && time.length() > 0) {
            String[] times = time.split(":");
            if (cycle == 0) {//是每天的闹钟
                AlarmManagerUtil.setAlarm(this, 0, Integer.parseInt(times[0]), Integer.parseInt
                        (times[1]), 0, 0, "闹钟响了", ring);
            }
            if (cycle == -1) {//是只响一次的闹钟
                AlarmManagerUtil.setAlarm(this, 1, Integer.parseInt(times[0]), Integer.parseInt
                        (times[1]), 0, 0, "闹钟响了", ring);
            }
            Toast.makeText(this, "闹钟设置成功", Toast.LENGTH_LONG).show();
        }

    }


}
