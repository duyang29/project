package com.xiaomai.telemarket.module.home.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.jinggan.library.base.BaseFragment;
import com.xiaomai.telemarket.R;
import com.xiaomai.telemarket.XiaoMaiBaseActivity;
import com.xiaomai.telemarket.view.widget.TitleLayout;

import butterknife.ButterKnife;

/**
 * @author yangdu <youngdu29@gmail.com>
 * @description 编辑设置界面
 * @createtime 06/05/2017 6:54 PM
 **/
public class SettingEditActivity extends XiaoMaiBaseActivity implements TitleLayout.OnNaviBarClickListener{

//    @BindView(R.id.layout_title)
//    TitleLayout layoutTitle;

    public static final String TAG_USERSTATE = "userstate";
    public static final String TAG_DIALING = "dialing";
    public static final String EXTRA_TAG = "set_tag";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_edit_layout);
        ButterKnife.bind(this);
        setToolbarVisibility(View.VISIBLE);
        setToolbarTitle(getResources().getString(R.string.settings));
//        layoutTitle.setOnNaviBarClickListener(this);
        getSupportFragmentManager().beginTransaction().add(R.id.layout_set_content, createFragment(), null).commit();
    }

    private BaseFragment createFragment() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String tag = bundle.getString(EXTRA_TAG);
            if (TextUtils.equals(tag,TAG_USERSTATE)) {
//                layoutTitle.setTitle(getResources().getString(R.string.set_user_state));
                setToolbarTitle(getResources().getString(R.string.set_user_state));
                return UserStateSetFragment.newInstance();
            }else if (TextUtils.equals(tag,TAG_DIALING)) {
//                layoutTitle.setTitle(getResources().getString(R.string.set_dialing_source));
                setToolbarTitle(getResources().getString(R.string.set_dialing_source));
                return DialingSourceSetFragment.newInstance();
            }
        }
        return null;
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onAddClick() {

    }

    @Override
    public void onFilterClick() {

    }
}
