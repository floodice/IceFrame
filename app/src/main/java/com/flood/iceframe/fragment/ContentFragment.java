package com.flood.iceframe.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.flood.api.ApiImpl;
import com.flood.api.ApiResponse;
import com.flood.iceframe.R;
import com.flood.iceframe.app.IceApplication;
import com.flood.model.NewsDetail;

import butterknife.Bind;


/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @author: flood
 * @date: 2016-02-19 14:44
 */
public class ContentFragment extends BaseFragment implements FragmentInterface, ApiImpl.CallBack<NewsDetail>{
    @Bind(R.id.webView)
    public WebView mWebView;

    private ApiImpl api;

    @Nullable
    @Override
    protected View initViewOnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);

        return view;
    }

    @Override
    protected void dealViewAfterBinder() {
        mWebView.getSettings().setJavaScriptEnabled(false);
        mWebView.getSettings().setSupportZoom(false);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setDefaultFontSize(18);

        initData();
    }

    @Override
    protected void onFirstTimeLaunched() {
        super.onFirstTimeLaunched();
        api = new ApiImpl();
        api.setCallBack(this);

    }

    public void initData() {
        Bundle bundle = getArguments();
        if (bundle != null){
            int id = bundle.getInt("id");
            Log.d("initData","id:"+id);
            api.getNews(id);
//            ZhihuApiManager.getNews(id, mWebView);
        }
    }

    @Override
    public void onSuccess(ApiResponse<NewsDetail> data, int tag) {
       if (data != null && data.obj != null)
       {
           final String body = data.obj.body;
           mWebView.loadDataWithBaseURL(null, body, "text/html", "utf-8", null);
       }
    }

    @Override
    public void onFailed(String msg, int tag) {

    }
}
