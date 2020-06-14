package com.study.classapp.base;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;


import com.study.classapp.R;
import com.study.classapp.databinding.ActionBarLayoutBinding;

//一个Activity基类的基本实现，已封装好标题栏，并对外提供setTitle 设置标题文字的方法

public class ThemeActivity extends BaseActivity {

    private ActionBarLayoutBinding actionBarLayoutBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCustomActionBar();
    }

    @Override
    public void setCustomActionBar() {
        actionBarLayoutBinding = ActionBarLayoutBinding.inflate(LayoutInflater.from(this));
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView = actionBarLayoutBinding.getRoot();
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setCustomView(mActionBarView, lp);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBarLayoutBinding.title.setText("课表信息");


    }

    public ActionBarLayoutBinding getActionBarLayoutBinding() {
        return actionBarLayoutBinding;
    }

    public void setTitle(String title) {
        actionBarLayoutBinding.title.setText(title);
    }
}
