package com.lihonghui.vinci;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by yq05481 on 2016/10/27.
 */

public class ExperimentActivity extends AppCompatActivity{
    @Inject
    public RequestManager requestManager;

    private ImageView imageView;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment);

        imageView = (ImageView)findViewById(R.id.iv_test);

        webView = (WebView)findViewById(R.id.wbv);
        webView.setBackgroundColor(Color.TRANSPARENT);
        setUpWebView();

        AppComponent appComponent = DaggerAppComponent.builder().appMoudle(new AppMoudle(this)).build();
        appComponent.inject(this);

        if(requestManager != null){
            requestManager.load("http://static.oschina.net/uploads/space/2015/0627/083244_BPCe_2306979.png").into(imageView);
        }else{
            Log.e("lhh","Dagger2 配置失败");
        }
    }



    private void setUpWebView(){
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

               // LogUtil.printLog("WebView 正在加载："+newProgress);
            }
        });

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
               // super.onPageFinished(view, url);

            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
              //  super.onPageCommitVisible(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Uri uri = Uri.parse(failingUrl);
                String scheme = uri.getScheme();
               // view.setVisibility(View.GONE);
                if(scheme != null){
                    if(DribbbleOAuth.Redirect_URL.contains(scheme)){
                        String errorParameter = uri.getQueryParameter("error");
                        String codeParameter = uri.getQueryParameter("code");
                        if(codeParameter != null){
                            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://dribbble.com/").addConverterFactory(GsonConverterFactory.create()).build();
                            GetAccessTokenApi api = retrofit.create(GetAccessTokenApi.class);
                            Call<ReponseJson> aToken = api.getAToken(DribbbleOAuth.Client_ID,DribbbleOAuth.Client_Secret,codeParameter);
                            aToken.enqueue(new Callback<ReponseJson>() {
                                @Override
                                public void onResponse(Call<ReponseJson> call, Response<ReponseJson> response) {
                                    ReponseJson body = response.body();
                                    if(body != null){
                                    }else{
                                    }
                                }

                                @Override
                                public void onFailure(Call<ReponseJson> call, Throwable t) {
                                    Log.e("lhh","onFailure");
                                }
                            });
                        }
                    }else{
                    }
                }else{
                }

            }
        });

        webView.loadUrl(DribbbleOAuth.Login_Url);
    }

    public interface GetAccessTokenApi{
        @FormUrlEncoded
        @POST("/oauth/token")
        Call<ReponseJson> getAToken(@Field("client_id") String client_id,@Field("client_secret") String client_secret,@Field("code") String code);
    }
    class ReponseJson{
        String access_token;
        String token_type;
        String scope;

        @Override
        public String toString() {
            return "ReponseJson{" +
                    "access_token='" + access_token + '\'' +
                    ", token_type='" + token_type + '\'' +
                    ", scope='" + scope + '\'' +
                    '}';
        }
    }
}
