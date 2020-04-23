package com.mdodot.gigsreminder;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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
    public EditText editTextTown, editTextVenue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_venue);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
    }

    public void saveEvent(View view) {
        boolean formIsValid = false;
        editTextTown = (EditText) findViewById(R.id.TownValue);
        editTextVenue = (EditText) findViewById(R.id.VenueValue);

        awesomeValidation.addValidation(this, editTextTown.getId(), "(.|\\s)*\\S(.|\\s)*", R.string.town_error );
        awesomeValidation.addValidation(this, editTextVenue.getId(), "(.|\\s)*\\S(.|\\s)*", R.string.venue_error );

        if (awesomeValidation.validate()) {
            Intent data = new Intent();
            data.putExtra("venueName", editTextTown.getText().toString());
            data.putExtra("townName", editTextVenue.getText().toString());
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
