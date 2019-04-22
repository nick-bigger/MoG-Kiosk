package com.example.mogkiosk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WorkFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WorkFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private TextView workTitle;
    private TextView workDate;
    private TextView date;
    private TextView artistTitle;
    private TextView workBio;
    private TextView mediumTitle;
    private TextView dimensions;
    private TextView collection;
    private TextView photoBy;
    private TextView workDescription;
    private View v;

    private GridView imageGrid;
    private ArrayList<Bitmap> bitmapList;

    public WorkFrag() {
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
    public static WorkFrag newInstance(String param1, String param2) {
        WorkFrag fragment = new WorkFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_work, container, false);

        this.imageGrid = (GridView) v.findViewById(R.id.gridview);
        this.bitmapList = new ArrayList<Bitmap>();

        try {
            this.bitmapList.add(BitmapFactory.decodeResource(v.getResources(),
                    R.drawable.related_work_1));
            this.bitmapList.add(BitmapFactory.decodeResource(v.getResources(),
                    R.drawable.related_work_2));
            this.bitmapList.add(BitmapFactory.decodeResource(v.getResources(),
                    R.drawable.related_work_3));
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.imageGrid.setAdapter(new ImageAdapter(getActivity(), this.bitmapList));

        artistTitle = v.findViewById(R.id.artist_entry);
        workDate = v.findViewById(R.id.work_date);
        date = v.findViewById(R.id.date_entry);
        workBio = v.findViewById(R.id.work_desc);
        workTitle = v.findViewById(R.id.work_title);
        collection = v.findViewById(R.id.collection_entry);
        mediumTitle = v.findViewById(R.id.medium_entry);
        dimensions = v.findViewById(R.id.dimensions_entry);
        photoBy = v.findViewById(R.id.photo_credit_entry);



        return v;
    }

    public void onResume() {
        super.onResume();
        Bundle extras = getArguments();

        if (extras != null) {
            // this string name is null
            if (extras.getString("TITLE") != null && !extras.getString("TITLE").isEmpty()) {
                workTitle.setText(extras.getString("TITLE"));
            }

            if (extras.getString("DATE") != null && !extras.getString("DATE").isEmpty()) {
                date.setText(extras.getString("DATE"));
            }

            if (extras.getString("COLLECTION") != null && !extras.getString("COLLECTION").isEmpty()) {
                collection.setText(extras.getString("COLLECTION"));
            }

            if (extras.getString("DIMENSIONS") != null && !extras.getString("DIMENSIONS").isEmpty()) {
                dimensions.setText(extras.getString("DIMENSIONS"));
            }


            if (extras.getString("MEDIUM") != null && !extras.getString("MEDIUM").isEmpty()) {
                mediumTitle.setText(extras.getString("MEDIUM"));
            }

            if (extras.getString("ARTIST") != null && !extras.getString("ARTIST").isEmpty()) {

                artistTitle.setText(extras.getString("ARTIST"));
            }

            if (extras.getString("PIECEDATE") != null && !extras.getString("PIECEDATE").isEmpty()) {
                workDate.setText(extras.getString("PIECEDATE"));
            }

        }
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
//        else {
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
