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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wassim.localwebcams.Objects.Webcam;

public class LiveWebcamsFragment extends Fragment implements LiveWebcamsRecyclerViewAdapter.onListItemClickListener {

    LiveWebcamsRecyclerViewAdapter adapter;
    private ProgressBar progressBar;
    private TextView emptyWebcamsArray;

    public LiveWebcamsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new LiveWebcamsRecyclerViewAdapter(getActivity(), null, this);
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
        emptyWebcamsArray = view.findViewById(R.id.empty_webcams_array);
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

                if (adapter.getItemCount() == 0) {
                    emptyWebcamsArray.setVisibility(View.VISIBLE);
                } else {
                    emptyWebcamsArray.setVisibility(View.INVISIBLE);
                }
            }
        };
        fetchWebcams.execute(RemoteDataURIBuilder.STATIC_LIVE_WEBCAMS_URL);
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
