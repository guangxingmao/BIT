package com.hackathon.bottosapp.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;

import com.hackathon.bottosapp.MainActivity;
import com.hackathon.bottosapp.R;
import com.hackathon.bottosapp.base.BaseActivity;
import com.hackathon.bottosapp.ui.wallet.WalletActivity;
import com.hackathon.bottosapp.utils.SharedPreferencesUtils;

import static com.hackathon.bottosapp.utils.SharedPreferencesUtils.BOTTOS_ACCOUNT_INFO;

public class SplashActivity extends BaseActivity {

    @Override
    public int initLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        AppCompatImageView splashLogoIv = findViewById(R.id.splash_logo_iv);

        //1s的渐显动画
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(splashLogoIv, "alpha", 0f, 1f);
        objectAnimator.setDuration(1000);
        objectAnimator.start();
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //TODO 简单查询本地是否有账号，没有则创建或导入
                String accountInfo = (String) SharedPreferencesUtils.getParam(SplashActivity.this, BOTTOS_ACCOUNT_INFO, "");
                if (TextUtils.isEmpty(accountInfo)) {
                    startActivity(new Intent(SplashActivity.this, WalletActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
                finish();
            }
        });
    }
}
