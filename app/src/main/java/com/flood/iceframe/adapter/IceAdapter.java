package com.flood.iceframe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.flood.iceframe.app.IceApplication;
import com.flood.model.DataBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @author: flood
 * @date: 2016-02-17 17:26
 */
public abstract class IceAdapter extends BaseAdapter implements AdapterInterface{
    protected List<DataBean> datas = new ArrayList<>();
    protected LayoutInflater inflater;
    protected Context mContext;
    protected ImageLoader imageLoader;

    public IceAdapter(Context context){
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        imageLoader = ((IceApplication)context.getApplicationContext()).getImageLoader();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return getIceView(i, view, viewGroup);
    }

    protected abstract View getIceView(int i, View view, ViewGroup viewGroup);

    @Override
    public void addData(List<? extends DataBean> data) {
        datas.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData(){
        datas.clear();
        imageLoader.destroy();
    }
}
