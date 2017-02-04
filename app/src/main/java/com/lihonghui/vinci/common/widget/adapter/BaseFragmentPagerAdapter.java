package com.lihonghui.vinci.common.widget.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lihonghui.vinci.base.BaseFragment;

import java.util.List;

/**
 * Created by yq05481 on 2016/11/12.
 */

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragmentList;
    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public BaseFragmentPagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        if(fragmentList == null){
            return 0;
        }
        return fragmentList.size();
    }
}
