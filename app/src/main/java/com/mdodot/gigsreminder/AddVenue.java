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

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.util.Calendar;

public class AddVenue extends AppCompatActivity {

    private AwesomeValidation awesomeValidation;
    private DBHelper dbHelper;
    public EditText editTextTown, editTextVenue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_venue);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        dbHelper = new DBHelper(this);
    }

    public void saveEvent(View view) {
        boolean formIsValid = false;
        editTextTown = (EditText) findViewById(R.id.TownValue);
        editTextVenue = (EditText) findViewById(R.id.VenueValue);

        awesomeValidation.addValidation(this, editTextTown.getId(), "(.|\\s)*\\S(.|\\s)*", R.string.town_error );
        awesomeValidation.addValidation(this, editTextVenue.getId(), "(.|\\s)*\\S(.|\\s)*", R.string.venue_error );

        if (awesomeValidation.validate()) {
//            Intent data = new Intent();
//            data.putExtra("venueName", editTextTown.getText().toString());
//            data.putExtra("townName", editTextVenue.getText().toString());
            setResult(RESULT_OK);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(VenueEntry.COL_VENUE_NAME, editTextVenue.getText().toString());
            values.put(VenueEntry.COL_VENUE_TOWN, editTextTown.getText().toString());
            db.insert(VenueEntry.TABLE_NAME, null, values);
            db.close();
            finish();
        }
    }
}
