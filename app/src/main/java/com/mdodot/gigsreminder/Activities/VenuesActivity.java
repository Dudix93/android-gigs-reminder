package com.mdodot.gigsreminder.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import com.mdodot.gigsreminder.Adapters.VenuesAdapter;
import com.mdodot.gigsreminder.DBHelper;
import com.mdodot.gigsreminder.DataManager;
import com.mdodot.gigsreminder.Models.VenueModel;
import com.mdodot.gigsreminder.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class VenuesActivity extends AppCompatActivity {

    ArrayList<VenueModel> venuesList;
    ListView listView;
    DBHelper dbHelper;
    DataManager dataManager;
    Intent intent;
    private static VenuesAdapter adapter;
    static final int REQUEST_CODE = 1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sorting_venues, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.sort_venue_asc:
                isMenuItemChecked(item);
                adapter.sort(new Comparator<VenueModel>() {
                    @Override
                    public int compare(VenueModel v1, VenueModel v2) {
                        return v1.getName().compareTo(v2.getName());
                    }
                });
                return(true);
            case R.id.sort_venue_desc:
                isMenuItemChecked(item);
                adapter.sort(new Comparator<VenueModel>() {
                    @Override
                    public int compare(VenueModel v1, VenueModel v2) {
                        return v1.getName().compareTo(v2.getName());
                    }
                });
                Collections.reverse(venuesList);
                adapter = new VenuesAdapter(venuesList, this);
                listView.setAdapter(adapter);
                return(true);
            case R.id.sort_town_asc:
                isMenuItemChecked(item);
                adapter.sort(new Comparator<VenueModel>() {
                    @Override
                    public int compare(VenueModel v1, VenueModel v2) {
                        return v1.getTown().compareTo(v2.getTown());
                    }
                });
                return(true);
            case R.id.sort_town_desc:
                isMenuItemChecked(item);
                adapter.sort(new Comparator<VenueModel>() {
                    @Override
                    public int compare(VenueModel v1, VenueModel v2) {
                        return v1.getTown().compareTo(v2.getTown());
                    }
                });
                Collections.reverse(venuesList);
                adapter = new VenuesAdapter(venuesList, this);
                listView.setAdapter(adapter);
                return(true);
        }
        return(super.onOptionsItemSelected(item));
    }

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
                Intent intent = new Intent(view.getContext(), AddVenueActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        Toolbar toolbar = findViewById(R.id.venuesToolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitle(R.string.venues_list);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

    public void deleteVenue(int venueId) {
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
        intent = new Intent(this, AddVenueActivity.class);
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

    protected void isMenuItemChecked(MenuItem item) {
        if (!item.isChecked()) {
            item.setChecked(true);
        }
    }
}
