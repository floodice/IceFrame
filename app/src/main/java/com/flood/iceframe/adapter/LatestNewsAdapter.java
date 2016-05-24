package com.flood.iceframe.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flood.iceframe.R;
import com.flood.model.LatestNew;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @author: flood
 * @date: 2016-02-17 17:33
 */
public class LatestNewsAdapter extends IceAdapter {

    public LatestNewsAdapter(Context context) {
        super(context);
    }

    @Override
    protected View getIceView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            holder = new ViewHolder();

            view = inflater.inflate(R.layout.listitem_latestnews, null);
            holder.imageView = (ImageView)view.findViewById(R.id.news_image);
            holder.title = (TextView)view.findViewById(R.id.news_title);

            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        LatestNew.News data = (LatestNew.News) datas.get(i);
        holder.title.setText(data.title);

        imageLoader.displayImage(data.images[0],
                holder.imageView);
        return view;
    }

    class ViewHolder{
        public TextView title;
        public ImageView imageView;
    }
}
