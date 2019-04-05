package com.example.mogkiosk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;


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

    private OnFragmentInteractionListener mListener;

    public ArtistFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param String Name.
     * @param String Bio.
     * @param String Tag
     * @return A new instance of fragment ArtistFrag.
     */
    // TODO: Rename and change types and number of parameters
    public ArtistFrag newInstance(String name, String bio, String tag) {
        ArtistFrag fragment = new ArtistFrag();
        Bundle args = new Bundle();
        //put the values into the arguments of this AristFragment instance and associate them with its final values
        args.putString(NAME, name);
        args.putString(BIO, bio);
        args.putString(BIO, tag);
        //set the arguments to the fragment and return
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_artist, container, false);

        //get layout EditTexts
        artistNameTextView = v.findViewById(R.id.artist_name);
        tagLineTextView = v.findViewById(R.id.artist_tagline);
        subBioTextView = v.findViewById(R.id.artist_subbio);
        bioTextView = v.findViewById(R.id.artist_bio);

        Bundle bundle = getArguments();
        //check if there are arguments in the savedInstanceState
        if (bundle != null) {
            //get the arguments that were passed
            name = bundle.getString(NAME);
            description = bundle.getString(BIO);
            tag = bundle.getString(TAG);
            subbio = bundle.getString(SUBBIO);

            //update artist fragment with those values, only if those values contain something
            if(!name.isEmpty()) {
                updateName(name, artistNameTextView);
            }

            if(!description.isEmpty()) {
                updateBio(description, bioTextView);
            }

            if(!subbio.isEmpty()) {
               updateSubBio(subbio, subBioTextView);
            }

            if(!tag.isEmpty()) {
                updateTagline(tag, tagLineTextView);
            }


        }
        return v;
    }

    /**
     * Method to set text of artist name fragment
     * @param newName the parameter name sent by admin artist fragment via AdminActivity
     * @param tv the textview associated with the name textview of the artist fragment
     */
    public void updateName(CharSequence newName, TextView view) {
        view.setText(newName);
    }

    /**
     * Method to set tagline of artist fragment
     * @param newTag the tagline sent by admin artist fragment via AdminActivity
     * @param tv
     */
    public void updateTagline(CharSequence newTag, TextView tv) {
        tv.setText(newTag);
    }

    public void updateSubBio(CharSequence newSubBio, TextView tv) {
        subBioTextView.setText(newSubBio);
    }

    /**
     * Method to set the bio of the artist fragment
     * @param newBio
     * @param tv
     */
    public void updateBio(CharSequence newBio, TextView tv) {
        tv.setText(newBio);
    }


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
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
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
