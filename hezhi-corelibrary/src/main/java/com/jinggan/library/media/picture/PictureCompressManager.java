package com.jinggan.library.media.picture;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.ImageView;


import com.jinggan.library.media.picture.data.PictureEntity;
import com.jinggan.library.utils.IFileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片压缩处理管理类
 * <p>
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/3/14 10:31
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class PictureCompressManager {

    private static PictureCompressManager pictureCompressManager;

    public static PictureCompressManager getInstance() {
        if (pictureCompressManager == null) {
            pictureCompressManager = new PictureCompressManager();
        }
        return pictureCompressManager;
    }

    /**
     * 图片压缩
     *
     * @param activity
     * @param list
     * @param compressCallBack
     */
    public void startCompressPictureToEntity(Activity activity, List<PictureEntity> list, PictureCompressCallBack compressCallBack) {
        photoCut(activity, list, null, 0, compressCallBack);
    }

    /**
     * 图片压缩
     *
     * @param activity
     * @param entity
     * @param compressCallBack
     */
    public void startCompressPictureToEntity(Activity activity, PictureEntity entity, ImageView imageView, int position, PictureCompressCallBack compressCallBack) {
        List<PictureEntity> entities = new ArrayList<>();
        entities.add(entity);
        photoCut(activity, entities, imageView, position, compressCallBack);
    }


    /**
     * 图片压缩
     *
     * @param activity
     * @param list
     * @param compressCallBack
     */
    private void photoCut(final Activity activity, final List<PictureEntity> list, final ImageView imageView, final int position, final PictureCompressCallBack compressCallBack) {
        if (list == null && list.size() == 0) {
            return;
        }
        if (compressCallBack != null) {
            compressCallBack.onPictureCompressStart();
        }

        new Thread() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < list.size(); i++) {
                        final String path = list.get(i).getUrl();
                        final String savePath = IFileUtils.getImageCatchDir();
                        final String photoPath = BitmapUtil.compressBitmap(path, savePath, list.get(i).getDate());
                        if (!TextUtils.isEmpty(photoPath)) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (compressCallBack != null) {
                                        compressCallBack.onPictureCompressResult(position, imageView, path, savePath);
                                    }
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            compressCallBack.onPictureCompressEnd();
                        }
                    });
                }
            }
        }.start();
    }


    public interface PictureCompressCallBack {
        void onPictureCompressStart();

        void onPictureCompressEnd();

        void onPictureCompressResult(int position, ImageView imageView, String oldPath, String path);
    }
}
