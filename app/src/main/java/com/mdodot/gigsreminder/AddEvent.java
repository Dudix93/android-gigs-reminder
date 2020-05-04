package com.mdodot.gigsreminder;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class AddEvent extends AppCompatActivity {

    private AwesomeValidation awesomeValidation;
    private DBHelper dbHelper;
    public EditText editTextBand, editTextTown, editTextDate, editTextTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        dbHelper = new DBHelper(this);
        final EditText date = (EditText) findViewById(R.id.EventDateValue);
        final EditText time = (EditText) findViewById(R.id.EventTimeValue);
        date.setInputType(InputType.TYPE_NULL);
        time.setInputType(InputType.TYPE_NULL);
        date.setOnClickListener(new View.OnClickListener() {
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
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        },
                year, month, day);
                picker.show();
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog picker = new TimePickerDialog(AddEvent.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String minutes = minute < 10 ? "0" + String.valueOf(minute) : String.valueOf(minute);
                                time.setText(String.valueOf(hourOfDay) + ":" + minutes);
                            }
                        },
                        hour, minute, true);
                picker.show();
            }
        });
    }

    public void saveEvent(View view) {
        boolean formIsValid = false;
        editTextBand = (EditText) findViewById(R.id.BandValue);
        editTextTown = (EditText) findViewById(R.id.TownValue);
        editTextDate = (EditText) findViewById(R.id.EventDateValue);
        editTextTime = (EditText) findViewById(R.id.EventTimeValue);

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
            db.insert(GigEntry.TABLE_NAME, null, values);
            db.close();
            finish();
        }
    }
}
