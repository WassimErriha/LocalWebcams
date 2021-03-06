package com.example.wassim.localwebcams;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapDialogFragment extends DialogFragment implements OnMapReadyCallback {

    public static final String LOCATION_LAT = "location_lat";
    public static final String LOCATION_LONG = "location_long";
    public static final String DEFAULT_LOCATION_TILE = "California";
    public static final String EXTRA_LAT_LONG_URL = "lat_long_url";
    private static final double DEFAULT_LOCATION_LATITUDE = 39;
    private static final double DEFAULT_LOCATION_LONGITUDE = -122;
    private double locationLong;
    private double locationLat;

    public MapDialogFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // used to locate a webcam
        if (getArguments() != null) {
            // locate webcam
            locationLat = getArguments().getDouble(LOCATION_LAT);
            locationLong = getArguments().getDouble(LOCATION_LONG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.google_maps_dialug_fragment, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        final GoogleMap mMap = googleMap;
        // mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        LatLng californiaArea = new LatLng(DEFAULT_LOCATION_LATITUDE, DEFAULT_LOCATION_LONGITUDE);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(californiaArea));
        mMap.addMarker(new MarkerOptions()
                .position(californiaArea)
                .title(DEFAULT_LOCATION_TILE));

        // if fragment is used to locate a webcam
        locateWebcam(mMap);

        // Don't set ClickListener on the map when this fragment is used to locate a webcam
        if (getArguments() == null) {
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng point) {
                    LatLng pickedLocation = new LatLng(point.latitude, point.longitude);
                    mMap.addMarker(new MarkerOptions().position(pickedLocation));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(pickedLocation));
                    Toast.makeText(getContext(), "Loading location webcams ...", Toast.LENGTH_SHORT).show();
                    String mapsWebcamUrl = RemoteDataURIBuilder.buildURLWithLatLong(Double.toString(point.latitude), Double.toString(point.longitude));
                    sendResult(1, mapsWebcamUrl);
                }
            });
        }
    }

    private void sendResult(int REQUEST_CODE, String latLongUrl) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_LAT_LONG_URL, latLongUrl);
        getTargetFragment().onActivityResult(getTargetRequestCode(), REQUEST_CODE, intent);
    }

    private void locateWebcam(GoogleMap mMap) {
        if (getArguments() != null) {
            LatLng newLocation = new LatLng(locationLat, locationLong);
            CameraPosition camPos = new CameraPosition.Builder()
                    .target(newLocation)
                    .zoom(12.8f)
                    .build();
            CameraUpdate camUpdate = CameraUpdateFactory.newCameraPosition(camPos);
            mMap.animateCamera(camUpdate);
            mMap.addMarker(new MarkerOptions()
                    .position(newLocation));
        }
    }
}