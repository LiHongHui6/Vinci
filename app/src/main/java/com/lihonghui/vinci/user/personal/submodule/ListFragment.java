package com.lihonghui.vinci.user.personal.submodule;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lihonghui.vinci.DribbbleOAuth;
import com.lihonghui.vinci.R;
import com.lihonghui.vinci.base.BaseFragment;
import com.lihonghui.vinci.common.OnRecycleViewScrollListener;
import com.lihonghui.vinci.common.RxBus.RxBus;
import com.lihonghui.vinci.common.RxBus.RxBusSubscriber;
import com.lihonghui.vinci.common.RxBus.RxSubscriptions;
import com.lihonghui.vinci.common.RxBus.event.UserLoginEvent;
import com.lihonghui.vinci.common.RxBus.event.UserLogoutEvent;
import com.lihonghui.vinci.common.utils.ToastUtil;
import com.lihonghui.vinci.common.widget.adapter.BaseRecyclerViewAdapter;
import com.lihonghui.vinci.user.personal.submodule.mvp.ListContact;

import java.util.List;

import rx.Subscription;

/**
 * Created by yq05481 on 2016/11/23.
 */

public abstract class ListFragment<T> extends BaseFragment implements ListContact.View<T> {

    protected RecyclerView recyclerView;
    protected BaseRecyclerViewAdapter adapter;
    protected SwipeRefreshLayout layoutRefresh;
    protected ListContact.Presenter mPresenter;

    private Subscription logoutSubs;
    private Subscription loginSubs;

    @Override
    public int getContentViewID() {
        return R.layout.fragment_type;
    }

    @Override
    public void findView(View contentView) {
        super.findView(contentView);
        layoutRefresh = (SwipeRefreshLayout) contentView.findViewById(R.id.layout_refresh);
        recyclerView = (RecyclerView) contentView.findViewById(R.id.test_recycler_view);
    }

    @Override
    public void initParam() {
        mPresenter = getPresenter();
        adapter = new MyAdapter(null, mActivity);
    }



    @Override
    public void initData() {
        super.initData();
        if(DribbbleOAuth.isUserLogin() && !layoutRefresh.isRefreshing()){
            mPresenter.initData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        if (layoutRefresh.isRefreshing()) {
            layoutRefresh.setRefreshing(false);
        }
        super.onDestroyView();
    }

    @Override
    public void setUpView() {
        super.setUpView();
        layoutRefresh.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorWhite));
        layoutRefresh.setColorSchemeColors(0xFFEA4C89);
        layoutRefresh.setOnRefreshListener(new RefreshListener());

        adapter.addOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, Object data) {
               subOnItemClick(v,position,data);
            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new OnRecycleViewScrollListener() {
            @Override
            public void onScrollBottom() {
                loadMore();
            }
        });

        if(DribbbleOAuth.isUserLogin()){
            layoutRefresh.setEnabled(true);
        }else{
            layoutRefresh.setEnabled(false);
        }
    }

    public void onRefreshLayoutRefresh() {
        mPresenter.refresh();
    }

    @Override
    public void onInitializing() {
        layoutRefresh.setRefreshing(true);
    }

    @Override
    public void onInitFinish() {
        layoutRefresh.setRefreshing(false);
    }

    @Override
    public void onInitData(List data) {
        adapter.setDataAndRefresh(data);
    }

    @Override
    public void onInitDataError() {

    }

    @Override
    public void onRefreshing() {
        layoutRefresh.setRefreshing(true);
    }

    @Override
    public void onRefreshFinish() {
        layoutRefresh.setRefreshing(false);
    }

    @Override
    public void onRefreshData(List data) {
        adapter.setDataAndRefresh(data);
    }

    @Override
    public void onLoadingMore() {
        layoutRefresh.setRefreshing(true);
    }

    @Override
    public void onLoadMoreFinish() {
        layoutRefresh.setRefreshing(false);
    }

    @Override
    public void onLoadMoreData(List data) {
        adapter.addDataAndRefresh(data);
    }

    @Override
    public void onError(String msg) {
        ToastUtil.showToast(mActivity,msg,ToastUtil.DURATION_SHORT);
        layoutRefresh.setRefreshing(false);
    }

    @Override
    public void onLoadAll() {
        if(layoutRefresh.isRefreshing()){
            layoutRefresh.setRefreshing(false);
        }
        ToastUtil.showToast(mActivity,getResources().getString(R.string.load_all),ToastUtil.DURATION_SHORT);
    }

    @Override
    public void subscribeEvent() {
        super.subscribeEvent();
        RxSubscriptions.remove(logoutSubs);
        logoutSubs = RxBus.getDefault().toObservable(UserLogoutEvent.class).subscribe(new RxBusSubscriber<UserLogoutEvent>() {
            @Override
            protected void onEvent(UserLogoutEvent userLogoutEvent) {
                if (adapter != null) {
                    adapter.setDataAndRefresh(null);
                }
            }
        });

        RxSubscriptions.remove(loginSubs);
        loginSubs = RxBus.getDefault().toObservable(UserLoginEvent.class).subscribe(new RxBusSubscriber<UserLoginEvent>() {
            @Override
            protected void onEvent(UserLoginEvent userLoginEvent) {
                if (!layoutRefresh.isRefreshing()) {
                    mPresenter.initData();
                }
            }
        });
    }

    @Override
    public void unSubscribeEvent() {
        super.unSubscribeEvent();
        RxSubscriptions.remove(loginSubs);
        RxSubscriptions.remove(logoutSubs);
    }

    protected void loadMore() {
        if(!layoutRefresh.isRefreshing()){
            mPresenter.loadMore();
        }
    }

    protected abstract void subOnItemClick(View v, int position, Object data);
    protected abstract ListContact.Presenter getPresenter() ;
    protected abstract int getItemViewResID();
    protected abstract void setUpItemView(T data,BaseRecyclerViewAdapter.CommonViewHolder holder);

    class MyAdapter extends BaseRecyclerViewAdapter<T> {
        MyAdapter(List<T> dataList, Context context) {
            super(dataList, context);
        }

        @Override
        public int getResID() {
            return getItemViewResID();
        }

        @Override
        public void onSubclassBindViewHolder(CommonViewHolder holder, int position) {
            T data = (T) dataList.get(position);
            setUpItemView(data,holder);
        }
    }

    class RefreshListener implements SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            onRefreshLayoutRefresh();
        }
    }
}
