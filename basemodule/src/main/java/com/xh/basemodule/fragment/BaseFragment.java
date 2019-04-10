package com.xh.basemodule.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xh.basemodule.app.BaseApplication;
import com.xh.basemodule.http.HttpManager;

import javax.inject.Inject;

/**
 * Fragment 基础类.
 */

public class BaseFragment extends Fragment {
    protected CoordinatorLayout main;
    protected LinearLayout mBaseLayout;
    @Inject
    protected HttpManager httpManager;//用于发起网络请求
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication application = BaseApplication.getApp();
        application.getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        main = new CoordinatorLayout(getActivity());
//        main.setBackgroundColor(getResources().getColor(R.color.app_background));
        mBaseLayout = new LinearLayout(getActivity());
        CoordinatorLayout.LayoutParams mainLp = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mBaseLayout.setLayoutParams(mainLp);
        ViewGroup.LayoutParams mainParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        main.setLayoutParams(mainParams);
        main.addView(mBaseLayout);
        return main;
    }

}
