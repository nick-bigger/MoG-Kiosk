package com.example.mogkiosk.activities.main.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.mogkiosk.R;
import com.example.mogkiosk.activities.main.MainActivity;

import java.util.ArrayList;



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
    private static final String NAME = "NAME";
    private static final String BIO = "BIO";
    private static final String TAG = "TAG";
    private static final String SUBBIO = "SUBBIO";

    // TODO: Rename and change types of parameters
    private String name;
    private String tag;
    private String description;
    private String subbio;
    private TextView artistNameTextView;
    private TextView tagLineTextView;
    private TextView bioTextView;
    private TextView subBioTextView;
    private SharedPreferences prefs;

    private GridView imageGrid;
    private ArrayList<Bitmap> bitmapList;

    private OnFragmentInteractionListener mListener;

    public ArtistFrag() {
        // Required empty public constructor
    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @return A new instance of fragment ArtistFrag.
//     */
//    // TODO: Rename and change types and number of parameters
//    public ArtistFrag newInstance(String name, String bio, String tag) {
//        ArtistFrag fragment = new ArtistFrag();
//        Bundle args = new Bundle();
//        //put the values into the arguments of this AristFragment instance and associate them with its final values
//        args.putString(NAME, name);
//        args.putString(BIO, bio);
//        args.putString(BIO, tag);
//        //set the arguments to the fragment and return
//        fragment.setArguments(args);
//        return fragment;
//    }

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

        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed (Uri uri){
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * >Communicating with Other Fragments</a> for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
