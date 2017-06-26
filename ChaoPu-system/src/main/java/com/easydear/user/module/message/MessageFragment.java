package com.easydear.user.module.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easydear.user.DataApplication;
import com.easydear.user.R;
import com.easydear.user.module.message.data.MessageItemEntity;
import com.easydear.user.module.message.data.source.MessageRepo;
import com.jinggan.library.base.BaseFragment;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.ui.widget.pullRefreshRecyler.PullToRefreshRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.mainFuncations.homeFuncation
 * @Description:消息模块
 * @date 2016/11/22 18:12
 */

public class MessageFragment extends BaseFragment implements PullToRefreshRecyclerView.PullToRefreshRecyclerViewListener, RemetoRepoCallbackV2<List<MessageItemEntity>> {

    @BindView(R.id.msg_recyclerView)
    PullToRefreshRecyclerView mRecyclerView;

    private static MessageFragment fragment;

    private MessageContentAdapter mAdapter;
    private MessageRepo mMessageRepo;

    public static MessageFragment newInstance() {
        if (fragment == null) {
            fragment = new MessageFragment();
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMessageRepo = MessageRepo.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment_record, null);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new MessageContentAdapter(getActivity());
        mRecyclerView.setRecyclerViewAdapter(mAdapter);
        mRecyclerView.setPullToRefreshListener(this);

        mRecyclerView.startUpRefresh();
    }

    @Override
    public void onDownRefresh() {
        String useNo = DataApplication.getInstance().getUserInfoEntity().getUserNo();
        mMessageRepo.queryTuiMessages(useNo, this);     // 获取系统消息列表
        mMessageRepo.queryBusMessages(useNo, this);     // 获取商家消息列表
    }

    @Override
    public void onPullRefresh() {

    }

    @Override
    public void onReqStart() {

    }

    @Override
    public void onSuccess(List<MessageItemEntity> data) {
        if (data == null) {
//            mRecyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
            return;
        }
        mAdapter.addItems(data, 0);
    }

    @Override
    public void onFailure(int code, String msg) {
//        mRecyclerView.setEmptyTextViewVisiblity(View.VISIBLE);
    }

    @Override
    public void onFinish() {
        mRecyclerView.closeDownRefresh();
    }
}
