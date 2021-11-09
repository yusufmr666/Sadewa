package com.example.cobalagi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cobalagi.model.User;
import com.example.cobalagi.model.UserInformation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class CobaActivity  extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private Double startLat = -7.324625;
    private Double startlng = 110.504875;
    DatabaseReference mUsers;
    MarkerOptions markerOptions = new MarkerOptions();
    Marker marker;
    double end_lat, end_lng;
    String title;
    GPSTracker gpsHandler;
    Spinner spinPlaces;
    List<String> vPlaces;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coba);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ChildEventListener mChildEventListener;
        mUsers= FirebaseDatabase.getInstance().getReference("wisata");
        mUsers.push().setValue(marker);
        gpsHandler = new GPSTracker(this);
        spinPlaces = findViewById(R.id.spin_places);

        Button btn_action = findViewById(R.id.B_find_route);
        btn_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedPlace = spinPlaces.getSelectedItem().toString();
                Bundle bundle = new Bundle();
                bundle.putString("target_place", selectedPlace);
                bundle.putDouble("from_lat", end_lat);
                bundle.putDouble("from_lng", end_lat);

                Intent findIntent = new Intent(CobaActivity.this, RouteActivity.class);
                findIntent.putExtras(bundle);
                startActivity(findIntent);
            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     *
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.clear();


        vPlaces = new ArrayList<>();

        // set marker current location
        LatLng currentDinat = new LatLng(startLat, startlng);
        MarkerOptions currentMarker = new MarkerOptions();
        currentMarker.position(currentDinat);
        currentMarker.title("Lokasi Anda");
        currentMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mMap.addMarker(currentMarker);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentDinat, 12));

        mUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    UserInformation user = s.getValue(UserInformation.class);


                    end_lat = Double.parseDouble(user.lat);
                    end_lng = Double.parseDouble(user.lng);
                    title = String.valueOf(user.name);


                    addMarker(end_lat, end_lng, title);

                    ArrayAdapter<String> adapterPlaces;
                    adapterPlaces = new ArrayAdapter<>(getApplicationContext(),
                            R.layout.my_spinner_item, R.id.tv_promo_txt, vPlaces);
                    adapterPlaces.setDropDownViewResource(R.layout.my_spinner_item);
                    spinPlaces.setAdapter(adapterPlaces);

                }
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addMarker(double end_lat, double end_lng, String title) {
        float results[] = new float[10];

        vPlaces.add(title);

        LatLng add_position = new LatLng(end_lat, end_lng);
        markerOptions.position(add_position);
        markerOptions.title(title);
        mMap.addMarker(markerOptions);


        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Toast.makeText(CobaActivity.this, marker.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
