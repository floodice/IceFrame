package com.flood.iceframe.app;

import android.app.Application;

import com.flood.iceframe.component.ApplicationComponent;
import com.flood.iceframe.module.ApplicationModule;
import com.flood.iceframe.module.DaggerApplicationComponent;


import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @author: flood
 * @date: 2016-01-14 15:48
 */
public class IceApplication extends Application {
    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        this.component = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        this.component.injectApplication(this);

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        };

        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        };

        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hello");
                subscriber.onCompleted();
            }

        });

        observable.subscribe(subscriber);
    }
    

    public ApplicationComponent getComponent(){
        return component;
    }
}
