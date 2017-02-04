package com.lihonghui.vinci.home;

import android.view.View;

import com.lihonghui.vinci.R;
import com.lihonghui.vinci.base.BaseFragment;
import com.lihonghui.vinci.common.widget.adapter.BaseFragmentPagerAdapter;
import com.lihonghui.vinci.common.widget.view.DisableViewPager;
import com.lihonghui.vinci.home.card.CardFragment;
import com.lihonghui.vinci.home.grid.GridFragment;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {

    private View mIconLayoutSwitch;
    private View mIconSearch;
    private DisableViewPager viewPager;
    private List<BaseFragment> fragmentList;
    @Override
    public int getContentViewID() {
        return R.layout.fragment_home;
    }

    @Override
    public void findView(View contentView) {
        super.findView(contentView);
        mIconLayoutSwitch = contentView.findViewById(R.id.icon_layout_switch);
        mIconSearch = contentView.findViewById(R.id.icon_search);

        mIconLayoutSwitch.setOnClickListener(this);
        mIconSearch.setOnClickListener(this);

        viewPager = (DisableViewPager) contentView.findViewById(R.id.view_pager);
    }

    @Override
    public void initParam() {
        super.initParam();
        CardFragment mCardFragment = new CardFragment();
        GridFragment mGridFragment = new GridFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(mCardFragment);
        fragmentList.add(mGridFragment);
    }

    @Override
    public void setUpView() {
        super.setUpView();
        viewPager.setAdapter(new BaseFragmentPagerAdapter(getFragmentManager(), fragmentList));
        viewPager.setCurrentItem(0,false);
    }



    private final int SWITCH_STATUS_CARD = 0;
    private final int SWITCH_STATUS_GRID = 1;
    private int SWITCH_STATUS = SWITCH_STATUS_CARD;

    private void changeListLayout() {
        changeSwitchStatus();
        changeSwitchIcon();
        change();
    }

    private void changeSwitchStatus() {

        if (SWITCH_STATUS == SWITCH_STATUS_CARD) {
            SWITCH_STATUS = SWITCH_STATUS_GRID;
        } else {
            SWITCH_STATUS = SWITCH_STATUS_CARD;
        }

    }

    private void changeSwitchIcon() {

        if (SWITCH_STATUS == SWITCH_STATUS_CARD) {
            mIconLayoutSwitch.setBackgroundResource(R.mipmap.switch_a);
        } else {
            mIconLayoutSwitch.setBackgroundResource(R.mipmap.switch_b);
        }

    }

    private void change() {
        showPage();
    }

    private void showPage() {

        if (SWITCH_STATUS == SWITCH_STATUS_CARD) {
            viewPager.setCurrentItem(0,false);
        } else if (SWITCH_STATUS == SWITCH_STATUS_GRID) {
            viewPager.setCurrentItem(1,false);
        }

    }



    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view == mIconSearch) {
            KLog.e("搜索");
        } else if (view == mIconLayoutSwitch) {
            changeListLayout();
        }
    }
}
