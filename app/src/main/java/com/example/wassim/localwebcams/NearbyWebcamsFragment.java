package com.example.wassim.localwebcams;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wassim.localwebcams.Objects.Webcam;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.net.MalformedURLException;
import java.net.URL;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;

public class NearbyWebcamsFragment extends Fragment implements NearbyWebcamsRecyclerViewAdapter.onListItemClickListener {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 111;
    NearbyWebcamsRecyclerViewAdapter adapter;
    FusedLocationProviderClient client;
    private Location mLastKnownLocation;
    private boolean mLocationPermissionGranted;
    private double locationLat;
    private double locationLong;
    private String nearbyWebcamsUrl;

    public NearbyWebcamsFragment() {
    }

    public static String buildURL(String locationLatitude1, String locationLongitude1) {
        if (locationLatitude1 == null || locationLatitude1 == null) {
            locationLatitude1 = "37.7704433522854";
            locationLongitude1 = "-122.43026733398438";
        }
        final String BASE_URL = "https://webcamstravel.p.mashape.com/webcams/list";
        final String LANGUAGE_QUERY_PARAM = "lang";
        final String LANGUAGE_QUERY_VALUE = "en";
        final String SHOW_QUERY_PARAM = "show";
        final String SHOW_QUERY_CONCATENATED_VALUE = "webcams:image,location,player,live";
//        final String NEARBY_QUERY_PARAM = "nearby";
//        String nearbyConcatenatedQueryValue = "webcams:image,location,player,live";
        final String LOCATION_RADIUS = "1000";
        String concatenatedPath = "nearby=" + locationLatitude1 + "," + locationLongitude1 + "," + LOCATION_RADIUS;
        Uri.Builder uri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(concatenatedPath)
                .appendQueryParameter(LANGUAGE_QUERY_PARAM, LANGUAGE_QUERY_VALUE)
                .appendQueryParameter(SHOW_QUERY_PARAM, SHOW_QUERY_CONCATENATED_VALUE);
        uri.build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.e("TAG", uri.toString());
        return url.toString();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = LocationServices.getFusedLocationProviderClient(getActivity());
        adapter = new NearbyWebcamsRecyclerViewAdapter(getActivity(), null, this);
    }

    /**
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    getLocationLatLong();
                }
            }
        }
    }


    /**
     * Prompts the user for permission to use the device location.
     */
    private void getLocationPermission() {
        if (checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public String[] getLocationLatLong() {
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = client.getLastLocation();
                locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            if (task != null) {
                                mLastKnownLocation = task.getResult();
                                locationLat = mLastKnownLocation.getLatitude();
                                locationLong = mLastKnownLocation.getLongitude();
                                Log.e("TAG", "Current location latitude is " + locationLat + "\n location longitude is " + locationLong);
                            }
                        } else {
                            Log.d("TAG", "Current location is null. Using defaults.");
                            Log.e("TAG", "Exception: %s", task.getException());
                            //TODO set default location
                            locationLat = 37.7704433522854;
                            locationLong = -122.43026733398438;
                        }
                        nearbyWebcamsUrl = buildURL(Double.toString(locationLat), Double.toString(locationLong));
                        @SuppressLint("StaticFieldLeak")
                        FetchWebcams fetchWebcams = new FetchWebcams() {
                            @Override
                            protected void onPostExecute(String response) {
                                adapter.swapData(response);
                                adapter.notifyDataSetChanged();
                            }
                        };
                        fetchWebcams.execute(nearbyWebcamsUrl);
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
        return new String[]{Double.toString(locationLat), Double.toString(locationLong)};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        // Set the adapter
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(false);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.e("TAG", client + "");
            getLocationPermission();
            getLocationLatLong();
        }
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
