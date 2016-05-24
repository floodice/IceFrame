package com.flood.iceframe.component;

import com.flood.iceframe.fragment.LatestNewsFragment;
import com.flood.iceframe.module.LatestNewsP;

import dagger.Component;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @author: flood
 * @date: 2016-02-26 17:28
 */
@Component(modules = LatestNewsP.class)
public interface LatestNewsComponent {
    void inject(LatestNewsFragment fragment);

}
