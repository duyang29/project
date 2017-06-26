package com.easydear.user.module.dynamic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easydear.user.BuildConfig;
import com.easydear.user.R;
import com.easydear.user.module.dynamic.data.DynamicEntity;
import com.jinggan.library.ui.view.RoundedBitmapImageViewTarget;
import com.jinggan.library.ui.widget.pullRefreshRecyler.BaseRecyclerViewAdapter;
import com.jinggan.library.utils.ISkipActivityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author:hezhiWu <wuhezhi007@gmail.com>
 * version:V1.0
 * created at 2017/6/12 下午12:07
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved
 */
public class DynamicListAdapter extends BaseRecyclerViewAdapter<DynamicEntity> {


    public DynamicListAdapter(Context context) {
        super(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_dynamic_lista, parent, false));
    }

    @Override
    public void onBindBaseViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;

        /*商家Logo*/
        Glide.with(mContent).load(BuildConfig.DOMAIN + mLists.get(position).getLogo())
                .asBitmap()
                .centerCrop()
                .placeholder(R.mipmap.default_head_img)
                .error(R.mipmap.default_head_img)
                .into(new RoundedBitmapImageViewTarget(mContent, viewHolder.DynamicListLogoImageView));
        viewHolder.DynamicListTitleTextView.setText(mLists.get(position).getBusinessName());
        viewHolder.DynamicListContentTextView.setText(mLists.get(position).getSummary());
        if (TextUtils.isEmpty(mLists.get(position).getSummary())) {
            viewHolder.DynamicListContentTextView.setVisibility(View.GONE);
        } else {
            viewHolder.DynamicListContentTextView.setVisibility(View.VISIBLE);
        }
        Glide.with(mContent).load(BuildConfig.DOMAIN + mLists.get(position).getLogo())
                .into(viewHolder.DynamicListImageView);

        viewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 跳转
                ISkipActivityUtil.startIntent(mContent,DynamicDetailsActivity.class);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.DynamicList_Logo_ImageView)
        ImageView DynamicListLogoImageView;
        @BindView(R.id.DynamicList_Title_TextView)
        TextView DynamicListTitleTextView;
        @BindView(R.id.DynamicList_Content_TextView)
        TextView DynamicListContentTextView;
        @BindView(R.id.DynamicList_ImageView)
        ImageView DynamicListImageView;
        @BindView(R.id.DynamicList_layout)
        LinearLayout itemLayout;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
