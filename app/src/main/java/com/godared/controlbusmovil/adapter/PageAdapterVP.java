package com.godared.controlbusmovil.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by ronald on 15/04/2017.
 */

public class PageAdapterVP extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;
    public PageAdapterVP(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
