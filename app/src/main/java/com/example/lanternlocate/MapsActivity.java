package com.example.lanternlocate;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private static final String TOAST_TEXT = "Activate Location.";

    private Location currentLocation;
    private double currentLat;
    private double currentLng;
    private Marker currentMarker;
    private boolean requestingLocationUpdates= true;
    LocationCallback locationCallback;
    LocationRequest locationRequest;
    MarkerOptions marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        marker = new MarkerOptions().position(new LatLng(0,0))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));

               //        mMap.addMarker(marker);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {

                    // Logic to handle location object
                    currentLat = locationResult.getLastLocation().getLatitude();
                    currentLng = locationResult.getLastLocation().getLongitude();
                    currentLocation = locationResult.getLastLocation();

                    LatLng brynMawr = new LatLng( currentLat, currentLng );

                    //mMap.addMarker(new MarkerOptions().position(brynMawr).title("Marker in Bryn Mawr"));
                    if (currentMarker == null) {
                        currentMarker = mMap.addMarker(marker);
                    } else {
                        currentMarker.setPosition(brynMawr);
                    }

                    float zoomLevel = 16.0f;
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(brynMawr, zoomLevel));

//                    Log.i("FARYAL1", Double.toString(currentLat));
//                    Log.i("FARYAL1", Double.toString(currentLng));

//                    Log.i("Location", "Updated to: " + currentLocation.toString());
                }

                if (locationResult == null) {
                    return;
                }

                for (Location location : locationResult.getLocations()) {

                    // Update UI with location data
                    marker.position(new LatLng(location.getLatitude(), location.getLongitude()));

                }
            };
        };

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //mMap.addMarker(marker);

        fusedLocationClient.getLastLocation().addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Context context = getApplicationContext();
                CharSequence text = "Activate Location Services.";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

        //LatLng brynMawr = new LatLng( 40.0289998, -75.3126857 );

//        Log.i("FARYAL2", Double.toString(currentLat));
//        Log.i("FARYAL2", Double.toString(currentLng));

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (requestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    private void startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback,
                Looper.getMainLooper());
    }




}
