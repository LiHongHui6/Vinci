package com.lihonghui.vinci.user.personal.submodule.mvp;

import com.lihonghui.vinci.data.DataManager;
import com.lihonghui.vinci.data.Entity.Shot;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yq05481 on 2016/12/20.
 */

public class BucketShotsPresenter implements ListContact.Presenter {
    private DataManager mDataManager;
    private ListContact.View mView;
    private int mBucketShot;
    private int page = 1;
    private int LOAD_ALL = -1;

    public BucketShotsPresenter(ListContact.View view, int bucketShot) {
        this.mDataManager = DataManager.getInstance();
        this.mView = view;
        this.mBucketShot = bucketShot;
    }

    @Override
    public void initData() {
        page = 1;
        getData(page, new Subscriber<List<Shot>>() {
            @Override
            public void onCompleted() {
                mView.onInitFinish();
            }

            @Override
            public void onError(Throwable e) {
                mView.onError(e.getMessage());
            }

            @Override
            public void onNext(List<Shot> shots) {
                mView.onInitData(shots);
            }

            @Override
            public void onStart() {
                mView.onInitializing();
            }
        });
    }

    @Override
    public void refresh() {
        page = 1;
        getData(page, new Subscriber<List<Shot>>() {
            @Override
            public void onCompleted() {
                mView.onRefreshFinish();
            }

            @Override
            public void onError(Throwable e) {
                mView.onError(e.getMessage());
            }

            @Override
            public void onNext(List<Shot> shots) {
                mView.onRefreshData(shots);
            }

            @Override
            public void onStart() {
                mView.onRefreshing();
            }
        });
    }

    @Override
    public void loadMore() {
        if(page == LOAD_ALL){
            mView.onLoadAll();
            return;
        }

        getData(page + 1, new Subscriber<List<Shot>>() {
            @Override
            public void onCompleted() {
                if(page == LOAD_ALL){
                    mView.onLoadAll();
                    return;
                }
                page ++;
                mView.onLoadMoreFinish();
            }

            @Override
            public void onError(Throwable e) {
                mView.onError(e.getMessage());
            }

            @Override
            public void onNext(List<Shot> shots) {
                if(shots.size() == 0){
                    page = LOAD_ALL;
                }
                mView.onLoadMoreData(shots);
            }

            @Override
            public void onStart() {
                mView.onLoadingMore();
            }
        });
    }

    private void getData(int page, Subscriber<List<Shot>> subscriber) {
        mDataManager.getNetWorkManager().getBucketShots(mBucketShot, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
