package com.mdodot.gigsreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class AddEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
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
        final String band = ((EditText) findViewById(R.id.BandValue)).getText().toString();
        final String town = ((EditText) findViewById(R.id.TownValue)).getText().toString();
        final String date = ((EditText) findViewById(R.id.EventDateValue)).getText().toString();
        final String time = ((EditText) findViewById(R.id.EventTimeValue)).getText().toString();
        Intent data = new Intent();
        data.putExtra("bandName", band);
        data.putExtra("townName", town);
        data.putExtra("eventDate", date);
        data.putExtra("eventTime", time);
        setResult(RESULT_OK, data);
        finish();
    }
}
