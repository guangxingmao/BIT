package com.hackathon.bottosapp.base;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.hackathon.bottosapp.bottos_sdk.BotcManger;

public class BaseApplication extends MultiDexApplication{

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
