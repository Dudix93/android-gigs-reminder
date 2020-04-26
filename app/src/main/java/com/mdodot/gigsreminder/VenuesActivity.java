package com.mdodot.gigsreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class VenuesActivity extends AppCompatActivity {

    ArrayList<VenueModel> venuesList;
    ListView listView;
    DBHelper dbHelper;
    DataManager dataManager;
    private static VenuesAdapter adapter;
    static final int REQUEST_CODE = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            adapter.add(new VenueModel(
                    data.getExtras().getString("venueName"),
                    data.getExtras().getString("townName")
            ));

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(VenueEntry.COL_VENUE_NAME, data.getExtras().getString("venueName"));
            values.put(VenueEntry.COL_VENUE_TOWN, data.getExtras().getString("townName"));
            db.insert(VenueEntry.TABLE_NAME, null, values);
            db.close();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venues);
        dbHelper = new DBHelper(this);
        listView = (ListView)findViewById(R.id.list);
        dataManager = DataManager.getInstance();
        loadData();
        adapter = new VenuesAdapter(venuesList,getApplicationContext());
        listView.setAdapter(adapter);
        FloatingActionButton fab = findViewById(R.id.addVenue);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddVenue.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    public void loadData() {
        dataManager.loadFromDatabase(dbHelper);
        dataManager.loadVenuesFromDatabase(dataManager.venuesCursor);
        venuesList = dataManager.venuesList;
    }
    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
