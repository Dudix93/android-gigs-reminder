package com.mdodot.gigsreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class GigsActivity extends AppCompatActivity {

    ArrayList<GigModel> gigsList;
    ListView listView;
    DBHelper dbHelper;
    DataManager dataManager;
    private static GigsAdapter adapter;
    static final int REQUEST_CODE = 1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
            switch(item.getItemId()) {
                case R.id.add_event:
                    intent = new Intent(this, AddEvent.class);
                    startActivityForResult(intent, REQUEST_CODE);
                    return(true);
                case R.id.venues:
                    intent = new Intent(this, VenuesActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                    return(true);
                case R.id.settings:
                    Snackbar.make(this.findViewById(android.R.id.content), "c", 2000).show();
                    return(true);
            }
        return(super.onOptionsItemSelected(item));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            adapter.add(new GigModel(
                    data.getExtras().getString("bandName"),
                    data.getExtras().getString("townName"),
                    data.getExtras().getString("eventDate"),
                    data.getExtras().getString("eventTime")
            ));

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(GigEntry.COL_EVENT_BAND, data.getExtras().getString("bandName"));
            values.put(GigEntry.COL_EVENT_TOWN, data.getExtras().getString("townName"));
            values.put(GigEntry.COL_EVENT_DATE, data.getExtras().getString("eventDate"));
            values.put(GigEntry.COL_EVENT_TIME, data.getExtras().getString("eventTime"));
            db.insert(GigEntry.TABLE_NAME, null, values);
            db.close();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);
        listView = (ListView)findViewById(R.id.list);
        dataManager = DataManager.getInstance();
        loadData();
        adapter = new GigsAdapter(gigsList,getApplicationContext());
        listView.setAdapter(adapter);
    }

    public void loadData() {
        dataManager.loadFromDatabase(dbHelper);
        dataManager.loadGigsFromDatabase(dataManager.gigsCursor);
        gigsList = dataManager.gigsList;
    }
    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
