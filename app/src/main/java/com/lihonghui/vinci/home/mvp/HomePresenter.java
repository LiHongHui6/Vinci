package com.lihonghui.vinci.home.mvp;

import com.lihonghui.vinci.data.DataManager;
import com.lihonghui.vinci.data.Entity.Shot;
import com.socks.library.KLog;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomePresenter implements HomeContact.Presenter {
    private DataManager mDataManager;
    protected HomeContact.View mView;
    private int page = 1;
    private final int LOAD_ALL = -1;
    private boolean isLike;

    public HomePresenter(HomeContact.View mView) {
        this.mDataManager = DataManager.getInstance();;
        this.mView = mView;
    }

    @Override
    public void getData() {
        page = 1;
        mDataManager.getNetWorkManager().getShots(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Shot>>() {
                    @Override
                    public void onCompleted() {
                        mView.onFinish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Shot> shots) {
                        mView.onGetData(shots);
                    }

                    @Override
                    public void onStart() {
                        mView.onStartGetData();
                    }
                });
    }

    @Override
    public void refresh() {
        KLog.e("refresh");
        getData();
    }

    @Override
    public void loadMore() {
        if(page == LOAD_ALL){
            return;
        }

        mDataManager.getNetWorkManager().getShots(page + 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Shot>>() {
                    @Override
                    public void onCompleted() {
                        mView.onFinish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Shot> shots) {
                        if(shots.size() != 0){
                            mView.onLoadMore(shots);
                            page ++;
                        }else{
                            page = LOAD_ALL;
                        }
                    }

                    @Override
                    public void onStart() {
                        onLoadingMore();
                    }
                })
        ;
    }

    public void onLoadingMore(){};
}
