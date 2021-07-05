package com.softwise.tracewizs.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.softwise.tracewizs.fragment.DocumentFragment;
import com.softwise.tracewizs.fragment.MaterialTypeFragment;
import com.softwise.tracewizs.fragment.PDFDocumentFragment;
import com.softwise.tracewizs.fragment.TruckDetailsFragment;
import com.softwise.tracewizs.models.AllGateEntry;

import java.util.HashMap;


public class GateEntryStepsPagerAdapter extends FragmentStatePagerAdapter {

    private HashMap<Integer, Fragment> positionFragmentMap;

    public GateEntryStepsPagerAdapter(FragmentManager fm, AllGateEntry allGateEntry) {
        super(fm);
        positionFragmentMap = new HashMap<>();
        positionFragmentMap.put(0, new MaterialTypeFragment(allGateEntry));
        positionFragmentMap.put(1, new TruckDetailsFragment(allGateEntry));
        positionFragmentMap.put(2, new PDFDocumentFragment(allGateEntry));
        //positionFragmentMap.put(2, new DocumentFragment(allGateEntry));
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
