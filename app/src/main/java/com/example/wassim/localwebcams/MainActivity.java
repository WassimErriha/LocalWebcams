package com.example.wassim.localwebcams;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.wassim.localwebcams.Objects.Webcam;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements FavoriteWebcamsFragment.OnListFragmentInteractionListener
        , LiveWebcamsFragment.OnListFragmentInteractionListener, MyItemRecyclerViewAdapter.onListItemClickListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.addFragment(new LiveWebcamsFragment(), "Live");
        mSectionsPagerAdapter.addFragment(new NearbyWebcamsFragment(), "Nearby");
        mSectionsPagerAdapter.addFragment(new DiscoverWebcamsFragment(), "Discover");
        mSectionsPagerAdapter.addFragment(new FavoriteWebcamsFragment(), "Favorites");

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(4);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public void onListFragmentInteraction(String item) {
        //  Toast.makeText(this,"" + item,Toast.LENGTH_LONG).show();
    }

    @Override
    public String onListItemClick(Webcam webcam) {
        //Toast.makeText(this,"" + webcam.getId(),Toast.LENGTH_LONG).show();
        return null;
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
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //instantiateItem(new LiveWebcamsFragment(), position)
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            // Show 4 total pages. first page is only for debugging
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
