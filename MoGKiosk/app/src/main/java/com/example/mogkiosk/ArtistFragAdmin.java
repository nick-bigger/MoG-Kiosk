package com.example.mogkiosk;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ArtistFragAdmin.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ArtistFragAdmin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArtistFragAdmin extends Fragment {
    private OnDataPass dataPasser;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText Name;
    private EditText Tag;
    private EditText Description;
    private Button submit;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ArtistFragAdmin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ArtistFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static ArtistFragAdmin newInstance(String param1, String param2) {
        ArtistFragAdmin fragment = new ArtistFragAdmin();
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
    public interface OnDataPass {
        //Interface method declaration to pass data to Admin
        void onDataPass(CharSequence name, CharSequence tag, CharSequence description);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.frag_artist_admin, container, false);
        //get values
        Name = rootView.findViewById(R.id.etName);
        Tag = rootView.findViewById(R.id.etTag);
        Description = rootView.findViewById(R.id.etDescription);
        submit = rootView.findViewById(R.id.submitBtn);
        //set onclick method
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get text of variables
                CharSequence input_name = Name.getText();
                CharSequence input_tag = Tag.getText();
                CharSequence input_description = Description.getText();
                //pass them to auxiliary method
                onButtonPressed(input_name, input_tag, input_description);
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
    public void onButtonPressed(CharSequence name, CharSequence tag, CharSequence description) {
        dataPasser.onDataPass(name, tag, description);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDataPass) {
            dataPasser = (OnDataPass) context;
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

}
