package com.mdodot.gigsreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

//    private ListView listView;
//    private String[] gigsList = new String[]{
//            "As I Lay Dying",
//            "Currents",
//            "Counterparts",
//            "Fit For A King",
//            "Bury Tomorrow",};
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        listView = (ListView)findViewById(R.id.list);
//        ArrayAdapter arrayAdapter = new ArrayAdapter(
//                this,
//                R.layout.array_list_item,
//                R.id.band_name, gigsList);
//        listView.setAdapter(arrayAdapter);
//    }

    ArrayList<GigModel> dataModels;
    ListView listView;
    private static CustomAdapter adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.add_event:
            Snackbar.make(this.findViewById(android.R.id.content), "a", 2000).show();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=(ListView)findViewById(R.id.list);

        dataModels= new ArrayList<>();

        dataModels.add(new GigModel("As I Lay Dying", "Wrocław"));
        dataModels.add(new GigModel("Parkway Drive", "Leeds"));
        dataModels.add(new GigModel("Trivium", "Ostrów Wlkp"));

        adapter= new CustomAdapter(dataModels,getApplicationContext());

        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                DataModel dataModel= dataModels.get(position);
//
//                Snackbar.make(view, dataModel.getName()+"\n"+dataModel.getType()+" API: "+dataModel.getVersion_number(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//            }
//        });
    }

}
