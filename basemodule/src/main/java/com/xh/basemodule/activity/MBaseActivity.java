package com.xh.basemodule.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;
import com.xh.basemodule.app.BaseApplication;
import com.xh.basemodule.http.HttpManager;
import com.xh.basemodule.utils.CleanLeakUtils;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * #2,Eventbus的注册和解注册在MBaseActivity实现.
 */
public class MBaseActivity extends FragmentActivity implements LifecycleProvider<ActivityEvent> {
    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();
    @Inject
    protected HttpManager httpManager;
    protected DisplayMetrics mOutMetrics = new DisplayMetrics();

    protected Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(ActivityEvent.CREATE);
        try {
            EventBus.getDefault().register(this);
        } catch (Exception e) {
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindowManager().getDefaultDisplay().getMetrics(mOutMetrics);
        context = this;
        BaseApplication application = (BaseApplication) getApplication();
        application.getComponent().inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //必须在此生命周期绑定 不然会影响前面的界面
        httpManager.setLifecycle(this);
        lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        lifecycleSubject.onNext(ActivityEvent.STOP);
    }

    @Override
    protected void onDestroy() {
        CleanLeakUtils.fixInputMethodManagerLeak(this);
        super.onDestroy();
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        try {
            EventBus.getDefault().unregister(this);
        } catch (Exception e) {
        }
    }

    @Override
    @NonNull
    @CheckResult
    public Observable<ActivityEvent> lifecycle() {
        return  lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject);
    }
}
