package com.flood.api;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.debug.L;
import com.flood.model.LatestNew;
import com.flood.model.NewsDetail;
import com.flood.model.StartImage;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.util.AndroidUtils;
import com.util.NetworkUtils;

import java.io.File;
import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @author: flood
 * @date: 2016-03-01 11:10
 */
public class ApiImpl implements Api {
    private CallBack callBack;
    private Context context;

    private static final int DEFAULT_TAG = 0;

    private interface ApiManagerService {
        @Headers("Cache-Control: public, max-age=60")
        @GET(DataConstants.LATEST_NEWS)
        Observable<LatestNew> getLatestNews();

        @GET(DataConstants.LOGIN_IMAGE + "1080*1776")
        Observable<StartImage> getStartImage();

        @GET(DataConstants.NEWS_CONTENT)
        Observable<NewsDetail> getNewsDetail(@Path("id") int id);

    }

    private final static Retrofit retrofit = new Retrofit.Builder().baseUrl(DataConstants.HOST)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).build();

    private static final ApiManagerService apiManager = retrofit.create(ApiManagerService.class);

    public void setCallBack(CallBack callBack){
        this.callBack = callBack;
    }
    public interface CallBack<T>{
        void onSuccess(ApiResponse<T> data, int tag);
        void onFailed(String msg, int tag);
    }

    public void clear(){
        callBack = null;
        retrofit.client().interceptors().clear();
    }

    public void init(Context context){
        this.context = context;
        File httpCacheDirectory = new File(AndroidUtils.getCacheDir(context), "responses");
        //设置缓存 10M
        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
        retrofit.client().setCache(cache);
        retrofit.client().interceptors().add(interceptor);

    }

    private final Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if(!NetworkUtils.isConnected(context)){
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                L.w("no network");
            }
            Response originalResponse = chain.proceed(request);
            if(NetworkUtils.isConnected(context)){
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            }else{
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    /**
     * 获取最新新闻
     */
    public void getLatestNews() {
        if (callBack == null){
            L.e("callBack is null");
            return;
        }
        Observable<LatestNew> latestNewObservable = apiManager.getLatestNews();
        latestNewObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LatestNew>() {
                    @Override
                    public void onCompleted() {
                        Log.d("getLatestNews", "onCompleted:");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("getLatestNews", "onError:" + e.getMessage());
                        callBack.onFailed(e.getMessage(), DEFAULT_TAG);
                    }

                    @Override
                    public void onNext(LatestNew latestNew) {
                        Log.d("getLatestNews", "onNext:" + (latestNew == null));
                        ApiResponse<LatestNew> response = new ApiResponse<LatestNew>("0","");
                        response.obj = latestNew;
                        callBack.onSuccess(response, DEFAULT_TAG);
                    }
                });
    }

    public void getStartImage() {
        if (callBack == null){
            L.e("callBack is null");
            return;
        }
        Observable<StartImage> latestNewObservable = apiManager.getStartImage();
        latestNewObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StartImage>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("getStartImage", "onError:" + e.getMessage());
                        callBack.onFailed(e.getMessage(), DEFAULT_TAG);
                    }

                    @Override
                    public void onNext(StartImage startImage) {
                        ApiResponse<StartImage> response = new ApiResponse<StartImage>("0", "");
                        response.obj = startImage;
                        callBack.onSuccess(response, DEFAULT_TAG);
                    }
                });

    }

    public void getNews(int id) {
        if (callBack == null){
            L.e("callBack is null");
            return;
        }
        Observable<NewsDetail> latestNewObservable = apiManager.getNewsDetail(id);
        latestNewObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewsDetail>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("getNews", "onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(NewsDetail data) {
                        ApiResponse<NewsDetail> response = new ApiResponse<NewsDetail>("0","");
                        response.obj = data;
                        callBack.onSuccess(response, DEFAULT_TAG);
                    }
                });
    }

//    Interceptor interceptor = new Interceptor() {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request request = chain.request();
//            String path = request.url().toString();
//            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).url(path).build();
//            return null;
//        }
//    }
}
