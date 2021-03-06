package com.xiaomai.telemarket.module.cstmr.fragment.car;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.base.EventBusValues;
import com.jinggan.library.net.retrofit.RemetoRepoCallback;
import com.jinggan.library.ui.widget.pullRefreshRecyler.PullToRefreshRecyclerView;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.module.cstmr.CusrometDetailsActivity;
import com.xiaomai.telemarket.module.cstmr.data.CarEntity;
import com.xiaomai.telemarket.module.cstmr.data.CusrometListEntity;
import com.xiaomai.telemarket.module.cstmr.data.repo.CusrometRemoteRepo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/5/16$ 下午10:38$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class CusrometCarFragment extends BaseFragment implements CusrometCarAdapter.OnClickItemLisenter, RemetoRepoCallback<List<CarEntity>>, PullToRefreshRecyclerView.PullToRefreshRecyclerViewListener {

    @BindView(R.id.Details_number_TextView)
    TextView DetailsNumberTextView;
    @BindView(R.id.Details_add_Button)
    Button DetailsAddButton;
    @BindView(R.id.Car_recyclerView)
    PullToRefreshRecyclerView CarRecyclerView;
    Unbinder unbinder;

    private CusrometCarAdapter adapter;
    private CusrometRemoteRepo remoteRepo;

    private String cusrometId;

    private CarEntity entity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

        cusrometId = getArguments().getString("id");
        adapter = new CusrometCarAdapter(getContext());
        adapter.setListenter(this);
        remoteRepo = CusrometRemoteRepo.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cusromet_car, null);
        unbinder = ButterKnife.bind(this, rootView);
        DetailsAddButton.setText("添加汽车");
        initRecyclerView();
        return rootView;
    }


    private void initRecyclerView() {
        CarRecyclerView.setRecyclerViewAdapter(adapter);
        CarRecyclerView.setMode(PullToRefreshRecyclerView.Mode.DISABLED);
        CarRecyclerView.setPullToRefreshListener(this);
//        CarRecyclerView.startUpRefresh();
        remoteRepo.queryCusrometCarLists(cusrometId, this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        remoteRepo.cancelRequest();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe
    public void onUpdateUIData(EventBusValues values){
        if (values.getWhat()==0x205){
            remoteRepo.queryCusrometCarLists(cusrometId, this);
        }
        if (values.getWhat()==0x890){
            cusrometId=((CusrometListEntity)values.getObject()).getID();
            remoteRepo.queryCusrometCarLists(cusrometId, this);
        }
    }

    @Override
    public void onDownRefresh() {
        remoteRepo.queryCusrometCarLists(cusrometId, this);
    }

    @Override
    public void onPullRefresh() {

    }

    @Override
    public void onSuccess(List<CarEntity> data) {
        adapter.clearList();
        if (data != null && data.size() > 0) {
            adapter.addItems(data);
            DetailsNumberTextView.setText("共" + data.size() + "条汽车明细");
            if (CarRecyclerView!=null){
                CarRecyclerView.setEmptyTextViewVisiblity(View.GONE);
            }
            ((CusrometDetailsActivity)getActivity()).getTabLayout().setTagNumber(4,data.size());
        } else {
            DetailsNumberTextView.setText("洗车明细");
            if (CarRecyclerView!=null){
                CarRecyclerView.setPageHint(R.mipmap.icon_data_empty,"资料为空");
            }
        }
    }

    @Override
    public void onFailure(int code, String msg) {
        if (CarRecyclerView!=null) {
            CarRecyclerView.setPageHint(R.mipmap.icon_page_error, "页面出错");
        }
    }

    @Override
    public void onThrowable(Throwable t) {
        if (CarRecyclerView!=null) {
            CarRecyclerView.setPageHint(R.mipmap.icon_page_error, "页面出错");
        }
    }

    @Override
    public void onUnauthorized() {
        if (CarRecyclerView!=null) {
            CarRecyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
        }
    }

    @Override
    public void onFinish() {
        if (CarRecyclerView!=null) {
            CarRecyclerView.closeDownRefresh();
        }
    }

    @OnClick(R.id.Details_add_Button)
    public void onClick() {
        CarActivity.startIntentToAdd(getActivity());
    }

    @Override
    public void onSeleceItemPosition(CarEntity entity) {
        this.entity=entity;
    }

    public CarEntity getEntity(){
        return entity;
    }
}
