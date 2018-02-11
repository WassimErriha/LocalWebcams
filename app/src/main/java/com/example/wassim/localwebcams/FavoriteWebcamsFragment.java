package com.example.wassim.localwebcams;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.wassim.localwebcams.Objects.Webcam;
import com.example.wassim.localwebcams.data.WebcamContract;

import java.util.ArrayList;

public class FavoriteWebcamsFragment extends Fragment
        implements FavoriteWebcamsRecyclerViewAdapter.onListItemClickListener {

    private FavoriteWebcamsRecyclerViewAdapter adapter;
    private ProgressBar progressBar;
    private TextView emptyWebcamsArray;

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
        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(false);

        progressBar = view.findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.INVISIBLE);
        emptyWebcamsArray = view.findViewById(R.id.empty_webcams_array);
        emptyWebcamsArray.setVisibility(View.VISIBLE);
        emptyWebcamsArray.setText("No webcams saved");
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
                progressBar.setVisibility(View.INVISIBLE);
            }
        };

        ArrayList webcamIds = getFavoriteWebcamIds();
        if (webcamIds.size() > 0) {
            fetchWebcams.execute(RemoteDataURIBuilder.buildURLWithFavoriteWebcams(webcamIds));
            emptyWebcamsArray.setVisibility(View.INVISIBLE);
        } else {
            emptyWebcamsArray.setVisibility(View.VISIBLE);
            //TODO why swap data here with a null value
            adapter.swapData(null);
        }
    }

    @Override
    public String onListItemClick(Webcam webcam) {
        Intent intent = new Intent(getActivity(), WebcamDetailsActivity.class);
        intent.putExtra("webcam", webcam);
        getActivity().startActivity(intent);
        return null;
    }

    private ArrayList getFavoriteWebcamIds() {
        ArrayList webcamIdsArray = new ArrayList<>();
        String[] favoriteMovieIdsProjection = {WebcamContract.WebcamEntry.COLUMN_WEBCAM_ID};
        Cursor fmCursor = getActivity().getContentResolver().query(WebcamContract.WebcamEntry.CONTENT_URI
                , favoriteMovieIdsProjection
                , null
                , null
                , null);
        try {
            while (fmCursor.moveToNext()) {
                String movieId = fmCursor.getString(fmCursor.getColumnIndex(
                        WebcamContract.WebcamEntry.COLUMN_WEBCAM_ID));
                webcamIdsArray.add(movieId);
            }
        } finally {
            fmCursor.close();
        }
        return webcamIdsArray;
    }
}
