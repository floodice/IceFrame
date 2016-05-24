package com.flood.iceframe.app;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.flood.iceframe.R;
import com.flood.iceframe.component.ApplicationComponent;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;
import com.zzt.KugouLayout.KugouLayout;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @author: flood
 * @date: 2016-01-14 18:55
 */
public abstract class BaseActivity extends AppCompatActivity {
    private KugouLayout kugouLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        ButterKnife.bind(this);
        setupActivityComponent(((IceApplication) getApplication()).getComponent());

//        kugouLayout = new KugouLayout(this);
//        kugouLayout.attach(this);
//
//        kugouLayout.setLayoutCloseListener(new KugouLayout.LayoutCloseListener() {
//            @Override
//            public void onLayoutClose() {
//                finish();
//            }
//        });

//        SwipeBackHelper.onCreate(this);
//        SwipeBackHelper.getCurrentPage(this)
//                .setSwipeBackEnable(true)
//                .setSwipeSensitivity(0.5f)
//                .setSwipeRelateEnable(true)
//                .setSwipeSensitivity(1);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void finish() {
        super.finish();
        setContentView(R.layout.empty);
//        overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
    }

    @Override
    protected void onDestroy() {

        ButterKnife.unbind(this);
        super.onDestroy();
//        kugouLayout.setLayoutCloseListener(null);
//        SwipeBackHelper.onDestroy(this);
        RefWatcher refWatcher = ((IceApplication) getApplication()).getRefWatcher();
        if (refWatcher != null) {
            refWatcher.watch(this);
        }

    }

    protected static class ActHandler extends Handler{
        private WeakReference<? extends BaseActivity> reference;
        public ActHandler(BaseActivity activity) {
            reference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BaseActivity baseActivity = reference.get();
            if (baseActivity != null){
                baseActivity.actHandlerMessage(msg);
            }
        }
    }

    protected void actHandlerMessage(Message msg){

    }

    /**
     * 防止横向滑动事件冲突
     *
     * @param view 包含横向滑动事件控件
     */
    protected void addHorizontalScrollableView(View view) {
//        kugouLayout.addHorizontalScrollableView(view);
    }

    protected abstract int getLayoutId();

    protected abstract void setupActivityComponent(ApplicationComponent applicationComponent);
}
