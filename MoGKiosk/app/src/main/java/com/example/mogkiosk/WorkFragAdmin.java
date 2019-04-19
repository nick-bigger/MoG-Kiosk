package com.example.mogkiosk;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WorkFragAdmin.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WorkFragAdmin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkFragAdmin extends Fragment {
    private OnWorkDataPass dataPasser;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView artist;
    private TextView piecedate;
    private TextView title;
    private TextView date;
    private TextView collection;
    private TextView medium;
    private TextView dimensions;
    private Button submit;


    public WorkFragAdmin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkFragAdmin newInstance(String param1, String param2) {
        WorkFragAdmin fragment = new WorkFragAdmin();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.frag_work_admin, container, false);

        artist = rootView.findViewById(R.id.artist);
        date = rootView.findViewById(R.id.label_date);
        medium = rootView.findViewById(R.id.medium);
        dimensions = rootView.findViewById(R.id.dimensions);
        collection = rootView.findViewById(R.id.collection_info);

        title = rootView.findViewById(R.id.title);
        piecedate = rootView.findViewById(R.id.date);
        submit = rootView.findViewById(R.id.submitBtn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get text of variables
                CharSequence artistInput = artist.getText();
                CharSequence piecedateInput = piecedate.getText();
                CharSequence mediumInput = medium.getText();
                CharSequence dimensionsInput = dimensions.getText();
                CharSequence collectionInput = collection.getText();
                CharSequence titleInput = title.getText();
                CharSequence dateInput = date.getText();
                //pass them to auxiliary method
                onButtonPressed(artistInput, piecedateInput, titleInput, dateInput, collectionInput,
                        dimensionsInput, mediumInput);
            }
        });



        return rootView;
    }

    /**
     * Auxiliary method that passed data to the interface method associated with the AdminActivity
     * This is used so AdminActivity can access the data and pass it along
     * @param name
     * @param tag
     * @param description
     */
    public void onButtonPressed(CharSequence artist, CharSequence piecedate, CharSequence title,
                                CharSequence date, CharSequence collection, CharSequence dimensions, CharSequence medium) {
        dataPasser.onWorkDataPass(artist, piecedate, title,
                date, collection, dimensions, medium);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ArtistFragAdmin.OnArtistDataPass) {
            dataPasser = (WorkFragAdmin.OnWorkDataPass) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDataPass interface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dataPasser = null;
    }

    /**
     * An interface that communicates with a method defined in the AdminActivity, the definition asks for arguments
     * pertinent to the EditText layout variables in the ArtistLayout xml.
     */
    public interface OnWorkDataPass {
        //Interface method declaration to pass data to Admin
        void onWorkDataPass(CharSequence artist, CharSequence piecedate, CharSequence title,
                            CharSequence date, CharSequence collection, CharSequence dimensions, CharSequence medium);
    }
}
