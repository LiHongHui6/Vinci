package com.lihonghui.vinci.user.personal.submodule.mvp;

import java.util.List;

/**
 * Created by yq05481 on 2016/12/9.
 */

public interface ListContact {
    interface View<T>{
        void onInitializing();
        void onInitFinish();
        void onInitData(List<T> data);
        void onInitDataError();

        void onRefreshing();
        void onRefreshFinish();
        void onRefreshData(List<T> data);

        void onLoadingMore();
        void onLoadMoreFinish();
        void onLoadMoreData(List<T> data);

        void onError(String msg);

        void onLoadAll();

    }
    interface Presenter{
        void initData();
        void refresh();
        void loadMore();
    }
}
