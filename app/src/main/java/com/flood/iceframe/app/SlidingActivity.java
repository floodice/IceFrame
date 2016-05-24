package com.flood.iceframe.app;


import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.debug.L;
import com.flood.iceframe.R;
import com.flood.iceframe.component.ApplicationComponent;
import com.flood.iceframe.component.DaggerSlideActComponent;
import com.flood.iceframe.component.SlideActComponent;
import com.flood.iceframe.fragment.LatestNewsFragment;
import com.flood.iceframe.jnis.JniUtils;
import com.flood.iceframe.module.ActivityModule;
import com.flood.iceframe.utils.DeviceUtils;
import com.flood.iceframe.widget.ToastHelper;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 在此写用途
 *
 * @version V1.0 <侧滑主activity>
 * @author: flood
 * @date: 2016-02-17 14:32
 */
public class SlidingActivity extends BaseActivity {

    private SlideActComponent component;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mainToolbar;
    @Bind(R.id.floatAction)
    public FloatingActionButton floatAction;
    @Inject
    public ToastHelper toastHelper;

    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;
    private static final int READ_PHONE_STATE_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
        component = DaggerSlideActComponent.builder()
                .applicationComponent(((IceApplication) getApplication()).getComponent())
                .activityModule(new ActivityModule(this)).build();
        component.inject(this);
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            SlidingTabFragment fragment = new SlidingTabFragment();
            LatestNewsFragment fragment = new LatestNewsFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }

        configureToolbar();
        configureDrawer();

        addHorizontalScrollableView(mDrawerLayout);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                    READ_PHONE_STATE_REQUEST_CODE);
        }

//        //获取SensorManager对象
//        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
//        //获取Sensor对象
//        Sensor ligthSensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
//
//        sm.registerListener(new MySensorListener(), ligthSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == READ_PHONE_STATE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                String deviceifo = DeviceUtils.getDeviceInfo(this);
                Log.d("deviceifo", "deviceifo:" + deviceifo);
                // Permission Granted
            } else {
                // Permission Denied
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sliding;
    }

    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {

    }

    @OnClick(R.id.floatAction)
    public void onClickFloatAction(){

        toastHelper.showToast(getApplicationContext(), "click");
    }

    /**
     * 配置toolbar
     */
    private void configureToolbar() {
        mainToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mainToolbar);
        JniUtils jniUtils = new JniUtils();
        String text = jniUtils.getCLanguageString();
        getSupportActionBar().setTitle(text);

        mainToolbar.setNavigationIcon(R.drawable.ic_menu_black_36dp);

        mainToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);

                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    /**
     * 配置drawer
     */
    private void configureDrawer() {
        // Configure drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setScrimColor(getResources().getColor(R.color.drawer_bg));//让半透明层不要太暗

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open,
                R.string.drawer_closed) {

            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                supportInvalidateOptionsMenu();
//                mainToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_36dp);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                supportInvalidateOptionsMenu();
//                mainToolbar.setNavigationIcon(R.drawable.ic_menu_black_36dp);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDrawerLayout.setDrawerListener(null);
        mainToolbar.setNavigationOnClickListener(null);
        mDrawerToggle = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }

    public class MySensorListener implements SensorEventListener {

        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        public void onSensorChanged(SensorEvent event) {
            //获取精度
            float acc = event.accuracy;
            //获取光线强度
            float lux = event.values[0];
            L.d("acc ----> " + acc);
            L.d("lux ----> " + lux);
        }

    }
}
