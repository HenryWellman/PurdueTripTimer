package com.purduetriptimer;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.*;
import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {
    private String[] favPairs;

    private String[] findFavorites() {
        //contains the top three favorites
        String[] favorites = new String[3];

        File f = new File(getFilesDir(), "trip.txt");
        FileReader fr;
        BufferedReader bfr;
        try {
            fr = new FileReader(f);
            bfr = new BufferedReader(fr);
        } catch (FileNotFoundException e) {
            return favorites;
        }

        //stores the users data
        ArrayList<String> data = new ArrayList<String>();

        //displays the number of times each element in the data ArrayList is present
        ArrayList<Integer> frequency = new ArrayList<Integer>();


        try {
            String line = bfr.readLine();

            while (line != null) {
                data.add(line);
                line = bfr.readLine();
            }
            bfr.close();
        } catch (IOException e) {
            e.printStackTrace();
            return favorites;
        }

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

        System.out.println(data.toString());
        System.out.println(frequency.toString());

        return favorites;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        favPairs = findFavorites();
        Button firstFav = findViewById(R.id.favorites1);
        firstFav.setText(favPairs[0]);
        Button secondFav = findViewById(R.id.favorites2);
        secondFav.setText(favPairs[1]);
        Button thirdFav = findViewById(R.id.favorites3);
        thirdFav.setText(favPairs[2]);
    }
}