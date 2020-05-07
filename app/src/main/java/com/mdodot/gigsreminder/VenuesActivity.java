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
    Intent intent;
    private static VenuesAdapter adapter;
    static final int REQUEST_CODE = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            loadData();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venues);
        dbHelper = new DBHelper(this);
        loadData();
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
        listView = (ListView)findViewById(R.id.list);
        dataManager = DataManager.getInstance();
        dataManager.loadFromDatabase(dbHelper);
        dataManager.loadVenuesFromDatabase(dataManager.venuesCursor);
        venuesList = dataManager.venuesList;
        adapter = new VenuesAdapter(venuesList,this);
        listView.setAdapter(adapter);
    }

    protected void deleteVenue(int venueId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbHelper.deleteVenue(db, venueId);
        if (!adapter.isEmpty()) {
            for (int i = 0; i < dataManager.venuesList.size(); i++) {
                VenueModel venue = dataManager.venuesList.get(i);
                if (venue.getId() == venueId) {
                    adapter.remove(venue);
                    adapter.notifyDataSetChanged();
                    break;
                }
            }
        }
        Snackbar.make(this.findViewById(android.R.id.content), "Venue deleted.", 2000).show();
    }

    public void editVenue(VenueModel venueModel) {
        intent = new Intent(this, AddVenue.class);
        intent.putExtra("venueId", venueModel.getId());
        intent.putExtra("venueName", venueModel.getName());
        intent.putExtra("venueTown", venueModel.getTown());
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
