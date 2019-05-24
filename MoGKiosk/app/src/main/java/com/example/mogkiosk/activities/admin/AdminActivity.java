package com.example.mogkiosk.activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;

import com.example.mogkiosk.CreateAccountActivity;
import com.example.mogkiosk.NavigationIconClickListener;
import com.example.mogkiosk.R;
import com.example.mogkiosk.UpdateCredActivity;
import com.example.mogkiosk.activities.admin.fragments.ArtistFragAdmin;
import com.example.mogkiosk.activities.admin.fragments.ProcessFragAdmin;
import com.example.mogkiosk.activities.admin.fragments.WorkFragAdmin;
import com.example.mogkiosk.activities.main.MainActivity;

/**
 * Class that facilitates the admin side of the app functionality. It has its own section adapter that manages admin counterpart fragments to the
 * xml layouts on the frontend (Artist, Process, Work). It implements an interface methods defined in the fragments to send variables back and forth
 * from the admin side to the front end.
 */
public class AdminActivity extends AppCompatActivity implements ArtistFragAdmin.OnArtistDataPass, ProcessFragAdmin.OnProcessDataPass
, WorkFragAdmin.OnWorkDataPass {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTitle("");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main);

        setUpToolbar();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        /**
         * The {@link android.support.v4.view.PagerAdapter} that will provide
         * fragments for each of the sections. We use a
         * {@link FragmentPagerAdapter} derivative, which will keep every
         * loaded fragment in memory. If this becomes too memory intensive, it
         * may be best to switch to a
         * {@link android.support.v4.app.FragmentStatePagerAdapter}.
         */
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        // The {@link ViewPager} that will host the section contents.
        ViewPager mViewPager = findViewById(R.id.viewPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        Button homeBtn = findViewById(R.id.homeBtn);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, MainActivity.class));
            }
        });

//        Button changePassBtn = findViewById(R.id.changePassBtn);
//
//        changePassBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(AdminActivity.this, ChangePassActivity.class));
//            }
//        });

        Button updateCredBtn = findViewById(R.id.updateCredBtn);

        updateCredBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, UpdateCredActivity.class));
            }
        });
//
//        Button createAccBtn = findViewById(R.id.createAccBtn);
//
//        createAccBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(AdminActivity.this, CreateAccountActivity.class));
//            }
//        });

        // change tab spacing
//        for(int i=0; i < tabLayout.getTabCount()-1; i++) {
//            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
//            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
//            p.setMargins(0, 0, 200, 0);
//            tab.requestLayout();
//        }
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
    public void onArtistDataPass(CharSequence name, CharSequence tag, CharSequence description, CharSequence subbio) {
        Intent intent = new Intent(AdminActivity.this, MainActivity.class);
        //convert to string
        String n = name.toString();
        String t = tag.toString();
        String d = description.toString();
        String s = subbio.toString();
        //set args
        intent.putExtra("name", n);
        intent.putExtra("tag", t);
        intent.putExtra("description", d);
        intent.putExtra("subbio", s);
        //start
        this.startActivity(intent);
    }

    @Override
    public void onWorkDataPass(CharSequence artist, CharSequence piecedate, CharSequence title, CharSequence date,
                               CharSequence collection, CharSequence dimensions, CharSequence medium, CharSequence photo, CharSequence workdesc) {
        Intent intent = new Intent(AdminActivity.this, MainActivity.class);
        //converting to string
        String a = artist.toString();
        String p = piecedate.toString();
        String t = title.toString();
        String d = date.toString();
        String c = collection.toString();
        String dim = dimensions.toString();
        String m = medium.toString();
        String ph = photo.toString();
        String wdesc = workdesc.toString();
        intent.putExtra("artist", a);
        intent.putExtra("piecedate", p);
        intent.putExtra("title", t);
        intent.putExtra("date", d);
        intent.putExtra("collection", c);
        intent.putExtra("dimensions", dim);
        intent.putExtra("medium", m);
        intent.putExtra("photo", ph);
        intent.putExtra("workdesc", wdesc);
        //start
        this.startActivity(intent);

    }

    @Override
    public void onProcessDataPass(CharSequence processTitle, CharSequence processDescription, CharSequence youtubeLink) {
        Intent intent = new Intent(AdminActivity.this, MainActivity.class);
        //converting
        String pTitle = processTitle.toString();
        String pDescription = processDescription.toString();
        String pYoutubeLink = youtubeLink.toString();
        intent.putExtra("processTitle", pTitle);
        intent.putExtra("processDescription", pDescription);
        intent.putExtra("processYoutube", pYoutubeLink);
        //start
        this.startActivity(intent);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
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
                    return "Artist";
                case 1:
                    return "Work";
                case 2:
                    return "Process";
            }
            return null;
        }
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(getDrawable(R.drawable.ic_logo_black));
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new NavigationIconClickListener(
                this,
                findViewById(R.id.viewPager),
                new AccelerateDecelerateInterpolator()));
    }

}


