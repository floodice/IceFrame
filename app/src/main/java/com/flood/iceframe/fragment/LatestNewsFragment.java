package com.flood.iceframe.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.widget.PullRefreshLayout;
import com.flood.api.ApiImpl;
import com.flood.api.ApiResponse;
import com.flood.iceframe.R;
import com.flood.iceframe.adapter.LatestNewsAdapter;
import com.flood.iceframe.adapter.LatestNewsRecycleAdapter;
import com.flood.iceframe.adapter.NormalRecyclerViewAdapter;
import com.flood.iceframe.adapter.TopCardAdapter;
import com.flood.iceframe.component.DaggerLatestNewsComponent;
import com.flood.iceframe.component.LatestNewsComponent;
import com.flood.iceframe.module.LatestNewsP;
import com.flood.model.DataBean;
import com.flood.model.LatestNew;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 *
 */
public class LatestNewsFragment extends BaseFragment implements ApiImpl.CallBack<LatestNew>, NormalRecyclerViewAdapter.OnItemClickListener{

    @Bind(R.id.recycler_view)
    public RecyclerView recycler_view;

    @Bind(R.id.swipeRefreshLayout)
    public PullRefreshLayout layout;

//    private LatestNewsAdapter adapter;
    private TopCardAdapter topCardAdapter;
    private NormalRecyclerViewAdapter recyclerViewAdapter;
//    private ZhihuApiComponent zhihuApiComponent;

    private ApiImpl api;

    @Inject
    public LatestNewsP latestNewsP;

    public LatestNewsFragment() {
    }

    @Override
    protected View initViewOnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_latestnews, container, false);
        return view;
    }

    @Override
    protected void dealViewAfterBinder() {
        layout.setRefreshStyle(PullRefreshLayout.STYLE_WATER_DROP);
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                layout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (layout != null){
                            layout.setRefreshing(false);
                        }
                    }
                }, 3000);
            }
        });

        LatestNewsComponent latestNewsComponent = DaggerLatestNewsComponent.create();
        latestNewsComponent.inject(this);


        Context context = getActivity().getApplicationContext();
//        StackViewVertical stackViewVertical = new StackViewVertical(context);
//        recycler_view.addHeaderView(stackViewVertical);

        topCardAdapter = new TopCardAdapter(context);
//        stackViewVertical.setAdapter(topCardAdapter);
        recycler_view.setLayoutManager(new LinearLayoutManager(context));

//        adapter = new LatestNewsAdapter(context);
        recyclerViewAdapter = new LatestNewsRecycleAdapter(context);
        recyclerViewAdapter.setOnItemClickListener(this);
        recycler_view.setAdapter(recyclerViewAdapter);
        api = new ApiImpl();
        api.init(getContext());
        api.setCallBack(this);

        api.getLatestNews();
//        zhihuApiManager.getLatestNews(adapter, topCardAdapter);
//        news_list.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        api.clear();
        recyclerViewAdapter.unBindKnife();
//        adapter.clearData();
    }



    @Override
    public void onSuccess(ApiResponse<LatestNew> data, int tag) {
        LatestNew latestNew = data.obj;
        List<LatestNew.News> newses = latestNew.stories;
//        adapter.addData(newses);
        recyclerViewAdapter.addData(newses);
//        topCardAdapter.addData(latestNew.top_stories);
    }

    @Override
    public void onFailed(String msg, int tag) {

    }

    @Override
    public void onItemClick(DataBean data) {
        latestNewsP.setItemClick((LatestNew.News) data, fragmentManager);
    }
}
