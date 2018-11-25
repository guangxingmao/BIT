package com.hackathon.bottosapp;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.hackathon.bottosapp.base.BaseActivity;
import com.hackathon.bottosapp.base.BaseFragment;
import com.hackathon.bottosapp.ui.mine.MineFragment;
import com.hackathon.bottosapp.ui.transaction.TransationFragment;

public class MainActivity extends BaseActivity {

    FrameLayout          mMainContentFl;
    BottomNavigationView mMainNavigation;
    private FragmentManager mFragmentManager;
    private BaseFragment    mMineFragment;
    private BaseFragment    mTransationFragment;
    private BaseFragment    mCurrentFragment;

    public static final String MINE_FG        = "mine_fg";
    public static final String TRANSACTION_FG = "transaction_fg";

    @Override
    public int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mMainContentFl = findViewById(R.id.main_content_fl);
        mMainNavigation = findViewById(R.id.main_navigation);
        mMainNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        initDefaultFragment();
    }

    private void initDefaultFragment() {
        mFragmentManager = getSupportFragmentManager();
        mMineFragment = (BaseFragment) mFragmentManager.findFragmentByTag(MINE_FG);
        mTransationFragment = (BaseFragment) mFragmentManager.findFragmentByTag("");
        if (mMineFragment == null)
            mMineFragment = MineFragment.newInstance();
        if (mTransationFragment == null)
            mTransationFragment = TransationFragment.newInstance();
        if (mMineFragment != null && !mMineFragment.isAdded()) {
            mFragmentManager.beginTransaction()
                    .add(R.id.main_content_fl, mMineFragment, MINE_FG)
                    .commitAllowingStateLoss();
        }
        mCurrentFragment = mMineFragment;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:          //我的
                    switchFragment(mMineFragment, MINE_FG);
                    return true;
                case R.id.navigation_transaction:   //交易
                    switchFragment(mTransationFragment, TRANSACTION_FG);
                    return true;
            }
            return false;
        }
    };

    public void switchFragment(BaseFragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        //未加载进容器
        if (fragment != null) {
            if (!fragment.isAdded() && !fragment.isRemoving()) {
                if (mCurrentFragment != null) {
                    fragmentTransaction.disallowAddToBackStack();
                    fragmentTransaction.hide(mCurrentFragment).add(R.id.main_content_fl, fragment, tag);
                } else {
                    fragmentTransaction.add(R.id.main_content_fl, fragment, tag);
                }
            } else {   //已经加载进容器里去了
                if (mCurrentFragment != null) {
                    fragmentTransaction.hide(mCurrentFragment).show(fragment);
                } else {
                    fragmentTransaction.show(fragment);
                }
            }
            mCurrentFragment = fragment;
            if (!isFinishing()) {
                fragmentTransaction.commitAllowingStateLoss();
                getSupportFragmentManager().executePendingTransactions();
            }
        }
    }

}
