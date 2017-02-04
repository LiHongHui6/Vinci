package com.lihonghui.vinci;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yq05481 on 2016/10/25.
 */

@Module
public class AppMoudle {
    private Context mContext;

    public AppMoudle(Context mContext) {
        this.mContext = mContext;
    }

    @Provides
    public Retrofit provideRetrofit(){
        Retrofit retrofit =  new Retrofit.Builder().baseUrl("http://www.baidu.com/").addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }

    @Provides
    public RequestManager provideGlide(){
        return Glide.with(mContext);
    }
}
