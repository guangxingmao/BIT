package com.hackathon.bottosapp.bottos_sdk.net.request;

import com.hackathon.bottosapp.bottos_sdk.utils.ArraysUtils;
import com.hackathon.bottosapp.bottos_sdk.utils.msgpack.MsgPack;

/**
 * Created by xionglh on 2018/9/13
 */
public class CreateAccountParamsRequest {
    private String name;
    private String pubkey;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPubkey() {
        return pubkey;
    }

    public void setPubkey(String pubkey) {
        this.pubkey = pubkey;
    }


    public static long[] getPackParam(CreateAccountParamsRequest param) {
        long[] arsize = MsgPack.packArraySize(2);
        long[] froml = MsgPack.packStr16(param.getName());
        long[] to1 = MsgPack.packStr16(param.getPubkey());
        return ArraysUtils.arrayCopylong(arsize, froml, to1);
    }
}
