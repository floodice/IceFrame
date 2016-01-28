package com.flood.iceframe.app;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.flood.iceframe.R;
import com.flood.iceframe.app.BaseActivity;
import com.flood.iceframe.app.IceApplication;
import com.flood.iceframe.config.Constants;
import com.flood.iceframe.component.ActivityComponent;
import com.flood.iceframe.module.ActivityModule;
import com.flood.iceframe.component.ApplicationComponent;
import com.flood.iceframe.module.DaggerActivityComponent;
import com.flood.iceframe.module.api.ApiManager;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

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
                Observable.just(1, 2, 3, 4, 5)
                        .scan(new Func2<Integer, Integer, Integer>() {
                            @Override
                            public Integer call(Integer integer, Integer integer2) {
                                Log.d("Subscriber", "integer:" + integer);
                                Log.d("Subscriber", "integer2:" + integer2);
                                return integer2;
                            }
                        })
                        .subscribe(new Subscriber<Integer>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Integer integer) {
                                Log.d("Subscriber", "onNext:" + integer);
                            }
                        });
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

    public void helloDagger() {
        this.component.getToastHelper().showToast(this, "hello");
    }

    public void getWeather() {
        String[] items = new String[]{"a", "b", "c", "d"};
        this.component.getApiManager().subscriberFrom(items);
    }
}
