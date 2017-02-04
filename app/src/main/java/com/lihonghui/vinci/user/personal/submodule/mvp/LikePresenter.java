package com.lihonghui.vinci.user.personal.submodule.mvp;

import com.lihonghui.vinci.data.DataManager;
import com.lihonghui.vinci.data.Entity.Like;
import com.socks.library.KLog;

import java.util.List;

import rx.Subscriber;

/**
 * Created by yq05481 on 2016/12/9.
 */

public class LikePresenter implements ListContact.Presenter {
    private DataManager mDataManager;
    private ListContact.View mView;
    private int page = 1;
    private int LOAD_ALL = -1;

    public LikePresenter(ListContact.View view) {
        this.mDataManager = DataManager.getInstance();
        this.mView = view;
    }


    @Override
    public void initData() {
        page = 1;
        getData(page, new Subscriber<List<Like>>() {
            @Override
            public void onCompleted() {
                mView.onInitFinish();
            }

            @Override
            public void onError(Throwable e) {
                mView.onError(e.getMessage());
            }

            @Override
            public void onNext(List<Like> likes) {
                mView.onInitData(likes);
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
        getData(page, new Subscriber<List<Like>>() {
            @Override
            public void onCompleted() {
                mView.onRefreshFinish();
            }

            @Override
            public void onError(Throwable e) {
                mView.onError(e.getMessage());
            }

            @Override
            public void onNext(List<Like> likes) {
                mView.onRefreshData(likes);
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

        getData(page + 1, new Subscriber<List<Like>>() {
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
            public void onNext(List<Like> likes) {
                KLog.e("Size:"+likes.size());
                if(likes.size() == 0){
                    page = LOAD_ALL;
                }
                mView.onLoadMoreData(likes);
            }

            @Override
            public void onStart() {
                mView.onLoadingMore();
            }
        });
    }

    private void getData(int page, Subscriber<List<Like>> subscriber){
        mDataManager.getOAuthUserLikes(page,subscriber);
    }
}
