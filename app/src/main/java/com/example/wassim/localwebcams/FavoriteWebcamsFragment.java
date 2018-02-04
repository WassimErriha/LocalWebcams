package com.example.wassim.localwebcams;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wassim.localwebcams.Objects.Webcam;

public class FavoriteWebcamsFragment extends Fragment implements FavoriteWebcamsRecyclerViewAdapter.onListItemClickListener {

    FavoriteWebcamsRecyclerViewAdapter adapter;

    public FavoriteWebcamsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new FavoriteWebcamsRecyclerViewAdapter(getActivity(), null, this);
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
        fetchWebcams.execute("https://webcamstravel.p.mashape.com/webcams/list/webcam=1010244116%2C1010908662%2C1040549429?lang=en&show=webcams%3Aimage%2Clocation%2Cplayer%2Clive");

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
