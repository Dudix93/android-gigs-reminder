package com.mdodot.gigsreminder.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.libraries.places.api.model.Place;
import com.mdodot.gigsreminder.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;

public class LocationDetailsFragment extends Fragment {

    private View layoutView;
    private Place place;
    private TextView venueNameTextView;
    private TextView venueAddressTextView ;
    private TextView venuePhoneTextView;
    private CardView getDirectionsCardView;
    private HashMap<String, String> placeDetails;

    public LocationDetailsFragment(HashMap<String, String> placeDetails, Place place) {
        this.placeDetails = placeDetails;
        this.place = place;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        layoutView =  inflater.inflate(R.layout.gig_details_fragment, container, false);
        this.venueNameTextView = (TextView) layoutView.findViewById(R.id.venue_name);
        this.venueAddressTextView = (TextView) layoutView.findViewById(R.id.venue_address);
        this.venuePhoneTextView = (TextView) layoutView.findViewById(R.id.venue_phone);
        this.getDirectionsCardView = (CardView) layoutView.findViewById(R.id.map_get_directions);

        this.venueNameTextView.setText(this.placeDetails.get("venue_name"));
        this.venueAddressTextView.setText(this.placeDetails.get("venue_address"));
        this.venuePhoneTextView.setText(this.placeDetails.get("venue_phone"));

        if (this.place != null) {
            getDirectionsCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        URL mapsURL = new URL("https://www.google.com/maps/dir/?api=1");
                        String uri = String.format(Locale.ENGLISH, mapsURL.toString().concat(String.format("&dir_action=navigate&destination=%s&destination_place_id=%s",
                                place.getAddress(),
                                place.getId())));
                        Intent googleMapsActivity = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        googleMapsActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getContext().startActivity(googleMapsActivity);
                    }
                    catch (MalformedURLException e) {
                        Toast.makeText(getContext(), e.getMessage() + "", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        return layoutView;
    }
}