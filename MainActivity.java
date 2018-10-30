package com.example.carlos.ratetherockstar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_MAIN = 1234;

    private final String RockStarsFinal[] = {
            "Dahvie",
            "Reita",
            "Skrillex",
            "Seike",
            "RozzWilliams",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for(int i = 0; i < RockStarsFinal.length; i++){
            AddRockstar(RockStarsFinal[i], i);
        }
        //AddRockstar();
    }
    public void AddRockstar(String rockstarName, int id) {
        View rockStar = getLayoutInflater()
                .inflate(R.layout.rockstar, /* parent */ null);
        TextView tv = (TextView) rockStar.findViewById(R.id.rName);
        tv.setText(rockstarName);
        ImageView img = (ImageView) rockStar.findViewById(R.id.rImageView);

        img.setOnClickListener((x) ->{
            Intent intent = new Intent(this, Details.class);
            intent.putExtra("rockStar", rockstarName);
            intent.putExtra("id", id );
            startActivityForResult(intent, REQ_CODE_MAIN);
        });
        // this code is to convert a string like "United States" into
        // an integer resource ID like R.drawable.unitedstates
        String rockstarName2 = rockstarName.replace(" ", "").toLowerCase();
        int rImageID = getResources().getIdentifier(
                rockstarName2, "drawable", getPackageName());
        img.setImageResource(rImageID);

        GridLayout grLayout = (GridLayout) findViewById(R.id.RockstarsGrid);
        grLayout.addView(rockStar);

    }
    //gets called when DetailsActivity finishes and comes back to me
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(resultCode, resultCode, intent);
        try{
            Float starRating = intent.getFloatExtra("starRating", (float) 0.0);
            Toast.makeText(this, "You rated this rockstar " + String.valueOf(starRating),
                    Toast.LENGTH_SHORT).show();
        }
        catch(Exception ex){

        }

    }

}
