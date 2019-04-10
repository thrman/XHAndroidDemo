package com.xh.basemodule.http.intercept;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 */
public class DqIntercept implements Interceptor {
private Context context;
    public DqIntercept(Context context){
//        this.context = context;
        //防止泄漏 先注释掉
    }
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        builder.header("Connection","keep-alive");
//        builder.header("token","token");//在此处加token
//        builder.header("Content-Type", "application/json;charset=UTF-8");
        Request requestNew = builder.build();
        //可能还要加版本号
//       Response.Builder resbuilder = new Response.Builder();
//       resbuilder.code(400);
//       resbuilder.body(ResponseBody.create(MediaType.parse(""),""));
//        Response emptyRes = resbuilder.build();

        return chain.proceed(requestNew);
    }




}
