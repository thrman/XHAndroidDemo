package com.xh.basemodule.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxFragment;
import com.xh.basemodule.app.BaseApplication;
import com.xh.basemodule.http.HttpManager;

import javax.inject.Inject;

/**
 * @describe
 */

public abstract class BaseLazyFragment extends RxFragment {
    private boolean hasData = false;
    //是否可见
    public boolean isVisable;
    // 标志位，标志Fragment已经初始化完成。
    public boolean isPrepared = false;
    @Inject
    protected HttpManager httpManager;//用于发起网络请求
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication application = BaseApplication.getApp();
        application.getComponent().inject(this);
    }

    /**
     * 实现Fragment数据的缓加载
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisable = true;
            onVisible();
        } else {
            isVisable = false;
            onInVisible();
        }
    }
    protected void onInVisible() {
    }
    protected void onVisible() {
        //加载数据
        loadData();
    }
    public abstract void loadData();

    public boolean isHasData() {
        return hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }
}
