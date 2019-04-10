package com.xh.basemodule.di;

import com.xh.basemodule.activity.BaseViewModel;
import com.xh.basemodule.activity.MBaseActivity;
import com.xh.basemodule.fragment.BaseFragment;
import com.xh.basemodule.fragment.BaseLazyFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @describe
 */
@Singleton
@Component(modules = BaseModule.class)
public interface BaseComponent {

    //此方法泛型必须实现
//    public void inject(MBaseDataBindingActivity activity);
    //databinding的模型类
    public void inject(BaseViewModel baseViewModel);
    public void inject(MBaseActivity activity);
    public void inject(BaseFragment fragment);
    public void inject(BaseLazyFragment fragment);
}
