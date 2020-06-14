package com.study.classapp.util;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.study.classapp.R;
import com.study.classapp.datebase.AppDatabase;
import com.study.classapp.model.StudentClass;


/**
 * 对话框封装类
 */
public class DialogUtil {
    /**
     * 修改课程的对话框
     */
    public static void updateClassDialog(Context context, String title, StudentClass studentClass, UpdateClassListener updateClassListener) {

        View outerView = LayoutInflater.from(context).inflate(R.layout.update_class_dialog, null);
        EditText etName = outerView.findViewById(R.id.et_class_name);
        EditText etNum = outerView.findViewById(R.id.et_class_num);
        EditText etAddress = outerView.findViewById(R.id.et_class_address);
        EditText etStartTime = outerView.findViewById(R.id.et_class_start_time);
        EditText etEndTime = outerView.findViewById(R.id.et_class_end_time);
        EditText etWeek = outerView.findViewById(R.id.et_class_weeks);

        EditText etTeacher = outerView.findViewById(R.id.et_class_teacher);
        etAddress.setText(studentClass.getClassAddress());
        etName.setText(studentClass.getClassName());
        etNum.setText(studentClass.getClassNum());

        etStartTime.setText(studentClass.getClassStartTime());
        etEndTime.setText(studentClass.getClassEndTime());
        etWeek.setText(studentClass.getClassWeeks());
        etTeacher.setText(studentClass.getClassTeacher());
        new AlertDialog.Builder(context)
                .setIcon(R.mipmap.ic_launcher_round)
                .setView(outerView)
                .setTitle(title)
                .setCancelable(true)
                .setPositiveButton("确定", (v, var) -> {
                    studentClass.setClassAddress(etAddress.getText().toString());
                    studentClass.setClassName(etName.getText().toString());
                    studentClass.setClassNum(etNum.getText().toString());
                    studentClass.setClassStartTime(etStartTime.getText().toString());
                    studentClass.setClassTeacher(etTeacher.getText().toString());
                    studentClass.setClassEndTime(etEndTime.getText().toString());
                    studentClass.setClassWeeks(etWeek.getText().toString());
                    AppDatabase.getInstance().studentClassDao().updateStudentClass(studentClass);
                    if (updateClassListener != null) updateClassListener.onUpdate(studentClass);
                })
                .show();
    }

    public interface UpdateClassListener {
        public void onUpdate(StudentClass studentClass);
    }

}

