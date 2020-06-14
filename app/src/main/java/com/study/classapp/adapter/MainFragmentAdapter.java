package com.study.classapp.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.study.classapp.ui.StudentClassFragment;

import java.util.ArrayList;
import java.util.List;

public class MainFragmentAdapter extends FragmentStatePagerAdapter {
    private String[] weekDay = new String[]{"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    private FragmentManager fragmentManager;
    private ArrayList<StudentClassFragment> list;


    public MainFragmentAdapter(@NonNull FragmentManager fm, ArrayList<StudentClassFragment> fragments) {
        super(fm);
        this.fragmentManager = fm;
        this.list = fragments;
    }



    @NonNull
    @Override
    public StudentClassFragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return weekDay[position].replace("周", "");
    }
}
