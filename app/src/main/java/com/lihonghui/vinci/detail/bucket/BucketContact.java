package com.lihonghui.vinci.detail.bucket;

import com.lihonghui.vinci.data.Entity.Bucket;
import com.lihonghui.vinci.data.Entity.BucketModel;

import java.util.List;

/**
 * Created by yq05481 on 2016/12/21.
 */

public interface BucketContact {
    interface View{
        void onStartCreateBucket();
        void onCreateBucket(Bucket bucket);

        void onInitializing();
        void onInitFinish();
        void onInitData(List<BucketModel> data);
        void onInitDataError();

        void onRefreshing();
        void onRefreshFinish();
        void onRefreshData(List<BucketModel> data);

        void onLoadingMore();
        void onLoadMoreFinish();
        void onLoadMoreData(List<BucketModel> data);

        void onError(String msg);

        void onLoadAll();
    }

    interface Presenter{
        void createBucket(String title,String describe);

        void initData();
        void refresh();
        void loadMore();
    }
}
