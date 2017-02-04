package com.lihonghui.vinci.detail.bucket;

import com.lihonghui.vinci.data.DataManager;
import com.lihonghui.vinci.data.Entity.Bucket;
import com.lihonghui.vinci.data.Entity.BucketModel;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yq05481 on 2016/12/21.
 */

public class BucketDialogPresenter implements BucketContact.Presenter {
    private DataManager mDataManager;
    private BucketContact.View mView;
    private int page = 1;
    private int LOAD_ALL = -1;

    public BucketDialogPresenter(BucketContact.View view) {
        this.mDataManager = DataManager.getInstance();
        this.mView = view;
    }


    @Override
    public void initData() {
        page = 1;
        getData(page, new BucketDialogPresenter.BucketModelSubscriber() {
            @Override
            void onCompleted(List<BucketModel> bucketModels) {
                mView.onInitData(bucketModels);
                mView.onInitFinish();
            }

            @Override
            public void onError(Throwable e) {
                mView.onError(e.getMessage());
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

        getData(page, new BucketDialogPresenter.BucketModelSubscriber() {
            @Override
            void onCompleted(List<BucketModel> bucketModels) {
                mView.onRefreshData(bucketModels);
                mView.onRefreshFinish();
            }

            @Override
            public void onError(Throwable e) {
                mView.onError(e.getMessage());
            }

            @Override
            public void onStart() {
                mView.onRefreshing();
            }

        });
    }

    @Override
    public void loadMore() {
        if (page == LOAD_ALL) {
            mView.onLoadAll();
            return;
        }

        getData(page + 1, new BucketDialogPresenter.BucketModelSubscriber() {
            @Override
            void onCompleted(List<BucketModel> bucketModels) {
                if (bucketModels.size() == 0) {
                    page = LOAD_ALL;
                }
                mView.onLoadMoreData(bucketModels);

                if (page == LOAD_ALL) {
                    mView.onLoadAll();
                    return;
                }
                page++;
                mView.onLoadMoreFinish();
            }

            @Override
            public void onError(Throwable e) {
                mView.onError(e.getMessage());
            }

            @Override
            public void onStart() {
                mView.onLoadingMore();
            }
        });
    }

    @Override
    public void createBucket(String title,String describe){
        mDataManager.getNetWorkManager().createBucket(title,describe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bucket>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(Bucket bucket) {
                        mView.onCreateBucket(bucket);
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        mView.onStartCreateBucket();
                    }
                });
    }

    private void getData(int page, BucketDialogPresenter.BucketModelSubscriber subscriber) {
        mDataManager.getBuckets(page,subscriber);
    }

    abstract class BucketModelSubscriber extends Subscriber<List<BucketModel>> {
        List<BucketModel> mBucketModels;

        @Override
        public void onCompleted() {
            onCompleted(mBucketModels);
        }

        @Override
        public void onNext(List<BucketModel> bucketModels) {
            mBucketModels = bucketModels;
        }

        abstract void onCompleted(List<BucketModel> bucketModels);
    }
}
