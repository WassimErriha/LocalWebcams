package com.example.wassim.localwebcams;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wassim.localwebcams.FavoriteWebcamsFragment.OnListFragmentInteractionListener;
import com.example.wassim.localwebcams.Objects.Location;
import com.example.wassim.localwebcams.Objects.Response;
import com.example.wassim.localwebcams.Objects.Webcam;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyItemRecyclerViewAdapter3 extends RecyclerView.Adapter<MyItemRecyclerViewAdapter3.ViewHolder> {

    private static List<Webcam> webcamList = null;
    private final OnListFragmentInteractionListener mListener;
    private Context mContext;

    public MyItemRecyclerViewAdapter3(Context context, List<Webcam> items, OnListFragmentInteractionListener listener) {
        if (webcamList != null) {
            webcamList.clear();
            webcamList = null;
        }

        mContext = context;
        webcamList = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        int ithemcount = getItemCount();
        Webcam webcam = webcamList.get(position);
        String title = webcam.getTitle();
        holder.mTitleTextView.setText(title);
        String imageLink = webcam.getImage().getDaylight().getThumbnail();
        Picasso.with(mContext).load(imageLink).fit().centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.mImageView);
        //Statistics statistics = webcam.getStatistics();
        //int views = statistics.getViews();
        Location location = webcam.getLocation();
        if (location != null) {
            String city = location.getCity();
            String region = location.getRegion();
            String country = location.getCountry();
            String continent = location.getContinent();
            String fullLocation = city + "," + region + "," + country + "," + continent;
            holder.mLocationTextView.setText(fullLocation);
        }
        holder.test.setText("Test");
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (webcamList == null) {
            return 0;
        }
        return webcamList.size();
    }

    public void swapData(String response) {
        if (webcamList != null) {
            webcamList.clear();
            webcamList = null;
        }
        final GsonBuilder gsonBuilder = new GsonBuilder();
        final Gson gson = gsonBuilder.create();

        if (response != null) {
            String string = response;
            Response result = gson.fromJson(response, new TypeToken<Response>() {
            }.getType());
            webcamList = new ArrayList<>();
            ArrayList<Webcam> webcams = (ArrayList<Webcam>) result.getResult().getWebcams();
            webcamList.addAll(webcams);
        }
        notifyDataSetChanged();
        notifyItemRangeChanged(0, webcamList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImageView;
        public final TextView mTitleTextView;
        public final TextView mLocationTextView;
        public final TextView test;
        public String mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.item_imageView);
            mTitleTextView = (TextView) view.findViewById(R.id.title_tv);
            mLocationTextView = (TextView) view.findViewById(R.id.location_tv);
            test = (TextView) view.findViewById(R.id.test);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mLocationTextView.getText() + "'";
        }
    }
}
