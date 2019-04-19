package com.example.mogkiosk.activities.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mogkiosk.LoginActivity;
import com.example.mogkiosk.R;
import com.example.mogkiosk.activities.AboutActivity;
import com.example.mogkiosk.activities.main.fragments.ArtistFrag;
import com.example.mogkiosk.activities.main.fragments.ProcessFrag;
import com.example.mogkiosk.activities.main.fragments.WorkFrag;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ArtistFrag a_frag;
    private WorkFrag w_frag;
    private ProcessFrag p_frag;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    /**
     * Initializes and creates the sections adapter, sets the content view on activity creation
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher_round);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        // logic for hiding the status bar
//        View decorView = getWindow().getDecorView();
//        // Hide the status bar.
//        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(uiOptions);
//        // Remember that you should never show the action bar if the
//        // status bar is hidden, so hide that too if necessary.
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
      
    }

    /**
     * Method that activates once the MainActivity lifecycle officially starts, upon which the container is found and set to the fragments.
     * The tab layout is also defined and set up on start.
     */
    public void onStart() {
        super.onStart();
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    /**
     * Once the activity is suspended and resumed, it will first retrieve the fragments in the sections adapter.
     * Once they are found, it will check if there has been any data passed on from the AdminActivity to the MainActivity
     * via bundle.
     */
    public void onResume() {
        super.onResume();

        a_frag = (ArtistFrag) mSectionsPagerAdapter.getItem(0);

        //Get transferred data from admin
        Bundle extras = getIntent().getExtras();
        //check if there are args passed to MainActivity, otherwise skip code block
        if(extras != null) {

            //get the arguments that were passed to the MainActivity by AdminActivity
            String name = extras.getString("name");
            String tag = extras.getString("tag");
            String description = extras.getString("description");
            String subbio = extras.getString("subbio");

            // Using bundle
            //  update artist
            Bundle data = new Bundle();
            data.putString("NAME", name);
            data.putString("BIO", description);
            data.putString("TAG", tag);
            data.putString("SUBBIO", subbio);

            //set arguments and update adapter
            a_frag.setArguments(data);
            mSectionsPagerAdapter.setItem(a_frag, 0);
            //begin the transaction and commit
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case R.id.action_settings:
                Intent intent1 = new Intent(this, LoginActivity.class);
                this.startActivity(intent1); //intended to quit MainActivity (I'm doing this in hopes that it resets the main page when admin submits)
                return true;
            case R.id.action_about:
                Intent intent2 = new Intent(this, AboutActivity.class);
                this.startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        Fragment artistFragment = new ArtistFrag();
        Fragment workFrag = new WorkFrag();
        Fragment processFrag = new ProcessFrag();
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {


            switch(position){
                case 0:
                    return artistFragment;
                case 1:
                    return workFrag;
                case 2:
                    return processFrag;

            }
            return null;
        }

        public void setItem(Fragment frag, int position) {
            switch(position) {
                case 0:
                    artistFragment = frag;
                    break;
                case 1:
                    workFrag = frag;
                    break;
                case 2:
                    processFrag = frag;
                    break;
            }
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            return rootView;
        }
    }
}
