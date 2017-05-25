package com.jinggan.library.media.picture.attachment;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.jinggan.library.R;
import com.jinggan.library.media.picture.SelectPictureActivity;
import com.jinggan.library.media.picture.ShowPictureActivity;
import com.jinggan.library.media.picture.data.PictureEntity;
import com.jinggan.library.ui.dialog.ToastUtil;
import com.jinggan.library.utils.IFileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 图片附件自定义控件
 * <p>
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/3/14 10:15
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class ImageAttachmentView extends LinearLayout implements AdapterView.OnItemClickListener, SelectPictureActivity.OnSeletePictureCallBack {
    /*默认图片数量*/
    public final static int DEFAULT_PICTURE_NUMBER = 10;

    private ImageAttachmentGridView gridView;
    private ImageAttachmentAdapter adapter;
    private TextView emptyTextView;

    private Context activity;

    private String photoPath;

    public ImageAttachmentView(Context context) {
        super(context);
        this.activity = context;
        initView(context);
    }

    public ImageAttachmentView(Context context, AttributeSet attri) {
        super(context, attri);
        this.activity = context;
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.accessory_img_layout, null);
        gridView = (ImageAttachmentGridView) view.findViewById(R.id.accessory_gridview);
        emptyTextView = ButterKnife.findById(view, R.id.accessory_empty_textView);

        adapter = new ImageAttachmentAdapter(context);
        gridView.setAdapter(adapter);

        initGridView();
        addView(view);
    }

    private void initGridView() {
        PictureEntity entity = new PictureEntity();
        entity.setNewUrl(ImageAttachmentAdapter.ADD_IMG_FLAG);
        adapter.appendToList(entity);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PictureEntity entity = (PictureEntity) adapter.getItem(position);
        if (TextUtils.isEmpty(entity.getNewUrl())) {
            return;
        }
        /*添加图片*/
        if (ImageAttachmentAdapter.ADD_IMG_FLAG.equals(entity.getNewUrl())) {
            List<String> list = new ArrayList<>();
            /*数据过虑，过虑掉最后"+"图片格式*/
            for (PictureEntity pictureEntity : adapter.getList()) {
                if (!pictureEntity.getNewUrl().equals(ImageAttachmentAdapter.ADD_IMG_FLAG))
                    list.add(pictureEntity.getUrl());
            }
            SelectPictureActivity.startIntent((Activity) activity, list, this);
        } else {
            /*查看大图*/
            ArrayList<String> list = new ArrayList<>();
            for (PictureEntity entity1 : adapter.getList()) {
                if (!ImageAttachmentAdapter.ADD_IMG_FLAG.equals(entity1.getNewUrl()))
                    list.add(entity1.getNewUrl());
            }
            ShowPictureActivity.startIntent(getContext(), list, position);
        }
    }


    /**
     * 添加图片
     * <p>
     * author: hezhiWu
     * created at 2017/3/21 15:33
     *
     * @param list
     */
    public void setImageListToStr(List<String> list) {
        if (list == null && list.size() == 0) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            if (adapter.getList().size() > DEFAULT_PICTURE_NUMBER) {
                adapter.removePos(adapter.getList().size() - 1);
                break;
            } else {
                setImageList(list.get(i));
            }
        }
    }

    /**
     * 添加图片
     * <p>
     * author: hezhiWu
     * created at 2017/3/21 15:33
     *
     * @param path
     */
    public void setImageList(String path) {
        PictureEntity entity = new PictureEntity();
        entity.setUrl(path);
        if ("photo".equals(path) && !TextUtils.isEmpty(photoPath)) {
            adapter.appendPositionToList(0, entity);
        } else {
            adapter.appendPositionToList(0, entity);
        }
        if (adapter.getList().size() > DEFAULT_PICTURE_NUMBER) {
            adapter.removePos(adapter.getList().size() - 1);
            return;
        }
    }

    /**
     * 添加图片
     * <p>
     * author: hezhiWu
     * created at 2017/3/21 15:34
     */
    public void setImageListToEntity(List<PictureEntity> entities) {
        if (entities == null || entities.size() <= 0) {
            clearImages();
            return;
        }
        for (PictureEntity entity : entities) {
            setImageListToEntity(entity);
        }
    }

    /**
     * 添加图片
     * <p>
     * author: hezhiWu
     * created at 2017/4/9 16:13
     */
    public void setImageListToEntity(PictureEntity entity) {
        boolean isAdd = true;
        looa:
        for (PictureEntity pictureEntity : adapter.getList()) {
            if (pictureEntity.getUrl().equals(entity.getUrl())) {
                isAdd = false;
                break looa;
            }
        }
        if (isAdd) {
            adapter.appendPositionToList(0, entity);
            if (adapter.getList().size() - 1 == DEFAULT_PICTURE_NUMBER && adapter.getList().get(adapter.getCount() - 1).getNewUrl().equals(ImageAttachmentAdapter.ADD_IMG_FLAG)) {
                adapter.removePos(adapter.getCount() - 1);
            }
        }
    }

    /**
     * 添加图片
     * <p>
     * author: hezhiWu
     * created at 2017/4/9 18:57
     */
//    public void setImageListToAttachment(List<Attachment> attachments) {
//        if (attachments != null && attachments.size() > 0) {
//            for (Attachment attachment : attachments) {
//                setImageListToAttachment(attachment);
//            }
//        }
//    }

    /**
     * 添加图片
     * <p>
     * author: hezhiWu
     * created at 2017/4/9 19:01
     */
//    public void setImageListToAttachment(Attachment attachment) {
//        PictureEntity entity = new PictureEntity();
//        entity.setNewUrl(attachment.getUrl());
//        adapter.appendPositionToList(0, entity);
//        adapter.setShowCloseIcon(true);
//    }

    /**
     * 显示图片
     * <p>
     * author: hezhiWu
     * created at 2017/3/21 15:35
     *
     * @param strs
     */
    public void setShowImageList(String[] strs) {
        List<PictureEntity> entities = new ArrayList<>();
        for (int i = 0; i < strs.length; i++) {
            PictureEntity entity = new PictureEntity();
            entity.setNewUrl(strs[i]);
            entities.add(entity);
        }
        setShowImage();
        if (adapter.getList().get(adapter.getCount() - 1).equals(ImageAttachmentAdapter.ADD_IMG_FLAG)) {
            adapter.removePos(adapter.getCount() - 1);
            adapter.appendToList(entities);
        }
    }

    /**
     * 设置显示图片方法2，保留+号按钮
     */
    @Deprecated
    public void setShowImageListWithAddButton(String[] strs) {
        List<PictureEntity> entities = new ArrayList<>();
        for (int i = 0; i < strs.length; i++) {
            PictureEntity entity = new PictureEntity();
            entity.setNewUrl(strs[i]);
            entities.add(entity);
        }
        setShowImage();
        adapter.appendPositionToList(0, entities);
    }

    /**
     * 设置显示图片
     * <p>
     * author: hezhiWu
     * created at 2017/3/21 15:35
     *
     * @param strs
     */
    public void setShowImageList(List<String> strs) {
        List<PictureEntity> entities = new ArrayList<>();
        for (int i = 0; i < strs.size(); i++) {
            PictureEntity entity = new PictureEntity();
            entity.setNewUrl(strs.get(i));
            entities.add(entity);
        }
        setShowImage();
        adapter.appendToList(entities);
    }

    /**
     * 设置显示图片
     * <p>
     * author: hezhiWu
     * created at 2017/4/9 14:59
     */
//    public void setShowImageListToAttachment(List<Attachment> attachments) {
//        if (attachments != null && attachments.size() > 0) {
//            List<String> list = new ArrayList<>();
//            for (Attachment attachment : attachments) {
//                list.add(attachment.getUrl());
//            }
//            setShowImageList(list);
//        } else {
//            emptyTextView.setVisibility(VISIBLE);
//            gridView.setVisibility(GONE);
//        }
//    }

    /**
     * 设置显示图片
     * <p>
     * author: hezhiWu
     * created at 2017/3/21 15:35
     *
     * @param url
     */
    public void setShowImage(String url) {
        PictureEntity entity = new PictureEntity();
        entity.setNewUrl(url);
        adapter.appendToList(entity);
    }

    private void setShowImage() {
        adapter.setShowCloseIcon(false);
        if (adapter.getList().get(adapter.getCount() - 1).getNewUrl().equals(ImageAttachmentAdapter.ADD_IMG_FLAG)) {
            adapter.removePos(adapter.getCount() - 1);
        }
    }

    /**
     * 获取图片资源
     * <p>
     * author: hezhiWu
     * created at 2017/3/21 15:36
     *
     * @return 图片集合
     */
    public List<String> getImageLists() {
        List<String> list = new ArrayList<>();
        for (PictureEntity entity : adapter.getList()) {
            if (!entity.getNewUrl().equals(ImageAttachmentAdapter.ADD_IMG_FLAG)) {
                list.add(entity.getNewUrl());
            }
        }
        return list;
    }

    /**
     * 获取图片资源
     * <p>
     * author: hezhiWu
     * created at 2017/4/7 17:12
     */
    public String getImageStrs() {
        StringBuffer stringBuffer = new StringBuffer();
        for (PictureEntity entity : adapter.getList()) {
            if (!entity.getNewUrl().equals(ImageAttachmentAdapter.ADD_IMG_FLAG)) {
                if (entity.getNewUrl().startsWith("http://") || IFileUtils.getFileSize(new File(entity.getNewUrl())) > 0) {
                    stringBuffer.append(entity.getNewUrl()).append(",");
                } else {
                    ToastUtil.showToast(getContext(), "图片处理中，稍后再试");
                    return "";
                }
            }
        }
        if (TextUtils.isEmpty(stringBuffer.toString())) {
            return "";
        }
        return stringBuffer.substring(0, stringBuffer.length() - 1);
    }

    /**
     * 清空列表图片
     * <p>
     * author: hezhiWu
     * created at 2017/3/21 15:36
     */
    public void clearImages() {
        while ((adapter.getCount()) > 1) {
            adapter.removePos(0);
        }
    }

    private List<PictureEntity> deleteEntity = new ArrayList<>();

    @Override
    public void onClickDelete(PictureEntity entity) {
        for (PictureEntity pictureEntity : adapter.getList()) {
            if (pictureEntity.getUrl().equals(entity.getUrl())) {
                deleteEntity.add(pictureEntity);
            }
        }
        for (PictureEntity entity1 : deleteEntity) {
            adapter.removeObject(entity1);
            adapter.notifyDataSetChanged();
            /*删除本地压缩过的图片*/
            IFileUtils.delete(entity1.getNewUrl());
        }
        deleteEntity.clear();
    }
}
