package com.flood.iceframe.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flood.iceframe.R;
import com.flood.iceframe.adapter.SamplePagerAdapter;
import com.flood.iceframe.app.IceApplication;
import com.flood.iceframe.component.ApplicationComponent;
import com.flood.iceframe.widget.SlidingTabLayout;

import java.util.ArrayList;

/**
 * 主体页面
 */
public class SlidingTabFragment extends BaseFragment {

    static final String LOG_TAG = "SlidingTabsBasicFragment";
    private ArrayList<Fragment> data = new ArrayList<>();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        SamplePagerAdapter adapter = new SamplePagerAdapter(getFragmentManager());

        LatestNewsFragment latestNewsFragment = new LatestNewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title","NEWS");
        latestNewsFragment.setArguments(bundle);
        data.clear();
        data.add(latestNewsFragment);
        adapter.setData(data);
        mViewPager.setAdapter(adapter);

        SlidingTabLayout mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    @Override
    protected View initViewOnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sliding_tab, container, false);
    }

    @Override
    protected void dealViewAfterBinder() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        data.clear();
    }
}