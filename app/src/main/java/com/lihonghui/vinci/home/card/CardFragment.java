package com.lihonghui.vinci.home.card;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.lihonghui.vinci.R;
import com.lihonghui.vinci.base.BaseFragment;
import com.lihonghui.vinci.common.SlideCard.CardSlidePanel;
import com.lihonghui.vinci.common.utils.ToastUtil;
import com.lihonghui.vinci.data.Entity.Shot;
import com.lihonghui.vinci.home.mvp.CardPresenter;
import com.lihonghui.vinci.home.mvp.HomeContact;
import com.socks.library.KLog;

import java.util.List;

public class CardFragment extends BaseFragment implements HomeContact.View {
    private CardSlidePanel slidePanel;
    private SwipeRefreshLayout refreshLayout;
    private HomeContact.Presenter mPresenter;
    private List<Shot> shots;

    @Override
    public int getContentViewID() {
        return R.layout.fragment_card;
    }

    @Override
    public void initParam() {
        super.initParam();
        mPresenter = new CardPresenter(this);
    }

    @Override
    public void findView(View contentView) {
        super.findView(contentView);
        slidePanel = (CardSlidePanel) contentView.findViewById(R.id.slide_panel);
        refreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.refresh_layout);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter.getData();
    }

    @Override
    public void setUpView() {
        super.setUpView();
        refreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorWhite));
        refreshLayout.setColorSchemeColors(0xFFEA4C89);

        slidePanel.setVisibility(View.GONE);
        slidePanel.setCardSwitchListener(new CardSlidePanel.CardSwitchListener() {
            @Override
            public void onShow(int index) {
                KLog.e("index: "+index);
            }

            @Override
            public void onCardVanish(int index, int type) {
                if (index == (shots.size() / 2) - 1) {
                    mPresenter.loadMore();
                }
            }

            @Override
            public void onItemClick(View cardImageView, int index) {
                KLog.e("onItemClick: " + index);
            }

            @Override
            public void onEmpty() {
                showRefrshLayout();
                mPresenter.loadMore();
                if (refreshLayout.isRefreshing()) {
                    slidePanel.fillData(shots);
                    slidePanel.refresh();
                    hideRefreshLayout();
                }
            }

        });
    }


    @Override
    public void onStartGetData() {
        showRefrshLayout();
    }

    @Override
    public void onGetData(List<Shot> datas) {
        KLog.e("data size: " + datas.size());
        shots = datas;
        slidePanel.fillData(shots);
        slidePanel.setVisibility(View.VISIBLE);
        refreshLayout.setEnabled(false);
    }

    @Override
    public void onLoadMore(List<Shot> datas) {
        shots.addAll(datas);
        if (refreshLayout.isRefreshing()) {
            slidePanel.fillData(shots);
            slidePanel.refresh();
            hideRefreshLayout();
        }
    }

    @Override
    public void onRefresh(List<Shot> datas) {

    }

    @Override
    public void onError(String errorMsg) {
        if(refreshLayout.isRefreshing()){
            refreshLayout.setRefreshing(false);
        }
        ToastUtil.showToast(mActivity,errorMsg,ToastUtil.DURATION_SHORT);
    }

    @Override
    public void onFinish() {
        hideRefreshLayout();
    }

    private void showRefrshLayout() {
        refreshLayout.setEnabled(true);
        refreshLayout.setRefreshing(true);
    }

    private void hideRefreshLayout() {
        refreshLayout.setRefreshing(false);
        refreshLayout.setEnabled(false);
    }
}
