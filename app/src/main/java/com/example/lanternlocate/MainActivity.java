package com.example.lanternlocate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;

public class MainActivity extends AppCompatActivity {

    Button driverButton;
    Button passengerButton;
    public static PubNub pubnub;
    private static final String PUBNUB_SUBSCRIBE_KEY = "sub-c-c71bc146-4ead-11ea-94fd-ea35a5fcc55f";
    private static final String PUBNUB_PUBLISH_KEY = "pub-c-205f476c-f387-44db-9036-72c18c02bd2a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        driverButton = (Button) findViewById(R.id.driverButton);
        passengerButton = (Button) findViewById(R.id.passengerButton);

        //
        driverButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DriverActivity.class));
            }
        });

        passengerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PassengerActivity.class));
            }
        });

        private void initPubnub() {
            PNConfiguration pnConfiguration = new PNConfiguration();
            pnConfiguration.setSubscribeKey(PUBNUB_SUBSCRIBE_KEY);
            pnConfiguration.setPublishKey(PUBNUB_PUBLISH_KEY);
            pnConfiguration.setSecure(true);
            pubnub = new PubNub(pnConfiguration);
        }


        public void checkPermission() {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            )
            {//Can add more as per requirement
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        123);
            }
        }
    }


//        public void onUser(View view) {
//            Intent intent = new Intent(this, UserActivity.class);
//            startActivity(intent);
//        }
//    public void onDriver(View view) {
//        Intent intent = new Intent(this, MapsActivity.class);
//        startActivity(intent);
//    }
    }

