package com.example.lanternlocate;

import android.location.Location;
import android.os.Bundle;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;

import java.util.LinkedHashMap;

public class DriverActivity extends AppCompatActivity {

    //FusedLocationClient - object used to receive location updates
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private static final String PUBNUB_CHANNEL_NAME = "driverLocation"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set location request to update every 5 seconds if the displacement is more than 10 meters
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setSmallestDisplacement(10);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //Pass the LocationRequest as a parameter to receive location updates
        mFusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult){
                Location location = locationResult.getLastLocation();

                //LinkedHashMap to fit the format of PubNub's location message
                LinkedHashMap<String, String> locationMessage = new LinkedHashMap<>();

                locationMessage.put("lat", String.valueOf(location.getLatitude()));
                locationMessage.put("lng", String.valueOf(location.getLongitude()));

                MainActivity.pubnub.publish()
                        .message(locationMessage)
                        .channel(PUBNUB_CHANNEL_NAME)
                        .sync(new PNCallback<PNPublishResult>() {

                            @Override
                            public void onResponse(PNPublishResult result, PNStatus status) {
                                // handle publish result, status always present, result if successful
                                // status.isError() - to check for errors

                                if (!status.isError()) {
                                    System.out.println("pub timetoken: " + result.getTimetoken());
                                }
                                System.out.println("pub status code: " + status.getStatusCode());
                            }

                        });
            }
        }, Looper.myLooper());


    }
}
