package com.mdodot.gigsreminder;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class EventTabLayoutAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private int mTotalTabs;
    private ArrayList<BandModel> supportsList;
    private HashMap<String, String> placeDetails;

    public EventTabLayoutAdapter(Context context , FragmentManager fragmentManager , int totalTabs, HashMap<String, String> placeDetails, ArrayList<BandModel> supportsList) {
        super(fragmentManager);
        mContext = context;
        mTotalTabs = totalTabs;
        this.placeDetails = placeDetails;
        this.supportsList = supportsList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new LocationDetailsFragment(this.placeDetails);
            case 1:
                return new LineupListFragment(this.supportsList);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mTotalTabs;
    }
}