package com.flood.core;

import android.content.Context;

import com.flood.api.ApiImpl;
import com.flood.api.ApiResponse;
import com.flood.model.StartImage;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @author: flood
 * @date: 2016-03-03 15:05
 */
public class StartPagePresenter implements Presenter, ApiImpl.CallBack<StartImage>{
    private PresenterCallBack callBack;

    @Override
    public void onSuccess(ApiResponse<StartImage> data, int tag) {
        if (data != null && callBack != null){
            callBack.onShowPic(data.obj);
        }
    }

    @Override
    public void onFailed(String msg, int tag) {


    }

    public void setCallBack(PresenterCallBack callBack){
        this.callBack = callBack;
    }

    public interface PresenterCallBack{
        void onShowPic(StartImage image);
    }

    public void getPicData(Context context){
        ApiImpl api = new ApiImpl();
//        api.init(context);
        api.setCallBack(this);
        api.getStartImage();
    }
}
