package com.easydear.user.module.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.easydear.user.DataApplication;
import com.easydear.user.R;
import com.easydear.user.common.Constant;
import com.easydear.user.module.message.data.MessageDetailEntity;
import com.easydear.user.module.message.data.source.MessageRepo;
import com.jinggan.library.base.BaseActivity;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.ui.dialog.ToastUtil;
import com.jinggan.library.ui.widget.pullRefreshRecyler.PullToRefreshRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LJH on 2017/1/16.
 * 消息详情列表
 */

public class MessageDetailActivity extends BaseActivity implements PullToRefreshRecyclerView.PullToRefreshRecyclerViewListener, RemetoRepoCallbackV2<List<MessageDetailEntity>> {

    private final String TAG = this.getClass().getSimpleName();

    private int defaultPageSize = 1;
    private boolean isRefresh = true;

    private String mUserNo = "";
    private String businessName = null;
    private String businessNo = null;

    @BindView(R.id.message_detail_recyclerView)
    PullToRefreshRecyclerView mRecyclerView;

    private MessageRepo mMessageRepo;
    private MessageDetailAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_message_detail);
        mUserNo = DataApplication.getInstance().getUserInfoEntity().getUserNo();
        businessName = getIntent().getStringExtra("businessName");
        businessNo = getIntent().getStringExtra("businessNo");
        mMessageRepo = MessageRepo.getInstance();
        resetBusinessName();
        setToolbarTitle(businessName);
        ButterKnife.bind(this);
        initRecyclerView();
    }

    // businessNo 为空，为系统消息
    private void resetBusinessName() {
        if (businessNo == null) {
            businessName = "易兑科技";
        }
    }

    private void initRecyclerView() {
        mAdapter = new MessageDetailAdapter(this);
        mRecyclerView.setRecyclerViewAdapter(mAdapter);
        mRecyclerView.setPullToRefreshListener(this);
        mRecyclerView.startUpRefresh();
    }

    @Override
    public void onDownRefresh() {
        defaultPageSize = 1;
        mMessageRepo.reqMsgDetail(mUserNo, businessNo, defaultPageSize, Constant.DEFAULT_LOAD_SIZE, this);
    }

    @Override
    public void onPullRefresh() {
        defaultPageSize++;
        mMessageRepo.reqMsgDetail(mUserNo, businessNo, defaultPageSize, Constant.DEFAULT_LOAD_SIZE, this);
    }

    @Override
    public void onReqStart() {

    }

    @Override
    public void onSuccess(List<MessageDetailEntity> list) {
        if (isRefresh) {
            mAdapter.clearList();
            mAdapter.addItems(list);
        } else {
            mAdapter.addItems(list, mAdapter.getItemCount() - 1);
        }
    }

    @Override
    public void onFailure(int code, String msg) {
        if (code == 404) {
            if (mAdapter.getItemCount() <= 0) {
//                mRecyclerView.setVisibility(View.GONE);
                mRecyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
            } else {
                mRecyclerView.onLoadMoreFinish();
            }
        } else {
            ToastUtil.showToast(this, msg);
        }
    }

    @Override
    public void onFinish() {
        mRecyclerView.closeDownRefresh();
        mRecyclerView.setLoading(false);
    }
}
