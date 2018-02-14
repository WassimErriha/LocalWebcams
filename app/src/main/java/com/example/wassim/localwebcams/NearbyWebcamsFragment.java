package com.example.wassim.localwebcams;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.wassim.localwebcams.Objects.Webcam;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;

public class NearbyWebcamsFragment extends Fragment implements NearbyWebcamsRecyclerViewAdapter.onListItemClickListener {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 111;
    private static final double DEFAULT_LOCATION_LATITUDE = 37;
    private static final double DEFAULT_LOCATION_LONGITUDE = -122;
    private static final String IMAGE_TRANSITION_NAME = "image_transition_name";
    NearbyWebcamsRecyclerViewAdapter adapter;
    FusedLocationProviderClient client;
    private Location mLastKnownLocation;
    private boolean mLocationPermissionGranted;
    private double locationLat;
    private double locationLong;
    private String nearbyWebcamsUrl;
    private ProgressBar progressBar;
    private TextView emptyWebcamArray;


    public NearbyWebcamsFragment() {
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

            // TODO handel the case when user denies location permission
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
                            //if (task != null) {
                            if (task.isComplete()) {
                                mLastKnownLocation = task.getResult();
                                locationLat = mLastKnownLocation.getLatitude();
                                locationLong = mLastKnownLocation.getLongitude();
                                Log.e("TAG", "Current location latitude is " + locationLat + "\n location longitude is " + locationLong);
                            }
                        } else {
                            Log.d("TAG", "Current location is null. Using defaults.");
                            Log.e("TAG", "Exception: %s", task.getException());
                            //TODO set default location
                            locationLat = DEFAULT_LOCATION_LATITUDE;
                            locationLong = DEFAULT_LOCATION_LONGITUDE;
                        }
                        nearbyWebcamsUrl = RemoteDataURIBuilder.buildURLWithLatLong(Double.toString(locationLat), Double.toString(locationLong));
                        @SuppressLint("StaticFieldLeak")
                        FetchWebcams fetchWebcams = new FetchWebcams(getContext()) {
                            @Override
                            protected void onPostExecute(String response) {
                                adapter.swapData(response);
                                progressBar.setVisibility(View.INVISIBLE);

                                if (adapter.getItemCount() == 0) {
                                    emptyWebcamArray.setVisibility(View.VISIBLE);
                                } else {
                                    emptyWebcamArray.setVisibility(View.INVISIBLE);
                                }
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
        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(false);

        progressBar = view.findViewById(R.id.progressBar2);
        emptyWebcamArray = view.findViewById(R.id.empty_webcams_array);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getLocationPermission();
            getLocationLatLong();
        }
    }

    @Override
    public String onListItemClick(Webcam webcam, ImageView sharedImageView) {
        Intent intent = new Intent(getActivity(), WebcamDetailsActivity.class);
        intent.putExtra("webcam", webcam);
        intent.putExtra(IMAGE_TRANSITION_NAME, getString(R.string.shared_transition_name));
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this.getActivity(), sharedImageView, ViewCompat.getTransitionName(sharedImageView));
        startActivity(intent, options.toBundle());
        return null;
    }
}
