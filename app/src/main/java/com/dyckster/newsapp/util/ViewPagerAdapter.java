package com.dyckster.newsapp.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    public Fragment getActiveFragment(ViewPager container, int position, FragmentManager manager) {
        String name = makeFragmentName(container.getId(), position);
        return manager.findFragmentByTag(name);
    }

    public List<Fragment> getFragmentList() {
        return mFragmentList;
    }

    private String makeFragmentName(int viewId, int index) {
        return "android:switcher:" + viewId + ":" + index;
    }

}
