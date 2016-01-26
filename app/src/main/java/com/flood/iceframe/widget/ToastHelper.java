package com.flood.iceframe.widget;

import android.content.Context;
import android.widget.Toast;

import javax.inject.Inject;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @author: flood
 * @date: 2016-01-14 16:11
 */
public class ToastHelper {
    @Inject
    public ToastHelper(){

    }

    Toast toast = null;

    public void showToast(Context context, CharSequence text){
        if (toast == null){
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }else{
            toast.setText(text);
        }

        toast.show();
    }

}
