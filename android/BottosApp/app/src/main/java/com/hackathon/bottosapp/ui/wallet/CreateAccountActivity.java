package com.hackathon.bottosapp.ui.wallet;

import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.hackathon.bottosapp.R;
import com.hackathon.bottosapp.base.BaseActivity;
import com.hackathon.bottosapp.bottos_sdk.BotcManger;
import com.hackathon.bottosapp.bottos_sdk.entity.TradeInfo;
import com.hackathon.bottosapp.bottos_sdk.entity.WalletKeyPair;
import com.hackathon.bottosapp.bottos_sdk.net.api.NetRequestException;
import com.hackathon.bottosapp.bottos_sdk.net.api.RequestCallBackImp;

public class CreateAccountActivity extends BaseActivity implements View.OnClickListener {

    private AppCompatEditText mCreateWalletAccountEdit;
    private AppCompatEditText mCreateWalletPasswordEdit;
    private AppCompatEditText mCreateWalletPasswordAgainEdit;

    @Override
    public int initLayout() {
        return R.layout.activity_create_account;
    }

    @Override
    public Toolbar getToolbar() {
        return findViewById(R.id.toolbar);
    }

    @Override
    public void initView() {
        AppCompatTextView toolbarTitleTv = findViewById(R.id.toolbar_title_tv);
        toolbarTitleTv.setText("创建账号");
        mCreateWalletAccountEdit = findViewById(R.id.create_account_edit);
        mCreateWalletPasswordEdit = findViewById(R.id.create_password_edit);
        mCreateWalletPasswordAgainEdit = findViewById(R.id.create_password_again_edit);
        AppCompatButton createAccountBtn = findViewById(R.id.create_account_btn);
        createAccountBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //创建账号
        requestCreateBotcAccount();
    }

    private void requestCreateBotcAccount() {
        final String accountName = mCreateWalletAccountEdit.getText().toString().trim();
        if (TextUtils.isEmpty(accountName)) {
            onToast("账号名不能为空");
            return;
        }
        //TODO 只能是数字加英文，这块先不判断
        if (accountName.length() < 10 || accountName.length() > 20) {
            onToast("账号名长度为10-20位");
            return;
        }
        final String password = mCreateWalletPasswordEdit.getText().toString().trim();
        String password_again = mCreateWalletPasswordAgainEdit.getText().toString().trim();
        //TODO 这块就先不做密码输入的实时监听，只对最后做判断
        if (!password.equals(password_again)) {
            onToast("两次密码不一致");
            return;
        }
        //创建账号
        final WalletKeyPair walletKeyPair = BotcManger.getInstance().getWalletService().createWalletKeyPair();
        if (walletKeyPair != null) {
            BotcManger.getInstance().getWalletService().createAccount(accountName, walletKeyPair.getPublicKey(), walletKeyPair.getPrivateKey(), new RequestCallBackImp<TradeInfo>() {
                @Override
                public void onError(NetRequestException apiexception) {
                    super.onError(apiexception);
                    onToast(apiexception.getMessage());
                }

                @Override
                public void onNext(TradeInfo tradeInfo) {
                    Intent intent = new Intent(CreateAccountActivity.this, CreateKeystoreActivity.class);
                    intent.putExtra("privatekey", walletKeyPair.getPrivateKey());
                    intent.putExtra("password", password);
                    intent.putExtra("account", accountName);
                    startActivity(intent);
                }
            });
        }
    }
}
