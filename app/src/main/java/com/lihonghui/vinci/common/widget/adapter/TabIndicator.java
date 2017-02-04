package com.lihonghui.vinci.common.widget.adapter;

import android.view.View;

import java.util.List;

/**
 * Created by yq05481 on 2016/11/12.
 */

public interface TabIndicator {
    void addTabTabView(List<TabViewModel> tabViewModels);

    View getTabView();

    class TabViewModel {
        String tabTitle;
        String tabIcon;

        public String getTabTitle() {
            return tabTitle;
        }

        public void setTabTitle(String tabTitle) {
            this.tabTitle = tabTitle;
        }
    }
}
