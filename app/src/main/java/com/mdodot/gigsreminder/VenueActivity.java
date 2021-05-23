package com.mdodot.gigsreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class VenueActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Place place;
    private String placeId;
    private PlacesClient placesClient;
    private Bundle extras;
    private Context mContext;
    private TextView venueNameTextView;
    private TextView venueTownTextView;
    private SupportMapFragment mapFragment;
    private List<Place.Field> placeFields;
    private UiSettings mUiSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue);
        mContext = getApplicationContext();
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        extras = getIntent().getExtras();
        placeId = extras.getString("placeId");
        venueNameTextView = (TextView)findViewById(R.id.venue_name);
        venueTownTextView = (TextView)findViewById(R.id.venue_town);
        venueNameTextView.setText(extras.getString("venueName"));
        venueTownTextView.setText(extras.getString("venueTown"));
        fetchPlaceDetails();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        setLocation();
    }

    public void fetchPlaceDetails() {
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.places_api_key), Locale.US);
        }
        placesClient = Places.createClient(mContext);
        placeFields = Arrays.asList(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS,
                Place.Field.ADDRESS_COMPONENTS
        );
        FetchPlaceRequest request = FetchPlaceRequest.builder(placeId, placeFields).build();
        placesClient.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
            public void onSuccess(FetchPlaceResponse response) {
                place = response.getPlace();
                mapFragment.getMapAsync((OnMapReadyCallback) mapFragment.getContext());
                findViewById(R.id.map_get_directions).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            URL mapsURL = new URL("https://www.google.com/maps/dir/?api=1");
                            String uri = String.format(Locale.ENGLISH, mapsURL.toString().concat(String.format("&dir_action=navigate&destination=%s&destination_place_id=%s",
                                    place.getAddress(),
                                    place.getId())));
                            Intent googleMapsActivity = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                            googleMapsActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(googleMapsActivity);
                        }
                        catch (MalformedURLException e) {
                            Toast.makeText(mContext, e.getMessage() + "", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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

    public void setLocation() {
        LatLng coordinations = place.getLatLng();
        mMap.addMarker(new MarkerOptions().position(coordinations));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinations, 16.0f));
    }
}