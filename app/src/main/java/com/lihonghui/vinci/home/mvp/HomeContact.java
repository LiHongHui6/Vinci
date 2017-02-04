package com.lihonghui.vinci.home.mvp;

import com.lihonghui.vinci.data.Entity.Shot;

import java.util.List;

/**
 * Created by yq05481 on 2017/1/20.
 */

public interface HomeContact {
    interface View{
        void onStartGetData();
        void onGetData(List<Shot> datas);
        void onLoadMore(List<Shot> datas);
        void onRefresh(List<Shot> datas);
        void onError(String errorMsg);
        void onFinish();
    }

    interface Presenter{
        void getData();
        void refresh();
        void loadMore();
    }
}
