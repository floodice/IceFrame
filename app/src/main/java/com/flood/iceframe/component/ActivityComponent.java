package com.flood.iceframe.component;

import com.flood.iceframe.app.MainActivity;
import com.flood.iceframe.module.ActivityModule;
import com.flood.iceframe.module.api.ApiManager;
import com.flood.iceframe.widget.ToastHelper;

import dagger.Component;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @author: flood
 * @date: 2016-01-14 15:35
 */
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, ApiManager.class})
public interface ActivityComponent {

    MainActivity injectActivity(MainActivity activity);

    ToastHelper getToastHelper();
    ApiManager getApiManager();

}
