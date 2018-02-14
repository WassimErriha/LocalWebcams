package com.example.wassim.localwebcams;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class WidgetDetailsActivity extends AppCompatActivity {

    private static final String CONTINENT_NAME = "continent_name";
    private static final String CONTINENT_ISO_CODE = "continent_iso_code";
    private static final String CONTINENT_WEBCAMS_URL = "continent_webcams_url";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_details);

        if ((getIntent().hasExtra(CONTINENT_ISO_CODE) && getIntent().hasExtra(CONTINENT_NAME))) {
            String continentISOCODE = getIntent().getStringExtra(CONTINENT_ISO_CODE);
            String continentName = getIntent().getStringExtra(CONTINENT_NAME);
            this.setTitle(continentName);
            String continentWebcamsUrl = RemoteDataURIBuilder.BuildContinentUrl(continentISOCODE);

            // reuse LiveWebcamsFragment to show webcams for the continent picked
            LiveWebcamsFragment fragment = new LiveWebcamsFragment();
            Bundle bundle = new Bundle();
            bundle.putString(CONTINENT_WEBCAMS_URL, continentWebcamsUrl);
            fragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.continent_fragment_container, new LiveWebcamsFragment())
                    .commit();
        }
    }
}
