package com.example.mogkiosk;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
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
import android.widget.GridView;
import android.widget.TextView;
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

    private View v;

    private GridView imageGrid;
    private ArrayList<Bitmap> bitmapList;

    private OnFragmentInteractionListener mListener;

    public ArtistFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
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
        v = inflater.inflate(R.layout.fragment_artist, container, false);

        artistNameTextView = v.findViewById(R.id.artist_name);
        bioTextView = v.findViewById(R.id.artistFrag_bio);
        subBioTextView = v.findViewById(R.id.artist_subbio);
        tagLineTextView = v.findViewById(R.id.artist_tagline);
        Bundle extras = getArguments();

        if(extras != null) {
            if(!extras.getString(NAME).isEmpty()) {
                artistNameTextView.setText(extras.getString(NAME));
            }

            if(!extras.getString(BIO).isEmpty()) {
                bioTextView.setText(extras.getString(BIO));
            }

            if(!extras.getString(TAG).isEmpty()) {
                tagLineTextView.setText(extras.getString(TAG));
            }

            if(!extras.getString(SUBBIO).isEmpty()) {
                subBioTextView.setText(extras.getString(SUBBIO));
            }

        }


        return v;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
