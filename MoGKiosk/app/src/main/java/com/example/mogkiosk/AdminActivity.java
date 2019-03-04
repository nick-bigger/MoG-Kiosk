package com.example.mogkiosk;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;


public class AdminActivity extends AppCompatActivity implements ArtistFragAdmin.OnDataPass {


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private AdminActivity.SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private ArtistFragAdmin a_frag_admin;
    private ArtistFrag a_frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main);

        //initialize fragments
        a_frag_admin = new ArtistFragAdmin();
        a_frag = new ArtistFrag();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    /**
     * Implementation of interface method located in ArtistFragAdmin, handles the passing of data
     * from the Artist Admin page. It takes the name, tag, and description and converts them to
     * a String explicitly. Then it uses a newly created intent that points to MainActivity and sets
     * the information as arguments for that intent. Finally, with the arguments set the intent
     * is started with startActivity(intent)
     *
     * @param name the name sent by the submit button in the AdminArtistFragment
     * @param tag the tag sent by the submit button in the AdminArtistFragment
     * @param description the description sent by the submit button in the AdminArtistFragment
     */
    @Override
    public void onDataPass(CharSequence name, CharSequence tag, CharSequence description) {
        Intent intent = new Intent(AdminActivity.this, MainActivity.class);
        //convert to string
        String n = name.toString();
        String t = tag.toString();
        String d = description.toString();
        //set args
        intent.putExtra("name", n);
        intent.putExtra("tag", t);
        intent.putExtra("description", d);
        //start
        startActivity(intent);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            switch(position){
                case 0:
                    fragment = new ArtistFragAdmin();
                    break;
                case 1:
                    fragment = new WorkFragAdmin();
                    break;
                case 2:
                    fragment = new ProcessFragAdmin();
                    break;

            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "The Artist";
                case 1:
                    return "The Work";
                case 2:
                    return "The Process";
            }
            return null;
        }
    }



}


