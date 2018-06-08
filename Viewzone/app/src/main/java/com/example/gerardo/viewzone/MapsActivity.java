package com.example.gerardo.viewzone;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gerardo.viewzone.objetos.FirebaseReferences;
import com.example.gerardo.viewzone.objetos.Zonas;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    MapView gMapView;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    FirebaseDatabase database;
    DatabaseReference DBreference;

    List<Zonas> ZonasP;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_maps, container, false);

        gMapView = (MapView) view.findViewById(R.id.soleViewMap);
        gMapView.getMapAsync(this);

        return view;
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

    String idc = "";
    Marker mPerth;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
            //addListenerForSingleValueEvent
        idc = "";
        DBreference.child(FirebaseReferences.Zonas_REFERENCE).addListenerForSingleValueEvent(new ValueEventListener() {
            int a = 0;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot :
                        dataSnapshot.getChildren()) {

                    Zonas test = snapshot.getValue(Zonas.class);
                    test.setColonia(snapshot.getKey());
                    ZonasP.add(test);
                }

                for (int i = 0; i < ZonasP.size(); i++) {
                    Zonas zona = ZonasP.get(i);

                    String nombre = zona.getColonia();
                    Double rating = zona.getRating();
                    Double radio = zona.getRadio();
                    String[] Coordenadas = zona.getCoordenadas().split(",");
                    Double lat = Double.parseDouble(Coordenadas[0]);
                    Double lon = Double.parseDouble(Coordenadas[1]);


                    CircleOptions circleOptions = new CircleOptions()

                            .center(new LatLng(lat, lon))
                            .radius(radio)
                            .strokeWidth(10)
                            .strokeColor(Color.argb(255, 36, 153, 237))
                            .fillColor(Color.argb(100, 36, 153, 237))
                            .clickable(true)
                            .zIndex(i);


                    // Get back the mutable Circle
                    Circle c = mMap.addCircle(circleOptions);


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());

            }


        });
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(24.14125, -110.3066797);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(sydney)      // Sets the center of the map to Mountain View
                .zoom(15)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {


            @Override
            public void onCircleClick(Circle circle) {
                // Flip the r, g and b components of the circle's
                // stroke color.


                mMap.moveCamera(CameraUpdateFactory.newLatLng(circle.getCenter()));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(circle.getCenter())      // Sets the center of the map to Mountain View
                        .zoom(15)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                Zonas a = ZonasP.get((int) circle.getZIndex());



                if(mPerth != null  ){
                    mPerth.setPosition(circle.getCenter());
                    mPerth.setTitle(a.getColonia());
                    mPerth.setSnippet("Raiting : "+ a.getRating().toString());
                }
                else{
                    mPerth = mMap.addMarker(new MarkerOptions()
                            .position(circle.getCenter())
                            .title(a.getColonia())
                            .snippet("Raiting : "+ a.getRating().toString())
                            .draggable(true)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.markericon1)));

                }

            }

        });
    }


}




