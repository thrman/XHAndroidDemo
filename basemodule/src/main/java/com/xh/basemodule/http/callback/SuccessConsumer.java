package com.xh.basemodule.http.callback;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import io.reactivex.functions.Consumer;

/**
 */
public class SuccessConsumer <T>implements Consumer <JsonElement>{
    @Override
    public void accept(JsonElement jsonElement) throws Exception {
        try {
            onSuccess(jsonElement);
//            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Gson gson = new Gson();
           T t =  gson.fromJson(jsonElement,new TypeToken<T>(){

           }.getType());
           onSuccessData(t);
        }catch (IllegalStateException e){
            e.printStackTrace();
        } finally {
            onCompleted();
        }
    }
    public  void onSuccess(JsonElement jsonElement) {
    }

    /**
     * 转bean类用到
     * @param t
     */
    public  void  onSuccessData(T t){

    }

    public void onCompleted(){

    }


}
