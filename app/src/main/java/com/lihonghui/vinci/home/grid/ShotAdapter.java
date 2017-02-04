package com.lihonghui.vinci.home.grid;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lihonghui.vinci.R;
import com.lihonghui.vinci.common.utils.ImageLoader;
import com.lihonghui.vinci.common.widget.adapter.BaseRecyclerViewAdapter;
import com.lihonghui.vinci.data.Entity.Shot;

import java.util.List;

/**
 * Created by yq05481 on 2017/1/22.
 */

public class ShotAdapter extends BaseRecyclerViewAdapter<Shot> {
    public ShotAdapter(List<Shot> dataList, Context context) {
        super(dataList, context);
    }
    private View thumbView;
    @Override
    public int getResID() {
        return R.layout.item_home_shot;
    }

    @Override
    public void onSubclassBindViewHolder(CommonViewHolder holder, int position) {
        ImageView shotThumb = (ImageView) holder.getView(R.id.home_shot_thumb);
        TextView shotViewsCount = (TextView) holder.getView(R.id.shot_views_count);
        TextView shotCommentCount = (TextView) holder.getView(R.id.shot_comment_count);
        TextView shotLikeCount = (TextView) holder.getView(R.id.shot_like_count);
        ImageView playerAvatar = (ImageView) holder.getView(R.id.player_avatar);
        TextView playerName = (TextView) holder.getView(R.id.player_name);

        Shot mShot = dataList.get(position);
        ImageLoader.loadImage(mContext,mShot.getImages().getTeaser(),shotThumb);
        ImageLoader.loadImage(mContext,mShot.getUser().getAvatar_url(),playerAvatar);
        shotViewsCount.setText(String.valueOf(mShot.getViews_count()));
        shotCommentCount.setText(String.valueOf(mShot.getComments_count()));
        shotLikeCount.setText(String.valueOf(mShot.getLikes_count()));
        playerName.setText(String.valueOf(mShot.getUser().getName()));
    }

    @Override
    public void onClick(View v) {
        thumbView = v.findViewById(R.id.home_shot_thumb);
        super.onClick(v);
    }

    public View getThumbView(){
        return thumbView;
    }
}
