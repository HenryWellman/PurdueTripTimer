package com.purduetriptimer;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
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
            "CARY - Cary Quadrangle", "ERHT - Earhart Residence Hall", "FST - First Street Towers",
            "GRFN - Griffin Residence Hall North", "GRFS - Griffin Residence Hall South (Formerly Third Street Suites)",
            "HARR - Harrison Residence Hall", "HAWK - Hawkins Hall", "HCRN - Honors College and Residences North",
            "HCRS - Honors College and Residences South", "HILL - Hillenbrand Residence Hall",
            "HLTP - Hilltop Apartments", "MCUT - McCutcheon Residence Hall", "MRDH - Meredith Residence Hall",
            "MRDS - Meredith Residence Hall South", "OWEN - Owen Residence Hall", "PVIL - Purdue Village",
            "SHRV - Shreve Residence Hall", "TARK - Tarkington Residence Hall", "WILY - Wiley Residence Hall",
            "WNSR - Windsor Residence Halls", "FORD - Ford Dining Court", "WDCT - Wiley Dining Court",
            "AERO - Aerospace Science Laboratory", "ARMS - Armstrong Hall of Engineering",
            "BCHM - Biochemistry Building", "BIND - Bindley Bioscience Center",
            "BRNG - Beering Hall of Liberal Arts and Education", "BRWN - Brown Laboratory of Chemistry",
            "CL50 - Class of 1950 Lecture Hall", "CRTN - Hobart and Russell Creighton Hall of Animal Sciences",
            "EE - Electrical Engineering Building", "EHSB - Equine Health Sciences Building",
            "ELLT - Elliott Hall of Music", "FRNY - Forney Hall of Chemical Engineering", "HAAS - Haas Hall",
            "HAMP - Hampton Hall of Civil Engineering", "HANS - Hansen Life Sciences Research Building",
            "HIKS - Hicks Undergraduate Library", "HLAB - Herrick Laboratories", "JNSN - Johnson Hall of Nursing",
            "KNOY - Knoy Hall of Technology", "KRCH - Krach Leadership Center", "LILY - Lilly Hall of Life Sciences",
            "LWSN - Lawson Computer Science Building", "LYNN - Lynn Hall of Veterinary Medicine",
            "MATH - Mathematical Sciences Building", "ME - Mechanical Engineering Building",
            "MJIS - Jischke Hall of Biomedical Engineering", "MRRT - Marriott Hall", "MTHW - Matthews Hall",
            "PHYS - Physics Building", "REC - Recitation Building", "RHPH - Heine Pharmacy Building",
            "SMTH - Smith Hall", "STON - Stone Hall", "UNIV - University Hall", "WALC - Wilmeth Active Learning Center",
            "YONG - Young Hall", "HOVD  - Hovde Hall of Administration",
            "PUSH - Purdue University Student Health Center", "SCHL - Schleman Hall of Student Services",
            "SMLY - Smalley Center for Housing and Food Services Administration", "AQUA - Boilermaker Aquatic Center",
            "CREC - Co-Rec", "MACK - Mackey Arena", "PMU - Purdue Memorial Union", "STDM - Ross-Ade Stadium",
            "STEW - Stewart Center", "TREC - Turf Recreation Exercise Center"
    };

    static final String[] TRAVEL_METHODS = {"Biking", "Driving", "E-Scooter", "Skateboarding", "Walking"};

    static boolean validateBuilding(String building) {
        int search = Arrays.binarySearch(PURDUE_BUILDINGS, building);
        return search != -1;
    }

    static boolean validateMethod(String method) {
        int search = Arrays.binarySearch(TRAVEL_METHODS, method);
        return search != -1;
    }

    private String getAverage(String from, String to, String method) {
        double avg = 0.0;
        double total = 0.0;
        File f = new File(getFilesDir(), "trip.txt");
        ArrayList<String> data = MainActivity.readTripData(f);

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).contains(from) && data.get(i).contains(to) && data.get(i).contains(method)) {
                String[] fields = data.get(i).split(",");
                double seconds = Double.parseDouble(fields[3]);
                total = total + seconds;
            }
        }
        if (total == 0) {
            return "0";
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

        favorites = (Button) findViewById(R.id.favorites);
        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FavoriteActivity.class));
            }
        });

        getTimeTextHeader = (TextView) findViewById(R.id.textView5);
        getTimeText = (TextView) findViewById(R.id.textView6);
        getTime = (Button) findViewById(R.id.button2);

        //Travel from with autocompletion
        final AutoCompleteTextView fromText = findViewById(R.id.travelFrom);
        ArrayAdapter<String> fromAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, PURDUE_BUILDINGS);
        fromText.setAdapter(fromAdapter);

        //Travel to with autocompletion
        final AutoCompleteTextView toText = findViewById(R.id.travelTo);
        ArrayAdapter<String> toAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, PURDUE_BUILDINGS);
        toText.setAdapter(toAdapter);

        //Travel method with a spinner
        final Spinner dropdown = findViewById(R.id.travelMethod);
        ArrayAdapter<String> dropdownAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, TRAVEL_METHODS);
        dropdown.setAdapter(dropdownAdapter);

        //Makes the Time required textview and label visible.
        getTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromText.getText().toString().equals("") || toText.getText().toString().equals("")) {
                    CharSequence toastText = "Please enter the full details of your trip!";
                    Toast toast = Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT);
                    toast.show();
                } else if (getAverage(fromText.getText().toString(), toText.getText().toString(), dropdown.getSelectedItem().toString()).equals("0")) {
                    CharSequence toastText = "Trip data not available!";
                    Toast toast = Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    getTimeTextHeader.setVisibility(View.VISIBLE);
                    getTimeText.setVisibility(View.VISIBLE);
                    getTimeText.setText(getAverage(fromText.getText().toString(), toText.getText().toString(), dropdown.getSelectedItem().toString()));
                }
            }
        });
    }

    public void launchTimer(View view) {
        Intent intent = new Intent(this, TimerActivity.class);
        startActivity(intent);
    }

}