package com.example.lanyetc.campusgo.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.lanyetc.campusgo.ui.fragment.Fragment1;
import com.example.lanyetc.campusgo.ui.fragment.Fragment2;
import com.example.lanyetc.campusgo.ui.fragment.Fragment3;
import com.example.lanyetc.campusgo.ui.fragment.Fragment4;


/**
 * Created by ZHANGXY on 2018/3/17.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    String[] title ={"全部","失物","招领","我发布的"};
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    Fragment4 fragment4;
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                fragment1 = new Fragment1();
                return fragment1;
            case 1:
                fragment2 = new Fragment2();
                return fragment2;
            case 2:
                fragment3 = new Fragment3();
                return fragment3;
            case 3:
                fragment4 = new Fragment4();
                return fragment4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {

        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}