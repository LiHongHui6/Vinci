package com.lihonghui.vinci.detail;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.transition.ChangeBounds;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.lihonghui.vinci.R;
import com.lihonghui.vinci.base.BaseActivity;
import com.lihonghui.vinci.common.OnRecycleViewScrollListener;
import com.lihonghui.vinci.common.utils.ToastUtil;
import com.lihonghui.vinci.common.widget.adapter.BaseRecyclerViewAdapter;
import com.lihonghui.vinci.common.widget.view.CustomAppBarLayout;
import com.lihonghui.vinci.data.Entity.Attachment;
import com.lihonghui.vinci.data.Entity.Bucket;
import com.lihonghui.vinci.data.Entity.Comment;
import com.lihonghui.vinci.data.Entity.Shot;
import com.lihonghui.vinci.detail.bucket.BucketDialog;
import com.socks.library.KLog;

import java.util.List;

/**
 * Created by yq05481 on 2016/12/10.
 */

public class DetailActivity extends BaseActivity implements DetailContact.View{
    private SimpleDraweeView mViewAvatar;
    private TextView mTextAuthorName;
    private SimpleDraweeView mImage;
    private TextView mTextCollectCount;
    private TextView mTextLikeCount;
    private TextView mTextViewsCount;
    private DetailContact.Presenter mPresenter;

    private Shot mShot;
    private final String KEY_BUNDLE = "KEY_BUNDLE";
    public static final String KEY_SHOT = "KEY_SHOT";
    private View mViewAttachment;
    private SwipeRefreshLayout layoutRefresh;
    private RecyclerView mCommentList;
    private CommentListAdapter mCommentListAdapter;
    private CustomAppBarLayout mAppBar;
    private RecyclerView mAttachmentList;
    private AttachmentListAdapter mAttachmentListAdapter;
    private View layoutLike;
    private View viewLike;
    private View layoutBucket;
    private BucketDialog bucketDialog;

    @Override
    public int getContentViewResourceID() {
        return R.layout.activity_detail;
    }

    @Override
    public void initParameter() {
        super.initParameter();
        mPresenter = new DetailPresenter(this);
    }

    @Override
    public void getData() {
        super.getData();
        Intent intent = getIntent();
        String jsonShot = intent.getStringExtra(KEY_SHOT);
        mShot = new Gson().fromJson(jsonShot, Shot.class);
        mPresenter.getData(mShot);
    }

    @Override
    public void findView() {
        super.findView();
        layoutRefresh = (SwipeRefreshLayout) findViewById(R.id.layout_refresh);
        mViewAvatar = (SimpleDraweeView) findViewById(R.id.view_avatar);
        mTextAuthorName = (TextView) findViewById(R.id.text_author_name);
        mTextViewsCount = (TextView) findViewById(R.id.text_views_count);
        mImage = (SimpleDraweeView) findViewById(R.id.image);
        mTextCollectCount = (TextView) findViewById(R.id.text_collect_count);
        mTextLikeCount = (TextView) findViewById(R.id.text_like_count);
        mViewAttachment = findViewById(R.id.view_attachment);
        mCommentList = (RecyclerView) findViewById(R.id.rv_comment_list);
        mAttachmentList = (RecyclerView) findViewById(R.id.rv_attachment_list);
        mAppBar = (CustomAppBarLayout) findViewById(R.id.app_bar);
        layoutLike = findViewById(R.id.layout_like);
        layoutBucket = findViewById(R.id.layout_bucket);
        viewLike = findViewById(R.id.view_like);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setUpView() {
        super.setUpView();
        getWindow().setSharedElementEnterTransition(new ChangeBounds());
        getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        Uri uri = Uri.parse(mShot.getImages().getNormal());

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();

        mImage.setController(controller);

        Uri uri2 = Uri.parse(mShot.getUser().getAvatar_url());
        mViewAvatar.setImageURI(uri2);
        setStatusBarColor(getResources().getColor(R.color.colorPink3));
        fitStatusBarEnble(true);

        int attachments_count = mShot.getAttachments_count();
        if (attachments_count == 0) {
            mViewAttachment.setVisibility(View.GONE);
            mAttachmentList.setVisibility(View.GONE);
        } else {
            mViewAttachment.setVisibility(View.VISIBLE);
            mAttachmentList.setVisibility(View.VISIBLE);
        }

        setShotBaseInfo(mShot);

        mCommentList.addOnScrollListener(new OnRecycleViewScrollListener() {
            @Override
            public void onScrollBottom() {
                super.onScrollBottom();
                mPresenter.getMoreComment(mShot);
                KLog.e("getMoreComment onScrollBottom");
            }
        });

        layoutRefresh.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.colorWhite));
        layoutRefresh.setColorSchemeColors(0xFFEA4C89);
        layoutRefresh.setOnRefreshListener(new RefreshListener());

        layoutLike.setOnClickListener(this);
        layoutBucket.setOnClickListener(this);

        mAppBar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                setmAppBarDragEnable(false);
                mAppBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(verticalOffset < 0 ){
                    if(!layoutRefresh.isRefreshing()){
                        layoutRefresh.setEnabled(false);
                    }
                }else{
                    layoutRefresh.setEnabled(true);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        super.onClick(view);
        if(view == layoutLike){

            mPresenter.likeSelect(mShot);

        }else if(view == layoutBucket){

            if(bucketDialog == null){
                bucketDialog = new BucketDialog(this);
                bucketDialog.setOnConfirmListener(new BucketDialog.OnConfirmListener() {
                    @Override
                    public void onConfirm(Bucket bucket) {
                        ToastUtil.showToast(mAcSelf,"Bucket ID:"+bucket.getId(),ToastUtil.DURATION_LONG);
                    }
                });
            }

            bucketDialog.show();

        }
    }

    private void setShotBaseInfo(Shot shot){
        mTextAuthorName.setText(shot.getUser().getName());
        mTextViewsCount.setText(String.valueOf(shot.getViews_count()));
        mTextCollectCount.setText(String.valueOf(shot.getBuckets_count()));
        mTextLikeCount.setText(String.valueOf(shot.getLikes_count()));
    }

    @Override
    protected boolean getTitleBarVisibility() {
        return true;
    }

    @Override
    protected String getTitleText() {
        return mShot.getTitle();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void showByShareElements(Activity parent, View view, Shot shot) {
        Intent intent = new Intent(parent, DetailActivity.class);
        Gson gson = new Gson();
        String jsonShot = gson.toJson(shot);
        intent.putExtra(KEY_SHOT, jsonShot);
        parent.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(parent, view, view.getTransitionName()).toBundle());
    }

    public static void show(Context context, Shot shot) {
        Intent intent = new Intent(context, DetailActivity.class);
        Gson gson = new Gson();
        String jsonShot = gson.toJson(shot);
        intent.putExtra(KEY_SHOT, jsonShot);
        context.startActivity(intent);
    }

    @Override
    public void onGetAttachment(List<Attachment> attachments) {
        KLog.e("onGetAttachment "+attachments.size());
        if(mAttachmentListAdapter == null){
            mAttachmentListAdapter = new AttachmentListAdapter(attachments, mAcSelf);
            mAttachmentList.setLayoutManager(new LinearLayoutManager(mAcSelf,LinearLayoutManager.HORIZONTAL,false));
            mAttachmentList.setAdapter(mAttachmentListAdapter);
        }else{
            mAttachmentListAdapter.setDataAndRefresh(attachments);
        }
    }

    @Override
    public void onGetComments(List<Comment> commentList) {
        if(mCommentListAdapter == null){
            mCommentListAdapter = new CommentListAdapter(commentList, mAcSelf);
            mCommentList.setLayoutManager(new LinearLayoutManager(mAcSelf));
            mCommentList.setAdapter(mCommentListAdapter);
        }else{
            mCommentListAdapter.setDataAndRefresh(commentList);
        }
    }

    @Override
    public void onGettingData() {
       layoutRefresh.setRefreshing(true);
    }

    @Override
    public void onDataRefresh(Shot shot) {
        setShotBaseInfo(shot);
        mShot = shot;
    }

    @Override
    public void onError(String message) {
        ToastUtil.showToast(this,message,ToastUtil.DURATION_LONG);
        layoutRefresh.setRefreshing(false);
        setmAppBarDragEnable(true);
    }

    @Override
    public void getDataFinish() {
        layoutRefresh.setRefreshing(false);
        setmAppBarDragEnable(true);
    }

    @Override
    public void likeChecked(boolean isLike) {
        if(isLike){
            viewLike.setBackgroundResource(R.mipmap.like_sel_detail);
        }else{
            viewLike.setBackgroundResource(R.mipmap.like_nor_detail);
        }

    }

    @Override
    public void unLikeShot() {
        viewLike.setBackgroundResource(R.mipmap.like_nor_detail);
        layoutLike.setClickable(true);
    }

    @Override
    public void likeShot() {
        viewLike.setBackgroundResource(R.mipmap.like_sel_detail);
        layoutLike.setClickable(true);
    }

    @Override
    public void onLikeShot() {
        layoutLike.setClickable(false);
    }

    @Override
    public void onUnLikeShot() {
        layoutLike.setClickable(false);
    }

    @Override
    public void onGetMoreComments(List<Comment> comments) {
        mCommentListAdapter.addDataAndRefresh(comments);
    }

    @Override
    public void onLoadAllComment() {
        ToastUtil.showToast(mAcSelf,getResources().getString(R.string.load_all),ToastUtil.DURATION_LONG);
    }

    private void setmAppBarDragEnable(final boolean enable){
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mAppBar.getLayoutParams();
        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override
            public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                return enable;
            }
        });
    }

    class RefreshListener implements SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            mPresenter.getData(mShot);
            setmAppBarDragEnable(false);
        }
    }

    class CommentListAdapter extends BaseRecyclerViewAdapter<Comment> {

        public CommentListAdapter(List<Comment> dataList, Context context) {
            super(dataList, context);
        }

        @Override
        public int getResID() {
            return R.layout.item_comment;
        }

        @Override
        public void onSubclassBindViewHolder(CommonViewHolder holder, int position) {
            Comment comment = dataList.get(position);

            SimpleDraweeView mViewCommentAvatar = (SimpleDraweeView) holder.getView(R.id.view_comment_avatar);
            TextView mTextCommentName = (TextView) holder.getView(R.id.text_comment_name);
            TextView mTextCommentContent = (TextView) holder.getView(R.id.text_comment_content);

            mViewCommentAvatar.setImageURI(Uri.parse(comment.getUser().getAvatar_url()));
            mTextCommentName.setText(comment.getUser().getName());
            mTextCommentContent.setText(Html.fromHtml(comment.getBody()));

            if(position % 2 == 0){
                holder.itemView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            }else{
                holder.itemView.setBackgroundColor(getResources().getColor(R.color.colorGray4));
            }
        }
    }

    class AttachmentListAdapter extends BaseRecyclerViewAdapter<Attachment>{

        public AttachmentListAdapter(List<Attachment> dataList, Context context) {
            super(dataList, context);
        }

        @Override
        public int getResID() {
            return R.layout.item_attachment;
        }

        @Override
        public void onSubclassBindViewHolder(CommonViewHolder holder, int position) {
            Attachment attachment = dataList.get(position);
            SimpleDraweeView mViewAttachmentThumbnail = (SimpleDraweeView) holder.getView(R.id.view_attachment_thumbnail);
            mViewAttachmentThumbnail.setImageURI(Uri.parse(attachment.getThumbnail_url()));
            KLog.e("AttachmentListAdapter onSubclassBindViewHolder "+attachment.getThumbnail_url());
        }
    }
}
