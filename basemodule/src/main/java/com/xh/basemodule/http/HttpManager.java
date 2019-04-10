package com.xh.basemodule.http;

import android.content.Context;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.orhanobut.logger.Logger;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.xh.basemodule.http.api.BaseApi;
import com.xh.basemodule.http.callback.ErrorConsumer;
import com.xh.basemodule.http.callback.SuccessConsumer;
import com.xh.basemodule.http.intercept.DqIntercept;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * http访问对象，封装了retrofit,所有的url封装在专门处理url的类里
 *
 * @describe 网络请求工具类
 */
public class HttpManager {

    //静态内部类单例 仅次于枚举的单例

    private Retrofit retrofit;
    private BaseApi api;
    private CompositeDisposable sCompositeDisposable;
    private OkHttpClient client;
    private Context mContext;

    public LifecycleProvider getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(LifecycleProvider lifecycle) {
        this.lifecycle = lifecycle;
    }

    private LifecycleProvider lifecycle;

    public static HttpManager getInstance(Context context) {
        HttpManager httpManager = HttpManagerBuilder.manager;
        if (httpManager.mContext == null) {
            httpManager.mContext = context;
            httpManager.init(context);
        }
        return httpManager;
    }

    public static class HttpManagerBuilder {

        private static HttpManager manager = new HttpManager();

    }

    private HttpManager() {

    }

    /**
     * okhttp初始化 retrofit初始化
     *
     * @param context
     */
    private void init(Context context) {
//        this.mContext = context;
        //打印请求体响应体
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Logger.e("retrofitBack = " + message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).
                writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(new DqIntercept(context))
                .addInterceptor(loggingInterceptor)
                .build();
        //一个host 同时最多10个请求
        client.dispatcher().setMaxRequestsPerHost(10);
        //初始化retrofit 创建api对象
        retrofit = new Retrofit.Builder().client(client).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiConfig.BaseUrl).build();
        sCompositeDisposable = new CompositeDisposable();
        api = retrofit.create(BaseApi.class);
//    retrofit.create()

    }

    public void addIntercept(Interceptor intercept) {
        client = client.newBuilder().addNetworkInterceptor(intercept).build();
        retrofit = retrofit.newBuilder().client(client).build();
    }


    /**
     * 封装的get请求
     *
     * @param url
     * @param params
     * @param callback
     */
    public Observable get(String url, Map<String, Object> params, SuccessConsumer callback, ErrorConsumer errorCallback) {
        Observable observable = api.get(url, params);
        sCompositeDisposable.add(observable.flatMap(
                new Function<String, ObservableSource<JsonObject>>() {
                    @Override
                    public ObservableSource<JsonObject> apply(String s) {
                        JsonParser jsonParser = new JsonParser();

                        return Observable.just(jsonParser.parse(s).getAsJsonObject());
                    }
                }
        ).compose(RxjavaUtils.<JsonObject>transformer()).compose(RxjavaUtils.<JsonObject>handleResult()).subscribe(callback, errorCallback));
        return observable;
    }


    public void post(String url, Map<String, Object> params, SuccessConsumer callback, ErrorConsumer errorCallback) {
        sCompositeDisposable.add(api.post(url, params).flatMap(
                new Function<String, ObservableSource<JsonObject>>() {
                    @Override
                    public ObservableSource<JsonObject> apply(String s) {
                        JsonParser jsonParser = new JsonParser();

                        return Observable.just(jsonParser.parse(s).getAsJsonObject());
                    }
                }
        ).compose(RxjavaUtils.<JsonObject>transformer()).compose(RxjavaUtils.<JsonObject>handleResult()).subscribe(callback, errorCallback));

    }


    public void removeRequst() {
        sCompositeDisposable.clear();
    }

}
