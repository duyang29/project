package com.easydear.user.module.message;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easydear.user.BuildConfig;
import com.easydear.user.R;
import com.easydear.user.module.message.data.MessageItemEntity;
import com.jinggan.library.ui.widget.pullRefreshRecyler.BaseRecyclerViewAdapter;
import com.jinggan.library.utils.ISkipActivityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by LJH on 2017/1/15.
 */

public class MessageContentAdapter extends BaseRecyclerViewAdapter<MessageItemEntity> implements BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener {

    public MessageContentAdapter(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        MessageContentAdapter.ItemViewHolder viewHolder = new MessageContentAdapter.ItemViewHolder(inflater.inflate(R.layout.item_message_layout, parent, false));
        setOnItemClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindBaseViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        final MessageItemEntity itemEntity = mLists.get(position);

        // BusinessNo 和 BusinessName 为空，为系统消息
        if (itemEntity.getBusinessNo() == null && itemEntity.getBusinessName() == null) {
            itemEntity.setBusinessName("易兑消息推送");
//            viewHolder.headView.setImageResource();
        }
        viewHolder.timeText.setText(itemEntity.getCreateTime());
        viewHolder.contentText.setText(itemEntity.getContent());
        viewHolder.titleTextView.setText(itemEntity.getBusinessName());
        Glide.with(mContent).load(BuildConfig.DOMAIN + itemEntity.getLogo())
                .placeholder(R.mipmap.default_head_img)
                .error(R.mipmap.default_head_img)
                .into(viewHolder.headView);
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("businessName", itemEntity.getBusinessName());
                bundle.putString("businessNo", itemEntity.getBusinessNo());
                ISkipActivityUtil.startIntent(mContent, MessageDetailActivity.class,bundle);
            }
        });
    }

    @Override
    public void onItemClick(View view, Object data, int position) {

    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_message_head_imageView)
        ImageView headView;
        @BindView(R.id.item_message_time_textView)
        TextView timeText;
        @BindView(R.id.item_message_type_textView)
        TextView typeText;
        @BindView(R.id.item_message_content_textView)
        TextView contentText;
        @BindView(R.id.item_message_layout)
        RelativeLayout layout;
        @BindView(R.id.item_message_title_textView)
        TextView titleTextView;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(ItemViewHolder.this, view);
        }
    }
}
