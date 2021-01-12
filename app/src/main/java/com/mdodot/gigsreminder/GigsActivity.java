package com.mdodot.gigsreminder;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class GigsActivity extends AppCompatActivity {

    ArrayList<GigModel> gigsList;
    ListView listView;
    DBHelper dbHelper;
    DataManager dataManager;
    Intent intent;
    SQLiteDatabase db;
    private static GigsAdapter adapter;
    static final int REQUEST_CODE = 1;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sorting_gigs, menu);
        inflater.inflate(R.menu.menu_main, menu);
        return true;
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
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);
        loadData();
        Toolbar toolbar = findViewById(R.id.gigsToolbar);
        toolbar.setTitle(R.string.events_list);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.addEvent);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddEvent.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    public void loadData() {
        listView = (ListView)findViewById(R.id.list);
        dataManager = DataManager.getInstance();
        dataManager.loadFromDatabase(dbHelper);
        dataManager.loadGigsFromDatabase(dataManager.gigsCursor);
        gigsList = dataManager.gigsList;
        adapter = new GigsAdapter(gigsList, this);
        listView.setAdapter(adapter);
    }

    protected void deleteGig(int gigId) {
        db = dbHelper.getWritableDatabase();
        dbHelper.deleteGig(db, gigId);
        loadData();
        Snackbar.make(this.findViewById(android.R.id.content), "Event deleted.", 2000).show();
    }

    public void editGig(GigModel gigModel) {
        intent = new Intent(this, AddEvent.class);
        intent.putExtra("eventId", gigModel.getId());
        intent.putExtra("eventBand", gigModel.getBand());
        intent.putExtra("eventTown", gigModel.getTown());
        intent.putExtra("eventTime", gigModel.getTime());
        intent.putExtra("eventDate", gigModel.getDate());
        intent.putExtra("venueId", gigModel.getVenue());
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.venues:
                intent = new Intent(this, VenuesActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                return(true);
            case R.id.settings:
                Snackbar.make(this.findViewById(android.R.id.content), "c", 2000).show();
                return(true);
            case R.id.sort_band_asc:
                isMenuItemChecked(item);
                adapter.sort(new Comparator<GigModel>() {
                    @Override
                    public int compare(GigModel gig1, GigModel gig2) {
                        return gig1.getBand().compareTo(gig2.getBand());
                    }
                });
                return(true);
            case R.id.sort_band_desc:
                isMenuItemChecked(item);
                adapter.sort(new Comparator<GigModel>() {
                    @Override
                    public int compare(GigModel gig1, GigModel gig2) {
                        return gig1.getBand().compareTo(gig2.getBand());
                    }
                });
                Collections.reverse(gigsList);
                adapter = new GigsAdapter(gigsList, this);
                listView.setAdapter(adapter);
                return(true);
            case R.id.sort_date_desc:
                isMenuItemChecked(item);
                adapter.sort(new Comparator<GigModel>() {
                    @Override
                    public int compare(GigModel gig1, GigModel gig2) {
                        try {
                            Date dateOne = sdf.parse(gig1.getDate().replace("/","-"));
                            Date dateTwo = sdf.parse(gig2.getDate().replace("/","-"));
                            return dateOne.compareTo(dateTwo);
                        }
                        catch (ParseException ex) {
                            ex.printStackTrace();
                            return 0;
                        }
                    }
                });
                Collections.reverse(gigsList);
                adapter = new GigsAdapter(gigsList, this);
                listView.setAdapter(adapter);
                return(true);
            case R.id.sort_date_asc:
                isMenuItemChecked(item);
                adapter.sort(new Comparator<GigModel>() {
                    @Override
                    public int compare(GigModel gig1, GigModel gig2) {
                        try {
                            Date dateOne = sdf.parse(gig1.getDate().replace("/","-"));
                            Date dateTwo = sdf.parse(gig2.getDate().replace("/","-"));
                            return dateOne.compareTo(dateTwo);
                        }
                        catch (ParseException ex) {
                            ex.printStackTrace();
                            return 0;
                        }
                    }
                });
                return(true);
        }
        return(super.onOptionsItemSelected(item));
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
