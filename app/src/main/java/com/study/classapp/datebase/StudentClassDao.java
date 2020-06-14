package com.study.classapp.datebase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.study.classapp.model.StudentClass;

import java.util.List;

@Dao
public interface StudentClassDao {
    @Insert
    void insertStudentClass(StudentClass studentClass);

    @Query("SELECT * FROM StudentClass where classDay = :classDay")
    List<StudentClass> getStudentClassesByDay(String classDay);

    @Update
    void updateStudentClass(StudentClass studentClass);

    @Query("SELECT * FROM StudentClass")
    List<StudentClass> getStudentClass();

    @Query("DELETE FROM StudentClass")
    int deleteAll();

    @Query("DELETE FROM StudentClass where class_id=:id")
    int deleteStudentClassById(String id);
}
