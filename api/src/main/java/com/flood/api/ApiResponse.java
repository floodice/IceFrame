package com.flood.api;

/**
 * 在此写用途
 *
 * @version V1.0 <通用api返回值>
 * @author: flood
 * @date: 2016-03-01 14:02
 */
public class ApiResponse<T> {
    public String event;    // 返回码，0为成功
    public String msg;      // 返回信息
    public T obj;           // 单个对象
    public T objList;       // 数组对象
    public int currentPage; // 当前页数
    public int pageSize;    // 每页显示数量
    public int maxCount;    // 总条数
    public int maxPage;     // 总页数

    // 构造函数，初始化code和msg
    public ApiResponse(String event, String msg) {
        this.event = event;
        this.msg = msg;
    }

    // 判断结果是否成功
    public boolean isSuccess() {
        return event.equals("0");
    }
}
