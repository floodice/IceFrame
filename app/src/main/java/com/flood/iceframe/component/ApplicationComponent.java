package com.flood.iceframe.component;

import com.flood.iceframe.app.IceApplication;
import com.flood.iceframe.module.ApplicationModule;

import dagger.Component;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @author: flood
 * @date: 2016-01-14 15:46
 */
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    IceApplication injectApplication(IceApplication application);
}
