package com.study.classapp.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//数据库 学生课程信息表
@Entity(tableName = "StudentClass")
public class StudentClass {

    @NonNull
    @PrimaryKey()
    @ColumnInfo(name = "class_id")
    private String classId;

    //课程名字
    @ColumnInfo(name = "className")
    private String className;

    //课程上课地点
    @ColumnInfo(name = "classAddress")
    private String classAddress;

    //上课日期 如2020-06-14
    @ColumnInfo(name = "classDate")
    private String classDate;

    //上课星期几 如 周日
    @ColumnInfo(name = "classDay")
    private String classDay;

    //开始时间
    @ColumnInfo(name = "classStartTime")
    private String classStartTime;

    //结束时间
    @ColumnInfo(name = "classEndTime")
    private String classEndTime;

    //周数
    @ColumnInfo(name = "classWeeks")
    private String classWeeks;

    //节数
    @ColumnInfo(name = "classNum")
    private String classNum;

    @ColumnInfo(name = "classTeacher")
    private String classTeacher;

    public String getClassAddress() {
        return classAddress;
    }

    public void setClassAddress(String classAddress) {
        this.classAddress = classAddress;
    }

    public String getClassDate() {
        return classDate;
    }

    public void setClassDate(String classDate) {
        this.classDate = classDate;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassDay() {
        return classDay;
    }

    public void setClassDay(String classDay) {
        this.classDay = classDay;
    }

    public String getClassStartTime() {
        return classStartTime;
    }

    public void setClassStartTime(String classStartTime) {
        this.classStartTime = classStartTime;
    }

    public String getClassEndTime() {
        return classEndTime;
    }

    public void setClassEndTime(String classEndTime) {
        this.classEndTime = classEndTime;
    }

    public String getClassWeeks() {
        return classWeeks;
    }

    public void setClassWeeks(String classWeeks) {
        this.classWeeks = classWeeks;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public String getClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(String classTeacher) {
        this.classTeacher = classTeacher;
    }

    @Override
    public String toString() {
        return "StudentClass{" +
                "classId='" + classId + '\'' +
                ", className='" + className + '\'' +
                ", classAddress='" + classAddress + '\'' +
                ", classDate='" + classDate + '\'' +
                ", classDay='" + classDay + '\'' +
                ", classStartTime='" + classStartTime + '\'' +
                ", classEndTime='" + classEndTime + '\'' +
                ", classWeeks='" + classWeeks + '\'' +
                ", classNum='" + classNum + '\'' +
                ", classTeacher='" + classTeacher + '\'' +
                '}';
    }
}
