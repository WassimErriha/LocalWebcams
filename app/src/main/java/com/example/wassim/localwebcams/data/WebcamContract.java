package com.example.wassim.localwebcams.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class WebcamContract {
    public static final String CONTENT_AUTHORITY = "com.example.wassim.localwebcams";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://com.example.wassim.localwebcams");
    public static final String PATH_FAVORITES = "webcams";

    public static final class WebcamEntry implements BaseColumns {
        public static final Uri CONTENT_URI = WebcamContract.BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FAVORITES)
                .build();
        public static final String TABLE_NAME = "webcams";
        public static final String _ID = "_id";
        public static final String COLUMN_WEBCAM_ID = "webcam_id";
    }
}
