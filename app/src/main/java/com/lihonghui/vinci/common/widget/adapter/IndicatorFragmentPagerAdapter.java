package com.lihonghui.vinci.common.widget.adapter;

import android.support.v4.app.FragmentManager;
import android.view.View;

import java.util.List;

/**
 * Created by yq05481 on 2016/11/12.
 */

public class IndicatorFragmentPagerAdapter extends BaseFragmentPagerAdapter implements TabIndicator{
    private List<TabViewModel> tabViewModels;
    public IndicatorFragmentPagerAdapter(FragmentManager fm, List fragmentList) {
        super(fm, fragmentList);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabViewModels.get(position).getTabTitle();
    }

    @Override
    public void addTabTabView(List<TabViewModel> tabViewModels) {
        this.tabViewModels = tabViewModels;
    }

    @Override
    public View getTabView() {
        return null;
    }
}
