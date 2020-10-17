package com.purduetriptimer;

import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Arrays;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

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

    static final String[] TRAVEL_METHODS = {"Walking", "Biking", "Skateboarding", "E-Scooter", "Driving"};

    static boolean validateBuilding(String building) {
        int search = Arrays.binarySearch(PURDUE_BUILDINGS, building);
        return search != -1;
    }

    static boolean validateMethod(String method) {
        int search = Arrays.binarySearch(TRAVEL_METHODS, method);
        return search != -1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchTimer(View view) {
        Intent intent = new Intent(this, TimerActivity.class);
        startActivity(intent);
    }
}