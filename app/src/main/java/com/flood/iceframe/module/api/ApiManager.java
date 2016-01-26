package com.flood.iceframe.module.api;

import android.util.Log;

import com.flood.iceframe.model.HotWord;
import com.flood.iceframe.model.WeatherData;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @author: flood
 * @date: 2016-01-20 15:30
 */
@Module
public class ApiManager {
    private interface ApiManagerService {
        @GET("/weather")
        WeatherData getWeather(@Query("q") String place, @Query("units") String units);

        @GET("/api/bookapp/hot_word.m")
        Observable<HotWord> getHotWord(@Query("cid") String cid);
    }

    @Inject
    @Singleton
    public ApiManager() {

    }

    private final static Retrofit retrofit = new Retrofit.Builder().baseUrl("http://120.197.138.35")
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).build();

    private static final ApiManagerService apiManager = retrofit.create(ApiManagerService.class);

    public static Observable<WeatherData> getWeatherData(final String city) {
        return Observable.create(new Observable.OnSubscribe<WeatherData>() {
            @Override
            public void call(Subscriber<? super WeatherData> subscriber) {
                subscriber.onNext(apiManager.getWeather(city, "metric"));
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

    Subscriber<WeatherData> subscriber = new Subscriber<WeatherData>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(WeatherData weatherData) {
            Log.d("onNext", "weatherData:" + weatherData.name);
        }
    };

    public void setMySubscriber() {
        Observable<WeatherData> observable = getWeatherData("Beijing");
        observable.subscribe(subscriber);
    }


    Subscriber<HotWord> subscriberHot = new Subscriber<HotWord>() {
        @Override
        public void onCompleted() {
            Log.d("onNext", "weatherData:onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            Log.d("onNext", "weatherData:" + e.getMessage());
        }

        @Override
        public void onNext(HotWord hotWord) {
            Log.d("onNext", "weatherData:" + hotWord.hotWord);
        }
    };

    public void setHotSubscriber() {
        Observable<HotWord> hotWordObservable = apiManager.getHotWord("eef_easou_book");
        hotWordObservable.map(new Func1<HotWord, HotWord>() {
            @Override
            public HotWord call(HotWord hotWord) {
                hotWord.hotWord = "map";
                return hotWord;
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriberHot);
    }

    public void subscriberFrom(String[] items) {
        Observable<String> observable = Observable.from(items);
        observable
                .take(3)
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d("doOnNext", "s:" + s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d("onCompleted", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("onError", "e:" + e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("onNext", "s:" + s);
                    }
                });
    }
}
