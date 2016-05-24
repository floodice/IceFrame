package com.flood.iceframe.adapter;

import com.flood.model.DataBean;

import java.util.List;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @author: flood
 * @date: 2016-02-17 16:49
 */
public interface AdapterInterface {
    void addData(List<? extends DataBean> data);
}
