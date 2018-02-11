package com.example.wassim.localwebcams;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.wassim.localwebcams.Objects.Webcam;


public class DiscoverWebcamsFragment extends Fragment implements DiscoverWebcamsRecyclerViewAdapter.onListItemClickListener {

    private static final double DEFAULT_LOCATION_LATITUDE = 37;
    private static final double DEFAULT_LOCATION_LONGITUDE = -122;
    public DiscoverWebcamsRecyclerViewAdapter adapter;
    private ProgressBar progressBar;
    private TextView emptyWebcamsArray;

    public DiscoverWebcamsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                showDialog();
            }
        });
        // Set the adapter
        RecyclerView recyclerView = view.findViewById(R.id.list_with_map);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(false);

        progressBar = view.findViewById(R.id.progressBar1);
        emptyWebcamsArray = view.findViewById(R.id.empty_webcams_array1);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String discoverWebcamsUrl = RemoteDataURIBuilder.buildURLWithLatLong(Double.toString(DEFAULT_LOCATION_LATITUDE), Double.toString(DEFAULT_LOCATION_LONGITUDE));
        fetchWebcams(discoverWebcamsUrl);
    }

    void showDialog() {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
        if (prev != null)
            fragmentTransaction.remove(prev);
        // Create and show the dialog.
        MapDialogFragment newFragment = new MapDialogFragment();
        //set target fragment to receive Maps point latitude and longitude from dialogue fragment
        newFragment.setTargetFragment(this, 1);
        newFragment.show(fragmentTransaction, "maps_dialogue_fragment");
    }

    @Override
    public String onListItemClick(Webcam webcam) {
        Intent intent = new Intent(getActivity(), WebcamDetailsActivity.class);
        intent.putExtra("webcam", webcam);
        getActivity().startActivity(intent);
        return null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1 && data.hasExtra("lat_long_url")) {
                String latLongUrl = data.getStringExtra("lat_long_url");
            fetchWebcams(latLongUrl);
        }
    }

    private void fetchWebcams(String latLongUrl) {
        @SuppressLint("StaticFieldLeak")
        FetchWebcams fetchWebcams = new FetchWebcams() {
            @Override
            protected void onPostExecute(String response) {
                adapter.swapData(response);
                progressBar.setVisibility(View.INVISIBLE);
                if (adapter.getItemCount() == 0) emptyWebcamsArray.setVisibility(View.VISIBLE);
                else emptyWebcamsArray.setVisibility(View.INVISIBLE);
            }
        };
        fetchWebcams.execute(latLongUrl);
    }
}
