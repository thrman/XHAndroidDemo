package com.xh.basemodule.http;


import com.xh.basemodule.http.callback.ErrorConsumer;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * 处理接口回调的分发逻辑
 *
 * @param <T>
 */
public class RxjavaUtils<T> {
    public static <T> ObservableTransformer<T, T> transformer() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> ObservableTransformer<T, T> handleResult() {

        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable.flatMap(new Function<T, ObservableSource<? extends T>>() {
                    @Override
                    public ObservableSource<? extends T> apply(T t) throws Exception {
                        return createData(t);
                    }
                });
            }
        };
    }

    /**
     * @param t
     * @param <T>
     * @return 返回观察者
     */
    private static <T> ObservableSource createData(final T t) {
        return Observable.create(new ObservableOnSubscribe<Object>() {

            @Override
            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                try {
                    e.onNext(t);
                } catch (Exception e1) {
                    ErrorConsumer.DqException exception = new ErrorConsumer.DqException();
                    if (e1 instanceof HttpException) {
                        //okhttp端的异常
                        int code = ((HttpException) e1).code();
                        exception.setCode(code + "");
                        //避免暴露异常内容
                        exception.setMessage(((HttpException) e1).message());
                    } else {
                        //其他异常
                        exception.setCode("");
                        exception.setMessage("网络异常");
                    }
                    exception.setSuccess(0);
                    e.onError(exception);
                } finally {
                    e.onComplete();
                }
            }
        });
    }
}
