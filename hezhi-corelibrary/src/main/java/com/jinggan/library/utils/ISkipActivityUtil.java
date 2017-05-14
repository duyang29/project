package com.jinggan.library.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Activity跳转工具类
 * <p>
 * author: hezhiWu <wuhezhi007@gmail.com>
 * version: V1.0
 * created at 2017/3/14 10:21
 * <p>
 * Copyright (c) 2017 Shenzhen O&M Cloud Co., Ltd. All rights reserved.
 */
public class ISkipActivityUtil {

    /**
     * 不带参数跳转
     *
     * @param context
     * @param cls
     */
    public static void startIntent(Context context, Class<?> cls) {
        context.startActivity(new Intent(context, cls));
    }

    /**
     * 带Bundle跳转
     *
     * @param context
     * @param cls
     * @param extras
     */
    public static void startIntent(Context context, Class<?> cls, Bundle extras) {
        Intent intent = new Intent();
        intent.putExtras(extras);
        intent.setClass(context, cls);
        context.startActivity(intent);
    }

    /**
     * 回调跳转
     *
     * @param activity
     * @param cls
     * @param requestCode
     */
    public static void startIntentForResult(Activity activity, Class<?> cls, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 回调跳转
     *
     * @param activity
     * @param cls
     * @param extras
     * @param requestCode
     */
    public static void startIntentForResult(Activity activity, Class<?> cls, Bundle extras, int requestCode) {
        Intent intent = new Intent();
        intent.putExtras(extras);
        intent.setClass(activity, cls);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 初始第三方APK
     * <p>
     * author: hezhiWu
     * created at 2017/3/29 17:03
     *
     * @param context
     * @param packageName
     */
    public static boolean startAPK(Context context, String packageName) {
        boolean isGo = true;
        try {
            Intent intent = context.getPackageManager()
                    .getLaunchIntentForPackage(packageName);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            isGo = false;
        }
        return isGo;
    }

    /**
     * 初始第三方APK
     * <p>
     * author: hezhiWu
     * created at 2017/3/29 17:03
     *
     * @param context
     * @param bundle
     * @param packageName
     */
    public static boolean startAPK(Context context, Bundle bundle, String packageName) {
        boolean isGo = true;
        try {
            Intent intent = context.getPackageManager()
                    .getLaunchIntentForPackage(packageName);
            intent.putExtras(bundle);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            isGo = false;
        }
        return isGo;
    }

    public static void openApp(Context context,Bundle build,String packageName,String className) {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName(packageName, className);
            intent.setComponent(cn);
            intent.putExtras(build);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
