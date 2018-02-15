package com.example.wassim.localwebcams;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.wassim.localwebcams.Utils.ConnectivityUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LIVE_WEBCAM_FRAGMENT_TITLE = "Live";
    private static final String NEARBY_WEBCAM_FRAGMENT_TITLE = "Nearby";
    private static final String DISCOVER_WEBCAM_FRAGMENT_TITLE = "Discover";
    private static final String FAVORITE_WEBCAM_FRAGMENT_TITLE = "Favorite";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //design
        // TODO redesign views to display data correctly
        // TODO  App includes support for accessibility. That includes content descriptions, navigation using a D-pad, and, if applicable, non-audio versions of audio cues.
        // TODO App keeps all strings in a strings.xml file and enables RTL layout switching on all layouts.

        // TODO BONUS add a button in details activity to locate webcam and animate the dialogue map to the location

        if (ConnectivityUtils.isNetworkAvailable(this)) {
            SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
            mSectionsPagerAdapter.addFragment(new LiveWebcamsFragment(), LIVE_WEBCAM_FRAGMENT_TITLE);
            mSectionsPagerAdapter.addFragment(new NearbyWebcamsFragment(), NEARBY_WEBCAM_FRAGMENT_TITLE);
            mSectionsPagerAdapter.addFragment(new DiscoverWebcamsFragment(), DISCOVER_WEBCAM_FRAGMENT_TITLE);
            mSectionsPagerAdapter.addFragment(new FavoriteWebcamsFragment(), FAVORITE_WEBCAM_FRAGMENT_TITLE);

            // Set up the ViewPager with the sections adapter.
            ViewPager mViewPager = findViewById(R.id.view_pager);
            mViewPager.setAdapter(mSectionsPagerAdapter);
            mViewPager.setOffscreenPageLimit(4);

            TabLayout tabLayout = findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(mViewPager);
        } else {
            Toast.makeText(this, "No network available", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
