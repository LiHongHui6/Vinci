package com.lihonghui.vinci.home.grid;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lihonghui.vinci.R;
import com.lihonghui.vinci.base.BaseFragment;
import com.lihonghui.vinci.common.OnRecycleViewScrollListener;
import com.lihonghui.vinci.common.utils.ToastUtil;
import com.lihonghui.vinci.common.widget.adapter.BaseRecyclerViewAdapter;
import com.lihonghui.vinci.data.Entity.Shot;
import com.lihonghui.vinci.detail.DetailActivity;
import com.lihonghui.vinci.home.mvp.GridPresenter;
import com.lihonghui.vinci.home.mvp.HomeContact;
import com.socks.library.KLog;

import java.util.List;

public class GridFragment extends BaseFragment implements HomeContact.View {
    private RecyclerView shotList;
    private ShotAdapter adapter;
    private SwipeRefreshLayout layoutRefresh;
    private HomeContact.Presenter mPresenter;

    @Override
    public int getContentViewID() {
        return R.layout.fragment_grid;
    }

    @Override
    public void findView(View contentView) {
        super.findView(contentView);
        shotList = (RecyclerView) contentView.findViewById(R.id.grid_list);
        layoutRefresh = (SwipeRefreshLayout) contentView.findViewById(R.id.layout_refresh);
    }

    @Override
    public void initParam() {
        super.initParam();
        mPresenter = new GridPresenter(this);
        adapter = new ShotAdapter(null, mActivity);
        adapter.addOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, Object data) {
                Shot shot = (Shot) data;
                View thumbView = adapter.getThumbView();

                DetailActivity.showByShareElements(mActivity, thumbView, shot);


            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        });
    }

    @Override
    public void setUpView() {
        super.setUpView();
        layoutRefresh.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorWhite));
        layoutRefresh.setColorSchemeColors(0xFFEA4C89);
        layoutRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refresh();
            }
        });

        shotList.setLayoutManager(new GridLayoutManager(mActivity, 2));
        shotList.setAdapter(adapter);
        shotList.addOnScrollListener(new OnRecycleViewScrollListener() {
            @Override
            public void onScrollBottom() {
                if(!layoutRefresh.isRefreshing()){
                    mPresenter.loadMore();
                }
            }
        });

    }

    @Override
    public void initData() {
        super.initData();
        mPresenter.getData();
    }

    @Override
    public void onStartGetData() {
        if (!layoutRefresh.isRefreshing()) {
            layoutRefresh.setRefreshing(true);
        }
    }

    @Override
    public void onGetData(List<Shot> datas) {
        adapter.setDataAndRefresh(datas);
    }

    @Override
    public void onLoadMore(List<Shot> datas) {
        adapter.addDataAndRefresh(datas);
    }

    @Override
    public void onRefresh(List<Shot> datas) {
        adapter.setDataAndRefresh(datas);
    }

    @Override
    public void onError(String errorMsg) {
        if (layoutRefresh.isRefreshing()) {
            layoutRefresh.setRefreshing(false);
        }
        KLog.e(errorMsg);
        ToastUtil.showToast(mActivity, errorMsg, ToastUtil.DURATION_SHORT);
    }

    @Override
    public void onFinish() {
        layoutRefresh.setRefreshing(false);
    }
}
