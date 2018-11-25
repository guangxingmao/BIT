package com.hackathon.bottosapp.ui.wallet;

import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.hackathon.bottosapp.R;
import com.hackathon.bottosapp.base.BaseActivity;

public class WalletActivity extends BaseActivity implements View.OnClickListener {
    @Override
    public int initLayout() {
        return R.layout.activity_wallet;
    }

    @Override
    public void initView() {
        AppCompatButton walletCreateBtn = findViewById(R.id.wallet_create_btn);
        AppCompatButton walletImportBtn = findViewById(R.id.wallet_import_btn);
        walletCreateBtn.setOnClickListener(this);
        walletImportBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.wallet_create_btn:        //创建钱包
                startActivity(new Intent(this, CreateAccountActivity.class));
                break;
            case R.id.wallet_import_btn:        //导入钱包
                startActivity(new Intent(this, ImportKeystoreActivity.class));
                break;
        }
    }
}
