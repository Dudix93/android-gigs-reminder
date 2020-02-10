package com.mdodot.gigsreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
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

public class MainActivity extends AppCompatActivity {

    ArrayList<GigModel> dataModels;
    ListView listView;
    private static CustomAdapter adapter;
    static final int REQUEST_CODE = 1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.add_event:
            Intent intent = new Intent(this, AddEvent.class);
            startActivityForResult(intent, REQUEST_CODE);
            return(true);
        case R.id.venues:
            Snackbar.make(this.findViewById(android.R.id.content), "b", 2000).show();
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
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.list);

        dataModels = new ArrayList<>();

        DBHelper dbHelper = new DBHelper(this);

        Cursor c = dbHelper.readFromDB();
        while(c.moveToNext()) {
            dataModels.add(
                new GigModel(
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getString(4)
                )
            );
            c.moveToNext();
        }
        adapter = new CustomAdapter(dataModels,getApplicationContext());
        listView.setAdapter(adapter);
        dbHelper.close();
    }
}
