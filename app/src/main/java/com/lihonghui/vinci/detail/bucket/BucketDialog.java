package com.lihonghui.vinci.detail.bucket;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lihonghui.vinci.R;
import com.lihonghui.vinci.common.OnRecycleViewScrollListener;
import com.lihonghui.vinci.common.utils.ToastUtil;
import com.lihonghui.vinci.common.widget.adapter.BaseRecyclerViewAdapter;
import com.lihonghui.vinci.data.Entity.Bucket;
import com.lihonghui.vinci.data.Entity.BucketModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yq05481 on 2016/12/20.
 */

public class BucketDialog extends Dialog implements View.OnClickListener,BucketContact.View {

    private View textCancel;
    private SwipeRefreshLayout layoutRefresh;
    private BucketContact.Presenter mPresenter;
    private RecyclerView mRecyclerView;
    private MyAdapter adapter;
    private View textConfirm;
    private View textCreateBucket;
    private View layoutSelectBucket;
    private View layoutCreateBucket;
    private View textCancelCreate;
    private View textConfirmCreate;
    private EditText inputerBucketTitle;
    private EditText inputerBucketDescribe;

    public BucketDialog(Context context) {
        super(context, R.style.MyDialogStyle);
        init();
    }

    private void init() {
        Window window = this.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(attributes);
        this.setContentView(getContentView());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new BucketDialogPresenter(this);
        mPresenter.initData();
    }

    private View getContentView() {
        View contentView = View.inflate(getContext(), R.layout.dialog_bucket, null);
        setUpContentView(contentView);
        return contentView;
    }

    private void setUpContentView(View contentView) {
        textConfirm = contentView.findViewById(R.id.text_confirm);
        textConfirm.setOnClickListener(this);
        textCancel = contentView.findViewById(R.id.text_cancel);
        textCancel.setOnClickListener(this);
        textCreateBucket = contentView.findViewById(R.id.text_create_bucket);
        textCreateBucket.setOnClickListener(this);
        textCancelCreate = contentView.findViewById(R.id.text_cancel_create);
        textCancelCreate.setOnClickListener(this);
        textConfirmCreate = contentView.findViewById(R.id.text_confirm_create);
        textConfirmCreate.setOnClickListener(this);

        layoutSelectBucket = contentView.findViewById(R.id.layout_select_bucket);
        layoutCreateBucket = contentView.findViewById(R.id.layout_create_bucket);

        inputerBucketTitle = (EditText) contentView.findViewById(R.id.inputer_bucket_title);
        inputerBucketDescribe = (EditText) contentView.findViewById(R.id.inputer_bucket_describe);

        mRecyclerView = (RecyclerView) contentView.findViewById(R.id.bucket_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new MyAdapter(null, getContext());
        adapter.addOnItemClickListener(new MyItemClickListener());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(new OnRecycleViewScrollListener() {
            @Override
            public void onScrollBottom() {
                loadMore();
            }
        });

        layoutRefresh = (SwipeRefreshLayout) contentView.findViewById(R.id.layout_refresh);
        layoutRefresh.setProgressBackgroundColorSchemeColor(getContext().getResources().getColor(R.color.colorWhite));
        layoutRefresh.setColorSchemeColors(0xFFEA4C89);
        layoutRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refresh();
            }
        });
    }

    private void loadMore() {
        if (!layoutRefresh.isRefreshing()) {
            mPresenter.loadMore();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == textCancel) {
            this.cancel();
        }else if(v == textConfirm){
            if(adapter.getItemCount() == 0 && !layoutRefresh.isRefreshing()){
                ToastUtil.showToast(getContext(),getContext().getResources().getString(R.string.logging),ToastUtil.DURATION_LONG);
            }else{
                callback.onConfirm(adapter.getSelected());
                this.cancel();
            }
        }else if(v == textCreateBucket){
            layoutSelectBucket.setVisibility(View.GONE);
            layoutCreateBucket.setVisibility(View.VISIBLE);
        }else if(v == textCancelCreate){
            layoutSelectBucket.setVisibility(View.VISIBLE);
            layoutCreateBucket.setVisibility(View.GONE);
        }else if(v == textConfirmCreate){
            layoutSelectBucket.setVisibility(View.VISIBLE);
            layoutCreateBucket.setVisibility(View.GONE);

            mPresenter.createBucket(inputerBucketTitle.getText().toString(),inputerBucketDescribe.getText().toString());
        }
    }

    private OnConfirmListener callback;
    public void setOnConfirmListener(OnConfirmListener listener){
        this.callback = listener;
    }

    @Override
    public void onStartCreateBucket() {
        layoutRefresh.setRefreshing(true);
    }

    @Override
    public void onCreateBucket(Bucket bucket) {
        BucketModel bucketModel = new BucketModel(null,bucket);
        adapter.addDataToHead(bucketModel);
        layoutRefresh.setRefreshing(false);
    }

    public interface OnConfirmListener{
        void onConfirm(Bucket bucket);
    }

    @Override
    public void onInitializing() {
        layoutRefresh.setRefreshing(true);
    }

    @Override
    public void onInitFinish() {
        layoutRefresh.setRefreshing(false);
    }

    @Override
    public void onInitData(List<BucketModel> datas) {
        adapter.setDataAndRefresh(datas);
    }

    @Override
    public void onInitDataError() {

    }

    @Override
    public void onRefreshing() {
        layoutRefresh.setRefreshing(true);
    }

    @Override
    public void onRefreshFinish() {
        layoutRefresh.setRefreshing(false);
    }

    @Override
    public void onRefreshData(List datas) {
        adapter.setDataAndRefresh(datas);
    }

    @Override
    public void onLoadingMore() {
        layoutRefresh.setRefreshing(true);
    }

    @Override
    public void onLoadMoreFinish() {
        layoutRefresh.setRefreshing(false);
    }

    @Override
    public void onLoadMoreData(List datas) {
        adapter.addDataAndRefresh(datas);
    }

    @Override
    public void onError(String msg) {
        ToastUtil.showToast(getContext(), msg, ToastUtil.DURATION_SHORT);
        layoutRefresh.setRefreshing(false);
    }

    @Override
    public void onLoadAll() {

    }

    private class MyItemClickListener implements BaseRecyclerViewAdapter.OnItemClickListener {

        @Override
        public void onItemClick(View v, int position, Object data) {
            adapter.onSelected(position);
        }

        @Override
        public void onItemLongClick(View v, int position) {

        }
    }

    private class MyAdapter extends BaseRecyclerViewAdapter<BucketModel> {
        int selectedPosition = 0;
        MyAdapter(List<BucketModel> dataList, Context context) {
            super(dataList, context);
        }
        @Override
        public int getResID() {
            return R.layout.item_bucket;
        }

        @Override
        public void onSubclassBindViewHolder(CommonViewHolder holder, int position) {
            BucketModel bucketModel = dataList.get(position);

            TextView textBucketTitle = (TextView) holder.getView(R.id.text_bucket_title);
            textBucketTitle.setText(bucketModel.getBucket().getName());

            TextView textShotCount = (TextView) holder.getView(R.id.text_shot_count);
            textShotCount.setText(bucketModel.getBucket().getShots_count()+" "+mContext.getResources().getString(R.string.shot));

            if(bucketModel.getFirstShot() != null){
                SimpleDraweeView simpleDraweeView = (SimpleDraweeView) holder.getView(R.id.bucket_thumbnail);
                simpleDraweeView.setImageURI(Uri.parse(bucketModel.getFirstShot().getImages().getNormal()));
            }

            View viewSelected =  holder.getView(R.id.view_selected);
            if(position == selectedPosition){
                viewSelected.setVisibility(View.VISIBLE);
            }else{
                viewSelected.setVisibility(View.GONE);
            }
        }

        public void addDataToHead(BucketModel bucketModel){
            if(dataList == null){
                dataList = new ArrayList<>();
            }
            dataList.add(0,bucketModel);
            notifyDataSetChanged();
        }

        public void onSelected(int position) {
            selectedPosition = position;
            notifyDataSetChanged();
        }

        public Bucket getSelected(){
            return dataList.get(selectedPosition).getBucket();
        }
    }
}
