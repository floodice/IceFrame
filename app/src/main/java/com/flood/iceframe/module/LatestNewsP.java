package com.flood.iceframe.module;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.flood.iceframe.R;
import com.flood.iceframe.fragment.ContentFragment;
import com.flood.model.LatestNew;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @author: flood
 * @date: 2016-02-26 17:12
 */
@Module
public class LatestNewsP {
    @Singleton
    @Inject
    public LatestNewsP() {

    }

    public void setItemClick(LatestNew.News news, FragmentManager fragmentManager){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        ContentFragment fragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", news.id);
        fragment.setArguments(bundle);
        transaction.add(R.id.sample_content_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
