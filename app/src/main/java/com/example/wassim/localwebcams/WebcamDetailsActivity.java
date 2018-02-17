package com.example.wassim.localwebcams;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wassim.localwebcams.Objects.Webcam;
import com.example.wassim.localwebcams.data.WebcamContract;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class WebcamDetailsActivity extends AppCompatActivity {

    public static final String WEBCAM_EXTRA = "webcam";
    public static final String LOCATION_LAT = "location_lat";
    public static final String LOCATION_LONG = "location_long";
    private static final String IMAGE_TRANSITION_NAME = "image_transition_name";
    private long currentWebcamId;
    private String stringCurrentWebcamId;
    private Webcam webcam;

    public void loadImage(Context context, String thumbnail, ImageView imageView) {
        Picasso.with(context).load(thumbnail).fit().centerCrop()
                .noFade()
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.error_image)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        supportStartPostponedEnterTransition();
                    }

                    @Override
                    public void onError() {
                        supportStartPostponedEnterTransition();
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webcam_details);

        TextView webcamTitleTV = findViewById(R.id.webcam_title_tv);
        ImageView mainImageView = findViewById(R.id.webcam_main_image);

        if (getIntent() != null && getIntent().hasExtra(WEBCAM_EXTRA)) {
            webcam = getIntent().getExtras().getParcelable(WEBCAM_EXTRA);
            stringCurrentWebcamId = webcam.getId();
            currentWebcamId = Long.parseLong(stringCurrentWebcamId);
            String thumbnail = webcam.getImage().getDaylight().getThumbnail();
            loadImage(this, thumbnail, mainImageView);
            webcamTitleTV.setText(webcam.getTitle());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                String imageTransitionName = getIntent().getExtras().getString(IMAGE_TRANSITION_NAME);
                mainImageView.setTransitionName(imageTransitionName);
            }

        }
    }

    public void viewLink(View view) {
        String webcamLink = "";
        switch (view.getId()) {
            case R.id.live_textview:
                webcamLink = webcam.getPlayer().getLive().getEmbed();
                break;
            case R.id.day_timelapse_textview:
                webcamLink = webcam.getPlayer().getDay().getEmbed();
                break;
            case R.id.month_timelapse_textview:
                webcamLink = webcam.getPlayer().getMonth().getEmbed();
                break;
            case R.id.year_timelapse_textview:
                webcamLink = webcam.getPlayer().getYear().getEmbed();
                break;
        }
        if (webcamLink != null && !TextUtils.isEmpty(webcamLink)) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(webcamLink));
            startActivity(i);
        } else {
            Toast.makeText(this, R.string.empty_link, Toast.LENGTH_LONG).show();
        }
    }

    private void saveWebcam() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(WebcamContract.WebcamEntry.COLUMN_WEBCAM_ID, currentWebcamId);
        Uri uriOfWebcamInserted = getContentResolver().insert(WebcamContract.WebcamEntry.CONTENT_URI, contentValues);
        Log.e("WebcamDetailsActivity", "webcam inserted URI " + uriOfWebcamInserted);
        if (uriOfWebcamInserted != null)
            Toast.makeText(WebcamDetailsActivity.this, "Webcam inserted ", Toast.LENGTH_LONG).show();
    }

    private void deleteWebcam() {
        Uri singleWebcamUri = ContentUris.withAppendedId(WebcamContract.WebcamEntry.CONTENT_URI, currentWebcamId);
        int numberOfRowsDeleted = getContentResolver().delete(singleWebcamUri, null, null);
        if (numberOfRowsDeleted == 1)
            Toast.makeText(WebcamDetailsActivity.this, "Webcam deleted ", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.delete_webcam:
                deleteWebcam();
                return true;
            case R.id.save_webcam:
                saveWebcam();
                return true;
            case R.id.locate_webcam:
                locateWebcam();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void locateWebcam() {
        if (webcam != null) {
            double locationLat = webcam.getLocation().getLatitude();
            double locationLong = webcam.getLocation().getLongitude();
            showDialog(locationLat, locationLong);

        }
    }

    private void showDialog(double locationLat, double locationLong) {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentById(R.id.map);
        if (prev != null)
            fragmentTransaction.remove(prev);
        // Create and show the dialog.
        MapDialogFragment newFragment = new MapDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putDouble(LOCATION_LAT, locationLat);
        bundle.putDouble(LOCATION_LONG, locationLong);
        newFragment.setArguments(bundle);
        newFragment.show(fragmentTransaction, "maps_dialogue_fragment");
    }
}
