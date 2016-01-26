package com.flood.iceframe.module;

import android.app.Activity;

import com.flood.iceframe.module.api.ApiManager;

import dagger.Module;
import dagger.Provides;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @author: flood
 * @date: 2016-01-14 15:02
 */
@Module
public class ActivityModule {
     private Activity mActivity;

    public ActivityModule(Activity activity){
        this.mActivity = activity;
    }

    @Provides
    Activity providersActivity(){
        return mActivity;
    }
}
