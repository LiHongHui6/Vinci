package com.lihonghui.vinci.main;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lihonghui.vinci.R;
import com.lihonghui.vinci.base.BaseActivity;
import com.lihonghui.vinci.common.widget.view.StateSelectButton;
import com.lihonghui.vinci.home.HomeFragment;
import com.lihonghui.vinci.user.personal.PersonalFragment;
import com.socks.library.KLog;

public class MainActivity extends BaseActivity{
    private FrameLayout mMainView;
    private LinearLayout mLayoutHome;
    private View mIconHome;
    private TextView mTextHome;
    private LinearLayout mLayoutPersonal;
    private View mIconPersonal;
    private TextView mTextPersonal;
    // private View mIconRelease;

    private HomeFragment mHomeFragment;
    private PersonalFragment mPersonalFragment;
    private FragmentManager mFragmentManager;
    private final int SELECT_TEB_HOME = 0;
    private final int SELECT_TEB_PERSONAL = 1;
    private StateSelectButton mIconPost;
    private TextView mTextPost;

    @Override
    public int getContentViewResourceID() {
        return R.layout.activity_main;
    }

    @Override
    public void findView() {
        super.findView();
        assignViews();
        mHomeFragment = new HomeFragment();
        mPersonalFragment = new PersonalFragment();
    }

    private void assignViews() {
        mMainView = (FrameLayout) findViewById(R.id.main_view);
        mLayoutHome = (LinearLayout) findViewById(R.id.layout_home);
        mIconHome = findViewById(R.id.icon_home);
        mTextHome = (TextView) findViewById(R.id.text_home);
        mLayoutPersonal = (LinearLayout) findViewById(R.id.layout_personal);
        mIconPersonal = findViewById(R.id.icon_personal);
        mTextPersonal = (TextView) findViewById(R.id.text_personal);
        mIconPost = (StateSelectButton)findViewById(R.id.icon_post);
        mTextPost = (TextView) findViewById(R.id.text_post);
    }

    @Override
    public void setUpView() {
        super.setUpView();
        mLayoutHome.setOnClickListener(this);
        mLayoutPersonal.setOnClickListener(this);
        mIconPost.setOnClickListener(this);
        mIconPost.addStateChangeListener(new MyStateChangeListener());

        fitStatusBarEnble(true);
        setStatusBarColor(getResources().getColor(R.color.colorPink3));
        setUpFragment();
        setBottomTab(SELECT_TEB_HOME);
    }

    @Override
    protected boolean getTitleBarVisibility() {
        return false;
    }

    @Override
    public void initParameter() {
        super.initParameter();
        mFragmentManager = getSupportFragmentManager();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == mLayoutHome) {
            fitStatusBarEnble(true);
            setStatusBarColor(getResources().getColor(R.color.colorPink3));
            setBottomTab(SELECT_TEB_HOME);

            showHomePage();
            hidePersonalPage();
        } else if (view == mLayoutPersonal) {
//            if(!DribbbleOAuth.isUserLogin()){
//                Intent intent = new Intent(this, LoginActivity.class);
//                startActivity(intent);
//                return;
//            }
            fitStatusBarEnble(false);
            setStatusBarColor(getResources().getColor(R.color.colorTransparent));
            setBottomTab(SELECT_TEB_PERSONAL);

            showPersonalPage();
            hideHomePage();
        }else if(view == mIconPost){
            KLog.e("view == mIconPost");
        }
    }

    private void setBottomTab(int SELECT_TAB) {
        if (SELECT_TAB == SELECT_TEB_HOME) {
            mIconPersonal.setBackgroundResource(R.mipmap.mine_nor);
            mIconHome.setBackgroundResource(R.mipmap.index_sel);
            mTextHome.setTextColor(getResources().getColor(R.color.colorPink1));
            mTextPersonal.setTextColor(getResources().getColor(R.color.colorPink2));
        } else {
            mIconPersonal.setBackgroundResource(R.mipmap.mine_sel);
            mIconHome.setBackgroundResource(R.mipmap.index_nor);
            mTextHome.setTextColor(getResources().getColor(R.color.colorPink2));
            mTextPersonal.setTextColor(getResources().getColor(R.color.colorPink1));
        }
    }

    private void showHomePage() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (mHomeFragment.isAdded()) {
            fragmentTransaction.show(mHomeFragment);
            fragmentTransaction.commit();
        } else {
            fragmentTransaction.add(R.id.main_view, mHomeFragment);
            fragmentTransaction.commit();
        }

    }

    private void hideHomePage() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.hide(mHomeFragment);
        fragmentTransaction.commit();
    }

    private void showPersonalPage() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        if (mPersonalFragment.isAdded()) {
            fragmentTransaction.show(mPersonalFragment);
            fragmentTransaction.commit();
        } else {
            fragmentTransaction.add(R.id.main_view, mPersonalFragment);
            fragmentTransaction.commit();
        }
    }

    private void hidePersonalPage() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.hide(mPersonalFragment);
        fragmentTransaction.commit();
    }

    private void setUpFragment() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_view, mHomeFragment);
        fragmentTransaction.commit();
    }

    class MyStateChangeListener implements StateSelectButton.StateChangeListener{

        @Override
        public void onPress() {
            mTextPost.setTextColor(getResources().getColor(R.color.colorPink1));
        }

        @Override
        public void onUp() {
            mTextPost.setTextColor(getResources().getColor(R.color.colorPink2));
        }
    }
}
