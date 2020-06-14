package com.study.classapp.util;

import com.blankj.utilcode.util.TimeUtils;
import com.study.classapp.R;
import com.study.classapp.datebase.AppDatabase;
import com.study.classapp.model.StudentClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DataCreator {

    //生成课程表数据
    public static void makeClassData() {
        try {
            AppDatabase.getInstance().studentClassDao().deleteAll();
            String json = FileUtil.getRawFile(R.raw.data);
            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = jsonObject.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
//                "className": "C++",
//                        "classDate": "2020-06-13",
//                        "classStartTime": "8:30",
//                        "classEndTime": "10:30",
//                        "classWeeks": "1-20weeks",
//                        "classNum": "3",
//                        "classTeacher": "Mr.A"
                JSONObject obj = array.getJSONObject(i);
                String className = obj.getString("className");
                String classDate = obj.getString("classDate");
                String classStartTime = obj.getString("classStartTime");
                String classEndTime = obj.getString("classEndTime");
                String classAddress = obj.getString("classAddress");

                String classWeeks = obj.getString("classWeeks");
                String classNum = obj.getString("classNum");
                String classTeacher = obj.getString("classTeacher");

                StudentClass studentClass = new StudentClass();
                studentClass.setClassId(UUIDCreator.uuid());
                studentClass.setClassName(className);
                studentClass.setClassDate(classDate);
                studentClass.setClassDay(TimeUtils.getChineseWeek(classDate, DateUtil.getDateFormat("yyyy-MM-dd")));
                studentClass.setClassStartTime(classStartTime);
                studentClass.setClassEndTime(classEndTime);
                studentClass.setClassNum(classNum);
                studentClass.setClassWeeks(classWeeks);
                studentClass.setClassTeacher(classTeacher);
                studentClass.setClassAddress(classAddress);
                AppDatabase.getInstance().studentClassDao().insertStudentClass(studentClass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
