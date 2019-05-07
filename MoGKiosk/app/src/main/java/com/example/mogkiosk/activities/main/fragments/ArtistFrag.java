package com.example.mogkiosk.activities.main.fragments;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mogkiosk.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ArtistFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ArtistFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArtistFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private TextView artistNameTextView;
    private TextView tagLineTextView;
    private TextView bioTextView;
    private TextView subBioTextView;
    private ImageView profilePic;
    private SharedPreferences prefs;

    public ArtistFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ArtistFrag.
     */
    public ArtistFrag newInstance(String name, String bio, String tag) {
        ArtistFrag fragment = new ArtistFrag();
//        Bundle args = new Bundle();
//        //put the values into the arguments of this AristFragment instance and associate them with its final values
//        args.putString(NAME, name);
//        args.putString(BIO, bio);
//        args.putString(BIO, tag);
//        //set the arguments to the fragment and return
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        prefs =  PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_artist, container, false);

        artistNameTextView = v.findViewById(R.id.artist_name);
        bioTextView = v.findViewById(R.id.artistFrag_bio);
        subBioTextView = v.findViewById(R.id.artist_subbio);
        tagLineTextView = v.findViewById(R.id.artist_tagline);
        profilePic = v.findViewById(R.id.artist_headshot);
        profilePic.setAdjustViewBounds(true);

        return v;
    }

    public void onResume() {

        super.onResume();
        Bundle extras = getArguments();

        if(extras != null) {
            // this string name is null
            String name = prefs.getString(getString(R.string.a_artistname), "");
            String bio = prefs.getString(getString(R.string.a_bio), "");
            String tag = prefs.getString(getString(R.string.a_tag), "");
            String subbio = prefs.getString(getString(R.string.a_subbio), "");


            if(name != null && !name.isEmpty() )  {
                artistNameTextView.setText(name);
            }

            if(bio != null && !bio.isEmpty()) {
                bioTextView.setText(bio);
            }

            if(tag != null && !tag.isEmpty()) {
                tagLineTextView.setText(tag);
            }

            if(subbio != null && !subbio.isEmpty()) {
                subBioTextView.setText(subbio);
            }

            ContextWrapper cw = new ContextWrapper(getContext());
            // path to /data/data/yourapp/app_data/imageDir
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            loadImageFromStorage(directory.getAbsolutePath());

        }
    }

    private void loadImageFromStorage(String path)
    {

        try {
            File f=new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            Drawable d = new BitmapDrawable(getResources(), b);
            profilePic.setImageDrawable(d);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
        }
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    /**
     * >Communicating with Other Fragments</a> for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
