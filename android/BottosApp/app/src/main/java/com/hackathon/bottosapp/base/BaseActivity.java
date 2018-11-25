package com.hackathon.bottosapp.base;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
        initBaseView();
    }

    private void initBaseView() {
        if (getToolbar() != null) {
            setSupportActionBar(getToolbar());
            if (getSupportActionBar() != null)
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
        initView();
    }

    public abstract int initLayout();

    public abstract void initView();

    public Toolbar getToolbar() {
        return null;
    }

    public void onToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void onClickbackUpKey(String title, String text) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(title, text);
        if (clipboardManager != null) {
            clipboardManager.setPrimaryClip(clipData);
        }
        onToast(title + "成功");
    }
}
