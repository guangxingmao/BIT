package com.hackathon.bottosapp.ui.adpter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hackathon.bottosapp.R;
import com.hackathon.bottosapp.bean.AccountTransationRecord;

import java.util.List;

public class MineAdapter extends BaseQuickAdapter<AccountTransationRecord, BaseViewHolder> {

    public MineAdapter(int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AccountTransationRecord item) {
        helper.setText(R.id.mine_title_tv, item.getName())
                .setText(R.id.mine_desc_tv, item.getContent())
                .setText(R.id.mine_date_tv, item.getUploadTime());
        if (item.getStatus() == 0) {        //上传
            helper.setImageResource(R.id.mine_action_iv, R.drawable.ic_upload);
        } else if (item.getStatus() == 1) {  //下载
            helper.setImageResource(R.id.mine_action_iv, R.drawable.ic_download);
        } else if (item.getStatus() == 2) {  //正在出售
            helper.setImageResource(R.id.mine_action_iv, R.drawable.ic_waiting);
        }
    }
}
