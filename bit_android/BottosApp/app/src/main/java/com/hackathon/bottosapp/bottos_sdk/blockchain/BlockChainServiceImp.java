package com.hackathon.bottosapp.bottos_sdk.blockchain;


import com.hackathon.bottosapp.bottos_sdk.BotcManger;
import com.hackathon.bottosapp.bottos_sdk.entity.BlockHeight;
import com.hackathon.bottosapp.bottos_sdk.net.api.RequestCallBackImp;

/**
 * Created by xionglh on 2018/9/6
 */
public class BlockChainServiceImp implements  BlockChainService {

    @Override
    public void getBlockHeight(RequestCallBackImp<BlockHeight> requestCallBackImp) {
        BotcManger.getInstance().getApiWrapper().requestBlockHeight(requestCallBackImp);
    }

}
