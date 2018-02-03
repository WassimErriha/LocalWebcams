package com.example.wassim.localwebcams;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.wassim.localwebcams.Objects.Webcam;
import com.example.wassim.localwebcams.data.WebcamContract;

import java.util.ArrayList;

public class WebcamDetailsActivity extends AppCompatActivity {

    private String stringCurrenrtWebcamId;
    private Webcam webcam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webcam_details);

        webcam = getIntent().getExtras().getParcelable("webcam");
        stringCurrenrtWebcamId = webcam.getId();
        final long currentWebcamId = Long.parseLong(stringCurrenrtWebcamId);

        findViewById(R.id.insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(WebcamContract.WebcamEntry.COLUMN_WEBCAM_ID, currentWebcamId);
                Uri uriOfWebcamInserted = getContentResolver().insert(WebcamContract.WebcamEntry.CONTENT_URI, contentValues);
                Toast.makeText(WebcamDetailsActivity.this
                        , "inserted " + uriOfWebcamInserted, Toast.LENGTH_LONG).show();
            }
        });
        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri singelWebcamUri = ContentUris.withAppendedId(WebcamContract.WebcamEntry.CONTENT_URI, currentWebcamId);
                int numberOfRowsDeleted = getContentResolver().delete(
                        singelWebcamUri, null, null);
                Toast.makeText(WebcamDetailsActivity.this, "Deleted " + numberOfRowsDeleted, Toast.LENGTH_LONG).show();
            }
        });
        findViewById(R.id.query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Update favoriteMoviesArrayList after navigating back from MovieDetailsActivity
                ArrayList webcamIdsArray = new ArrayList<>();
                String[] favoriteMovieIdsProjection = {WebcamContract.WebcamEntry.COLUMN_WEBCAM_ID};
                Cursor fmCursor = getContentResolver().query(WebcamContract.WebcamEntry.CONTENT_URI
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
                Toast.makeText(WebcamDetailsActivity.this, "hello " + webcamIdsArray.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


}
