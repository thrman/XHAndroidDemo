package com.xh.basemodule.http.request;

import io.reactivex.disposables.Disposable;

/**
 * @describe 管理http请求 方便取消订阅
 */
public interface RequestManager<T> {
    /**
     * 添加
     *
     * @param tag
     * @param disposable
     */
    void add(T tag, Disposable disposable);

    /**
     * 移除
     *
     * @param tag
     */
    void remove(T tag);

    /**
     * 取消
     *
     * @param tag
     */
    void cancel(T tag);

    /**
     * 取消全部
     */
    void cancelAll();
}
