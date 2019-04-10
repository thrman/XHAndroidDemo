package com.xh.basemodule.utils;

import android.app.Activity;

import java.lang.ref.WeakReference;

/**
 * Activity栈顶得工具类
 */
public class ActivityManagerUtils {
    private static ActivityManagerUtils sInstance = new ActivityManagerUtils();
    private WeakReference<Activity> sCurrentActivityWeakRef;


    private ActivityManagerUtils() {

    }

    public static ActivityManagerUtils getInstance() {
        return sInstance;
    }

    /**
     * 获得栈中最顶层的Activity
     *
     * @return
     */
    public Activity getCurrentActivity() {
        Activity currentActivity = null;
        if (sCurrentActivityWeakRef != null) {
            currentActivity = sCurrentActivityWeakRef.get();
        }
        return currentActivity;
    }

    /**
     * 设置activity对象存取
     *
     * @param activity
     */
    public void setCurrentActivity(Activity activity) {
        sCurrentActivityWeakRef = new WeakReference<Activity>(activity);
    }

}
