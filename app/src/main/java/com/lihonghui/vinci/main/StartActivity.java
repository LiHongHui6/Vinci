package com.lihonghui.vinci.main;

import android.content.Intent;
import android.os.Handler;

import com.lihonghui.vinci.R;
import com.lihonghui.vinci.base.BaseActivity;

public class StartActivity extends BaseActivity {

    private Handler handler;

    @Override
    public int getContentViewResourceID() {
        return R.layout.activity_start;
    }

    @Override
    protected boolean getTitleBarVisibility() {
        return false;
    }

    @Override
    public void initParameter() {
        super.initParameter();
        handler = new Handler();
    }

    @Override
    public void setUpView() {
        super.setUpView();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(mAcSelf,MainActivity.class);
                startActivity(intent);
            }
        },3000);
    }

    @Override
    protected boolean fullScreenWithOutStatusBar() {
        return true;
    }
}
