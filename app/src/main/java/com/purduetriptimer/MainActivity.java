package com.purduetriptimer;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.*;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private Button favorites;
    private Button getTime;
    private TextView getTimeText;
    private TextView getTimeTextHeader;


    static final String[] PURDUE_BUILDINGS = {"ADPA-C - Aspire at Discovery Park",
            "AERO - Aerospace Science Laboratory", "AQUA - Boilermaker Aquatic Center",
            "ARMS - Armstrong Hall of Engineering", "BCHM - Biochemistry Building", "BIND - Bindley Bioscience Center",
            "BRNG - Beering Hall of Liberal Arts and Education", "BRWN - Brown Laboratory of Chemistry",
            "CARY - Cary Quadrangle", "CL50 - Class of 1950 Lecture Hall", "CREC - Co-Rec",
            "CRTN - Hobart and Russell Creighton Hall of Animal Sciences", "EE - Electrical Engineering Building",
            "EHSB - Equine Health Sciences Building", "ELLT - Elliott Hall of Music", "ERHT - Earhart Residence Hall",
            "FORD - Ford Dining Court", "FRNY - Forney Hall of Chemical Engineering", "FST - First Street Towers",
            "GRFN - Griffin Residence Hall North", "GRFS - Griffin Residence Hall South (Third Street Suites)",
            "HAAS - Haas Hall", "HAMP - Hampton Hall of Civil Engineering",
            "HANS - Hansen Life Sciences Research Building", "HARR - Harrison Residence Hall",
            "HAWK - Hawkins Hall", "HCRN - Honors College and Residences North",
            "HCRS - Honors College and Residences South", "HIKS - Hicks Undergraduate Library",
            "HILL - Hillenbrand Residence Hall", "HLAB - Herrick Laboratories", "HLTP - Hilltop Apartments",
            "HOVD  - Hovde Hall of Administration", "JNSN - Johnson Hall of Nursing", "KNOY - Knoy Hall of Technology",
            "KRCH - Krach Leadership Center", "LILY - Lilly Hall of Life Sciences",
            "LWSN - Lawson Computer Science Building", "LYNN - Lynn Hall of Veterinary Medicine", "MACK - Mackey Arena",
            "MATH - Mathematical Sciences Building", "MCUT - McCutcheon Residence Hall",
            "ME - Mechanical Engineering Building", "MJIS - Jischke Hall of Biomedical Engineering",
            "MRDH - Meredith Residence Hall", "MRDS - Meredith Residence Hall South", "MRRT - Marriott Hall",
            "MTHW - Matthews Hall", "OWEN - Owen Residence Hall", "PHYS - Physics Building",
            "PMU - Purdue Memorial Union", "PUSH - Purdue University Student Health Center", "PVIL - Purdue Village",
            "REC - Recitation Building", "RHPH - Heine Pharmacy Building", "SCHL - Schleman Hall of Student Services",
            "SHRV - Shreve Residence Hall", "SMLY - Smalley Center for Housing and Food Services Administration",
            "SMTH - Smith Hall", "STDM - Ross-Ade Stadium", "STEW - Stewart Center", "STON - Stone Hall",
            "TARK - Tarkington Residence Hall", "TREC - Turf Recreation Exercise Center", "UNIV - University Hall",
            "WALC - Wilmeth Active Learning Center", "WDCT - Wiley Dining Court", "WILY - Wiley Residence Hall",
            "WNSR - Windsor Residence Halls", "YONG - Young Hall"
    };

    static final String[] TRAVEL_METHODS = {"Biking", "Driving", "E-Scooter", "Skateboarding", "Walking"};

    private CharSequence selectedTravelMethod;
    private AutoCompleteTextView instanceFromText;
    private AutoCompleteTextView instanceToText;
    private Spinner instanceTravel;

    static boolean validateBuilding(String building) {
        if (building == null)
            return false;
        int search = Arrays.binarySearch(PURDUE_BUILDINGS, building);
        return search >= 0;
    }

    static boolean validateMethod(String method) {
        if (method == null)
            return false;
        int search = Arrays.binarySearch(TRAVEL_METHODS, method);
        return search >= 0;
    }

    static String getAverage(File f, String from, String to, String method) {
        double avg = 0.0;
        double total = 0.0;
        ArrayList<String> data = MainActivity.readTripData(f);

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).contains(from) && data.get(i).contains(to) && data.get(i).contains(method)) {
                String[] fields = data.get(i).split(",");
                double seconds = Double.parseDouble(fields[3]);
                total = total + seconds;
            }
        }
        avg = total / data.size();
        String answer = "";
        int numMinutes = (int) avg / 60;
        int numSeconds = (int) avg % 60;

        answer = numMinutes + " Minutes " + numSeconds + " Seconds";
        return answer;
    }

    static ArrayList<String> readTripData(File f) {
        FileReader fr;
        BufferedReader bfr;
        try {
            fr = new FileReader(f);
            bfr = new BufferedReader(fr);
        } catch (FileNotFoundException e) {
            return new ArrayList<String>();
        }

        //stores the users data
        ArrayList<String> data = new ArrayList<String>();

        try {
            String line = bfr.readLine();

            while (line != null) {
                data.add(line);
                line = bfr.readLine();
            }
            bfr.close();
            fr.close();
        } catch (IOException e) {
            return new ArrayList<String>();
        }
        return data;
    }

    static void storeTripData(File f, String from, String to, String method, String time)
            throws FileNotFoundException {
            FileOutputStream fos = new FileOutputStream(f, true);
            PrintWriter pw = new PrintWriter(fos);

            // write time in seconds
            String[] timeParts = time.split(":");
            int minutes = Integer.parseInt(timeParts[0]);
            int seconds = Integer.parseInt(timeParts[1]);
            int totalTime = (minutes * 60) + seconds;

            pw.println(from + "," + to + "," + method + "," + totalTime);
            pw.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        favorites = findViewById(R.id.favorites);
        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FavoriteActivity.class));
            }
        });

        getTimeTextHeader = findViewById(R.id.textView5);
        getTimeText = findViewById(R.id.textView6);
        getTime = findViewById(R.id.button2);

        //Travel from with autocompletion
        final AutoCompleteTextView fromText = findViewById(R.id.travelFrom);
        ArrayAdapter<String> fromAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, PURDUE_BUILDINGS);
        fromText.setAdapter(fromAdapter);
        instanceFromText = fromText;

        //Travel to with autocompletion
        final AutoCompleteTextView toText = findViewById(R.id.travelTo);
        ArrayAdapter<String> toAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, PURDUE_BUILDINGS);
        toText.setAdapter(toAdapter);
        instanceToText = toText;

        //Travel method with a spinner
        final Spinner travelDropdown = findViewById(R.id.travelMethod);
        ArrayAdapter<String> dropdownAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, TRAVEL_METHODS);
        travelDropdown.setAdapter(dropdownAdapter);
        instanceTravel = travelDropdown;
        travelDropdown.setOnItemSelectedListener(new SpinnerSelectedListener());
    }

    public void launchTimer(View view) {
        if (!validateBuilding(instanceFromText.getText().toString())
                || !validateBuilding(instanceToText.getText().toString())
                || !validateMethod(instanceTravel.getSelectedItem().toString())) {
            CharSequence toastText = "Your input is invalid! Please check your input and resubmit.";
            Toast toast = Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        Intent intent = new Intent(this, TimerActivity.class);
        intent.putExtra("from", instanceFromText.getText().toString());
        intent.putExtra("to", instanceToText.getText().toString());
        intent.putExtra("method", selectedTravelMethod);
        startActivity(intent);
    }

    public void onGetTimeClick(View view) {
        if (!validateBuilding(instanceFromText.getText().toString())
                || !validateBuilding(instanceToText.getText().toString())
                || !validateMethod(instanceTravel.getSelectedItem().toString())) {
            getTimeTextHeader.setVisibility(View.INVISIBLE);
            getTimeText.setVisibility(View.INVISIBLE);
            CharSequence toastText = "Your input is invalid! Please check your input and resubmit.";
            Toast toast = Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            getTimeTextHeader.setVisibility(View.VISIBLE);
            getTimeText.setVisibility(View.VISIBLE);
            getTimeText.setText(getAverage(instanceFromText.getText().toString(), instanceToText.getText().toString(),
                    instanceTravel.getSelectedItem().toString()));
        }
    }

    public class SpinnerSelectedListener implements Spinner.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            selectedTravelMethod = parent.getItemAtPosition(pos).toString();
        }

        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }
}