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
public class TopCardAdapter extends IceAdapter {

    public TopCardAdapter(Context context) {
        super(context);
    }

    @Override
    protected View getIceView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            holder = new ViewHolder();

            view = inflater.inflate(R.layout.listitem_top_news, null);
            holder.imageView = (ImageView)view.findViewById(R.id.news_image);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        LatestNew.TopStories data = (LatestNew.TopStories) datas.get(i);
        imageLoader.displayImage(data.image,
                holder.imageView);
        return view;
    }

    class ViewHolder{
        public TextView title;
        public ImageView imageView;
    }
}
