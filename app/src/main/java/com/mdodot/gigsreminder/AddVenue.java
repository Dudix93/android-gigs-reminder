package com.mdodot.gigsreminder;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddVenue extends AppCompatActivity {

    private static final String TAG = "AddVenueActivity";
    private AwesomeValidation awesomeValidation;
    private DBHelper dbHelper;
    private EditText editTextTown, editTextVenue;
    private Bundle extras;
    private Geocoder mGeocoder;
    private String placeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_venue);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        dbHelper = new DBHelper(this);
        mGeocoder = new Geocoder(this, Locale.getDefault());
        editTextTown = (EditText) findViewById(R.id.TownValue);
        editTextVenue = (EditText) findViewById(R.id.VenueValue);
        extras = getIntent().getExtras();
        populateFieldsToEdit();

        Toolbar toolbar = findViewById(R.id.addVenueToolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitle(R.string.venue_add);
        if (extras != null) {
            if (extras.get("venueId") != null && extras.get("venueId") != "") toolbar.setTitle(R.string.venue_edit);
        }
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToAddEventActivity(view);
            }
        });

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.places_api_key), Locale.US);
        }

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.ADDRESS_COMPONENTS,
                Place.Field.LAT_LNG
        ));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NotNull Place place) {
                placeId = place.getId();
                editTextVenue.setText(place.getName());
                try {
                    editTextTown.setText(getCityNameByCoordinates(place.getLatLng().latitude, place.getLatLng().longitude));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(@NotNull Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });

    }

    public void saveEvent(View view) {
        boolean formIsValid = false;

        awesomeValidation.addValidation(this, editTextTown.getId(), "(.|\\s)*\\S(.|\\s)*", R.string.town_error );
        awesomeValidation.addValidation(this, editTextVenue.getId(), "(.|\\s)*\\S(.|\\s)*", R.string.venue_error );

        if (awesomeValidation.validate()) {
            setResult(RESULT_OK);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(VenueEntry.COL_VENUE_NAME, editTextVenue.getText().toString());
            values.put(VenueEntry.COL_VENUE_TOWN, editTextTown.getText().toString());
            values.put(VenueEntry.COL_VENUE_PLACE_ID, placeId);

            if (extras != null && extras.get("venueId") != null && extras.get("venueId") != "") {
                db.update(VenueEntry.TABLE_NAME, values, VenueEntry.COL_VENUE_ID + "=" + extras.get("venueId"), null);
            }
            else {
                db.insert(VenueEntry.TABLE_NAME, null, values);
            }

            db.close();
            backToAddEventActivity(view);
        }
    }

    private void populateFieldsToEdit() {
        if (extras != null) {
            editTextVenue.setText(extras.getString("venueName"));
            editTextTown.setText(extras.getString("venueTown"));
        }
    }

    private void backToAddEventActivity(View view) {
        if (extras != null) {
            if (extras.getBoolean("FromAddEventActivity") && extras.get("event") != null) {
                Intent intent = new Intent(view.getContext(), AddEvent.class);
                intent.putExtra("event", (GigModel)extras.get("event"));
                startActivity(intent);
            }
        }
        finish();
    }

    private String getCityNameByCoordinates(double lat, double lon) throws IOException {

        List<Address> addresses = mGeocoder.getFromLocation(lat, lon, 10);
        if (addresses != null && addresses.size() > 0) {
            for (Address adr : addresses) {
                if (adr.getLocality() != null && adr.getLocality().length() > 0) {
                    return adr.getLocality();
                }
            }
        }
        return null;
    }
}
