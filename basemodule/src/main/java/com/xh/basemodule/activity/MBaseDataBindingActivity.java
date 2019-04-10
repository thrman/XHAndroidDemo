package com.xh.basemodule.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.InflateException;
import android.view.View;
import android.view.ViewStub;

import com.xh.basemodule.R;


/**
 * 泛型在dagger2中无法动态代理，故改为父类viewdatabinding
 * databinding对象具体使用的时候 根据xml对应的binding类去转型
 * toolbar 对象自己去修改标题栏样式,暂时未添加右侧图标
 *
 * @describe
 */
public abstract class MBaseDataBindingActivity extends MBaseActivity {


    public ViewDataBinding dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uim_baselayout);
        try {
            ViewStub vb = findViewById(R.id.vb_content);
            vb.setLayoutResource(getLayoutResource());
            View view = vb.inflate();
            dataBinding = DataBindingUtil.bind(view);
        } catch (InflateException exception) {
            exception.printStackTrace();
        } catch (IllegalArgumentException exception) {
            exception.printStackTrace();
        }
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        decorView.setSystemUiVisibility(option);
    }


    /**
     * 重写这个方法返回布局id
     *
     * @return
     */
    protected abstract int getLayoutResource();
}
