package com.easydear.user.api;

import com.easydear.user.module.account.data.UserInfoEntity;
import com.easydear.user.module.business.data.BusinessEntity;
import com.easydear.user.module.cards.data.CardEntity;
import com.easydear.user.module.dynamic.data.DynamicEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * author:hezhiWu <wuhezhi007@gmail.com>
 * version:V1.0
 * created at 2017/6/16 下午3:14
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved
 */

public interface APIService {


    @POST("login/loginUser")
    Call<ResponeEntity<UserInfoEntity>> login(@Query("mobile") String mobile, @Query("password") String password);

    /**
     * 查询商家列表
     *
     * @param url
     * @return
     */
    @POST
    Call<ResponeEntity<List<BusinessEntity>>> queryBusiness(@Url String url);

    /**
     * 查询动态列表
     * <p>
     * author: hezhiWu
     * created at 2017/6/16 下午3:14
     */
    @POST
    Call<ResponeEntity<List<DynamicEntity>>> queryDynamics(@Url String url);

    /**
     * 查询卡包列表
     * <p>
     * author: hezhiWu
     * created at 2017/6/16 下午8:31
     */
    @POST
    Call<ResponeEntity<List<CardEntity>>> queryCards(@Url String url);
}
