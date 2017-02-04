package com.lihonghui.vinci.home.mvp;

/**
 * Created by yq05481 on 2017/1/24.
 */

public class GridPresenter extends HomePresenter {
    public GridPresenter(HomeContact.View mView) {
        super(mView);
    }

    @Override
    public void onLoadingMore() {
        mView.onStartGetData();
    }
}
