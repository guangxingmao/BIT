package com.hackathon.bottosapp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.hackathon.bottosapp.bean.AccountInfoDb;
import com.hackathon.bottosapp.utils.SharedPreferencesUtils;

public abstract class BaseFragment extends Fragment {

    protected boolean isViewInitiated;  //view是否加载，默认false
    protected boolean isVisibleToUser;  //是否用户可见,默认false
    protected boolean isDataInitiated;  //数据是否加载,默认false

    public AccountInfoDb mAccountInfoDb;   //当前的bottos账号

    public abstract int getLayoutId();

    public abstract void initViews(View root);

    private BaseActivity mActivity;

    public void fetchData() {
    }

    public AccountInfoDb getAccountInfoDb() {
        if (mAccountInfoDb == null) {
            //如果账户不存在，则查询本地是否存在账户
            String accountInfoString = (String) SharedPreferencesUtils.getParam(getContext(), SharedPreferencesUtils.BOTTOS_ACCOUNT_INFO, "");
            if (!TextUtils.isEmpty(accountInfoString)) {
                mAccountInfoDb = new Gson().fromJson(accountInfoString, AccountInfoDb.class);
            }
        }
        return mAccountInfoDb;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(getLayoutId(), container, false);
        initViews(inflate);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();
    }

    /**
     * @param isVisibleToUser 配合viewPager使用时才调用。
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    private void prepareFetchData() {
        if (isVisibleToUser && isViewInitiated && !isDataInitiated) {
            //加载数据
            fetchData();
            //数据加载成功
            isDataInitiated = true;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            this.mActivity = (BaseActivity) context;
        }
    }

    public BaseActivity getContext() {
        return mActivity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }
}
