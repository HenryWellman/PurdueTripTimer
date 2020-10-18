package com.purduetriptimer;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {
    private String[] favPairs;
    private String[] favorites;

    private String[] findFavorites() {
        //contains the top three favorites
        String[] favorites = new String[3];

        //stores the users data
        File f = new File(getFilesDir(), "trip.txt");
        ArrayList<String> data = MainActivity.readTripData(f);

        //displays the number of times each element in the data ArrayList is present
        ArrayList<Integer> frequency = new ArrayList<Integer>();

        for (int i = 0; i < data.size(); i++) {
            int counter = 1;
            String temp = data.get(i);

            for (int j = 0; j < data.size(); j++) {
                //makes sure it is not adding itself twice
                if (i != j) {
                    if (temp.contentEquals(data.get(j))) {
                        counter = counter + 1;
                    }
                }

                if (j == data.size() - 1) {
                    if (i != j) {
                        if (temp.contentEquals(data.get(j))) {
                            counter = counter + 1;
                        }
                    }
                    frequency.add(counter);  //stores the number of occurrences into the frequency array
                }
            }
        }

        // identifies the most frequent element, adds that value to the favorites array,
        // then deletes that value from the ArrayList.
        for (int i = 0; i < 3; i++) {    //tells the program to run 3 times
            int max = 0;
            int maxLocation = 0;

            for (int j = 0; j < frequency.size(); j++) {
                if (max <= frequency.get(j)) {
                    max = frequency.get(j);
                    maxLocation = j;
                }

                if ( j == frequency.size() - 1) {
                    favorites[i] = data.get(maxLocation);

                    for (int k = 0; k < frequency.size(); k++) {
                        String kd = data.get(k);
                        if (kd != null && kd.equals(favorites[i])) {
                            frequency.set(k, -1);
                            data.set(k, null);
                        }

                    }
                }
            }
        }

        return favorites;
    }

    private String[] parseFavorites() {
        String[] result = new String[favorites.length];
        for (int i = 0; i < favorites.length; i++) {
            if (favorites[i] == null) {
                continue;
            }
            String[] parts = favorites[i].split(",");
            result[i] = String.format("From: %s \n To: %s \n %s", parts[0], parts[1], parts[2]);
        }
        return result;
    }

    public void onFavorite1CLick(View view) {
        String[] parts = favorites[0].split(",");
        File f = new File(getFilesDir(), "trip.txt");
        String average = MainActivity.getAverage(f, parts[0], parts[1], parts[2]);
        TextView timeDisplay = findViewById(R.id.timeDisplay);
        timeDisplay.setText(average);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        favorites = findFavorites();
        favPairs = parseFavorites();
        Button firstFav = findViewById(R.id.favorites1);
        firstFav.setText(favPairs[0]);
        Button secondFav = findViewById(R.id.favorites2);
        secondFav.setText(favPairs[1]);
        Button thirdFav = findViewById(R.id.favorites3);
        thirdFav.setText(favPairs[2]);
    }
}