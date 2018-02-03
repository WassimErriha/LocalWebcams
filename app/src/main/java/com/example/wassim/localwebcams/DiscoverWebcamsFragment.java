package com.example.wassim.localwebcams;

import android.annotation.SuppressLint;
import android.content.Context;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class DiscoverWebcamsFragment extends Fragment implements OnMapReadyCallback {

    MyItemRecyclerViewAdapter3 adapter;
    boolean isMapContainerVisible = true;
    private LiveWebcamsFragment.OnListFragmentInteractionListener mListener;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private CameraPosition position;

    public DiscoverWebcamsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        adapter = new MyItemRecyclerViewAdapter3(getActivity(), null, (FavoriteWebcamsFragment.OnListFragmentInteractionListener) mListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_item_list_with_map, container, false);
        final Context context = view.getContext();
        ImageView imageView = (ImageView) view.findViewById(R.id.get_location_view);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.map_container);
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

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_with_map);
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
        fetchWebcams.execute("https://webcamstravel.p.mashape.com/webcams/list/nearby=37.7704433522854,-122.43026733398438,1000?lang=en&show=webcams%3Aimage%2Clocation%2Cplayer%2Clive'");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LiveWebcamsFragment.OnListFragmentInteractionListener) {
            mListener = (LiveWebcamsFragment.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
                fetchWebcams.execute("https://webcamstravel.p.mashape.com/webcams/list/nearby=" + point.latitude + "," + point.longitude + ",1000?lang=en&show=webcams%3Aimage%2Clocation%2Cplayer%2Clive'");
            }
        });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(String item);
    }
}
