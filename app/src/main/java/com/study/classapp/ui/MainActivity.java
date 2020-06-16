package com.study.classapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;


import com.blankj.utilcode.util.TimeUtils;
import com.google.android.material.tabs.TabLayout;
import com.study.classapp.adapter.MainFragmentAdapter;
import com.study.classapp.base.ThemeActivity;
import com.study.classapp.databinding.ActivityMainBinding;

import java.util.ArrayList;


public class MainActivity extends ThemeActivity {
    private String[] weekDay = new String[]{"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    private ActivityMainBinding mainBinding;
    private String currentDay;
    private MainFragmentAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(mainBinding.getRoot());

        getActionBarLayoutBinding().back.setOnClickListener(view -> this.finish());
        getActionBarLayoutBinding().setting.setText("添加");
        getActionBarLayoutBinding().setting.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddClassActivity.class);
            startActivity(intent);
        });
        currentDay = TimeUtils.getChineseWeek(System.currentTimeMillis());
        mainBinding.tabDay.setTabMode(TabLayout.MODE_FIXED);
        mainBinding.alarm.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AlarmActivity.class);
            startActivity(intent);
        });
        ArrayList<StudentClassFragment> fragments = new ArrayList<>();
        int currentIndex = 0;
        for (int i = 0; i < weekDay.length; i++) {
            StudentClassFragment fragment = StudentClassFragment.newInstance(weekDay[i]);
            if (currentDay.equalsIgnoreCase(weekDay[i])) currentIndex = i;
            fragments.add(fragment);
        }
        mAdapter = new MainFragmentAdapter(getSupportFragmentManager(), fragments);
        mainBinding.vpClass.setAdapter(mAdapter);
        mainBinding.vpClass.setOffscreenPageLimit(weekDay.length);
        mainBinding.tabDay.setupWithViewPager(mainBinding.vpClass);
        if (mainBinding.tabDay.getTabAt(currentIndex) != null) {
            mainBinding.tabDay.getTabAt(currentIndex).select();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
