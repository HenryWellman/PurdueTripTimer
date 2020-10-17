package com.purduetriptimer;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.*;
import androidx.fragment.app.FragmentActivity;

import java.io.File;

public class TimerActivity extends FragmentActivity {
    private AutoCompleteTextView textViewFrom;
    private AutoCompleteTextView textViewTo;
    private AutoCompleteTextView textViewMethod;
    private Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        ArrayAdapter<String> buildingAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, MainActivity.PURDUE_BUILDINGS);
        ArrayAdapter<String> methodAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, MainActivity.TRAVEL_METHODS);

        textViewFrom = findViewById(R.id.From);
        textViewTo = findViewById(R.id.To);
        textViewMethod = findViewById(R.id.Method);

        textViewFrom.setAdapter(buildingAdapter);
        textViewTo.setAdapter(buildingAdapter);
        textViewMethod.setAdapter(methodAdapter);
    }

    public void startStopChronometer(View view) {
        Button startStopButton = findViewById(R.id.startStop);
        Button submitButton = findViewById(R.id.submit);
        chronometer = findViewById(R.id.chronometer1);

        CharSequence timeText;
        // read timer
        timeText = chronometer.getText();

        // start if time = 0
        if (timeText.equals("00:00")) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            startStopButton.setText("Stop Trip");
        } else {
            // stop when time > 0 and hide button
            chronometer.stop();
            startStopButton.setVisibility(Button.INVISIBLE);
            submitButton.setVisibility(Button.VISIBLE);
        }
    }

    public void submitTrip(View view) {
        if (chronometer == null)
            return;

        // read info from input
        String from = textViewFrom.getText().toString();
        String to = textViewTo.getText().toString();
        String method = textViewMethod.getText().toString();
        String time = chronometer.getText().toString();

        // validate inputs
        if (!MainActivity.validateBuilding(from) || !MainActivity.validateBuilding(to) ||
                !MainActivity.validateMethod(method)) {
            CharSequence toastText = "Your input is invalid! Please check your input and resubmit.";
            Toast toast = Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        // write to file
        CharSequence toastText;
        try {
            File f = new File(getFilesDir(), "trip.txt");
            MainActivity.storeTripData(f, from, to, method, time);
            toastText = "Thank you! Your submission is successful!";
        } catch (Exception e) {
            toastText = "An error occurred!";
        }

        // show status toast, and end activity
        Toast toast = Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT);
        toast.show();
        finish();
    }
}