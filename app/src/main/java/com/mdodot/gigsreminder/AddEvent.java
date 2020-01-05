package com.mdodot.gigsreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class AddEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
    }

    public void saveEvent(View view) {
        final String band = ((EditText) findViewById(R.id.BandValue)).getText().toString();
        final String town = ((EditText) findViewById(R.id.TownValue)).getText().toString();
        Intent data = new Intent();
        data.putExtra("bandName", band);
        data.putExtra("townName", town);
        setResult(RESULT_OK, data);
        finish();
    }
}
