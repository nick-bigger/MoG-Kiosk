package com.example.mogkiosk.activities.main.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.mogkiosk.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProcessFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProcessFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProcessFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private VideoView videoView;

    private SharedPreferences prefs;

    private TextView title;
    private TextView description;
    private OnFragmentInteractionListener mListener;

    public ProcessFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProcessFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static ProcessFrag newInstance(String param1, String param2) {
        ProcessFrag fragment = new ProcessFrag();
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
        prefs =  PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_process, container, false);
        videoView = rootView.findViewById(R.id.videoView);
        String videoPath = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.videoplayback;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        MediaController mediaController = new MediaController(getActivity());
        videoView.setMediaController(mediaController);
//        FrameLayout fl = rootView.findViewById(R.id.frameLayout4);
//        mediaController.setAnchorView(fl);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        /*
                         * add media controller
                         */
                        MediaController mediaController = new MediaController(getActivity());
                        videoView.setMediaController(mediaController);
                        mediaController.setAnchorView(videoView);
                    }
                });
            }
        });


        title = rootView.findViewById(R.id.video_title);
        description = rootView.findViewById(R.id.video_duration);
        // Inflate the layout for this fragment
        return rootView;
    }

    public void onResume() {
        super.onResume();
        Bundle extras = getArguments();
        if(extras != null) {
            String processTitle = prefs.getString(getString(R.string.a_ptitle), "");
            String processDescription = prefs.getString(getString(R.string.a_pdescription), "");
            if(processTitle != null && !processTitle.isEmpty()) {
                title.setText(processTitle);
            }

            if(processDescription != null && !processDescription.isEmpty()) {
                description.setText(processDescription);
            }
        }
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
            videoView.start();
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
