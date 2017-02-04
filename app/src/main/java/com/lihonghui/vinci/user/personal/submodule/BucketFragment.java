package com.lihonghui.vinci.user.personal.submodule;

import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lihonghui.vinci.R;
import com.lihonghui.vinci.common.widget.adapter.BaseRecyclerViewAdapter;
import com.lihonghui.vinci.data.Entity.BucketModel;
import com.lihonghui.vinci.user.personal.submodule.mvp.BucketPresenter;
import com.lihonghui.vinci.user.personal.submodule.mvp.ListContact;

/**
 * Created by yq05481 on 2016/12/9.
 */

public class BucketFragment extends ListFragment<BucketModel> {

    @Override
    protected void subOnItemClick(View v, int position, Object data) {
        BucketModel bucketModel = (BucketModel) data;
        BucketShotsActivity.show(mActivity,bucketModel.getBucket());
    }

    @Override
    protected ListContact.Presenter getPresenter() {
        return new BucketPresenter(this);
    }

    @Override
    protected int getItemViewResID() {
        return R.layout.item_personal_bucket;
    }

    @Override
    protected void setUpItemView(BucketModel data, BaseRecyclerViewAdapter.CommonViewHolder holder) {
        TextView textBucketTitle = (TextView) holder.getView(R.id.text_bucket_title);
        textBucketTitle.setText(data.getBucket().getName());

        TextView textShotCount = (TextView) holder.getView(R.id.text_shot_count);
        textShotCount.setText(data.getBucket().getShots_count()+" "+getResources().getString(R.string.shot));
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) holder.getView(R.id.bucket_thumbnail);
        if(data.getFirstShot() != null){
            simpleDraweeView.setImageURI(Uri.parse(data.getFirstShot().getImages().getNormal()));
        }else{
            simpleDraweeView.setBackground(null);
        }
    }


}
