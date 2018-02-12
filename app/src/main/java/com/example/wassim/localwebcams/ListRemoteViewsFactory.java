package com.example.wassim.localwebcams;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    Context mContext;
    ArrayList<String> continents;

    public ListRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
    }

    @Override
    public void onDataSetChanged() {
        continents = new ArrayList<>();
        continents.add("AF");
        continents.add("AN");
        continents.add("AS");
        continents.add("EU");
        continents.add("NA");
        continents.add("OC");
        continents.add("SA");
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_row);
        String continent = continents.get(position);
        remoteViews.setTextViewText(R.id.row_text_view, continent);

        // set a fill-intent, which will be used to fill in the pending intent template
        // that is set on the collection view in WidgetProvider.
        Bundle extras = new Bundle();
        extras.putString("continent", continent);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        remoteViews.setOnClickFillInIntent(R.id.widget_item, fillInIntent);


        return remoteViews;
    }

    @Override
    public int getCount() {
        if (continents == null) return 0;
        else return continents.size();
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