package com.flood.iceframe.app;

import android.app.Application;
import android.util.Log;

import com.debug.L;
import com.flood.iceframe.component.ApplicationComponent;
import com.flood.iceframe.component.DaggerApplicationComponent;
import com.flood.iceframe.config.Constants;
import com.flood.iceframe.module.ApplicationModule;
import com.flood.iceframe.utils.DeviceUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.orhanobut.logger.LogLevel;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;
import com.util.AndroidUtils;

import java.io.File;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @author: flood
 * @date: 2016-01-14 15:48
 */
public class IceApplication extends Application {
    private ApplicationComponent component;
    private RefWatcher refWatcher;
    private ImageLoader imageLoader;
    private ImageLoaderConfiguration config;

    @Override
    public void onCreate() {
        super.onCreate();
        this.component = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        this.component.injectApplication(this);

        initImageLoader();
        if (Constants.LeakCanaryEnable){
            refWatcher = LeakCanary.install(this);
        }
        MobclickAgent.setDebugMode(Constants.MOB_DEBUG);


        L.init().setLogLevel(Constants.LOG_SWITCH ? LogLevel.FULL : LogLevel.NONE);
    }

    private void initImageLoader(){

        config = new
                ImageLoaderConfiguration .Builder(getApplicationContext())
//                .maxImageWidthForMemoryCache(800)
//                .maxImageHeightForMemoryCache(480)
//                .httpConnectTimeout(5000)
//                .httpReadTimeout(20000)
                .memoryCacheSize(5 * 1024 * 1024)
                .diskCacheSize(10 * 1024 * 1024)
                .threadPoolSize(5)
                .threadPriority(Thread.MIN_PRIORITY + 3)
                .denyCacheImageMultipleSizesInMemory()
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }

    public ImageLoader getImageLoader(){
        if (!imageLoader.isInited()){
            imageLoader.init(config);
        }
        return imageLoader;
    }
    public RefWatcher getRefWatcher(){
        return refWatcher;
    }

    public ApplicationComponent getComponent(){
        return component;
    }
}
