package com.example.wassim.localwebcams;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wassim.localwebcams.Objects.Location;
import com.example.wassim.localwebcams.Objects.Response;
import com.example.wassim.localwebcams.Objects.Webcam;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavoriteWebcamsRecyclerViewAdapter extends RecyclerView.Adapter<FavoriteWebcamsRecyclerViewAdapter.ViewHolder> {

    private static List<Webcam> webcamList = null;
    private Context mContext;
    private onListItemClickListener mItemClickListener;

    public FavoriteWebcamsRecyclerViewAdapter(Context context, List<Webcam> items, onListItemClickListener itemClickListener) {
        mContext = context;
        webcamList = items;
        mItemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Webcam webcam = webcamList.get(position);
        String title = webcam.getTitle();
        holder.mTitleTextView.setText(title);
        String imageLink = webcam.getImage().getDaylight().getThumbnail();
        Picasso.with(mContext).load(imageLink).fit().centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.mImageView);
        Location location = webcam.getLocation();
        if (location != null) {
            String city = location.getCity();
            String region = location.getRegion();
            String country = location.getCountry();
            String continent = location.getContinent();
            String fullLocation = city + "," + region + "," + country + "," + continent;
            holder.mLocationTextView.setText(fullLocation);
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onListItemClick(webcam);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (webcamList == null) return 0;
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
            Response result = gson.fromJson(response, new TypeToken<Response>() {
            }.getType());
            webcamList = new ArrayList<>();
            ArrayList<Webcam> webcams = (ArrayList<Webcam>) result.getResult().getWebcams();
            webcamList.addAll(webcams);
        }
        notifyDataSetChanged();
    }

    public interface onListItemClickListener {
        String onListItemClick(Webcam webcam);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private final ImageView mImageView;
        private final TextView mTitleTextView;
        private final TextView mLocationTextView;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = view.findViewById(R.id.item_imageView);
            mTitleTextView = view.findViewById(R.id.title_tv);
            mLocationTextView = view.findViewById(R.id.location_tv);

        }
    }
}
