package com.purduetriptimer;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.*;
import androidx.fragment.app.FragmentActivity;

import java.io.File;

public class TimerActivity extends FragmentActivity {
    private Chronometer chronometer;
    private boolean running;
    private boolean timerStarted;
    private long pauseOffset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        chronometer = findViewById(R.id.chronometer1);
        Button resumeButton = findViewById(R.id.resumeButton);
        Button pauseButton = findViewById(R.id.pauseButton);
        Button resetButton = findViewById(R.id.resetButton);

        resumeButton.setVisibility(Button.INVISIBLE);
        pauseButton.setVisibility(Button.INVISIBLE);
        resetButton.setVisibility(Button.INVISIBLE);
    }


    public void startStopChronometer(View view) {
        Button startStopButton = findViewById(R.id.startStop);
        Button submitButton = findViewById(R.id.submit);
        Button resumeButton = findViewById(R.id.resumeButton);
        Button pauseButton = findViewById(R.id.pauseButton);
        Button resetButton = findViewById(R.id.resetButton);
        TextView timeStatusText = findViewById(R.id.timeStatusText);

        // if the timer is not yet clicked
        if (!timerStarted) {
            chronometer = findViewById(R.id.chronometer1);
            // Elapsed real time returns the milli seconds since boot, including time spent in sleep
            // allows the timer to start when the start button is clicked
            // pause offset is the time elapsed after the timer is paused and subtracts it
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
            startStopButton.setText("End Trip");
            resumeButton.setVisibility(Button.VISIBLE);
            pauseButton.setVisibility(Button.VISIBLE);
            resetButton.setVisibility(Button.VISIBLE);
            timerStarted = true;
            timeStatusText.setText("");

        } else {
            // stop when timer has been "started"
            chronometer.stop();
            startStopButton.setVisibility(Button.INVISIBLE);
            submitButton.setVisibility(Button.VISIBLE);
            resumeButton.setVisibility(Button.INVISIBLE);
            pauseButton.setVisibility(Button.INVISIBLE);
            resetButton.setVisibility(Button.INVISIBLE);

            timeStatusText.setText("");
        }
    }

    public void resumeChronometer (View view) {
        if(!running) {
            TextView timeStatusText = findViewById(R.id.timeStatusText);
            // Elapsed real time returns the milli seconds since boot, including time spent in sleep
            // allows the timer to start when the start button is clicked
            // pause offset is the time elapsed after the timer is paused and subtracts it
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
            timeStatusText.setText("");
        }
    }

    public void pauseChronometer(View view) {
        TextView timeStatusText = findViewById(R.id.timeStatusText);
        if (running) {
            chronometer.stop();
            //pause offset is the time
            pauseOffset =  SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
            timeStatusText.setText("Paused");
        }
    }

    public void resetChronometer(View view) {
        TextView timeStatusText = findViewById(R.id.timeStatusText);
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
        timeStatusText.setText("");
    }

    public void submitTrip(View view) {
        if (chronometer == null)
            return;

        // read info from input
        Intent intent = getIntent();
        String from = intent.getStringExtra("from");
        String to = intent.getStringExtra("to");
        String method = intent.getStringExtra("method");
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