package com.hackathon.bottosapp.ui.wallet;

import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;
import com.hackathon.bottosapp.MainActivity;
import com.hackathon.bottosapp.R;
import com.hackathon.bottosapp.base.BaseActivity;
import com.hackathon.bottosapp.bean.AccountInfoDb;
import com.hackathon.bottosapp.bottos_sdk.BotcManger;
import com.hackathon.bottosapp.bottos_sdk.utils.crypto.WalletFile;
import com.hackathon.bottosapp.utils.SharedPreferencesUtils;

import static com.hackathon.bottosapp.utils.SharedPreferencesUtils.BOTTOS_ACCOUNT_INFO;

public class CreateKeystoreActivity extends BaseActivity implements View.OnClickListener {

    private String mAccount;
    private String mKeystore;

    @Override
    public int initLayout() {
        return R.layout.activity_create_keystore;
    }

    @Override
    public Toolbar getToolbar() {
        return findViewById(R.id.toolbar);
    }

    @Override
    public void initView() {
        String privatekey = getIntent().getStringExtra("privatekey");
        String password = getIntent().getStringExtra("password");
        mAccount = getIntent().getStringExtra("account");
        AppCompatTextView toolbarTitleTv = findViewById(R.id.toolbar_title_tv);
        toolbarTitleTv.setText("备份Keystore");
        AppCompatTextView createKeystoreTv = findViewById(R.id.create_keystore_tv);
        AppCompatButton createSuccessBtn = findViewById(R.id.create_success_btn);
        createSuccessBtn.setOnClickListener(this);
        createKeystoreTv.setOnClickListener(this);
        //获取keystore
        WalletFile walletFile = BotcManger.getInstance().getWalletService().createaWalletFile(password, privatekey);
        if (walletFile != null) {
            walletFile.setAccount(mAccount);
            mKeystore = new Gson().toJson(walletFile);
            createKeystoreTv.setText(mKeystore);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create_keystore_tv:           //备份keystore
                onClickbackUpKey("备份Keystore", mKeystore);
                break;
            case R.id.create_success_btn:           //备份完成
                saveAccountAndaOpenMainActivity();
                break;
        }
    }

    private void saveAccountAndaOpenMainActivity() {
        //TODO 不判断用户是否点击备份了keystore,并且只对账号简单的存储
        //将账号信息保存到本地
        AccountInfoDb accountInfoDb = new AccountInfoDb();
        accountInfoDb.setAccount(mAccount);
        accountInfoDb.setKeystore(mKeystore);
        SharedPreferencesUtils.setParam(this, BOTTOS_ACCOUNT_INFO, new Gson().toJson(accountInfoDb));
        //跳转到首页
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
