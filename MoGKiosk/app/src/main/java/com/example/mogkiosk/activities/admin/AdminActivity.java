package com.example.mogkiosk.activities.admin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.mogkiosk.CreateAccountActivity;
import com.example.mogkiosk.R;
import com.example.mogkiosk.UpdateCredActivity;
import com.example.mogkiosk.activities.admin.fragments.ArtistFragAdmin;
import com.example.mogkiosk.activities.admin.fragments.ProcessFragAdmin;
import com.example.mogkiosk.activities.admin.fragments.WorkFragAdmin;
import com.example.mogkiosk.activities.changepass.ChangePassActivity;
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

        setTitle("Settings");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setTabTextColors(Color.parseColor("#3b3a3c"),Color.parseColor("#3b3a3c"));
        tabLayout.setupWithViewPager(mViewPager);

        for(int i=0; i < tabLayout.getTabCount()-1; i++) {
            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(0, 0, 200, 0);
            tab.requestLayout();
        }
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
                               CharSequence collection, CharSequence dimensions, CharSequence medium, CharSequence photo) {
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
        intent.putExtra("artist", a);
        intent.putExtra("piecedate", p);
        intent.putExtra("title", t);
        intent.putExtra("date", d);
        intent.putExtra("collection", c);
        intent.putExtra("dimensions", dim);
        intent.putExtra("medium", m);
        intent.putExtra("photo", ph);
        //start
        this.startActivity(intent);

    }

    @Override
    public void onProcessDataPass(CharSequence processTitle, CharSequence processDescription) {
        Intent intent = new Intent(AdminActivity.this, MainActivity.class);
        //converting
        String pTitle = processTitle.toString();
        String pDescription = processDescription.toString();
        intent.putExtra("processTitle", pTitle);
        intent.putExtra("processDescription", pDescription);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case R.id.action_changePass:
                Intent intent1 = new Intent(this, ChangePassActivity.class);
                this.startActivity(intent1); //intended to quit MainActivity (I'm doing this in hopes that it resets the main page when admin submits)
                return true;
            case R.id.action_updateCred:
                Intent intent2 = new Intent(this, UpdateCredActivity.class);
                this.startActivity(intent2);
                return true;
            case R.id.action_createAccount:
                Intent intent3 = new Intent(this, CreateAccountActivity.class);
                this.startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}


