package com.cs.week.modle.adpter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cs.week.base.BaseFragment;
import com.cs.week.modle.activity.MainActivity;

import java.util.List;

/**
 * Created by chenshuai on 2016/10/12.
 *
 */

public class TabAdapter extends FragmentStatePagerAdapter {
    private List<BaseFragment> mList_fm;
    public TabAdapter(FragmentManager fm, List<BaseFragment> mList_fm) {
        super(fm);
        this.mList_fm=mList_fm;
    }

    @Override
    public Fragment getItem(int position) {
        return mList_fm.get(position);
    }
    @Override
    public int getCount() {
        return mList_fm.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return MainActivity.tabTitle[position];
    }
}
