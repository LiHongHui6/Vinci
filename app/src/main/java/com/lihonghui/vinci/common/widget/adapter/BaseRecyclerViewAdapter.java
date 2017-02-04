package com.lihonghui.vinci.common.widget.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseRecyclerViewAdapter<T> extends Adapter<RecyclerView.ViewHolder> implements OnClickListener, OnLongClickListener {
    protected List<T> dataList;
    protected Context mContext;

    /**
     * n
     *
     * @param dataList
     */
    public BaseRecyclerViewAdapter(List<T> dataList, Context context) {
        this.dataList = dataList;
        this.mContext = context;
    }


    private OnItemClickListener onItemClickListener;

    public void addOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // TODO Auto-generated method stub
        View itemView = LayoutInflater.from(mContext).inflate(getResID(), parent, false);
        CommonViewHolder viewHolder = new CommonViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        // TODO Auto-generated method stub
        if (dataList != null && dataList.size() > 0) {
            holder.itemView.setOnClickListener(this);
            holder.itemView.setOnLongClickListener(this);
        }
        holder.itemView.setTag(position);
        onSubclassBindViewHolder((CommonViewHolder) holder, position);
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        if (dataList == null) {
            return 0;
        }
        return dataList.size();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        if (onItemClickListener != null) {
            Object data;
            if (dataList != null) {
                data = dataList.get((Integer) v.getTag());
            } else {
                data = null;
            }
            onItemClickListener.onItemClick(v, (Integer) v.getTag(), data);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        // TODO Auto-generated method stub

        if (onItemClickListener != null) {
            onItemClickListener.onItemLongClick(v, (Integer) v.getTag());
        }

        return true;
    }

    public abstract int getResID();

    public abstract void onSubclassBindViewHolder(CommonViewHolder holder, final int position);


    public interface OnItemClickListener {

        public void onItemClick(View v, int position, Object data);

        public void onItemLongClick(View v, int position);

    }

    public void setDataAndRefresh(List<T> datas) {
        this.dataList = datas;
        this.notifyDataSetChanged();
    }

    public void addDataAndRefresh(List<T> datas) {
        this.dataList.addAll(datas);
        this.notifyDataSetChanged();
    }

    public class CommonViewHolder extends RecyclerView.ViewHolder {
        private View mItemView;
        private SparseArray<View> views;

        public CommonViewHolder(View itemView) {
            super(itemView);
            views = new SparseArray<>();
            this.mItemView = itemView;
        }

        public View getView(int resID) {
            View view = views.get(resID);

            if (view == null) {
                view = mItemView.findViewById(resID);
                views.put(resID,view);
            }

            return view;
        }
    }
}
