package com.flood.iceframe.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flood.iceframe.R;
import com.flood.model.DataBean;
import com.flood.model.LatestNew;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import butterknife.Bind;

/**
 * 最新适配器
 *
 * @version V1.0 <描述当前版本功能>
 * @author: flood
 * @date: 2016-03-17 15:45
 */
public class LatestNewsRecycleAdapter extends NormalRecyclerViewAdapter {
    DisplayImageOptions options;
    public LatestNewsRecycleAdapter(Context context) {
        super(context);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.icon_dark_refresh)            //加载图片时的图片
                .showImageForEmptyUri(R.drawable.icon_dark_refresh)         //没有图片资源时的默认图片
                .showImageOnFail(R.drawable.icon_dark_refresh)              //加载失败时的图片
                .cacheInMemory(true)                               //启用内存缓存
                .cacheOnDisk(true)                                 //启用外存缓存
                .considerExifParams(true)                          //启用EXIF和JPEG图像格式
                .displayer(new RoundedBitmapDisplayer(20))         //设置显示风格这里是圆角矩形
                .build();
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new LatestNewsViewHolder(mLayoutInflater.inflate(R.layout.listitem_latestnews, parent, false));
    }

    @Override
    protected void bindView(DataBean data, RecyclerView.ViewHolder holder) {
        LatestNew.News news = (LatestNew.News) data;
        ((LatestNewsViewHolder) holder).title.setText(news.title);
        imageLoader.displayImage(news.images[0], ((LatestNewsViewHolder) holder).imageView, options);
    }


    public class LatestNewsViewHolder extends NormalRecyclerViewHolder {
        @Bind(R.id.news_title)
        TextView title;

        @Bind(R.id.news_image)
        ImageView imageView;

        LatestNewsViewHolder(View view) {
            super(view);
        }
    }

}
