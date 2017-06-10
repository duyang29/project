package com.easydear.user.module.home;

import android.os.Bundle;
import android.text.style.DynamicDrawableSpan;
import android.view.View;
import android.widget.FrameLayout;

import com.easydear.user.R;
import com.easydear.user.module.cards.CardsFragment;
import com.easydear.user.module.dynamic.DynamicFragment;
import com.easydear.user.module.mine.MineFragment;
import com.easydear.user.module.scann.ScanningFragment;
import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.ui.view.MainBottomNavigationBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: hezhiWu <hezhi.woo@gmail.com>
 * version: V1.0
 * created at 2017/6/9 22:55
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class MainActivity extends BaseActivity implements MainBottomNavigationBar.BottomTabSelectedListener{

    private final static int TAB_HOME=0;
    private final static int TAB_DYNAMIC=1;
    private final static int TAB_CARDS=2;
    private final static int TAB_SCANN=3;
    private final static int TAB_MINE=4;

    @BindView(R.id.main_bottom_navigationBar)
    MainBottomNavigationBar mainBottomNavigationBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSwipeEnabled(false);

        initBottomNavigationBar();
    }

    /**
     * 初始化BottomNavigationBar
     * author: hezhiWu
     * created at 2017/3/14 22:25
     */
    private void initBottomNavigationBar() {
        mainBottomNavigationBar.initConfig(this, R.id.main_container_FrameLayout);
        mainBottomNavigationBar.addTabItem(R.mipmap.ic_tab_home, R.string.tab_home, new HomeFragment())
                .addTabItem(R.mipmap.ic_tab_dynamic, R.string.tab_dynamic, new DynamicFragment())
                .addTabItem(R.mipmap.ic_tab_cards, R.string.tab_cards, new CardsFragment())
                .addTabItem(R.mipmap.ic_tab_scann,R.string.tab_scann,new ScanningFragment())
                .addTabItem(R.mipmap.ic_tab_mine,R.string.tab_mine,new MineFragment())
                .setTabSelectedListener(this)
                .setFirstSelectedTab(TAB_HOME);
        setToolbarCenterTitle(R.string.tab_home);
    }

    @Override
    public void onTabSelected(int position) {
        switch (position){
            case TAB_HOME:
                setToolbarCenterTitle(R.string.tab_home);
                break;
            case TAB_DYNAMIC:
                setToolbarCenterTitle(R.string.tab_dynamic);
                break;
            case TAB_CARDS:
                setToolbarCenterTitle(R.string.tab_cards);
                break;
            case TAB_SCANN:
                setToolbarCenterTitle(R.string.tab_scann);
                break;
            case TAB_MINE:
                setToolbarCenterTitle(R.string.tab_mine);
                break;
        }
    }
}
