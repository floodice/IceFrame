package com.flood.iceframe.module;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @author: flood
 * @date: 2016-01-14 15:31
 */
@Module
public class ApplicationModule {
    public Application mApplication;

    public ApplicationModule(Application application){
        this.mApplication = application;
    }

    @Provides
    public Application providersApplication(){
        return mApplication;
    }
}
