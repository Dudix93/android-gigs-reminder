package com.mdodot.gigsreminder.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AddressComponent;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.tabs.TabLayout;
import com.mdodot.gigsreminder.Adapters.EventTabLayoutAdapter;
import com.mdodot.gigsreminder.DBEntries.BandEntry;
import com.mdodot.gigsreminder.DBHelper;
import com.mdodot.gigsreminder.DBEntries.GigEntry;
import com.mdodot.gigsreminder.Models.BandModel;
import com.mdodot.gigsreminder.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class GigActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int gigId;
    private int venueId;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private TextView bandNameTextView ;
    private TextView eventDateTextView;
    private Context mContext;
    private List<Place.Field> placeFields;
    public ArrayList<BandModel> supportsList;
    private HashMap<String, String> address_components;
    private HashMap<String, String> placeDetails;
    private PlacesClient placesClient;
    private Place place;
    private String placeId;
    private String venueTown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gig);
        gigId = getIntent().getExtras().getInt("gigId");
        mContext = getApplicationContext();
        bandNameTextView = findViewById(R.id.band_name);
        eventDateTextView = findViewById(R.id.event_date);
        dbHelper = new DBHelper(mContext);
        db = dbHelper.getReadableDatabase();
        loadGigData();
        fetchPlaceDetails();
        db.close();
    }

    public void setupTabLayout() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText(mContext.getResources().getString(R.string.location)));
        tabLayout.addTab(tabLayout.newTab().setText(mContext.getResources().getString(R.string.lineup)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        EventTabLayoutAdapter eventTabLayoutAdapter = new EventTabLayoutAdapter(this,
                getSupportFragmentManager(),
                tabLayout.getTabCount(),
                placeDetails,
                supportsList);
        viewPager.setAdapter(eventTabLayoutAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void loadGigData(){
        Cursor gig = dbHelper.getGigById(db, this.gigId);
        Cursor bandsByEvent = dbHelper.getBandsByEvent(db, this.gigId);
        this.supportsList = new ArrayList<>();
        if (gig.getCount() > 0) {
            gig.moveToFirst();
            int gigBandPos = gig.getColumnIndex(GigEntry.COL_EVENT_BAND);
            int gigDatePos = gig.getColumnIndex(GigEntry.COL_EVENT_DATE);
            int gigTimePos = gig.getColumnIndex(GigEntry.COL_EVENT_TIME);
            int gigTownPos = gig.getColumnIndex(GigEntry.COL_EVENT_TOWN);
            int gigVenuePos = gig.getColumnIndex(GigEntry.COL_EVENT_VENUE);

            bandNameTextView.setText(gig.getString(gigBandPos));
            String gigTimeAndDate = gig.getString(gigDatePos) + " " + gig.getString(gigTimePos);
            eventDateTextView.setText(gigTimeAndDate);
            venueId = gig.getInt(gigVenuePos);
            venueTown = gig.getString(gigTownPos);
        }
        if (bandsByEvent.getCount() > 0) {
            while (bandsByEvent.moveToNext()) {
                int bandNamePos = bandsByEvent.getColumnIndex(BandEntry.COL_BAND_NAME);
                supportsList.add(new BandModel(supportsList.size(), bandsByEvent.getString(bandNamePos)));
            }
        }
        else  {
            Toast.makeText(mContext, "Error loading the gig info", Toast.LENGTH_SHORT);
        }
    }

    public void fetchPlaceDetails() {
        placeId = dbHelper.getPlaceId(db, venueId);
        if (placeId != null && !placeId.equals("error")) {
            if (!Places.isInitialized()) {
                Places.initialize(mContext, getString(R.string.places_api_key), Locale.US);
            }
            placesClient = Places.createClient(mContext);
            placeFields = Arrays.asList(
                    Place.Field.ID,
                    Place.Field.NAME,
                    Place.Field.LAT_LNG,
                    Place.Field.ADDRESS,
                    Place.Field.ADDRESS_COMPONENTS,
                    Place.Field.PHONE_NUMBER
            );
            FetchPlaceRequest request = FetchPlaceRequest.builder(placeId, placeFields).build();
            placesClient.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                public void onSuccess(FetchPlaceResponse response) {
                    fillPlaceDetails(response.getPlace());
                    setupTabLayout();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    if (exception instanceof ApiException) {
                        Toast.makeText(mContext, exception.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            Toast.makeText(mContext, "Error fetching place details", Toast.LENGTH_SHORT).show();
        }
    }

    public void fillPlaceDetails(Place place) {
        address_components = new HashMap<String, String>();
        for (AddressComponent addressComponent : place.getAddressComponents().asList()) {
            for (String type: addressComponent.getTypes()) {
                switch (type) {
                    case "street_number" :
                        address_components.put(type, addressComponent.getName());
                        break;
                    case "route" :
                        address_components.put(type, addressComponent.getName());
                        break;
                    case "country" :
                        address_components.put(type, addressComponent.getName());
                        break;

                }
            }
        }

        placeDetails = new HashMap<String, String>();
        placeDetails.put("venue_name", place.getName());
        placeDetails.put("venue_address", address_components.get("route") +" "+ address_components.get("street_number") +", "+ venueTown);
        placeDetails.put("venue_phone", place.getPhoneNumber() == null ? getResources().getString(R.string.no_phone_number) : place.getPhoneNumber());
    }
}