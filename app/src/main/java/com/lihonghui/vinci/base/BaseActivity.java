package com.lihonghui.vinci.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lihonghui.vinci.R;
import com.lihonghui.vinci.common.utils.DisplayUtil;
import com.lihonghui.vinci.common.utils.StatusBarColourHelper;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected ViewGroup rootView;
    protected View contentView;
    private View mViewBack;
    private TextView mTextTitle;
    protected BaseActivity mAcSelf;
    private StatusBarColourHelper mStatusBarColourHelper;
    private FrameLayout mTitleBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(fullScreenWithOutStatusBar()){
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_base);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mAcSelf = this;

        initParameter();
        findView();
        getData();
        setUpView();
    }

    public abstract int getContentViewResourceID();

    public void findView() {
        rootView = (ViewGroup) this.findViewById(R.id.content_view);
        contentView = this.getLayoutInflater().inflate(getContentViewResourceID(), rootView, true);
        mViewBack = findViewById(R.id.view_back);
        mTextTitle = (TextView) findViewById(R.id.text_title);
        mTitleBar = (FrameLayout) findViewById(R.id.title_bar);
    }

    public void getData() {
    }

    protected boolean fullScreenWithOutStatusBar(){
        return false;
    }

    public void initParameter() {
        mStatusBarColourHelper = new StatusBarColourHelper(this);
    }

    public void setUpView() {
        mViewBack.setOnClickListener(this);
        if (getTitleBarVisibility()) {
            mTitleBar.setVisibility(View.VISIBLE);
            mTextTitle.setText(getTitleText());
        } else {
            mTitleBar.setVisibility(View.GONE);
        }
    }

    protected String getTitleText() {
        return "";
    }

    protected abstract boolean getTitleBarVisibility();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        if (view == mViewBack) {
            mAcSelf.finishAfterTransition();
        }
    }

    public void setStatusBarColor(int color) {
        colourStatusBar(color);
    }

    public void fitStatusBarEnble(boolean isFitStatusBar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            if (isFitStatusBar) {
                DisplayUtil.getInstance(this).fitStatusBar(rootView);
            } else {
                DisplayUtil.getInstance(this).coverStatusBar(rootView);
            }

        }
    }

    /**
     * 兼容4.4及以上状态栏的着色
     */
    protected void colourStatusBar(int color) {
        mStatusBarColourHelper.colourStatusBar(color);
    }

}
