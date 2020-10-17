package com.purduetriptimer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private static final String[] places = new String[] {
            "Aspire at Discovery Park", "Cary Quadrangle", "Griffin Residence Hall North",
            "Griffin Residence Hall South (Formerly Third Street Suites)"
    };

    private static final String[] modeOfTransportations = new String[]{
            "Walking", "Bicycle","Skateboard", "E-scooter"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Travel from with autocompletion
        AutoCompleteTextView fromText = findViewById(R.id.travelFrom);
        ArrayAdapter<String> fromAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, places);
        fromText.setAdapter(fromAdapter);

        //Travel to with autocompletion
        AutoCompleteTextView toText = findViewById(R.id.travelTo);
        ArrayAdapter<String> toAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, places);
        toText.setAdapter(toAdapter);

        //Travel method with a spinner
        Spinner dropdown = findViewById(R.id.travelMethod);
        ArrayAdapter<String> dropdownAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, modeOfTransportations);
        dropdown.setAdapter(dropdownAdapter);
    }




}