package com.hackathon.bottosapp.ui.transaction;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hackathon.bottosapp.R;
import com.hackathon.bottosapp.base.BaseFragment;
import com.hackathon.bottosapp.bean.BlockChainTransationRecord;
import com.hackathon.bottosapp.ui.adpter.TransactionAdapter;

import java.util.ArrayList;

public class BuyFragment extends BaseFragment {

    public static BuyFragment newInstance() {
        BuyFragment buyFragment = new BuyFragment();
        return buyFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_rlv;
    }

    @Override
    public void initViews(View root) {
        RecyclerView rlv = root.findViewById(R.id.rlv);
        rlv.setLayoutManager(new LinearLayoutManager(getContext()));
        TransactionAdapter transactionAdapter = new TransactionAdapter(R.layout.rlv_item_transaction, null);
        rlv.setAdapter(transactionAdapter);
        ArrayList<BlockChainTransationRecord> blockChainTransationRecords = new ArrayList<>();
        BlockChainTransationRecord blockChainTransationRecord = new BlockChainTransationRecord();
        blockChainTransationRecord.setName("人艰不拆");
        blockChainTransationRecord.setContent("http://www.baidu.com");
        blockChainTransationRecord.setStatus(0);
        blockChainTransationRecord.setUploadTime("2018年11月25日");
        blockChainTransationRecord.setPrice("2.5 BTO");
        blockChainTransationRecord.setAction(0);
        blockChainTransationRecords.add(blockChainTransationRecord);
        BlockChainTransationRecord blockChainTransationRecord1 = new BlockChainTransationRecord();
        blockChainTransationRecord1.setPrice("1.2 BTO");
        blockChainTransationRecord1.setName("滴水湖之旅");
        blockChainTransationRecord1.setContent("感谢项目方");
        blockChainTransationRecord1.setAction(0);
        blockChainTransationRecords.add(blockChainTransationRecord1);
        BlockChainTransationRecord blockChainTransationRecord2 = new BlockChainTransationRecord();
        blockChainTransationRecord2.setPrice("5.0 BTO");
        blockChainTransationRecord2.setName("阳光普照奖");
        blockChainTransationRecord2.setContent("今天天气不错");
        blockChainTransationRecord2.setAction(0);
        blockChainTransationRecords.add(blockChainTransationRecord2);
        BlockChainTransationRecord blockChainTransationRecord3 = new BlockChainTransationRecord();
        blockChainTransationRecord3.setPrice("1.8 BTO");
        blockChainTransationRecord3.setName("给力的小伙子");
        blockChainTransationRecord3.setContent("sdk没更新，好多和文档对不上");
        blockChainTransationRecord3.setAction(0);
        blockChainTransationRecords.add(blockChainTransationRecord3);
        transactionAdapter.setNewData(blockChainTransationRecords);
    }
}
