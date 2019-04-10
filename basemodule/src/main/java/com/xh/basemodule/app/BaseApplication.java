package com.xh.basemodule.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.xh.basemodule.di.BaseComponent;
import com.xh.basemodule.di.BaseModule;
import com.xh.basemodule.di.DaggerBaseComponent;
import com.xh.basemodule.utils.ActivityManagerUtils;

/**
 * @describe
 */
public class BaseApplication extends Application {
    static BaseApplication instance;
    private BaseComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        component = DaggerBaseComponent.builder().baseModule(new BaseModule(this)).build();
        initGlobeActivity();
    }


    public BaseComponent getComponent() {
        return component;
    }

    public static BaseApplication getApp() {
        return instance;
    }

    /**
     * Application通过ActivityLifecycleCallbacks使用接口提供了一套回调方法，用于让开发者对Activity的生命周期事件进行集中处理
     */
    private void initGlobeActivity() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }

            /** Unused implementation **/
            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
                ActivityManagerUtils.getInstance().setCurrentActivity(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }
        });
    }

}
