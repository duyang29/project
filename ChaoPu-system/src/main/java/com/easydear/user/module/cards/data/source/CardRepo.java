package com.easydear.user.module.cards.data.source;

import com.easydear.user.DataApplication;
import com.easydear.user.api.ResponseEntity;
import com.easydear.user.api.RetrofitManager;
import com.easydear.user.module.cards.data.CardEntity;
import com.jinggan.library.base.BaseDataSourse;
import com.jinggan.library.net.retrofit.RemetoRepoCallbackV2;
import com.jinggan.library.net.retrofit.RetrofitCallbackV2;

import java.util.List;

import retrofit2.Call;

/**
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/6/16$ 下午8:24$
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */

public class CardRepo implements BaseDataSourse {

    private Call<ResponseEntity<List<CardEntity>>> cardsCall;
    private static CardRepo instance;

    public static CardRepo getInstance() {
        if (instance == null) {
            instance = new CardRepo();
        }
        return instance;
    }

    public void queryCards(int pageSize, String keywords, final RemetoRepoCallbackV2<List<CardEntity>> callback) {
        callback.onReqStart();
        String url = "card/listByKey?pageSize=" + pageSize + "&pageCount=10&keywords=" + keywords+"&userNo="+ DataApplication.getInstance().getUserInfoEntity().getUserNo();
        cardsCall = RetrofitManager.getInstance().getService().queryCards(url);
        cardsCall.enqueue(new RetrofitCallbackV2<ResponseEntity<List<CardEntity>>>() {
            @Override
            public void onSuccess(ResponseEntity<List<CardEntity>> data) {
                if (data.getCode() == 200) {
                    callback.onSuccess(data.getData());
                } else {
                    callback.onFailure(data.getCode(), data.getMessage());
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                callback.onFailure(code, msg);
            }

            @Override
            public void onFinish() {
                callback.onFinish();
            }
        });
    }

    @Override
    public void cancelRequest() {
        if (cardsCall != null && !cardsCall.isCanceled()) {
            cardsCall.cancel();
        }
    }
}
