package com.example.wassim.localwebcams;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String CONTINENT_NAME = "continent_name";
    private static final String CONTINENT_ISO_CODE = "continent_iso_code";
    Context mContext;
    ArrayList<String> continentISOCodeArray;
    ArrayList<String> continentNameArray;


    public ListRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onDataSetChanged() {

        /* Continent codes
        Code	Continent name
        AF	    Africa
        AN	    Antarctica
        AS	    Asia
        EU	    Europe
        NA	    North america
        OC	    Oceania
        SA	    South america  */

        continentISOCodeArray = new ArrayList<>();
        continentISOCodeArray.add("AF");
        continentISOCodeArray.add("AN");
        continentISOCodeArray.add("AS");
        continentISOCodeArray.add("EU");
        continentISOCodeArray.add("NA");
        continentISOCodeArray.add("OC");
        continentISOCodeArray.add("SA");

        continentNameArray = new ArrayList<>();
        continentNameArray.add("Africa");
        continentNameArray.add("Antarctica");
        continentNameArray.add("Asia");
        continentNameArray.add("Europe");
        continentNameArray.add("North america");
        continentNameArray.add("Oceania");
        continentNameArray.add("South america");
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_row);
        String continentName = continentNameArray.get(position);
        remoteViews.setTextViewText(R.id.row_text_view, continentName);

        // set a fill-intent, which will be used to fill in the pending intent template
        // that is set on the collection view in WidgetProvider.
        String continentISOCode = continentISOCodeArray.get(position);
        Bundle extras = new Bundle();
        extras.putString(CONTINENT_ISO_CODE, continentISOCode);
        extras.putString(CONTINENT_NAME, continentName);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        remoteViews.setOnClickFillInIntent(R.id.widget_item, fillInIntent);

        return remoteViews;
    }

    @Override
    public int getCount() {
        if (continentISOCodeArray == null) return 0;
        else return continentISOCodeArray.size();
    }

    @Override
    public void onCreate() {
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDestroy() {

    }
}