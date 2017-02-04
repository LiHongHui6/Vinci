package com.lihonghui.vinci.user.personal.submodule;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.lihonghui.vinci.R;
import com.lihonghui.vinci.base.BaseActivity;
import com.lihonghui.vinci.data.Entity.Bucket;

/**
 * Created by yq05481 on 2016/12/20.
 */

public class BucketShotsActivity extends BaseActivity {
    public static final String KEY_BUCKET = "KEY_BUCKET";
    private Bucket mBucket;
    private FrameLayout layoutFragment;

    @Override
    public int getContentViewResourceID() {
        return R.layout.activity_bucket_shots;
    }

    @Override
    protected boolean getTitleBarVisibility() {
        return true;
    }

    @Override
    public void getData() {
        super.getData();
        Intent intent = getIntent();
        String jsonBucket = intent.getStringExtra(KEY_BUCKET);
        mBucket = new Gson().fromJson(jsonBucket, Bucket.class);
    }

    @Override
    public void findView() {
        super.findView();
        layoutFragment = (FrameLayout) findViewById(R.id.layout_fragment);
    }

    @Override
    protected void colourStatusBar(int color) {
        super.colourStatusBar(color);
    }

    @Override
    public void setUpView() {
        super.setUpView();

        fitStatusBarEnble(true);
        setStatusBarColor(getResources().getColor(R.color.colorPink3));

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        BucketShotsFragment bucketShotsFragment = new BucketShotsFragment();
        bucketShotsFragment.setBucket(mBucket);
        fragmentTransaction.replace(R.id.layout_fragment,bucketShotsFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected String getTitleText() {
        return mBucket.getName();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void showByShareElements(Activity parent, View view, Bucket bucket) {
        Intent intent = new Intent(parent, BucketShotsActivity.class);
        Gson gson = new Gson();
        String jsonShot = gson.toJson(bucket);
        intent.putExtra(KEY_BUCKET, jsonShot);
        parent.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(parent, view, view.getTransitionName()).toBundle());
    }

    public static void show(Activity parent, Bucket bucket){
        Intent intent = new Intent(parent, BucketShotsActivity.class);
        Gson gson = new Gson();
        String jsonShot = gson.toJson(bucket);
        intent.putExtra(KEY_BUCKET, jsonShot);
        parent.startActivity(intent);
    }
}
