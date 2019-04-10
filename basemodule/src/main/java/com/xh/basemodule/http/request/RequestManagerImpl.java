package com.xh.basemodule.http.request;

import android.util.ArrayMap;

import java.util.Set;

import io.reactivex.disposables.Disposable;

/**
 * @describe
 */
public class RequestManagerImpl implements RequestManager<Object> {
    private ArrayMap<Object,Disposable> disposables;
    public static RequestManagerImpl getInstance(){
        return InstanceHolder.instance;
    }
    private RequestManagerImpl(){
        disposables = new ArrayMap<>();
    }
    static class InstanceHolder{
       static volatile RequestManagerImpl instance = new RequestManagerImpl();
    }
    @Override
    public void add(Object tag, Disposable disposable) {
        disposables.put(tag, disposable);
    }

    /**
     * 根据tag移除请求
     * @param tag
     */
    @Override
    public void remove(Object tag) {
        if(!disposables.isEmpty()){
            disposables.remove(tag);
        }
    }

    @Override
    public void cancel(Object tag) {
        if(!disposables.isEmpty()&&tag!=null){
            if(!disposables.get(tag).isDisposed()){
                disposables.get(tag).dispose();
            }
            disposables.remove(tag);

        }
        return;
    }

    @Override
    public void cancelAll() {
        if(!disposables.isEmpty()){
            //遍历取消所有请求
            Disposable disposable;
            Set<Object> keySet = disposables.keySet();
            for (Object key : keySet) {
                disposable = disposables.get(key);
                if (!disposable.isDisposed()) {
                    disposable.dispose();
                }
            }
            disposables.clear();
        }
            return;
    }
    /**
     * 根据tag判断是否取消了请求
     *
     * @param tag
     * @return
     */
    public boolean isDisposed(Object tag) {
        if (disposables.isEmpty() || disposables.get(tag) == null) {
            return true;
        }
        return disposables.get(tag).isDisposed();
    }

}
