package com.xh.xhdemo.viewmodel;

import android.app.Activity;
import android.databinding.ObservableField;
import android.view.View;

import com.google.gson.JsonElement;
import com.xh.basemodule.activity.BaseViewModel;
import com.xh.basemodule.http.callback.SuccessConsumer;
import com.xh.xhdemo.R;
import com.xh.xhdemo.databinding.ActivityMainBinding;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 描述：
 *
 * @Time 2019/4/10.
 */
public class MainViewModel extends BaseViewModel {
    private Activity activity;

    ActivityMainBinding binding;

    ObservableField<String> title = new ObservableField<>();

    public ObservableField<String> getTitle() {
        return title;
    }

    public void setTitle(ObservableField<String> title) {
        this.title = title;
    }

    public MainViewModel(Activity activity, ActivityMainBinding binding) {
        this.activity = activity;
        this.binding = binding;
    }


    public void changeContent(View view) {
        switch (view.getId()) {
            case R.id.tv_content:
                getTitle().set("TEXT=" + (new Random().nextInt(10)));
                break;
            case R.id.tv_net:
                getNetData();
                break;
        }

    }

    private void getNetData() {
        String url = "http://www.weather.com.cn/data/sk/101110101.html";
        Map<String, Object> map = new HashMap<>();
        httpManager.get(url, map, new SuccessConsumer() {
            @Override
            public void onSuccess(JsonElement jsonElement) {
                super.onSuccess(jsonElement);
                getTitle().set(jsonElement.getAsJsonObject().toString());
            }
        }, null);
    }
}
