package com.mdodot.gigsreminder.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.libraries.places.api.model.Place;
import com.mdodot.gigsreminder.Fragments.LineupListFragment;
import com.mdodot.gigsreminder.Fragments.LocationDetailsFragment;
import com.mdodot.gigsreminder.Models.BandModel;

import java.util.ArrayList;
import java.util.HashMap;

public class EventTabLayoutAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private int mTotalTabs;
    private ArrayList<BandModel> supportsList;
    private HashMap<String, String> placeDetails;
    private HashMap<String, String> eventDetails;
    private Place place;

    public EventTabLayoutAdapter(Context context ,
                                 FragmentManager fragmentManager ,
                                 int totalTabs,
                                 HashMap<String, String> placeDetails,
                                 ArrayList<BandModel> supportsList,
                                 Place place,
                                 HashMap<String, String> eventDetails) {
        super(fragmentManager);
        mContext = context;
        mTotalTabs = totalTabs;
        this.placeDetails = placeDetails;
        this.supportsList = supportsList;
        this.place = place;
        this.eventDetails = eventDetails;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new LocationDetailsFragment(this.placeDetails, this.place, this.eventDetails);
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