package com.hackathon.bottosapp.ui.transaction;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.hackathon.bottosapp.R;
import com.hackathon.bottosapp.base.BaseFragment;

import java.util.ArrayList;

public class TransationFragment extends BaseFragment {

    public static TransationFragment newInstance() {
        TransationFragment transationFragment = new TransationFragment();
        return transationFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_transaction;
    }


    @Override
    public void initViews(View root) {
        TabLayout tabLayout = root.findViewById(R.id.transation_tab);
        ViewPager vb = root.findViewById(R.id.tansaction_vp);
        ArrayList<String> titleDatas = new ArrayList<>();
        titleDatas.add("买入");
        titleDatas.add("卖出");
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(BuyFragment.newInstance());
        fragmentList.add(SellFragment.newInstance());
        MyViewPageAdapter myViewPageAdapter = new MyViewPageAdapter(getChildFragmentManager(), titleDatas, fragmentList);
        vb.setAdapter(myViewPageAdapter);
        tabLayout.setupWithViewPager(vb);
        tabLayout.setTabsFromPagerAdapter(myViewPageAdapter);
    }

    public class MyViewPageAdapter extends FragmentPagerAdapter {
        private ArrayList<String>   titleList;
        private ArrayList<Fragment> fragmentList;

        public MyViewPageAdapter(FragmentManager fm, ArrayList<String> titleList, ArrayList<Fragment> fragmentList) {
            super(fm);
            this.titleList = titleList;
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }
}
