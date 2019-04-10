package com.xh.basemodule.http.api;


import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @describe * 网络请求的封装,支持map和bean的请求封装
 */
public interface BaseApi {
    //统一返回的是string类型,方便自定义解析

    @GET
    Observable<String> get(@Url String path, @QueryMap Map<String, Object> params);

    @POST
    Observable<String> post(@Url String path, @Body Map<String, Object> params);

    @POST
    Observable<String> post(@Url String path, @Body Object params);

    @POST
    Observable<ResponseBody> _post(@Url String path, @Body Map<String, Object> params);//直接返回响应体

    @POST
    Observable<ResponseBody> _post(@Url String path);//直接返回响应体

    @Multipart
    @POST
    Observable<String> upLoadImageList(@Url String path, @PartMap Map<String, RequestBody> requestBodyMap);//上传图片

    @Multipart
    @POST
    Observable<String> upLoadImage(@Url String path, @Part List<MultipartBody.Part> imgMap);

    @Multipart
    @POST
    Observable<String> upLoadImage(@Url String path, @Part("file\"; filename=\"avator.png\"") RequestBody img, @Part("description") String des);

    @POST
    Observable<String> post(@Url String path, @Body RequestBody body);

    @POST
    Observable<String> post(@Url String path, @Body Request request);

    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);//下載文件
}
