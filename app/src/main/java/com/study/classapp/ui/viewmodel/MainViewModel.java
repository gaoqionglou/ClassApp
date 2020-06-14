package com.study.classapp.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.study.classapp.datebase.AppDatabase;
import com.study.classapp.model.StudentClass;

import java.util.List;

public class MainViewModel extends ViewModel {

    private MutableLiveData<List<StudentClass>> studentClasses = new MutableLiveData<>();

    public MutableLiveData<List<StudentClass>> getStudentClasses() {
        return studentClasses;
    }



    public void queryStudentClasses(String day) {
        List<StudentClass> data = AppDatabase.getInstance().studentClassDao().getStudentClassesByDay(day);
        studentClasses.setValue(data);
    }

}
