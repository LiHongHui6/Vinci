package com.lihonghui.vinci.user.login;

import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lihonghui.vinci.DribbbleOAuth;
import com.lihonghui.vinci.R;
import com.lihonghui.vinci.base.BaseActivity;
import com.lihonghui.vinci.common.utils.ToastUtil;

/**
 * Created by yq05481 on 2016/11/28.
 */

public class LoginActivity extends BaseActivity implements LoginContact.View {
    private ImageView mViewLoading;
    private WebView mWebView;

    private LoginPresenter mLoginPresenter;
    private TextView mTextHint;

    @Override
    public int getContentViewResourceID() {
        return R.layout.activity_login;
    }

    @Override
    public void initParameter() {
        super.initParameter();
        mLoginPresenter = new LoginPresenter(this);
    }

    @Override
    public void findView() {
        super.findView();
        assignViews();
    }

    @Override
    public void setUpView() {
        super.setUpView();

        setUpWebView();

        Glide.with(this)
                .load(R.mipmap.loading_login)
                .asGif()
                .dontAnimate()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)// DiskCacheStrategy.NONE
                .into(mViewLoading);

        setStatusBarColor(getResources().getColor(R.color.colorPink3));
        fitStatusBarEnble(true);
    }

    @Override
    protected boolean getTitleBarVisibility() {
        return true;
    }

    @Override
    protected String getTitleText() {
        return getResources().getString(R.string.title_login);
    }

    private void assignViews() {
        mViewLoading = (ImageView) findViewById(R.id.view_loading);
        mWebView = (WebView) findViewById(R.id.web_view);
        mTextHint = (TextView) findViewById(R.id.text_hint);
    }

    private void setUpWebView() {
        mWebView.setBackgroundColor(Color.TRANSPARENT);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.loadUrl(DribbbleOAuth.Login_Url);
    }

    @Override
    public void onLoginStart() {
        mTextHint.setText(getResources().getString(R.string.logging));
        mWebView.setVisibility(View.GONE);
    }

    @Override
    public void onLoginSuccess() {
        ToastUtil.showToast(mAcSelf, "登录成功", ToastUtil.DURATION_SHORT);
        mAcSelf.finish();
    }

    @Override
    public void onNetworkException() {
        mWebView.setVisibility(View.VISIBLE);
    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Uri uri = Uri.parse(url);
            String scheme = uri.getScheme();
            if (mLoginPresenter.checkLoginExecute(scheme)) {
                mLoginPresenter.getOAuthUserInfo(url);
            } else {
                view.loadUrl(url);
            }
            return true;
        }
    }
}
