package com.xh.basemodule.fragment;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;


/**
 */
public abstract class BaseDataBindFragment<T extends ViewDataBinding> extends BaseLazyFragment {

    protected View mView;
    protected Context mContext;
    public T dataBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if(mView==null) {
            try {
                dataBinding = getBindingView(inflater, container);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mView = ((ViewDataBinding) dataBinding).getRoot();
                EventBus.getDefault().register(this);
            isPrepared = true;
            initView();
        }
        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    public abstract @LayoutRes int getLayoutId();

    /**
     * 在onCreateView周期类调用
     */
    public abstract void initView();


    public T getBindingView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
    }
}
