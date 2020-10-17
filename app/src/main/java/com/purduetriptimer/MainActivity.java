package com.purduetriptimer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends AppCompatActivity {

    private static final String[] places = new String[] {
            "Aspire at Discovery Park", "Cary Quadrangle", "Griffin Residence Hall North",
            "Griffin Residence Hall South (Formerly Third Street Suites)"
    };
    private static final String[] transportationMethod = new String[] {
            "Walking", "Bicycle", "Skateboard"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Travel from
        AutoCompleteTextView fromText = findViewById(R.id.travelFrom);
        ArrayAdapter<String> fromAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, places);
        fromText.setAdapter(fromAdapter);

        //Travel to
        AutoCompleteTextView toText = findViewById(R.id.travelTo);
        ArrayAdapter<String> toAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, places);
        toText.setAdapter(toAdapter);

        //Travel method
        AutoCompleteTextView travelText = findViewById(R.id.travelMethod);
        ArrayAdapter<String> travelAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, transportationMethod);
        travelText.setAdapter(travelAdapter);

    }




}