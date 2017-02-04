package com.lihonghui.vinci.common.SlideCard;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lihonghui.vinci.R;
import com.lihonghui.vinci.common.utils.ImageLoader;
import com.lihonghui.vinci.common.widget.view.CircleImageView;
import com.lihonghui.vinci.data.Entity.Shot;
import com.lihonghui.vinci.detail.DetailActivity;
import com.socks.library.KLog;

public class CardItemView extends BaseCardItemView<Shot> implements View.OnClickListener{

    private CardView cardView;
    private ImageView mShotThumbnail;
    private CircleImageView mPlayerAvatar;
    private TextView mPlayerName;
    private View mFlagAttachment;
    private TextView mTextViewsCount;
    private TextView mTextCommentCount;
    private View mIconLike;
    private TextView mTextLikeCount;

    private Shot mShot;

    public CardItemView(Context context) {
        super(context);
    }

    public CardItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.item_swipe_card;
    }

    @Override
    public void fillData(Shot itemData) {
        assignViews();
        mShot = itemData;
        mPlayerName.setText(itemData.getUser().getName());
        mTextViewsCount.setText(String.valueOf(itemData.getViews_count()));
        mTextCommentCount.setText(String.valueOf(itemData.getComments_count()));
        mTextLikeCount.setText(String.valueOf(itemData.getLikes_count()));

        ImageLoader.loadImage(getContext(), itemData.getImages().getTeaser(), mShotThumbnail);
        ImageLoader.loadImage(getContext(), itemData.getUser().getAvatar_url(), mPlayerAvatar);

        if(itemData.getAttachments_count() == 0){
            mFlagAttachment.setVisibility(GONE);
        }else{
            mFlagAttachment.setVisibility(VISIBLE);
        }
    }

    private boolean isFirst = true;
    private void assignViews() {
        if(isFirst){
            cardView = (CardView) rootView.findViewById(R.id.root_card_view);
            cardView.setOnClickListener(this);
            mShotThumbnail = (ImageView) rootView.findViewById(R.id.shot_thumbnail);
            mPlayerAvatar = (CircleImageView) rootView.findViewById(R.id.player_avatar);
            mPlayerName = (TextView) rootView.findViewById(R.id.player_name);
            mFlagAttachment = rootView.findViewById(R.id.flag_attachment);
            mTextViewsCount = (TextView) rootView.findViewById(R.id.text_views_count);
            mTextCommentCount = (TextView) rootView.findViewById(R.id.text_comment_count);
            mIconLike = rootView.findViewById(R.id.icon_like);
            mIconLike.setOnClickListener(this);
            mTextLikeCount = (TextView) rootView.findViewById(R.id.text_like_count);

            isFirst = false;
        }

    }


    @Override
    public void onClick(View v) {
        if(v == mIconLike){
            KLog.e("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }else if(v == cardView){
            Context context = cardView.getContext();
            if(context instanceof Activity){
               Activity activity = (Activity) context;
                DetailActivity.showByShareElements(activity,mShotThumbnail,mShot);
            }
        }
    }
}
