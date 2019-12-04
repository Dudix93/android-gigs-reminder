package com.mdodot.gigsreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private String[] gigsList = new String[]{
            "As I Lay Dying",
            "Currents",
            "Counterparts",
            "Fit For A King",
            "Bury Tomorrow",
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.list);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.array_list_item, R.id.array_element, gigsList);
        listView.setAdapter(arrayAdapter);
    }

}
