package com.xh.basemodule.di;

import android.content.Context;

import com.xh.basemodule.http.HttpManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @describe
 */
@Module
public class BaseModule {
    public BaseModule(Context context){
        this.context = context;
    }
private Context context;

    /**
     * 全局单例 避免泄漏
     * @return
     */
    @Provides
    @Singleton
    public HttpManager provideHttpManager(){
        return HttpManager.getInstance(context);
    }
}
