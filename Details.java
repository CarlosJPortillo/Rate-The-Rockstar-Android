package com.example.carlos.ratetherockstar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Details extends AppCompatActivity {

    private float Rating;
    private int id;
   /* private SharedPreferences mPrefences;
    private SharedPreferences.Editor mEditor;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        Rating = sharedPreferences.getFloat("starRating" + id, 0.0f);
        ratingBar.setRating(Rating);

        if(savedInstanceState != null){
            ratingBar.setRating(savedInstanceState.getFloat("starRating"));
        }


        String rockstarName = intent.getStringExtra("rockStar").toLowerCase();
        ImageView rImage = (ImageView) findViewById(R.id.rockStarImage);
        int rImageID = getResources().getIdentifier(
                rockstarName + "2", "drawable", getPackageName());
        rImage.setImageResource(rImageID);





        TextView rDetails = (TextView) findViewById(R.id.rDetails);
        StringBuilder txt = new StringBuilder("");

        try{
            String line;
            String fileName = rockstarName + ".txt";
            int fileNameID = getResources().getIdentifier(rockstarName, "raw", getPackageName());
            Scanner scan = new Scanner(
                    getResources().openRawResource(fileNameID));
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                txt.append(line);
            }

            rDetails.setText(txt);
            scan.close();
        }
        catch(Exception ex){

        }

        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser)->{
            Rating = ratingBar.getRating();
            Intent goBack = new Intent();
            goBack.putExtra("starRating", rating);
            setResult(RESULT_OK, goBack);
            finish();;

        });

    }
    public void onSaveInstanceState(Bundle savedInstanceState){

        savedInstanceState.putFloat("starRating", Rating);

        super.onSaveInstanceState(savedInstanceState);
    }
    protected void onPause(){
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();

        ed.putFloat("starRating" + id, Rating);
        ed.apply();

    }




}
