package com.hackathon.bottosapp.bottos_sdk.wallet;

import com.hackathon.bottosapp.bottos_sdk.entity.AccountInfo;
import com.hackathon.bottosapp.bottos_sdk.entity.TradeInfo;
import com.hackathon.bottosapp.bottos_sdk.entity.WalletKeyPair;
import com.hackathon.bottosapp.bottos_sdk.net.api.RequestCallBackImp;
import com.hackathon.bottosapp.bottos_sdk.utils.crypto.WalletFile;

/**
 * Created by xionglh on 2018/9/4
 */
public interface WalletService {

    WalletKeyPair createWalletKeyPair();

    String createKeys();

    String createKeystore(String password, String privateKey);

    WalletFile createaWalletFile(String password, String privateKey);

    String recoverKeystore(String pwd, String keystore);

    void sendTransaction(String toUser, String fromUser, long price, String privateKey, RequestCallBackImp<TradeInfo> requestCallBackImp);

    void createAccount(String name, String publicKey, String privateKey, final RequestCallBackImp<TradeInfo> requestCallBackImp);

    void getAccountInfo(String account_name, final RequestCallBackImp<AccountInfo> requestCallBackImp);

}
