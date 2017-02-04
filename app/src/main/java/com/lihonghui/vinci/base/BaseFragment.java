package com.lihonghui.vinci.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public abstract class BaseFragment extends Fragment implements View.OnClickListener{
    protected View contentView;
    protected AppCompatActivity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mActivity == null){
            mActivity = (AppCompatActivity) getActivity();
        }

        if(contentView == null){
            contentView = inflater.inflate(getContentViewID(),null);
            findView(contentView);
            initParam();
            initData();
            setUpView();
        }else{
            removeParent(contentView);
            refreshView();
        }
        subscribeEvent();
        return contentView;
    }

    public abstract int getContentViewID();
    public void findView(View contentView){};
    public void subscribeEvent(){};
    public void unSubscribeEvent(){};
    public void initData(){};
    public void setUpView(){};
    public void refreshView(){};
    public void initParam(){}

    public void removeParent(View view){
        ViewParent parent = view.getParent();
        if(parent != null){
            ((ViewGroup)(parent)).removeView(view);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {

    }
}
