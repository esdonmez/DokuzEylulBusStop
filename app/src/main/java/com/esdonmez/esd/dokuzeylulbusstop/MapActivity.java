package com.esdonmez.esd.dokuzeylulbusstop;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewDebug;
import android.widget.Toast;

import com.esdonmez.esd.dokuzeylulbusstop.Helpers.DatabaseHandler;
import com.esdonmez.esd.dokuzeylulbusstop.Models.StopModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity {

    private MapView mapView;
    private GoogleMap googleMap;
    private Marker positionMarker;
    public static List<StopModel> stopList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        DatabaseHandler db = new DatabaseHandler(this);

        if(db.getAllStops().size() == 0) {
            db.addStop(new StopModel("Giriş Durağı", "15.958", "13.222", 4, 2));
            db.addStop(new StopModel("Çıkış Durağı", "22.958", "45.222", 1, 5));
            db.addStop(new StopModel("Orta Durağı", "38.958", "74.222", 3, 47));
        }

        stopList = db.getAllStops();

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if(status == ConnectionResult.SUCCESS) {
            try {
                MapsInitializer.initialize(getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }

            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap mMap) {
                    googleMap = mMap;

                    for(int i = 0; i < stopList.size(); i++)
                    {
                        StopModel model = stopList.get(i);

                        MarkerOptions positionMarkerOptions = new MarkerOptions()
                                .position(new LatLng(Double.parseDouble(model.getLatitude()), Double.parseDouble(model.getLongitude())))
                                .title(model.getName())
                                .anchor(0.5f, 0.5f);
                        positionMarker = googleMap.addMarker(positionMarkerOptions);
                        positionMarker.setTag(model.getId());
                    }

                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(stopList.get(0).getLatitude()),Double.parseDouble(stopList.get(0).getLongitude()))));
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f), 5000, null);

                    googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(final Marker marker) {

                            AlertDialog alertDialog = new AlertDialog.Builder(MapActivity.this).create();
                            alertDialog.setTitle(marker.getTitle());
                            alertDialog.setMessage("Bu durağı oylamak ister misiniz?");
                            alertDialog.setIcon(R.mipmap.ic_launcher);
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ankete Git",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(MapActivity.this, MainActivity.class);
                                            intent.putExtra("name", marker.getTitle());
                                            intent.putExtra("id", marker.getTag().toString());
                                            startActivity(intent);
                                        }
                                    });
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Kapat",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();

                            return false;
                        }
                    });
                }
            });

        }else{
            GooglePlayServicesUtil.getErrorDialog(status, getParent(), status);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
