package com.hackathon.bottosapp.bottos_sdk;


import com.hackathon.bottosapp.bottos_sdk.blockchain.BlockChainService;
import com.hackathon.bottosapp.bottos_sdk.blockchain.BlockChainServiceImp;
import com.hackathon.bottosapp.bottos_sdk.net.api.ApiWrapper;
import com.hackathon.bottosapp.bottos_sdk.wallet.WalletService;
import com.hackathon.bottosapp.bottos_sdk.wallet.WalletServiceImp;

/**
 * Created by xionglh on 2018/9/6
 */
public class BotcManger {

    private static BotcManger botcManger;

    private ApiWrapper mApiWrapper;

    private BotcManger() {
        mApiWrapper = new ApiWrapper();
    }

    public ApiWrapper getApiWrapper() {
        return mApiWrapper;
    }

    public synchronized static BotcManger getInstance() {
        if (botcManger == null) {
            botcManger = new BotcManger();
        }
        return botcManger;
    }

    public BlockChainService getBlickChainService() {
        return new BlockChainServiceImp();
    }

    public WalletService getWalletService() {
        return new WalletServiceImp();
    }


}
