package com.example.wassim.localwebcams;

import android.content.Intent;
import android.widget.RemoteViewsService;


public class WidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
