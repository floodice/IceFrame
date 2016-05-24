package com.flood.iceframe.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.flood.iceframe.R;
import com.flood.iceframe.component.ActivityComponent;
import com.flood.iceframe.component.ApplicationComponent;
import com.flood.iceframe.component.DaggerActivityComponent;
import com.flood.iceframe.config.Constants;
import com.flood.iceframe.module.ActivityModule;
import com.flood.iceframe.module.api.ApiManager;
import com.flood.iceframe.widget.pull_refresh_view.PullToRefreshView;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;
import rx.functions.Func2;

public class MainActivity extends BaseActivity {

    private ActivityComponent component;

    @Bind(R.id.editText)
    EditText editText;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.image)
    ImageView imageView;

    String text = "one";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Constants.STRICT_MODE){
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }
        component = DaggerActivityComponent.builder()
                .applicationComponent(((IceApplication) getApplication()).getComponent())
                .activityModule(new ActivityModule(this)).apiManager(new ApiManager()).build();
        component.injectActivity(this);

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        imageLoader.displayImage("http://img30.360buyimg.com/da/jfs/t2227/61/2062744775/39778/bed10026/56a96947N1fca5e98.jpg",
                imageView);

        final PullToRefreshView pullToRefreshView = (PullToRefreshView)findViewById(R.id.pull_to_refresh);
        pullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {

            @Override
            public void onRefresh() {
                pullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pullToRefreshView.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        ListView listView = (ListView)findViewById(R.id.list_view);
        String[] items = new String[]{"a","b","c","d","e","f","g","h","i","j","k","l","m","n"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        RxTextView.textChanges(editText)
                .subscribe(new Subscriber<CharSequence>() {
                    @Override
                    public void onCompleted() {
                        Log.d("Subscriber", "onCompleted:");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Subscriber", "onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(CharSequence charSequence) {
                        Log.d("Subscriber", "onNext:" + charSequence);
                        textView.setText(charSequence);
                    }
                });
        final Observable<String> defer = Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                Log.d("Subscriber", "call:");
                return Observable.just("asd");
            }
        });


        RxView.clicks(button).subscribe(new Subscriber<Void>() {
            @Override
            public void onStart() {
                super.onStart();
                text = "two";
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Void aVoid) {
                Log.d("Subscriber", "onNext:");
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SlidingActivity.class);
                startActivity(intent);
            }
        });


    }

//    @OnClick(R.id.button)
//    public void buttonClick() {
//        getWeather();
//    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {

    }

    @Override
    protected void onDestroy() {
        button.setText("destory");
        super.onDestroy();

    }

    public void helloDagger() {
        this.component.getToastHelper().showToast(this, "hello");
    }

    public void getWeather() {
        String[] items = new String[]{"a", "b", "c", "d"};
        this.component.getApiManager().subscriberFrom(items);
    }
}
