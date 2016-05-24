package com.flood.iceframe.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.flood.iceframe.R;
import com.flood.iceframe.component.ApplicationComponent;
import com.github.johnpersano.supertoasts.SuperActivityToast;
import com.github.johnpersano.supertoasts.SuperToast;
import com.github.johnpersano.supertoasts.util.OnClickWrapper;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.OnClick;

public class Main2Activity extends BaseActivity {

    @Bind(R.id.button2)
    Button button;
    private WeakReference<Activity> reference;

    private static final String ID_ONDISMISSWRAPPER = "id_ondismisswrapper";
    private static final String ID_UNDO_WRAPPER = "id_undo_wrapper";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        reference = new WeakReference<Activity>(this);

    }

    SuperActivityToast superActivityToast;
    @OnClick(R.id.button2)
    public void btnClick() {
        Activity activity = reference.get();
        if (superActivityToast != null)
        {
        }
        superActivityToast = new SuperActivityToast(activity, SuperToast.Type.BUTTON);
        superActivityToast.setText("Hello world!");
        superActivityToast.setDuration(SuperToast.Duration.LONG);
        superActivityToast.setBackground(SuperToast.Background.BLUE);
        superActivityToast.setButtonIcon(SuperToast.Icon.Dark.UNDO, "UNDO");
//        superActivityToast.setOnDismissWrapper(onDismissWrapper);
        superActivityToast.setOnClickWrapper(onClickWrapper);
        superActivityToast.show();
    }

//    OnDismissWrapper onDismissWrapper = new OnDismissWrapper(ID_ONDISMISSWRAPPER, new SuperToast.OnDismissListener() {
//
//        @Override
//        public void onDismiss(View view) {
//
//            Log.d(Main2Activity.this.getClass().toString(), "On Dismiss");
//
//        }
//    });

    OnClickWrapper onClickWrapper = new OnClickWrapper(ID_UNDO_WRAPPER, new SuperToast.OnClickListener() {
        @Override
        public void onClick(View view, Parcelable parcelable) {
            Log.d("onClickWrapper","onClickWrapper");
        }
    });

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SuperActivityToast.cancelAllSuperActivityToasts();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main2;
    }

    @Override
    protected void setupActivityComponent(ApplicationComponent applicationComponent) {

    }
}
