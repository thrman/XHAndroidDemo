package com.xh.xhdemo.activity;

import android.os.Bundle;

import com.xh.basemodule.activity.MBaseDataBindingActivity;
import com.xh.xhdemo.R;
import com.xh.xhdemo.databinding.ActivityMainBinding;
import com.xh.xhdemo.viewmodel.MainViewModel;

public class MainActivity extends MBaseDataBindingActivity {


    ActivityMainBinding binding;
    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = (ActivityMainBinding) dataBinding;
        viewModel = new MainViewModel(this, binding);
        binding.setViewModel(viewModel);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }
}
