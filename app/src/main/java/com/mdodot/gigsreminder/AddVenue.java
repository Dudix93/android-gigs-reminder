package com.mdodot.gigsreminder;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.util.Calendar;

public class AddVenue extends AppCompatActivity {

    private AwesomeValidation awesomeValidation;
    private DBHelper dbHelper;
    private EditText editTextTown, editTextVenue;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_venue);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        dbHelper = new DBHelper(this);
        editTextTown = (EditText) findViewById(R.id.TownValue);
        editTextVenue = (EditText) findViewById(R.id.VenueValue);
        extras = getIntent().getExtras();
        populateFieldsToEdit();

        Toolbar toolbar = findViewById(R.id.addVenueToolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitle(extras != null ? R.string.venue_edit : R.string.venue_add);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

            if (extras == null) {
                db.insert(VenueEntry.TABLE_NAME, null, values);
            }
            else {
                db.update(VenueEntry.TABLE_NAME, values, VenueEntry.COL_VENUE_ID + "=" + extras.get("venueId"), null);
            }

            db.close();
            finish();
        }
    }

    private void populateFieldsToEdit() {
        if (extras != null) {
            editTextVenue.setText(extras.getString("venueName"));
            editTextTown.setText(extras.getString("venueTown"));
        }
    }
}