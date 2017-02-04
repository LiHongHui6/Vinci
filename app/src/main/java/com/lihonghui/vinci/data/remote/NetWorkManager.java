package com.lihonghui.vinci.data.remote;

import com.lihonghui.vinci.DribbbleOAuth;
import com.lihonghui.vinci.data.Entity.Attachment;
import com.lihonghui.vinci.data.Entity.Bucket;
import com.lihonghui.vinci.data.Entity.Comment;
import com.lihonghui.vinci.data.Entity.Like;
import com.lihonghui.vinci.data.Entity.Shot;
import com.lihonghui.vinci.data.Entity.User;

import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class NetWorkManager {
    private ApiManager.TokenApi tokenApi;
    private ApiManager.AppApi appApi;

    private static NetWorkManager apiManager;

    private NetWorkManager() {
        Retrofit baseRetrofit = RetrofitManager.createRetrofit(RetrofitManager.BASE_URL);
        Retrofit appRetrofit = RetrofitManager.createRetrofit(RetrofitManager.API_BASE_URL);
        this.tokenApi = baseRetrofit.create(ApiManager.TokenApi.class);
        this.appApi = appRetrofit.create(ApiManager.AppApi.class);
    }

    public static NetWorkManager getInstance() {
        if (apiManager == null) {
            synchronized (NetWorkManager.class) {
                if (apiManager == null) {
                    apiManager = new NetWorkManager();
                }
            }
        }
        return apiManager;
    }

    public void getOAuthUserInfo(String requestCode, Subscriber<User> subscriber) {
        Observable<ApiManager.TokenResponseJson> observable = tokenApi.getAToken(DribbbleOAuth.Client_ID, DribbbleOAuth.Client_Secret, requestCode);
        observable.flatMap(new Func1<ApiManager.TokenResponseJson, Observable<User>>() {
            @Override
            public Observable<User> call(ApiManager.TokenResponseJson tokenResponseJson) {
                if (tokenResponseJson.access_token == null) {
                    Observable.error(null);
                }
                DribbbleOAuth.saveToken(tokenResponseJson.access_token);
                return appApi.getOAuthUserInfo(tokenResponseJson.access_token);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getOAuthUserLikes(int page, Subscriber<List<Like>> subscriber) {
        Observable<List<Like>> observable = appApi.getOAuthUserLikes(DribbbleOAuth.getToken(), page);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Observable<List<Attachment>> getAttachmentList(int id){
        return appApi.getAttachmentList(id,DribbbleOAuth.getToken());
    }

    public  Observable<List<Comment>> getCommentList(int id,int page){
        return appApi.getCommentList(id,DribbbleOAuth.getToken(),page);
    }

    public Observable<Shot> getShot(int id){
        return appApi.getShot(id,DribbbleOAuth.getToken());
    }

    public Observable<Void> checkIsLike(int id){
        return appApi.checkIsLike(id,DribbbleOAuth.getToken());
    }

    public Observable<Void> likeShot(int id){
        return appApi.likeShot(id,DribbbleOAuth.getToken());
    }

    public Observable<Void> unLikeShot(int id){
        return appApi.unlikeShot(id,DribbbleOAuth.getToken());
    }

    public Observable<List<Bucket>> getOAuthUserBuckets(int page){
        return appApi.getOAuthUserBucket(DribbbleOAuth.getToken(),page);
    }

    public Observable<List<Shot>> getBucketShots(int bucketID,int page){
        return appApi.getBucketShots(bucketID,DribbbleOAuth.getToken(),page);
    }

    public  Observable<Bucket> createBucket(String title,String describe){
        return appApi.createBucket(DribbbleOAuth.getToken(),title,describe);
    }

    public Observable<List<Shot>> getShots(int page) {
        return appApi.getShots(DribbbleOAuth.getToken(),page);
    }
}
