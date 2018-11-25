package com.hackathon.bottosapp.ui.wallet;

import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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

public class ImportKeystoreActivity extends BaseActivity implements View.OnClickListener {

    private AppCompatEditText mImportKeystoreEdit;
    private AppCompatEditText mImportPasswordEdit;

    @Override
    public int initLayout() {
        return R.layout.activity_import_keystore;
    }

    @Override
    public Toolbar getToolbar() {
        return findViewById(R.id.toolbar);
    }

    @Override
    public void initView() {
        AppCompatTextView toolbarTitleTv = findViewById(R.id.toolbar_title_tv);
        toolbarTitleTv.setText("导入Keystore");
        mImportKeystoreEdit = findViewById(R.id.import_keystore_edit);
        mImportPasswordEdit = findViewById(R.id.import_password_edit);
        AppCompatButton importKeystoreBtn = findViewById(R.id.import_keystore_btn);
        importKeystoreBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String keystore = mImportKeystoreEdit.getText().toString().trim();
        String password = mImportPasswordEdit.getText().toString().trim();
        if (TextUtils.isEmpty(keystore)) {
            onToast("请导入 Keystore");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            onToast("请输入密码");
            return;
        }
        try {
            String privateKey = BotcManger.getInstance().getWalletService().recoverKeystore(password, keystore);
            //解密成功
            WalletFile walletFile = new Gson().fromJson(keystore, WalletFile.class);
            if (walletFile != null) {
                if (TextUtils.isEmpty(walletFile.getAccount())) {
                    onToast("账号不存在！");
                    return;
                }
                //将账户信息保存本地
                AccountInfoDb accountInfoDb = new AccountInfoDb();
                accountInfoDb.setKeystore(keystore);
                accountInfoDb.setAccount(walletFile.getAccount());
                SharedPreferencesUtils.setParam(this, BOTTOS_ACCOUNT_INFO, new Gson().toJson(accountInfoDb));
                //跳转到首页
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        } catch (Exception e) {
            onToast("请输入正确的 Keystore 或密码");
        }

    }
}
