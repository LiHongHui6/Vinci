package com.lihonghui.vinci.user.personal.submodule;

import android.net.Uri;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lihonghui.vinci.R;
import com.lihonghui.vinci.common.widget.adapter.BaseRecyclerViewAdapter;
import com.lihonghui.vinci.data.Entity.Bucket;
import com.lihonghui.vinci.data.Entity.Shot;
import com.lihonghui.vinci.detail.DetailActivity;
import com.lihonghui.vinci.user.personal.submodule.mvp.BucketShotsPresenter;
import com.lihonghui.vinci.user.personal.submodule.mvp.ListContact;

/**
 * Created by yq05481 on 2016/12/20.
 */

public class BucketShotsFragment extends ListFragment<Shot> {
    private Bucket mBucket;
    public void setBucket(Bucket bucket){
        this.mBucket = bucket;
    }

    @Override
    protected void subOnItemClick(View v, int position, Object data) {
        SimpleDraweeView view = (SimpleDraweeView) v.findViewById(R.id.bucket_shot_thumbnail);
        Shot shot = (Shot) data;
        DetailActivity.showByShareElements(mActivity, view, shot);
        mActivity.overridePendingTransition(0, 0);
    }

    @Override
    protected ListContact.Presenter getPresenter() {
        return new BucketShotsPresenter(this,mBucket.getId());
    }

    @Override
    protected int getItemViewResID() {
        return R.layout.item_bucket_shot;
    }

    @Override
    protected void setUpItemView(Shot data, BaseRecyclerViewAdapter.CommonViewHolder holder) {
        SimpleDraweeView ivThumbnail = (SimpleDraweeView) holder.getView(R.id.bucket_shot_thumbnail);
        String url = data.getImages().getNormal();
        Uri uri = Uri.parse(url);
        ivThumbnail.setImageURI(uri);
    }
}
