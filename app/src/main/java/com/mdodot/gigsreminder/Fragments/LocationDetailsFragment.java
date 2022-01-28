package com.mdodot.gigsreminder.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mdodot.gigsreminder.R;

import java.util.HashMap;

public class LocationDetailsFragment extends Fragment {

    private View layoutView;
    private TextView venueNameTextView;
    private TextView venueAddressTextView ;
    private TextView venuePhoneTextView;
    private HashMap<String, String> placeDetails;

    public LocationDetailsFragment(HashMap<String, String> placeDetails) {
        this.placeDetails = placeDetails;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        layoutView =  inflater.inflate(R.layout.location_details, container, false);
        this.venueNameTextView = (TextView) layoutView.findViewById(R.id.venue_name);
        this.venueAddressTextView = (TextView) layoutView.findViewById(R.id.venue_address);
        this.venuePhoneTextView = (TextView) layoutView.findViewById(R.id.venue_phone);

        this.venueNameTextView.setText(this.placeDetails.get("venue_name"));
        this.venueAddressTextView.setText(this.placeDetails.get("venue_address"));
        this.venuePhoneTextView.setText(this.placeDetails.get("venue_phone"));

        return layoutView;
    }

}