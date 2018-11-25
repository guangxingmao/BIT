package com.hackathon.bottosapp.ui.mine;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hackathon.bottosapp.R;
import com.hackathon.bottosapp.base.BaseFragment;
import com.hackathon.bottosapp.bean.AccountTransationRecord;
import com.hackathon.bottosapp.bottos_sdk.BotcManger;
import com.hackathon.bottosapp.bottos_sdk.entity.AccountInfo;
import com.hackathon.bottosapp.bottos_sdk.net.api.NetRequestException;
import com.hackathon.bottosapp.bottos_sdk.net.api.RequestCallBackImp;
import com.hackathon.bottosapp.ui.adpter.MineAdapter;
import com.hackathon.bottosapp.ui.listener.AppBarStateChangeListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class MineFragment extends BaseFragment {

    private RecyclerView mMineRlv;
    private MineAdapter  mMineAdapter;

    private AppCompatTextView mMineAccountTv;
    private AppCompatTextView mMineAmountTv;
    private AppBarLayout      mMineAppBar;
    private AppCompatTextView mToolbarAccountTv;
    private AppCompatTextView mToolbarAmountTv;

    public static MineFragment newInstance() {
        MineFragment mineFragment = new MineFragment();
        return mineFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initViews(View root) {
        mMineAccountTv = root.findViewById(R.id.mine_account_tv);
        mToolbarAccountTv = root.findViewById(R.id.toolbar_account_tv);
        mToolbarAmountTv = root.findViewById(R.id.toolbar_amount_tv);
        mMineAmountTv = root.findViewById(R.id.mine_amount_tv);
        mMineAppBar = root.findViewById(R.id.mine_appbar);
        mMineRlv = root.findViewById(R.id.mine_rlv);
        mMineRlv = root.findViewById(R.id.mine_rlv);

        mMineRlv = root.findViewById(R.id.mine_rlv);
        mMineAdapter = new MineAdapter(R.layout.rlv_item_mine, null);
        mMineRlv.setLayoutManager(new LinearLayoutManager(getContext()));
        mMineRlv.setAdapter(mMineAdapter);
        mMineAppBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {     //展开
                    mToolbarAccountTv.setVisibility(View.INVISIBLE);
                    mToolbarAmountTv.setVisibility(View.INVISIBLE);
                } else if (state == State.COLLAPSED) { //折叠状态
                    mToolbarAccountTv.setVisibility(View.VISIBLE);
                    mToolbarAmountTv.setVisibility(View.VISIBLE);
                } else {              //中间状态
                    mToolbarAccountTv.setVisibility(View.INVISIBLE);
                    mToolbarAmountTv.setVisibility(View.INVISIBLE);
                }
            }
        });
        //获取账号信息
        getAccountInfo();
        //获取账号的上传下载信息
        getAccountTransationRecord();
    }


    private void getAccountTransationRecord() {
        ArrayList<AccountTransationRecord> accountTransationRecords = new ArrayList<>();
        AccountTransationRecord accountTransationRecord1 = new AccountTransationRecord();
        accountTransationRecord1.setName("薛之谦-动物世界");
        accountTransationRecord1.setContent("人类用沙，想捏出梦的通天塔，为贪婪不惜代价，驾驭着昂贵的木马，巢穴一层层叠加，最后却一丝不挂，别害怕，我们都孤寡。");
        accountTransationRecord1.setStatus(0);
        accountTransationRecord1.setUploadTime("2018年11月1日");
        accountTransationRecord1.setType(0);
        accountTransationRecords.add(accountTransationRecord1);

        AccountTransationRecord accountTransationRecord2 = new AccountTransationRecord();
        accountTransationRecord2.setName("区块链从入门到放弃");
        accountTransationRecord2.setContent("币圈熊市不好混，吃枣药丸");
        accountTransationRecord2.setStatus(1);
        accountTransationRecord2.setUploadTime("2018年11月20日");
        accountTransationRecord2.setType(0);
        accountTransationRecords.add(accountTransationRecord2);

        AccountTransationRecord accountTransationRecord3 = new AccountTransationRecord();
        accountTransationRecord3.setName("正在出售。。。");
        accountTransationRecord3.setContent("脑回路不好用");
        accountTransationRecord3.setStatus(2);
        accountTransationRecord3.setUploadTime("2018年11月25日");
        accountTransationRecord3.setType(0);
        accountTransationRecords.add(accountTransationRecord3);

        mMineAdapter.setNewData(accountTransationRecords);
        //        BotcManger.getInstance().getWalletService()
        //                .getAccountTransationRecord();
    }

    private void getAccountInfo() {
        BotcManger.getInstance().getWalletService()
                .getAccountInfo("test111111", new RequestCallBackImp<AccountInfo>() {
                    @Override
                    public void onError(NetRequestException apiexception) {
                        super.onError(apiexception);
                    }

                    @Override
                    public void onNext(AccountInfo accountInfo) {
                        mMineAccountTv.setText(accountInfo.getAccount_name());
                        String balance = new BigDecimal(accountInfo.getBalance()).divide(new BigDecimal(100000000), 4, RoundingMode.DOWN).toPlainString();
                        mMineAmountTv.setText(balance);
                        mToolbarAccountTv.setText(String.format("账号：%s", accountInfo.getAccount_name()));
                        mToolbarAmountTv.setText(String.format("余额：%s", balance));
                    }
                });
    }
}
