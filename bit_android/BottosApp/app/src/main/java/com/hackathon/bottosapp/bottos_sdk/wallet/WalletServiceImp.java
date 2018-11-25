package com.hackathon.bottosapp.bottos_sdk.wallet;

import com.hackathon.bottosapp.bottos_sdk.BotcManger;
import com.hackathon.bottosapp.bottos_sdk.Config.Constants;
import com.hackathon.bottosapp.bottos_sdk.entity.AccountInfo;
import com.hackathon.bottosapp.bottos_sdk.entity.BlockHeight;
import com.hackathon.bottosapp.bottos_sdk.entity.TradeInfo;
import com.hackathon.bottosapp.bottos_sdk.entity.WalletKeyPair;
import com.hackathon.bottosapp.bottos_sdk.exceptions.BotcError;
import com.hackathon.bottosapp.bottos_sdk.exceptions.BotcException;
import com.hackathon.bottosapp.bottos_sdk.net.api.RequestCallBackImp;
import com.hackathon.bottosapp.bottos_sdk.net.request.CreateAccountParamsRequest;
import com.hackathon.bottosapp.bottos_sdk.net.request.RequestAccountInfo;
import com.hackathon.bottosapp.bottos_sdk.net.request.RequestDataSign;
import com.hackathon.bottosapp.bottos_sdk.net.request.SendTransactionParamsRequest;
import com.hackathon.bottosapp.bottos_sdk.utils.KeystoreKeyCreatTool;
import com.hackathon.bottosapp.bottos_sdk.utils.Strings;
import com.hackathon.bottosapp.bottos_sdk.utils.crypto.CryptTool;
import com.hackathon.bottosapp.bottos_sdk.utils.crypto.WalletFile;

/**
 * Created by xionglh on 2018/9/5
 */
public class WalletServiceImp implements WalletService {

    @Override
    public WalletKeyPair createWalletKeyPair() {
        return KeystoreKeyCreatTool.createWalletKeyPair();
    }

    @Override
    public String createKeys() {
        return KeystoreKeyCreatTool.createKeys();
    }

    @Override
    public String createKeystore(String password, String privateKey) {
        return KeystoreKeyCreatTool.createKeystore(password, privateKey);
    }

    @Override
    public WalletFile createaWalletFile(String password, String privateKey) {
        return KeystoreKeyCreatTool.createWalletFile(password, privateKey);
    }

    @Override
    public String recoverKeystore(String pwd, String keystore) {
        return KeystoreKeyCreatTool.recoverKeystore(pwd, keystore);
    }

    @Override
    public void createAccount(final String name, final String publicKey, final String privateKey, final RequestCallBackImp<TradeInfo> requestCallBackImp) {
        if (Strings.isEmpty(name)) {
            throw new BotcException(BotcError.ACCOUNT_NAME_ERROR);
        }
        if (Strings.isEmpty(publicKey)) {
            throw new BotcException(BotcError.PUBLICKYE_EMPTY_ERR);
        }
        if (Strings.isEmpty(privateKey)) {
            throw new BotcException(BotcError.PRIVATEKEY_EMPTY_ERR);
        }
        BotcManger.getInstance().getBlickChainService().getBlockHeight(new RequestCallBackImp<BlockHeight>() {
            @Override
            public void onNext(BlockHeight blockHeight) {
                RequestDataSign sendTransactionRequest = new RequestDataSign();
                sendTransactionRequest.setVersion(1);
                sendTransactionRequest.setCursor_label(blockHeight.getCursor_label());
                sendTransactionRequest.setCursor_num(blockHeight.getHead_block_num());
                sendTransactionRequest.setLifetime(blockHeight.getHead_block_time() + 30);
                sendTransactionRequest.setCursor_label(blockHeight.getCursor_label());
                sendTransactionRequest.setCursor_num(blockHeight.getHead_block_num());
                sendTransactionRequest.setLifetime(blockHeight.getHead_block_time() + 30);
                sendTransactionRequest.setSender(Constants.CREATE_ACCOUNT_SEND);
                sendTransactionRequest.setMethod(Constants.CREATE_ACCOUNT_METHOD);
                sendTransactionRequest.setContract(Constants.CREATE_ACCOUNT_CONTRACT);
                sendTransactionRequest.setSig_alg(1);
                CreateAccountParamsRequest param = new CreateAccountParamsRequest();
                param.setName(name);
                param.setPubkey(publicKey);
                long[] params = CreateAccountParamsRequest.getPackParam(param);
                sendTransactionRequest.setParam(CryptTool.getHex16(params));
                RequestDataSign.getSignaturedFetchParam(sendTransactionRequest, params, privateKey, blockHeight.getChain_id());
                BotcManger.getInstance().getApiWrapper().sendTransaction(sendTransactionRequest, requestCallBackImp);
            }
        });
    }

    @Override
    public void getAccountInfo(String account_name, RequestCallBackImp<AccountInfo> requestCallBackImp) {
        RequestAccountInfo requestAccountInfo = new RequestAccountInfo();
        requestAccountInfo.setAccount_name(account_name);
        BotcManger.getInstance().getApiWrapper().getAccountInfo(requestAccountInfo, requestCallBackImp);
    }

    @Override
    public void sendTransaction(final String toUser, final String fromUser, final long price, final String privateKey, final RequestCallBackImp<TradeInfo> requestCallBackImp) {
        if (Strings.isEmpty(toUser) || Strings.isEmpty(fromUser)) {
            throw new BotcException(BotcError.PARAMS_EMPTY_EMPTY_ERR);
        }

        BotcManger.getInstance().getBlickChainService().getBlockHeight(new RequestCallBackImp<BlockHeight>() {
            @Override
            public void onNext(BlockHeight blockHeight) {
                RequestDataSign sendTransactionRequest = new RequestDataSign();
                sendTransactionRequest.setVersion(1);
                sendTransactionRequest.setCursor_label(blockHeight.getCursor_label());
                sendTransactionRequest.setCursor_num(blockHeight.getHead_block_num());
                sendTransactionRequest.setLifetime(blockHeight.getHead_block_time() + 30);
                sendTransactionRequest.setSender(fromUser);
                sendTransactionRequest.setMethod(Constants.SEND_TRANSACTION_METHOD);
                sendTransactionRequest.setContract(Constants.SEND_TRANSACTION_CONTRACT);
                sendTransactionRequest.setSig_alg(1);
                SendTransactionParamsRequest param = new SendTransactionParamsRequest();
                param.setTo(toUser);
                param.setFrom(fromUser);
                param.setPrice(price);
                long[] params = SendTransactionParamsRequest.getPackParam(param);
                sendTransactionRequest.setParam(CryptTool.getHex16(params));
                RequestDataSign.getSignaturedFetchParam(sendTransactionRequest, params, privateKey, blockHeight.getChain_id());
                BotcManger.getInstance().getApiWrapper().sendTransaction(sendTransactionRequest, requestCallBackImp);
            }
        });
    }


}
