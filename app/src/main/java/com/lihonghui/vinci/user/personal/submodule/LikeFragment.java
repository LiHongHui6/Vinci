package com.lihonghui.vinci.user.personal.submodule;

import android.net.Uri;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lihonghui.vinci.R;
import com.lihonghui.vinci.common.widget.adapter.BaseRecyclerViewAdapter;
import com.lihonghui.vinci.data.Entity.Like;
import com.lihonghui.vinci.detail.DetailActivity;
import com.lihonghui.vinci.user.personal.submodule.mvp.LikePresenter;
import com.lihonghui.vinci.user.personal.submodule.mvp.ListContact;

/**
 * Created by yq05481 on 2016/12/9.
 */

public class LikeFragment extends ListFragment<Like> {

    @Override
    protected void subOnItemClick(View v, int position, Object data) {
        SimpleDraweeView view = (SimpleDraweeView) v.findViewById(R.id.thumbnail);
        Like like = (Like) data;
        DetailActivity.showByShareElements(mActivity, view, like.getShot());
        mActivity.overridePendingTransition(0, 0);
    }

    @Override
    protected ListContact.Presenter getPresenter() {
        return new LikePresenter(this);
    }

    @Override
    protected int getItemViewResID() {
        return R.layout.item_personal_like;
    }

    @Override
    protected void setUpItemView(Like data, BaseRecyclerViewAdapter.CommonViewHolder holder) {
        SimpleDraweeView ivThumbnail = (SimpleDraweeView) holder.getView(R.id.thumbnail);
        String url = data.getShot().getImages().getNormal();
        Uri uri = Uri.parse(url);
        ivThumbnail.setImageURI(uri);
    }
}
