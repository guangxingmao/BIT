package com.hackathon.bottosapp.bottos_sdk.net.api;


import com.hackathon.bottosapp.bottos_sdk.entity.AccountInfo;
import com.hackathon.bottosapp.bottos_sdk.entity.BlockHeight;
import com.hackathon.bottosapp.bottos_sdk.entity.TradeInfo;
import com.hackathon.bottosapp.bottos_sdk.net.request.RequestAccountInfo;
import com.hackathon.bottosapp.bottos_sdk.net.request.RequestDataSign;

/**
 * Created by xionglh on 2018/9/4
 */

@SuppressWarnings("unchecked")
public class ApiWrapper extends RetrofitClient {


    public void requestBlockHeight(RequestCallBackImp<BlockHeight> requestCallBackImp) {
        applySchedulers(getService(ApiService.class).requestBlockHeight()).subscribe(newMySubscriber(requestCallBackImp));
    }

    public void sendTransaction(RequestDataSign sendTransactionRequest, RequestCallBackImp<TradeInfo> requestCallBackImp) {
        applySchedulers(getService(ApiService.class).transactionSend(toRequestBody(sendTransactionRequest))).subscribe(newMySubscriber(requestCallBackImp));
    }

    public void getAccountInfo(RequestAccountInfo accountInfo, RequestCallBackImp<AccountInfo> requestCallBackImp) {
        applySchedulers(getService(ApiService.class).getAccountInfo(toRequestBody(accountInfo))).subscribe(newMySubscriber(requestCallBackImp));
    }
}
