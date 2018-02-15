package com.example.wassim.localwebcams;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

    private static final String IMAGE_TRANSITION_NAME = "image_transition_name";
    long currentWebcamId;
    private String stringCurrentWebcamId;
    private Webcam webcam;

    public void loadImage(Context context, String thumbnail, ImageView imageView) {


        Picasso.with(context).load(thumbnail).fit().centerCrop()
                .noFade()
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

        if (getIntent() != null && getIntent().hasExtra("webcam")) {
            webcam = getIntent().getExtras().getParcelable("webcam");
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
            Toast.makeText(this, "No link found for this selection", Toast.LENGTH_LONG).show();
        }
    }

    private void saveWebcam() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(WebcamContract.WebcamEntry.COLUMN_WEBCAM_ID, currentWebcamId);
        Uri uriOfWebcamInserted = getContentResolver().insert(WebcamContract.WebcamEntry.CONTENT_URI, contentValues);
        Toast.makeText(WebcamDetailsActivity.this, "inserted " + uriOfWebcamInserted, Toast.LENGTH_LONG).show();
    }

    private void deleteWebcam() {
        Uri singleWebcamUri = ContentUris.withAppendedId(WebcamContract.WebcamEntry.CONTENT_URI, currentWebcamId);
        int numberOfRowsDeleted = getContentResolver().delete(singleWebcamUri, null, null);
        Toast.makeText(WebcamDetailsActivity.this, "Deleted " + numberOfRowsDeleted, Toast.LENGTH_LONG).show();
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
