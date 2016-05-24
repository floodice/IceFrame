package com.flood.iceframe.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ImageView;

import com.debug.L;
import com.flood.core.StartPagePresenter;
import com.flood.iceframe.R;
import com.flood.iceframe.component.ApplicationComponent;
import com.flood.model.StartImage;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import butterknife.Bind;


public class StartActivity extends BaseActivity implements StartPagePresenter.PresenterCallBack {
    private static final int CLOSED_ACTIVITY = 1;
    private static final int SHOW_TIME = 5 * 1000;
    @Bind(R.id.start_img)
    public ImageView start_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    @MyDependency(desc = "my init method1")
    private void init() {
        getAndshowLoginPic();

        ActHandler actHandler = new ActHandler(this);
        actHandler.sendEmptyMessageDelayed(CLOSED_ACTIVITY, SHOW_TIME);
    }

    /**
     * 获取起始页image图片
     */
    private void getAndshowLoginPic() {
        StartPagePresenter startPagePresenter = new StartPagePresenter();
        startPagePresenter.getPicData(getApplicationContext());
        startPagePresenter.setCallBack(this);
    }

    @Override
    public void actHandlerMessage(Message msg){
        switch (msg.what) {
            case CLOSED_ACTIVITY:
                intoMainAct();
                break;
        }
    }

    @Override
    public void onShowPic(StartImage image) {
        ImageLoader loader = ((IceApplication) getApplication()).getImageLoader();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)                               //启用内存缓存
                .cacheOnDisk(true)                                 //启用外存缓存
                .considerExifParams(true)                          //启用EXIF和JPEG图像格式
                .build();
        String img = image.img;
        if (!TextUtils.isEmpty(img)) {
            loader.displayImage(img, start_img, options);
        }

    }


    private void intoMainAct() {
        Annotation[] annotations = getClass().getAnnotations();
        for (Annotation annotation : annotations){
            System.out.println(annotation);
            if (annotation instanceof MyDependency)
            {
                String text = ((MyDependency)annotation).desc();
                L.d(text);
            }
        }
        try {
            Method method = getClass().getMethod("init");
            Annotation[] annotations1 = method.getAnnotations();
            for (Annotation annotation : annotations1){
                if (annotation instanceof MyDependency){
                    String text = ((MyDependency)annotation).desc();
                    L.d(text);
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent();
        intent.setClass(this, SlidingActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {

    }
}
