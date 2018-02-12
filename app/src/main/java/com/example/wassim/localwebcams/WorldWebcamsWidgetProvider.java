package com.example.wassim.localwebcams;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class WorldWebcamsWidgetProvider extends AppWidgetProvider {

    static RemoteViews updateAppWidget(Context context, int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.world_webcams_widget);
        Intent svcIntent = new Intent(context, WidgetRemoteViewsService.class);
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
        remoteView.setRemoteAdapter(R.id.widget_list_view, svcIntent);

        // set up a template for a collection item click listener
        Intent continentIntent = new Intent(context, WidgetDetailsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, continentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteView.setPendingIntentTemplate(R.id.widget_list_view, pendingIntent);

        return remoteView;


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; ++i) {
            RemoteViews remoteViews = updateAppWidget(context, appWidgetIds[i]);
            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

