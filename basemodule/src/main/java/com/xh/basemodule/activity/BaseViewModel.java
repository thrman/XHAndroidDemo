package com.xh.basemodule.activity;

import android.databinding.BaseObservable;

import com.xh.basemodule.app.BaseApplication;
import com.xh.basemodule.http.HttpManager;

import javax.inject.Inject;

/**
 * viewmodel的基类,在xml里声明
 * @describe
 */
public class BaseViewModel extends BaseObservable {

    @Inject
    protected HttpManager httpManager;//用于发起网络请求

    public BaseViewModel(){
        BaseApplication.getApp().getComponent().inject(this);

    }
}
