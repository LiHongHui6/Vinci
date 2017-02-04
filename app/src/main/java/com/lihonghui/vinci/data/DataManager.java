package com.lihonghui.vinci.data;

import com.lihonghui.vinci.DribbbleOAuth;
import com.lihonghui.vinci.data.Entity.Bucket;
import com.lihonghui.vinci.data.Entity.BucketModel;
import com.lihonghui.vinci.data.Entity.Like;
import com.lihonghui.vinci.data.Entity.Shot;
import com.lihonghui.vinci.data.Entity.User;
import com.lihonghui.vinci.data.local.UserLocalData;
import com.lihonghui.vinci.data.remote.NetWorkManager;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yq05481 on 2016/12/7.
 */

public class DataManager {
    private static DataManager mDataManager;
    private NetWorkManager mNetWorkManager;

    private DataManager() {
        mNetWorkManager = NetWorkManager.getInstance();
    }

    public static DataManager getInstance() {
        if (mDataManager == null) {
            synchronized (DataManager.class) {
                if (mDataManager == null) {
                    mDataManager = new DataManager();
                }
            }
        }
        return mDataManager;
    }

    public NetWorkManager getNetWorkManager() {
        return mNetWorkManager;
    }

    public void getOAuthUserInfo(String requestCode, Subscriber<User> subscriber) {
        mNetWorkManager.getOAuthUserInfo(requestCode, subscriber);
    }

    public void saveOAuthUserInfo(User user) {
        UserLocalData.saveUserInfo(user);
    }

    public void getOAuthUserLikes(int page, Subscriber<List<Like>> subscriber) {
        mNetWorkManager.getOAuthUserLikes(page, subscriber);
    }

//    public void getAttachmentList(int id,Subscriber<List<Attachment>> subscriber){
//        mNetWorkManager.getAttachmentList(id,subscriber);
//    }
//
//    public void getCommentList(int id,Subscriber<List<Comment>> subscriber){
//        mNetWorkManager.getCommentList(id,subscriber);
//    }

    public void getBuckets(final int page, Subscriber<List<BucketModel>> subscriber) {
        final List<BucketModel> bucketModels = new ArrayList<>();

        mNetWorkManager.getOAuthUserBuckets(page)
                .flatMap(new Func1<List<Bucket>, Observable<Bucket>>() {
                    @Override
                    public Observable<Bucket> call(List<Bucket> buckets) {
                        KLog.e("buckets size " + buckets.size());
                        return Observable.from(buckets);
                    }
                })
                .flatMap(new Func1<Bucket, Observable<BucketModel>>() {
                    @Override
                    public Observable<BucketModel> call(final Bucket bucket) {
                        return mNetWorkManager.getBucketShots(bucket.getId(), 1).map(new Func1<List<Shot>, BucketModel>() {
                            @Override
                            public BucketModel call(List<Shot> shots) {
                                KLog.e("Shot size " + shots.size() + " bucketID: "+bucket.getId() + "  token:"+ DribbbleOAuth.getToken() + "  page: "+page);
                                Shot shot = null;
                                if(shots.size() != 0){
                                    shot = shots.get(0);
                                }
                                return new BucketModel(shot, bucket);
                            }
                        });
                    }
                })
                .map(new Func1<BucketModel, List<BucketModel>>() {
                    @Override
                    public List<BucketModel> call(BucketModel bucketModel) {
                        bucketModels.add(bucketModel);
                        return bucketModels;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
