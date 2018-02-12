package com.example.wassim.localwebcams;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RemoteDataURIBuilder {

    public static final String STATIC_LIVE_WEBCAMS_URL = "https://webcamstravel.p.mashape.com/" +
            "webcams/list/limit=50/property=live?lang=en&show=webcams%3Aimage%2Clocation%2Cplayer";
    private static final String TAG = "RemoteDataURIBuilder";
    private static final String BASE_URL = "https://webcamstravel.p.mashape.com/webcams/list";
    private static final String LANGUAGE_QUERY_PARAM = "lang";
    private static final String LANGUAGE_QUERY_VALUE = "en";
    private static final String SHOW_QUERY_PARAM = "show";
    private static final String SHOW_QUERY_CONCATENATED_VALUE = "webcams:image,location,player,live";

    public static String buildURLWithLatLong(String locationLatitude, String locationLongitude) {

        final String LOCATION_RADIUS = "1000";
        if (locationLatitude == null || locationLongitude == null) {
            locationLatitude = "37";
            locationLongitude = "-122";
        }

        String concatenatedPath = "nearby=" + locationLatitude + "," + locationLongitude + "," + LOCATION_RADIUS;

        Uri.Builder uri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(concatenatedPath)
                .appendQueryParameter(LANGUAGE_QUERY_PARAM, LANGUAGE_QUERY_VALUE)
                .appendQueryParameter(SHOW_QUERY_PARAM, SHOW_QUERY_CONCATENATED_VALUE);
        uri.build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.e(TAG, uri.toString());
        return url.toString();
    }

    public static String buildURLWithFavoriteWebcams(ArrayList webcamIds) {

        String concatenatedPath = "webcam=" + concatenateFavoriteWebcams(webcamIds);


        Uri.Builder uri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(concatenatedPath)
                .appendQueryParameter(LANGUAGE_QUERY_PARAM, LANGUAGE_QUERY_VALUE)
                .appendQueryParameter(SHOW_QUERY_PARAM, SHOW_QUERY_CONCATENATED_VALUE);
        uri.build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.e(TAG, uri.toString());
        return url.toString();
    }

    private static String concatenateFavoriteWebcams(ArrayList webcamIds) {
        StringBuilder concatenatedWebcamIds = new StringBuilder();
        for (int i = 0; i < webcamIds.size(); i++) {
            if (i > 0) {
                concatenatedWebcamIds.append("%2C");
            }
            concatenatedWebcamIds.append(webcamIds.get(i));
        }
        return concatenatedWebcamIds.toString();
    }


    public static String BuildContinentUrl(String continent) {
        String concatenatedPath = "continent=" + continent;

        Uri.Builder uri = Uri.parse(BASE_URL).buildUpon()
                .appendEncodedPath(concatenatedPath)
                .appendQueryParameter(LANGUAGE_QUERY_PARAM, LANGUAGE_QUERY_VALUE)
                .appendQueryParameter(SHOW_QUERY_PARAM, SHOW_QUERY_CONCATENATED_VALUE);
        uri.build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.e(TAG, uri.toString());
        return url.toString();
    }
}
