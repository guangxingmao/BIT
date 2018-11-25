package com.hackathon.bottosapp.ui.adpter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hackathon.bottosapp.R;
import com.hackathon.bottosapp.bean.BlockChainTransationRecord;

import java.util.List;

public class TransactionAdapter extends BaseQuickAdapter<BlockChainTransationRecord, BaseViewHolder> {

    public TransactionAdapter(int layoutResId, @Nullable List<BlockChainTransationRecord> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BlockChainTransationRecord item) {
        helper.setText(R.id.mine_title_tv, item.getName())
                .setText(R.id.mine_desc_tv, item.getContent())
                .setText(R.id.mine_date_tv, item.getUploadTime())
                .setText(R.id.mine_price_tv, item.getPrice());
        if (item.getAction() == 0) {
            helper.setText(R.id.mine_btn, "买入");
        } else {
            helper.setText(R.id.mine_btn, "卖出");
        }
    }
}
