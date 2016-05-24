package com.flood.iceframe.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flood.iceframe.app.IceApplication;

import butterknife.ButterKnife;

/**
 * 在此写用途
 *
 * @version V1.0 <所有fragment基类>
 * @author: flood
 * @date: 2016-02-17 14:32
 */
public abstract class BaseFragment extends Fragment {

    private Bundle savedState;
    protected FragmentManager fragmentManager;
    protected FragmentActivity activity;

    public BaseFragment() {
        super();
        if (getArguments() != null){
            setArguments(getArguments());
        }else{
            setArguments(new Bundle());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = getActivity();
//        setApp((IceApplication) activity.getApplication());
        fragmentManager = activity.getSupportFragmentManager();

        if (!restoreStateFromArguments()) {
            // First Time, Initialize something here
            onFirstTimeLaunched();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initViewOnCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);
        dealViewAfterBinder();
        return view;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
        saveStateToArguments();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveStateToArguments();
    }

    private void saveStateToArguments() {
        if (getView() != null) {
            savedState = saveState();
        }

        if (savedState != null) {
            Bundle b = getArguments();
            b.putBundle("internalSavedViewState", savedState);
        }
    }

    private boolean restoreStateFromArguments() {
        Bundle b = getArguments();
        savedState = b.getBundle("internalSavedViewState");
        if (savedState != null) {
            restoreState();
            return true;
        }
        return false;
    }

    private void restoreState() {
        if (savedState != null) {
            onRestoreState(savedState);
        }
    }

    private Bundle saveState() {
        Bundle state = new Bundle();
        onSaveState(state);
        return state;
    }

    protected void onRestoreState(Bundle savedInstanceState){

    }

    protected void onSaveState(Bundle outState){

    }

    protected void onFirstTimeLaunched(){

    }

    protected abstract View initViewOnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    protected abstract void dealViewAfterBinder();
}
