package com.xiaomai.telemarket.module.cstmr.fragment.car;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.jinggan.library.base.EventBusValues;
import com.jinggan.library.utils.ISkipActivityUtil;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.XiaoMaiBaseActivity;
import com.xiaomai.telemarket.module.cstmr.data.CarEntity;

import org.greenrobot.eventbus.EventBus;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/5/20 18:54
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class CarActivity extends XiaoMaiBaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        try {
            if (getIntent().getExtras().containsKey("entity")) {
                switchToEditCarFragment();
            } else {
                switchToAddCartoFragment();
            }
        } catch (Exception e) {
            e.printStackTrace();
            switchToAddCartoFragment();
        }
    }

    @Override
    public void onClickToolbarRightLayout() {
        super.onClickToolbarRightLayout();
        EventBusValues busValues=new EventBusValues();
        busValues.setWhat(0x1004);
        EventBus.getDefault().post(busValues);
    }

    private void switchToEditCarFragment() {
        setToolbarTitle("汽车明细");
        setToolbarRightText("保存");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        CarEditFragment fragment=new CarEditFragment();
        fragment.setArguments(getIntent().getExtras());

        transaction.replace(R.id.Car_Content_Layout, fragment);
        transaction.commit();
    }

    private void switchToAddCartoFragment() {
        setToolbarTitle("添加汽车明细");
        setToolbarRightText("保存");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.Car_Content_Layout, new CarAddFragment());
        transaction.commit();
    }

    public static void startIntentToEdit(Activity activity, CarEntity entity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("entity", entity);
        ISkipActivityUtil.startIntent(activity, CarActivity.class, bundle);
    }

    public static void startIntentToAdd(Activity activity) {
        ISkipActivityUtil.startIntent(activity, CarActivity.class);
    }
}
