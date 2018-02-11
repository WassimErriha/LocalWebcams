package com.example.wassim.localwebcams;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapDialogFragment extends DialogFragment implements OnMapReadyCallback {

    GoogleMap mMap;
    SupportMapFragment mapFragment;
    private CameraPosition position;
    private DiscoverWebcamsRecyclerViewAdapter adapter;

    public MapDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new DiscoverWebcamsRecyclerViewAdapter(getActivity(), null, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.google_maps_dialug_fragment, container, false);
        mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera

        //        new LatLng(37.7688472,-122.4130859);
        LatLng sydney = new LatLng(-34, 151);
        final Marker marker = mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        position = mMap.getCameraPosition();
        Log.e(" Tag1", " " + position.toString());
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                if (marker != null) {
                    marker.remove();
                }
                LatLng pickedLocation = new LatLng(point.latitude, point.longitude);
                mMap.addMarker(new MarkerOptions().position(pickedLocation));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(pickedLocation));
                position = mMap.getCameraPosition();
                Log.e(" Tag2", " " + position.toString());
                Toast.makeText(getContext(), point.toString(), Toast.LENGTH_SHORT).show();

                String mapsWebcamUrl = RemoteDataURIBuilder.buildURLWithLatLong(Double.toString(point.latitude), Double.toString(point.longitude));
                sendResult(1, mapsWebcamUrl);
            }
        });
    }

    private void sendResult(int REQUEST_CODE, String latLongUrl) {
        Intent intent = new Intent();
        intent.putExtra("lat_long_url", latLongUrl);
        getTargetFragment().onActivityResult(getTargetRequestCode(), REQUEST_CODE, intent);
    }
}