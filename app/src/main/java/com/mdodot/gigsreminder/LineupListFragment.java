package com.mdodot.gigsreminder;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class LineupListFragment extends Fragment {

    private final ArrayList<BandModel> supportsList;

    public LineupListFragment(ArrayList<BandModel> supportsList) {
        this.supportsList = supportsList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (supportsList.size() > 0) {
            View layoutView = inflater.inflate(R.layout.lineup_listview, container, false);
            ListView supportsListView = layoutView.findViewById(R.id.supportsListView);
            BandsAdapter supportsAdapter = new BandsAdapter(this.supportsList, getContext());
            supportsListView.setAdapter(supportsAdapter);
            return layoutView;
        }
        else {
            View layoutView = inflater.inflate(R.layout.screen_message, container, false);
            TextView screenMessageTextView = layoutView.findViewById(R.id.screen_text_message);
            screenMessageTextView.setText(getResources().getText(R.string.no_supports));
            return layoutView;
        }
    }
}