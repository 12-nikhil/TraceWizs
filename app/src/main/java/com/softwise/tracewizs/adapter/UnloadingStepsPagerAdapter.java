package com.softwise.tracewizs.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.softwise.tracewizs.fragment.CheckAndCorrectFragment;
import com.softwise.tracewizs.fragment.StorageLocationFragment;
import com.softwise.tracewizs.models.AllGateEntry;
import com.softwise.tracewizs.models.MaterialStorage;
import com.softwise.tracewizs.models.UnloadingCheck;

import java.util.HashMap;


public class UnloadingStepsPagerAdapter extends FragmentStatePagerAdapter {

    private HashMap<Integer, Fragment> positionFragmentMap;

    public UnloadingStepsPagerAdapter(FragmentManager fm, AllGateEntry allGateEntry) {
        super(fm);
        positionFragmentMap = new HashMap<>();
        positionFragmentMap.put(0, new CheckAndCorrectFragment(allGateEntry));
        positionFragmentMap.put(1, new StorageLocationFragment(allGateEntry));
    }

    @Override
    public Fragment getItem(int position) {
        return positionFragmentMap.get(position);
    }

    @Override
    public int getCount() {
        return positionFragmentMap.size();
    }
}
