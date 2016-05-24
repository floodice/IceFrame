package com.flood.iceframe.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flood.iceframe.R;
import com.flood.iceframe.app.IceApplication;
import com.flood.model.DataBean;
import com.flood.model.LatestNew;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @author: flood
 * @date: 2016-03-17 14:13
 */
public abstract class NormalRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements AdapterInterface {

    protected final LayoutInflater mLayoutInflater;
    protected final Context mContext;
    protected List<DataBean> mList = new ArrayList<>();
    protected ImageLoader imageLoader;

    public NormalRecyclerViewAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        imageLoader = ((IceApplication)context.getApplicationContext()).getImageLoader();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return getViewHolder(parent, viewType);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public void addData(List<? extends DataBean> data) {
        mList.addAll(data);
        notifyItemRangeChanged(0, data.size()-1);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mList != null && holder != null){
            DataBean data = mList.get(position);
            holder.itemView.setTag(data);
            bindView(data, holder);
        }

    }

    public abstract class NormalRecyclerViewHolder extends RecyclerView.ViewHolder {

        public NormalRecyclerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null){
                        DataBean data = (DataBean) v.getTag();
                        onItemClickListener.onItemClick(data);
                    }
                }
            });
        }
    }

    protected abstract RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType);
    protected abstract void bindView(DataBean data, RecyclerView.ViewHolder holder);

    protected OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener l){
        this.onItemClickListener = l;
    }
    public interface OnItemClickListener{
        void onItemClick(DataBean data);
    }

    public void unBindKnife(){
        mList.clear();
        imageLoader.stop();
//        imageLoader.destroy();
        ButterKnife.unbind(this);
    }

}
