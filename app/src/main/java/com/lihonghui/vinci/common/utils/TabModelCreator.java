package com.lihonghui.vinci.common.utils;

import com.lihonghui.vinci.common.widget.adapter.TabIndicator;

import java.util.ArrayList;
import java.util.List;

public class TabModelCreator {

    public static  List<TabIndicator.TabViewModel> getTabModelList(String [] tabTexts){

        List<TabIndicator.TabViewModel> tabViewModels = new ArrayList<>();

        for (int index = 0; index < tabTexts.length; index ++){

            TabIndicator.TabViewModel tabViewModel = new TabIndicator.TabViewModel();
            tabViewModel.setTabTitle(tabTexts[index]);
            tabViewModels.add(tabViewModel);

        }

        return tabViewModels;
    }

}
