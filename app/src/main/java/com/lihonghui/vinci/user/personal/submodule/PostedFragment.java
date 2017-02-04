package com.lihonghui.vinci.user.personal.submodule;

import android.view.View;

import com.lihonghui.vinci.R;
import com.lihonghui.vinci.common.widget.adapter.BaseRecyclerViewAdapter;
import com.lihonghui.vinci.user.personal.submodule.mvp.ListContact;

/**
 * Created by yq05481 on 2016/12/9.
 */

public class PostedFragment extends ListFragment {

    @Override
    protected void subOnItemClick(View v, int position, Object data) {

    }

    @Override
    protected ListContact.Presenter getPresenter() {
        return new ListContact.Presenter() {
            @Override
            public void initData() {

            }

            @Override
            public void refresh() {

            }

            @Override
            public void loadMore() {

            }
        };
    }

    @Override
    protected int getItemViewResID() {
        return R.layout.item_personal_bucket;
    }

    @Override
    protected void setUpItemView(Object data, BaseRecyclerViewAdapter.CommonViewHolder holder) {

    }
}
