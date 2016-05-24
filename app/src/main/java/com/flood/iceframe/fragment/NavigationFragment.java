package com.flood.iceframe.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flood.iceframe.R;
import com.flood.iceframe.app.IceApplication;
import com.flood.iceframe.component.ApplicationComponent;

/**
 * 在此写用途
 *
 * @version V1.0 <侧拉菜单>
 * @author: flood
 * @date: 2016-02-17 14:32
 */
public class NavigationFragment extends BaseFragment{

    @Override
    protected View initViewOnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
    }

    @Override
    protected void dealViewAfterBinder() {

    }
}