package com.example.cobalagi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;

public class RouteActivity extends AppCompatActivity {


    private String lastLat;
    private String lastLng;
    String targetPlace;
    TextView text_place, text_lat, text_lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);


        Bundle bundle = getIntent().getExtras();




        targetPlace = bundle.getString("target_place");
        text_place = findViewById(R.id.txt_targetplace);
        text_place.setText(targetPlace);

    }
}