package com.flood.iceframe.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;


@SuppressWarnings("FieldCanBeLocal")
public class SamplePagerAdapter extends FragmentPagerAdapter{


    private ArrayList<Fragment> fragments;

    public SamplePagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getArguments().getString("title");
    }


    public void setData(ArrayList<Fragment> data) {
        fragments = data;
    }
}