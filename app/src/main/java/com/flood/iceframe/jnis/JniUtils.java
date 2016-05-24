package com.flood.iceframe.jnis;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @author: flood
 * @date: 2016-03-09 10:59
 */
public class JniUtils {
    public native String getCLanguageString();

    static {
        System.loadLibrary("JniUtils");
    }
}
