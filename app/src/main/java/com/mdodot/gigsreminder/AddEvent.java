package com.mdodot.gigsreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.lang.String.valueOf;

public class AddEvent extends AppCompatActivity {

    private AwesomeValidation awesomeValidation;
    private DBHelper dbHelper;
    private EditText editTextBand, editTextTown, editTextDate, editTextTime;
    private Spinner spinnerVenue;
    private Button buttonAddVenue;
    private Bundle extras;
    private ArrayList<VenueModel> venuesList;
    private DataManager dataManager;
    private static VenuesAdapter adapter;
    private int selectedVenueId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        dbHelper = new DBHelper(this);

        editTextBand = (EditText) findViewById(R.id.BandValue);
        editTextTown = (EditText) findViewById(R.id.TownValue);
        editTextDate = (EditText) findViewById(R.id.EventDateValue);
        editTextTime = (EditText) findViewById(R.id.EventTimeValue);
        spinnerVenue = (Spinner) findViewById(R.id.EventVenueValue);
        extras = getIntent().getExtras();

        dataManager = dataManager.getInstance();
        dataManager.loadFromDatabase(dbHelper);
        dataManager.loadVenuesFromDatabase(dataManager.venuesCursor);
        venuesList = dataManager.venuesList;

        editTextDate.setInputType(InputType.TYPE_NULL);
        editTextTime.setInputType(InputType.TYPE_NULL);
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog picker = new DatePickerDialog(AddEvent.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                editTextDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        },
                year, month, day);
                picker.show();
            }
        });
        editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog picker = new TimePickerDialog(AddEvent.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String minutes = minute < 10 ? "0" + valueOf(minute) : valueOf(minute);
                                editTextTime.setText(valueOf(hourOfDay) + ":" + minutes);
                            }
                        },
                        hour, minute, true);
                picker.show();
            }
        });

        List<VenueModel> venuesList = new ArrayList<VenueModel>();

        populateSpinnerWithVenues(venuesList);

        ArrayAdapter<VenueModel> dataAdapter = new ArrayAdapter<VenueModel>(this, android.R.layout.simple_spinner_item, venuesList);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerVenue.setAdapter(dataAdapter);
        populateFieldsToEdit();
        spinnerVenue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedVenueId = ((VenueModel)parent.getSelectedItem()).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        Toolbar toolbar = findViewById(R.id.addGigToolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitle(extras != null ? R.string.event_edit : R.string.event_add);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void populateSpinnerWithVenues(List<VenueModel> venuesNamesList) {
        adapter = new VenuesAdapter(venuesList,this);
        if (!adapter.isEmpty()) {
            venuesNamesList.addAll(dataManager.venuesList);
        }
        else {
            venuesNamesList.add(new VenueModel(-1, getResources().getString(R.string.venue_add), null));
            spinnerVenue.setEnabled(false);
            findViewById(R.id.Submit).setEnabled(false);
        }
    }

    private void populateFieldsToEdit() {
        if (extras != null) {
            editTextBand.setText(extras.getString("eventBand"));
            editTextTown.setText(extras.getString("eventTown"));
            editTextTime.setText(extras.getString("eventTime"));
            editTextDate.setText(extras.getString("eventDate"));
            for (VenueModel venue : venuesList) {
                if (venue.getId() == (extras.getInt("venueId"))) {
                    spinnerVenue.setSelection(venuesList.indexOf(venue));
                }
            }
        }
    }

    public void saveEvent(View view) {
        boolean formIsValid = false;

        awesomeValidation.addValidation(this, editTextBand.getId(), "(.|\\s)*\\S(.|\\s)*", R.string.band_error );
        awesomeValidation.addValidation(this, editTextTown.getId(), "(.|\\s)*\\S(.|\\s)*", R.string.town_error );
        awesomeValidation.addValidation(this, editTextDate.getId(), "^(10|[1-9]|[0-2][1-9]|[3][0-1])\\/(10|[1-9]|[0-1][1-2])\\/[2][0][0-2][0-5]$", R.string.date_error );
        awesomeValidation.addValidation(this, editTextTime.getId(), "^([1-9]|1[0-9]|2[0-3]):[0-5][0-9]$", R.string.time_error );

        if (awesomeValidation.validate()) {
            setResult(RESULT_OK);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(GigEntry.COL_EVENT_BAND, editTextBand.getText().toString());
            values.put(GigEntry.COL_EVENT_TOWN, editTextTown.getText().toString());
            values.put(GigEntry.COL_EVENT_DATE, editTextDate.getText().toString());
            values.put(GigEntry.COL_EVENT_TIME, editTextTime.getText().toString());
            values.put(GigEntry.COL_EVENT_VENUE, selectedVenueId);

            if (extras == null) {
                db.insert(GigEntry.TABLE_NAME, null, values);
            }
            else {
                db.update(GigEntry.TABLE_NAME, values, GigEntry.COL_EVENT_ID + "=" + extras.get("eventId"), null);
            }
            db.close();
            finish();
        }
    }

   public void addNewVenue(View view) {
        Intent intent = new Intent(view.getContext(), AddVenue.class);
        intent.putExtra("FromAddEventActivity", true);
        startActivity(intent);
        finish();
    }
}
