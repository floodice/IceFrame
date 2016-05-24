package com.flood.iceframe.component;

import com.flood.iceframe.app.SlidingActivity;
import com.flood.iceframe.module.ActivityModule;
import com.flood.iceframe.module.ActivityScope;

import dagger.Component;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @author: flood
 * @date: 2016-03-31 17:01
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface SlideActComponent {
    SlidingActivity inject(SlidingActivity activity);
}
