package com.example.mogkiosk.activities.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.example.mogkiosk.LoginActivity;
import com.example.mogkiosk.NavigationIconClickListener;
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
    private SharedPreferences.Editor editor;

    /**
     * Initializes and creates the sections adapter, sets the content view on activity creation
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpToolbar();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        editor.apply();

        Button homeBtn = findViewById(R.id.homeBtn);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });

        Button aboutBtn = findViewById(R.id.aboutBtn);

        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
            }
        });

        Button loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }

    /**
     * Method that activates once the MainActivity lifecycle officially starts, upon which the container is found and set to the fragments.
     * The tab layout is also defined and set up on start.
     */
    public void onStart() {
        super.onStart();
        // Set up the ViewPager with the sections adapter.
        /**
         * The {@link ViewPager} that will host the section contents.
         */
        ViewPager mViewPager = findViewById(R.id.viewPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        //
        mViewPager.setOffscreenPageLimit(3);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // change tab spacing
//        for(int i=0; i < tabLayout.getTabCount()-1; i++) {
//            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
//            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
//            p.setMargins(0, 0, 200, 0);
//            tab.requestLayout();
//        }
    }

    /**
     * Once the activity is suspended and resumed, it will first retrieve the fragments in the sections adapter.
     * Once they are found, it will check if there has been any data passed on from the AdminActivity to the MainActivity
     * via bundle.
     */
    public void onResume() {
        super.onResume();

        ArtistFrag a_frag = (ArtistFrag) mSectionsPagerAdapter.getItem(0);
        WorkFrag w_frag = (WorkFrag) mSectionsPagerAdapter.getItem(1);
        ProcessFrag p_frag = (ProcessFrag) mSectionsPagerAdapter.getItem(2);

        //Get transferred data from admin
        Bundle extras = getIntent().getExtras();
        //check if there are args passed to MainActivity, otherwise skip code block
        if(extras != null) {
            //set the values in the adapter if the check is true

            //get the arguments that were passed to the MainActivity by AdminActivity for Artist
            String name = extras.getString("name");
            String tag = extras.getString("tag");
            String description = extras.getString("description");
            String subbio = extras.getString("subbio");
            //get arguments form Work Fragment
            String artist = extras.getString("artist");
            String piecedate = extras.getString("piecedate");
            String title = extras.getString("title");
            String dimensions = extras.getString("dimensions");
            String medium = extras.getString("medium");
            String collection = extras.getString("collection");
            String date = extras.getString("date");
            String photo = extras.getString("photo");
            //get process data from the bundle
            String processTitle = extras.getString("processTitle");
            String processDescription = extras.getString("processDescription");
            // Using bundle
            //  update artist
            Bundle artistData = new Bundle();
            artistData.putString("NAME", name);
            artistData.putString("BIO", description);
            artistData.putString("TAG", tag);
            artistData.putString("SUBBIO", subbio);
            //update work
            Bundle workData = new Bundle();
            workData.putString("ARTIST", artist);
            workData.putString("TITLE", title);
            workData.putString("PIECEDATE", piecedate);
            workData.putString("DIMENSIONS", dimensions);
            workData.putString("MEDIUM", medium);
            workData.putString("COLLECTION", collection);
            workData.putString("DATE", date);
            workData.putString("PHOTOBY", photo);
            //update process
            Bundle processData = new Bundle();
            processData.putString("PTITLE", processTitle);
            processData.putString("PDESCRIPTION", processDescription);
            //set arguments and update adapter
            a_frag.setArguments(artistData);
            w_frag.setArguments(workData);
            p_frag.setArguments(processData);
            updateSharedPreferences(extras);
            mSectionsPagerAdapter.setItem(a_frag, 0);
            mSectionsPagerAdapter.setItem(w_frag, 1);
            mSectionsPagerAdapter.setItem(p_frag, 2);
        }
    }

    private void updateSharedPreferences(Bundle extras) {
        //get the arguments that were passed to the MainActivity by AdminActivity for Artist
        String name = extras.getString("name");
        String tag = extras.getString("tag");
        String description = extras.getString("description");
        String subbio = extras.getString("subbio");
        //get arguments form Work Fragment
        String artist = extras.getString("artist");
        String piecedate = extras.getString("piecedate");
        String title = extras.getString("title");
        String dimensions = extras.getString("dimensions");
        String medium = extras.getString("medium");
        String collection = extras.getString("collection");
        String date = extras.getString("date");
        String photo = extras.getString("photo");
        //get arguments from process
        String pTitle = extras.getString("processTitle");
        String pDescription = extras.getString("processDescription");

        if(name != null && !name.isEmpty()) {
            editor.putString(getString(R.string.a_artistname), name);
            editor.commit();
        }

        if(tag != null && !tag.isEmpty()) {
            editor.putString(getString(R.string.a_tag), tag);
            editor.commit();
        }

        if(description != null && !description.isEmpty()) {
            editor.putString(getString(R.string.a_bio), description);
            editor.commit();
        }

        if(subbio != null && !subbio.isEmpty()) {
            editor.putString(getString(R.string.a_subbio), subbio);
            editor.commit();
        }

        if(artist != null && !artist.isEmpty()) {
            editor.putString(getString(R.string.a_workartist), artist);
            editor.commit();
        }

        if(piecedate != null && !piecedate.isEmpty()) {
            editor.putString(getString(R.string.a_piecedate), piecedate);
            editor.commit();
        }

        if(title != null && !title.isEmpty()) {
            editor.putString(getString(R.string.a_title), title);
            editor.commit();
        }

        if(dimensions != null && !dimensions.isEmpty()) {
            editor.putString(getString(R.string.a_dimension), dimensions);
            editor.commit();
        }

        if(medium != null && !medium.isEmpty()) {
            editor.putString(getString(R.string.a_medium), medium);
            editor.commit();
        }

        if(collection != null && !collection.isEmpty()) {
            editor.putString(getString(R.string.a_collection), collection);
            editor.commit();
        }

        if(date != null && !date.isEmpty()) {
            editor.putString(getString(R.string.a_date), date);
            editor.commit();
        }

        if(photo != null && !photo.isEmpty()) {
            editor.putString(getString(R.string.a_photo), photo);
            editor.commit();
        }
        if(pTitle != null && !pTitle.isEmpty()) {
            editor.putString(getString(R.string.a_ptitle), pTitle);
            editor.commit();
        }

        if(pDescription != null && !pDescription.isEmpty()) {
            editor.putString(getString(R.string.a_pdescription), pDescription);
            editor.commit();
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    class SectionsPagerAdapter extends FragmentPagerAdapter {
        Fragment artistFragment = new ArtistFrag();
        Fragment workFrag = new WorkFrag();
        Fragment processFrag = new ProcessFrag();
        SectionsPagerAdapter(FragmentManager fm) {
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

        void setItem(Fragment frag, int position) {
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
                    return "Artist";
                case 1:
                    return "Work";
                case 2:
                    return "Process";
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
            TextView textView = rootView.findViewById(R.id.section_label);
            return rootView;
        }
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(getDrawable(R.drawable.ic_logo));
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new NavigationIconClickListener(
                this,
                findViewById(R.id.viewPager),
                new AccelerateDecelerateInterpolator()));

//                AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
//                params.setScrollFlags(0);  // clear all scroll flags
    }
}
