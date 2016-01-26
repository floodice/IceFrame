package com.flood.iceframe;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.flood.iceframe.app.BaseActivity;
import com.flood.iceframe.app.IceApplication;
import com.flood.iceframe.module.ActivityComponent;
import com.flood.iceframe.module.ActivityModule;
import com.flood.iceframe.module.ApplicationComponent;
import com.flood.iceframe.module.DaggerActivityComponent;
import com.flood.iceframe.module.api.ApiManager;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxCompoundButton;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

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
                defer.subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("Subscriber", "onNext:" + s);
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
