package com.example.wassim.localwebcams;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wassim.localwebcams.Objects.Webcam;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class DiscoverWebcamsFragment extends Fragment implements OnMapReadyCallback, DiscoverWebcamsRecyclerViewAdapter.onListItemClickListener {

    private static final double DEFAULT_LOCATION_LATITUDE = 37;
    private static final double DEFAULT_LOCATION_LONGITUDE = -122;
    DiscoverWebcamsRecyclerViewAdapter adapter;
    boolean isMapContainerVisible = true;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private CameraPosition position;

    public DiscoverWebcamsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        adapter = new DiscoverWebcamsRecyclerViewAdapter(getActivity(), null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_item_list_with_map, container, false);
        final Context context = view.getContext();
        ImageView imageView = view.findViewById(R.id.get_location_view);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameLayout frameLayout = view.findViewById(R.id.map_container);
                if (isMapContainerVisible) {
                    frameLayout.setVisibility(View.GONE);
                    isMapContainerVisible = false;
                    Toast.makeText(context, " Find location" + mapFragment, Toast.LENGTH_LONG).show();
                } else {
                    frameLayout.setVisibility(View.VISIBLE);
                    isMapContainerVisible = true;
                }
            }
        });
        // Set the adapter
        RecyclerView recyclerView = view.findViewById(R.id.list_with_map);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        @SuppressLint("StaticFieldLeak")
        FetchWebcams fetchWebcams = new FetchWebcams() {
            @Override
            protected void onPostExecute(String response) {
                adapter.swapData(response);
                adapter.notifyDataSetChanged();

            }
        };
        String discoverWebcamsUrl = RemoteDataURIBuilder.buildURLWithLatLong(Double.toString(DEFAULT_LOCATION_LATITUDE), Double.toString(DEFAULT_LOCATION_LONGITUDE));
        fetchWebcams.execute(discoverWebcamsUrl);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().add(R.id.map1, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
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
        // Add a marker in Sydney and move the camera
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

                @SuppressLint("StaticFieldLeak")
                FetchWebcams fetchWebcams = new FetchWebcams() {
                    @Override
                    protected void onPostExecute(String response) {
                        adapter.swapData(response);
                        adapter.notifyDataSetChanged();
                    }
                };
                String discoverWebcamsUrl = RemoteDataURIBuilder.buildURLWithLatLong(Double.toString(point.latitude), Double.toString(point.longitude));
                fetchWebcams.execute(discoverWebcamsUrl);
            }
        });
    }

    @Override
    public String onListItemClick(Webcam webcam) {
        Toast.makeText(getContext(), "hello " + webcam.getId(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getActivity(), WebcamDetailsActivity.class);
        intent.putExtra("webcam", webcam);
        getActivity().startActivity(intent);
        return null;
    }
}
