package com.mdodot.gigsreminder.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.libraries.places.api.model.Place;
import com.mdodot.gigsreminder.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class LocationDetailsFragment extends Fragment {

    private View layoutView;
    private Place place;
    private TextView venueNameTextView;
    private TextView venueAddressTextView ;
    private TextView venuePhoneTextView;
    private CardView getDirectionsCardView;
    private CardView addEventToCalendarCardView;
    private HashMap<String, String> placeDetails;
    private HashMap<String, String> eventDetails;

    public LocationDetailsFragment(HashMap<String, String> placeDetails, Place place, HashMap<String, String> eventDetails) {
        this.placeDetails = placeDetails;
        this.place = place;
        this.eventDetails = eventDetails;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        layoutView =  inflater.inflate(R.layout.gig_details_fragment, container, false);
        this.venueNameTextView = (TextView) layoutView.findViewById(R.id.venue_name);
        this.venueAddressTextView = (TextView) layoutView.findViewById(R.id.venue_address);
        this.venuePhoneTextView = (TextView) layoutView.findViewById(R.id.venue_phone);
        this.getDirectionsCardView = (CardView) layoutView.findViewById(R.id.get_directions_button);
        this.addEventToCalendarCardView = (CardView) layoutView.findViewById(R.id.add_to_calendar_button);

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

        this.addEventToCalendarCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eventDate = eventDetails.get("date")+" "+eventDetails.get("time");
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.UK);
                try {
                    Date date = sdf.parse(eventDate);
                    long miliseconds = date.getTime();
                    Intent intent = new Intent(Intent.ACTION_INSERT);
                    intent.setType("vnd.android.cursor.item/event");
                    intent.putExtra(CalendarContract.Events.TITLE, eventDetails.get("band"));
                    intent.putExtra(CalendarContract.Events.ALL_DAY, false);
                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, miliseconds);
                    intent.putExtra(CalendarContract.Events.EVENT_LOCATION, place.getAddress());

                    if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                        startActivity(intent);
                    }
                } catch(Exception e) {
                    Toast.makeText(getContext(), e.getMessage() + "", Toast.LENGTH_LONG).show();
                }
            }
        });

        return layoutView;
    }
}