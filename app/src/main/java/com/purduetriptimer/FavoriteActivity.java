package com.purduetriptimer;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

public class FavoriteActivity extends AppCompatActivity {
    private String[] favPairs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        favPairs = new String[3];
        Button firstFav = (Button) findViewById(R.id.favorites1);
        //firstFav.setText(favPairs[0]);
        Button secondFav = (Button) findViewById(R.id.favorites2);
        //secondFav.setText(favPairs[1]);
        Button thirdFav = (Button) findViewById(R.id.favorites3);
        //thirdFav.setText(favPairs[2]);

        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}