package com.flood.api;

import com.flood.model.LatestNew;
import com.flood.model.NewsDetail;
import com.flood.model.StartImage;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @author: flood
 * @date: 2016-02-17 17:11
 */
public class ZhihuApiManager {
    private interface ApiManagerService {
        @GET(DataConstants.LATEST_NEWS)
        Observable<LatestNew> getLatestNews();

        @GET(DataConstants.LOGIN_IMAGE + "1080*1776")
        Observable<StartImage> getStartImage();

        @GET(DataConstants.NEWS_CONTENT)
        Observable<NewsDetail> getNewsDetail(@Path("id") int id);

    }

    public ZhihuApiManager getProvideZhihuApi(){
        return new ZhihuApiManager();
    }
    public ZhihuApiManager() {

    }

    private final static Retrofit retrofit = new Retrofit.Builder().baseUrl(DataConstants.HOST)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).build();

    private static final ApiManagerService apiManager = retrofit.create(ApiManagerService.class);

//    public void getLatestNews(final LatestNewsAdapter adapter, final TopCardAdapter topCardAdapter) {
//        Observable<LatestNew> latestNewObservable = apiManager.getLatestNews();
//        latestNewObservable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<LatestNew>() {
//                    @Override
//                    public void onCompleted() {
//                        Log.d("getLatestNews", "onCompleted:");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d("getLatestNews", "onError:" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(LatestNew latestNew) {
//                        Log.d("getLatestNews", "onNext:" + (latestNew == null));
//                        if (latestNew != null) {
////                            LatestNewsAdapter adapter = reference.get();
//                            Log.d("getLatestNews", "onNext:stories:" + latestNew.stories.size());
//                            List<LatestNew.News> data = latestNew.stories;
//                            List<LatestNew.TopStories> tops = latestNew.top_stories;
//                            adapter.setData(data);
//                            topCardAdapter.setData(tops);
//                        }
//                    }
//                });
//    }

//    public static void getStartImage(final ImageView imageView, final ImageLoader loader) {
//
//        Observable<StartImage> latestNewObservable = apiManager.getStartImage();
//        latestNewObservable.subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<StartImage>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d("getStartImage", "onError:" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(StartImage startImage) {
//                        if (startImage != null) {
//                            String img = startImage.img;
//                            if (!TextUtils.isEmpty(img)) {
//                                loader.displayImage(img, imageView);
//                            }
//                        }
//                    }
//                });
//    }
//
//    public static void getNews(int id, final WebView webView) {
//
//        Observable<NewsDetail> latestNewObservable = apiManager.getNewsDetail(id);
//        latestNewObservable.subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<NewsDetail>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d("getNews", "onError:" + e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(NewsDetail data) {
//                        if (data != null) {
//                            final String body = data.body;
//                            webView.loadDataWithBaseURL(null, body, "text/html", "utf-8", null);
//
//                        }
//                    }
//                });
//    }

}
