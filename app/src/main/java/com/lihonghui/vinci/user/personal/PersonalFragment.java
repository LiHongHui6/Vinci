package com.lihonghui.vinci.user.personal;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lihonghui.vinci.DribbbleOAuth;
import com.lihonghui.vinci.R;
import com.lihonghui.vinci.base.BaseFragment;
import com.lihonghui.vinci.common.RxBus.RxBus;
import com.lihonghui.vinci.common.RxBus.RxBusSubscriber;
import com.lihonghui.vinci.common.RxBus.RxSubscriptions;
import com.lihonghui.vinci.common.RxBus.event.UserLoginEvent;
import com.lihonghui.vinci.common.RxBus.event.UserLogoutEvent;
import com.lihonghui.vinci.common.utils.DensityUtil;
import com.lihonghui.vinci.common.utils.DisplayUtil;
import com.lihonghui.vinci.common.utils.ImageLoader;
import com.lihonghui.vinci.common.utils.TabModelCreator;
import com.lihonghui.vinci.common.widget.adapter.IndicatorFragmentPagerAdapter;
import com.lihonghui.vinci.common.widget.adapter.TabIndicator;
import com.lihonghui.vinci.common.widget.view.CircleImageView;
import com.lihonghui.vinci.data.Entity.User;
import com.lihonghui.vinci.data.local.UserLocalData;
import com.lihonghui.vinci.user.login.LoginActivity;
import com.lihonghui.vinci.user.personal.submodule.BucketFragment;
import com.lihonghui.vinci.user.personal.submodule.LikeFragment;
import com.lihonghui.vinci.user.personal.submodule.PostedFragment;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * Created by yq05481 on 2016/11/11.
 */

public class PersonalFragment extends BaseFragment {
    private AppBarLayout mAppbar;
    private FrameLayout mLayoutFans;
    private TextView mTextFansCount;
    private FrameLayout mLayoutAttention;
    private TextView mTextAttentionCount;
    private TextView mTextUserNickname;
    private TextView mTextUserDescribe;
    //private Toolbar mTbToolbar;
    private CircleImageView mImageAvatarMini;
    private View mIconSearch;
    private FrameLayout mLayoutMore;
    private CircleImageView mImageAvatar;

    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private Subscription mRxSubSticky;
    private TextView mTextUserNicknameTop;
    private TextView mTextLoginHint;
    private Button mButtonLogin;
    private PopupWindow menuMore;
    private LinearLayout layoutSetting;
    private LinearLayout layoutLogout;
    private ImageView mTopBackground;
    private View mTopCover;

    private boolean isAppBarLayout = false;

    @Override
    public int getContentViewID() {
        return R.layout.fragment_personal;
    }

    @Override
    public void findView(View contentView) {
        super.findView(contentView);
        assignViews(contentView);
    }


    private void assignViews(View contentView) {
        mAppbar = (AppBarLayout) contentView.findViewById(R.id.appbar);
        mLayoutFans = (FrameLayout) contentView.findViewById(R.id.layout_fans);
        mTextFansCount = (TextView) contentView.findViewById(R.id.text_fans_count);
        mLayoutAttention = (FrameLayout) contentView.findViewById(R.id.layout_attention);
        mTextAttentionCount = (TextView) contentView.findViewById(R.id.text_attention_count);
        mTextUserNickname = (TextView) contentView.findViewById(R.id.text_user_nickname);
        mTextUserDescribe = (TextView) contentView.findViewById(R.id.text_user_describe);
        //mTbToolbar = (Toolbar) contentView.findViewById(R.id.tb_toolbar);
        mImageAvatarMini = (CircleImageView) contentView.findViewById(R.id.image_avatar_mini);
        mIconSearch = contentView.findViewById(R.id.icon_search);
        mLayoutMore = (FrameLayout) contentView.findViewById(R.id.layout_more);
        mImageAvatar = (CircleImageView) contentView.findViewById(R.id.image_avatar);
        mViewPager = (ViewPager) contentView.findViewById(R.id.personal_view_pager);
        tabLayout = (TabLayout) contentView.findViewById(R.id.tab_layout);
        mTextUserNicknameTop = (TextView) contentView.findViewById(R.id.text_user_nickname_top);
        mTopBackground = (ImageView) contentView.findViewById(R.id.top_background);
        mTopCover = contentView.findViewById(R.id.top_cover);
        mTextLoginHint = (TextView) contentView.findViewById(R.id.text_login_hint);
        mButtonLogin = (Button) contentView.findViewById(R.id.button_login);
    }

    @Override
    public void setUpView() {
        super.setUpView();
        mImageAvatarMini.setAlpha(0f);
        mButtonLogin.setOnClickListener(this);
        mLayoutMore.setOnClickListener(this);

        if (DribbbleOAuth.isUserLogin()) {
            loginStyle(UserLocalData.getUserInfo());
        } else {
            logoutStyle();
        }

        mAppbar.addOnOffsetChangedListener(new MyOffsetChangedListener());
        mAppbar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                KLog.e("addOnGlobalLayoutListener");
                setmAppBarDragEnable(false);
                isAppBarLayout = true;
                mAppbar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        List<TabIndicator.TabViewModel> tabModelList = TabModelCreator.getTabModelList(new String[]{"发布", "收藏", "喜欢"});

        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(new PostedFragment());
        fragmentList.add(new BucketFragment());
        fragmentList.add(new LikeFragment());

        IndicatorFragmentPagerAdapter adapter = new IndicatorFragmentPagerAdapter(getFragmentManager(), fragmentList);
        adapter.addTabTabView(tabModelList);
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void logoutStyle() {
        mLayoutFans.setVisibility(View.GONE);
        mLayoutAttention.setVisibility(View.GONE);
        mTextUserNickname.setVisibility(View.GONE);
        mTextUserDescribe.setVisibility(View.GONE);
        mImageAvatarMini.setVisibility(View.GONE);
        mTextUserNicknameTop.setVisibility(View.GONE);
        mTopCover.setVisibility(View.GONE);
        mTopBackground.setImageDrawable(null);
        mImageAvatar.setImageDrawable(null);
        mImageAvatarMini.setImageDrawable(null);

        mTextLoginHint.setVisibility(View.VISIBLE);
        mButtonLogin.setVisibility(View.VISIBLE);

        if(isAppBarLayout){
            KLog.e("logoutStyle setmAppBarDragEnable");
            setmAppBarDragEnable(false);
        }
    }

    private void setmAppBarDragEnable(final boolean enable){
        String b = enable?"true":"false";
        KLog.e("setmAppBarDragEnable "+b);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mAppbar.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override
            public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                return enable;
            }
        });
    }

    private void loginStyle(User user) {
        mLayoutFans.setVisibility(View.VISIBLE);
        mTextFansCount.setText(String.valueOf(user.getFollowers_count()));

        mLayoutAttention.setVisibility(View.VISIBLE);
        mTextAttentionCount.setText(String.valueOf(user.getFollowings_count()));

        mTextUserNickname.setVisibility(View.VISIBLE);
        mTextUserNickname.setText(user.getUsername());

        mTextUserNicknameTop.setVisibility(View.VISIBLE);
        mTextUserNicknameTop.setText(user.getUsername());

        mTextUserDescribe.setVisibility(View.VISIBLE);
        mTextUserDescribe.setText(user.getBio());

        mImageAvatarMini.setVisibility(View.VISIBLE);
        mTopCover.setVisibility(View.VISIBLE);

        mTextLoginHint.setVisibility(View.GONE);
        mButtonLogin.setVisibility(View.GONE);

        ImageLoader.loadImage(this,user.getAvatar_url(),mImageAvatar);
        ImageLoader.loadImage(this,user.getAvatar_url(),mImageAvatarMini);
        ImageLoader.loadBlurImage(this,user.getAvatar_url(),mTopBackground,20);

        if(isAppBarLayout){
            setmAppBarDragEnable(true);
        }
    }


    @Override
    public void subscribeEvent() {
        super.subscribeEvent();
        RxSubscriptions.remove(mRxSubSticky);
        mRxSubSticky = RxBus.getDefault().toObservableSticky(UserLoginEvent.class).subscribe(new RxBusSubscriber<UserLoginEvent>() {
            @Override
            protected void onEvent(UserLoginEvent userLoginEvent) {
                loginStyle(userLoginEvent.getmUser());
            }
        });
        RxSubscriptions.add(mRxSubSticky);
    }

    @Override
    public void unSubscribeEvent() {
        super.unSubscribeEvent();
        RxSubscriptions.remove(mRxSubSticky);
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == mButtonLogin) {
            Intent intent = new Intent(mActivity, LoginActivity.class);
            startActivity(intent);
        } else if (view == mLayoutMore) {
            showPopupWindow(mLayoutMore);
        } else if (view == layoutSetting) {
            KLog.e("设置");
            menuMore.dismiss();
        } else if (view == layoutLogout) {
            logout();
            menuMore.dismiss();
        }
    }

    private void logout() {
        DribbbleOAuth.logout();
        logoutStyle();
        RxBus.getDefault().postSticky(new UserLogoutEvent());
    }

    private void showPopupWindow(View parent) {
        if (menuMore == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.menu_more, null);

            layoutSetting = (LinearLayout) view.findViewById(R.id.layout_setting);
            layoutLogout = (LinearLayout) view.findViewById(R.id.layout_logout);
            layoutSetting.setOnClickListener(this);
            layoutLogout.setOnClickListener(this);

            menuMore = new PopupWindow(view, DensityUtil.dip2px(mActivity, 108.5f), DensityUtil.dip2px(mActivity, 104));
            menuMore.setFocusable(true);
            menuMore.setOutsideTouchable(true);
            ColorDrawable background = new ColorDrawable(0x00000000);
            menuMore.setBackgroundDrawable(background);
        }

        if (DribbbleOAuth.isUserLogin()) {
            layoutLogout.setVisibility(View.VISIBLE);
        } else {
            layoutLogout.setVisibility(View.GONE);
        }

        int gravity = Gravity.TOP | Gravity.RIGHT;
        menuMore.showAtLocation(parent, gravity, DensityUtil.dip2px(mActivity, 16), DensityUtil.dip2px(mActivity, 16) + DisplayUtil.getInstance(mActivity).getStateBarHeight());
    }

    class MyOffsetChangedListener implements AppBarLayout.OnOffsetChangedListener {
        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            //      LogUtil.printLog("verticalOffset :"+verticalOffset+"  getTotalScrollRange :"+appBarLayout.getTotalScrollRange());
            if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                mImageAvatarMini.setAlpha(1f);
                mTextUserNicknameTop.setAlpha(1f);
            } else {
                mImageAvatarMini.setAlpha(0f);
                mTextUserNicknameTop.setAlpha(0f);
            }
        }
    }
}
