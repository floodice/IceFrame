package com.flood.iceframe.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.flood.iceframe.component.ApplicationComponent;

import butterknife.ButterKnife;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @author: flood
 * @date: 2016-01-14 18:55
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        ButterKnife.bind(this);
        setupActivityComponent(((IceApplication)getApplication()).getComponent());
    }

    protected abstract int getLayoutId();
    protected abstract void setupActivityComponent(ApplicationComponent applicationComponent);
}
