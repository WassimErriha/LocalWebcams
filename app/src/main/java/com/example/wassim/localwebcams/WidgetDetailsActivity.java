package com.example.wassim.localwebcams;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class WidgetDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_details);

        if (getIntent().hasExtra("continent")) {
            String continent = getIntent().getStringExtra("continent");
            String continentWebcamsUrl = RemoteDataURIBuilder.BuildContinentUrl(continent);

            // reuse LiveWebcamsFragment to show webcams for the continent picked
            LiveWebcamsFragment fragment = new LiveWebcamsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("continent_webcams_url", continentWebcamsUrl);
            fragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.continent_fragment_container, fragment, "continent_list_fragment")
                    .commit();
        }
    }
}
