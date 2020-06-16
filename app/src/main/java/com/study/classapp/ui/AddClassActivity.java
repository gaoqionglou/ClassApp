package com.study.classapp.ui;


import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.study.classapp.R;
import com.study.classapp.base.BaseActivity;
import com.study.classapp.base.ThemeActivity;
import com.study.classapp.databinding.ActivityAddClassBinding;
import com.study.classapp.datebase.AppDatabase;
import com.study.classapp.model.StudentClass;
import com.study.classapp.util.DateUtil;
import com.study.classapp.util.UUIDCreator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class AddClassActivity extends ThemeActivity {
    ActivityAddClassBinding addClassBinding;

    private String classNum = "1";
    private String className;
    private String classTeacher;
    private String classAddress;
    private String classDate;
    private String classWeeks;
    private String classStartTime, classEndTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addClassBinding = ActivityAddClassBinding.inflate(LayoutInflater.from(this));
        setContentView(addClassBinding.getRoot());
        getActionBarLayoutBinding().title.setText("添加课程");
        getActionBarLayoutBinding().setting.setText("提交");
        getActionBarLayoutBinding().back.setVisibility(View.GONE);
        getActionBarLayoutBinding().setting.setOnClickListener(v -> {
            addClass();
        });
        addClassBinding.num.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == R.id.one) {
                classNum = "1";
            } else if (i == R.id.two) {
                classNum = "2";
            } else if (i == R.id.three) {
                classNum = "3";
            }
        }));

        addClassBinding.etClassStartTime.setOnClickListener(v -> {
            new TimePickerBuilder(AddClassActivity.this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    classStartTime = getTime(date);
                    addClassBinding.etClassStartTime.setText(classStartTime);
                }
            }).setType(new boolean[]{false, false, false, true, true, false})
                    .build().show();
        });

        addClassBinding.etClassEndTime.setOnClickListener(v -> {
            new TimePickerBuilder(AddClassActivity.this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    classEndTime = getTime(date);
                    addClassBinding.etClassEndTime.setText(classEndTime);
                }
            }).setType(new boolean[]{false, false, false, true, true, false})
                    .build().show();
        });


    }


    public void addClass() {

        className = addClassBinding.etClassName.getText().toString();
        classTeacher = addClassBinding.etClassTeacher.getText().toString();
        classAddress = addClassBinding.etClassAddress.getText().toString();
        classDate = addClassBinding.etClassDate.getText().toString();
        classTeacher = addClassBinding.etClassTeacher.getText().toString();
        classWeeks = addClassBinding.etClassWeeks.getText().toString();
        classTeacher = addClassBinding.etClassTeacher.getText().toString();
        if (TextUtils.isEmpty(className)
                && TextUtils.isEmpty(classTeacher)
                && TextUtils.isEmpty(classDate)
                && TextUtils.isEmpty(classAddress)
                && TextUtils.isEmpty(classWeeks)
                && TextUtils.isEmpty(classTeacher)
        ) {
            ToastUtils.showShort("请填写完整信息");
            return;
        }
        StudentClass studentClass = new StudentClass();
        studentClass.setClassId(UUIDCreator.uuid());
        studentClass.setClassName(className);
        studentClass.setClassAddress(classAddress);
        studentClass.setClassDay(TimeUtils.getChineseWeek(classDate, DateUtil.getDateFormat("yyyy-MM-dd")));
        studentClass.setClassWeeks(classWeeks);
        studentClass.setClassNum(classNum);
        studentClass.setClassTeacher(classTeacher);
        studentClass.setClassStartTime(classStartTime);
        studentClass.setClassEndTime(classEndTime);
        studentClass.setClassDate(classDate);
        AppDatabase.getInstance().studentClassDao().insertStudentClass(studentClass);
        List<StudentClass> studentClasses = AppDatabase.getInstance().studentClassDao().getStudentClass();
        Log.i("app", studentClasses.toString());
        this.finish();
    }

    public String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }


}
