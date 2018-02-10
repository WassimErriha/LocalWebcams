package com.example.wassim.localwebcams;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wassim.localwebcams.Objects.Webcam;
import com.example.wassim.localwebcams.data.WebcamContract;
import com.squareup.picasso.Picasso;

public class WebcamDetailsActivity extends AppCompatActivity {

    long currentWebcamId;
    private String stringCurrentWebcamId;
    private Webcam webcam;

    public static void loadImage(Context context, String thumbnail, ImageView imageView) {
        Picasso.with(context).load(thumbnail).fit().centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(imageView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webcam_details);

        ImageView mainImageView = findViewById(R.id.webcam_main_image);

        if (getIntent() != null && getIntent().hasExtra("webcam")) {
            webcam = getIntent().getExtras().getParcelable("webcam");
            stringCurrentWebcamId = webcam.getId();
            currentWebcamId = Long.parseLong(stringCurrentWebcamId);
            String thumbnail = webcam.getImage().getDaylight().getThumbnail();
            loadImage(this, thumbnail, mainImageView);
            if (webcam.getPlayer().getLive().getAvailable()) {
                String liveWebcamLink = webcam.getPlayer().getLive().getEmbed();
            }
            String dayTimeLapsLink = webcam.getPlayer().getLifetime().getEmbed();
            String monthTimeLapseLink = webcam.getPlayer().getMonth().getEmbed();
            String yearTimeLapseLink = webcam.getPlayer().getYear().getEmbed();


//            Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setData(Uri.parse(dayTimeLapsLink));
//            startActivity(i);
        }
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

    private void saveWebcam() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(WebcamContract.WebcamEntry.COLUMN_WEBCAM_ID, currentWebcamId);
        Uri uriOfWebcamInserted = getContentResolver().insert(WebcamContract.WebcamEntry.CONTENT_URI, contentValues);
        Toast.makeText(WebcamDetailsActivity.this
                , "inserted " + uriOfWebcamInserted, Toast.LENGTH_LONG).show();
    }

    private void deleteWebcam() {
        Uri singleWebcamUri = ContentUris.withAppendedId(WebcamContract.WebcamEntry.CONTENT_URI, currentWebcamId);
        int numberOfRowsDeleted = getContentResolver().delete(
                singleWebcamUri, null, null);
        Toast.makeText(WebcamDetailsActivity.this, "Deleted " + numberOfRowsDeleted, Toast.LENGTH_LONG).show();
    }
}
