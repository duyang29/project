package com.easydear.user.module.dynamic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.easydear.user.R;
import com.jinggan.library.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/6/22 20:21
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class DynamicDetailsActivity extends BaseActivity {

    @BindView(R.id.Dynamic_bg)
    ImageView DynamicBg;
    @BindView(R.id.Dynamic_Title)
    TextView DynamicTitle;
    @BindView(R.id.Dynamic_Business_logo)
    ImageView DynamicBusinessLogo;
    @BindView(R.id.Dynamic_Business_Name)
    TextView DynamicBusinessName;
    @BindView(R.id.Dynamic_Content)
    TextView DynamicContent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_details);
        setToolbarTitle("潮铺正文");
        ButterKnife.bind(this);
    }
}
