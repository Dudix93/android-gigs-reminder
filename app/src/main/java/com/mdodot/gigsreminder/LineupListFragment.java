package com.mdodot.gigsreminder;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class LineupListFragment extends Fragment {

    private ArrayList<BandModel> supportsList;

    public LineupListFragment(ArrayList<BandModel> supportsList) {
        this.supportsList = supportsList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.lineup_listview, container, false);

        ListView supportsListView = layoutView.findViewById(R.id.supportsListView);

        BandsAdapter supportsAdapter = new BandsAdapter(this.supportsList, getContext());
        supportsListView.setAdapter(supportsAdapter);

        return layoutView;
    }
}