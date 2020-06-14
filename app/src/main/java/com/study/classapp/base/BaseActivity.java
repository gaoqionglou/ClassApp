package com.study.classapp.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.study.classapp.databinding.ActionBarLayoutBinding;

//Activity基类，抽象类，对外实现抽象方法用来设置顶部菜单栏的样式
public abstract class BaseActivity extends AppCompatActivity {
    private BasePopupView msgPopup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomActionBar();
    }


    public abstract void setCustomActionBar();

    public void showLoading() {
        showLoading("正在加载数据...", true);
    }

    public void showLoading(String msg, boolean cancel) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (msgPopup != null && msgPopup.isShow()) {
                    msgPopup.dismiss();
                }
                msgPopup = new XPopup.Builder(getApplicationContext()).dismissOnTouchOutside(cancel).dismissOnBackPressed(cancel).asLoading(msg).show();
            }
        });

    }

    public void dismissPopup() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (msgPopup != null && msgPopup.isShow()) {
                    msgPopup.dismiss();
                }
            }
        });

    }
}
