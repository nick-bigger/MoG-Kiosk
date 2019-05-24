package com.example.mogkiosk.activities.admin.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mogkiosk.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProcessFragAdmin.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProcessFragAdmin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProcessFragAdmin extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int RESULT_LOAD_IMG = 1;
    private static final String YOUTUBE_API_KEY = "AIzaSyC4N2Q4nQxCv6Pm_wZt-QCNqgDq-fe27UI";
    private SharedPreferences prefs;

    private OnProcessDataPass dataPasser;

    private TextView processTitle;
    private TextView processDescription;
    private TextView youtubeID;
    private FragmentTransaction transaction;

    public ProcessFragAdmin() {
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
    public static ProcessFragAdmin newInstance(String param1, String param2) {
        ProcessFragAdmin fragment = new ProcessFragAdmin();
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
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
        prefs =  PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.frag_process_admin, container, false);

        // set up youtube view
        final String youtubeLink = prefs.getString(getString(R.string.a_pyoutube), "");

        String title = prefs.getString(getString(R.string.a_ptitle), "");
        String id = prefs.getString(getString(R.string.a_pyoutube), "");

        TextInputLayout VidTitle = rootView.findViewById(R.id.workTitleInputLayout);
        TextInputLayout YTId = rootView.findViewById(R.id.youtubeIdLayout);

        VidTitle.setHint(title);
        YTId.setHint(id);

        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_view, youTubePlayerFragment).commit();
        youTubePlayerFragment.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                if (!wasRestored) {
                    player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    //player.cueVideo("_zX5Uki421c");
                    player.cueVideo(youtubeLink);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {
                String errorMessage = error.toString();
                Snackbar.make(getActivity().getWindow().getDecorView().findViewById(android.R.id.content), errorMessage, Toast.LENGTH_LONG).show();
                Log.d("errorMessage:", errorMessage);
            }
        });



        Button submit = rootView.findViewById(R.id.submitBtn);
        processTitle = rootView.findViewById(R.id.title);
        youtubeID = rootView.findViewById(R.id.youtubeEdit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get text of variables
                CharSequence title = processTitle.getText();
                CharSequence youtubeLink = youtubeID.getText();
                //pass them to auxiliary method
                onButtonPressed(title, "", youtubeLink);
            }
        });

        return rootView;
    }

    public void onResume() {
        super.onResume();
        final String youtubeLink = prefs.getString(getString(R.string.a_pyoutube), "");

        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        transaction.replace(R.id.youtube_view, youTubePlayerFragment);

        if(!youtubeLink.isEmpty()) {
            youTubePlayerFragment.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {

                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                    if (!wasRestored) {
                        player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                        //player.cueVideo("_zX5Uki421c");
                        player.cueVideo(youtubeLink);
                    }
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {
                    String errorMessage = error.toString();
                    Snackbar.make(getActivity().getWindow().getDecorView().findViewById(android.R.id.content), errorMessage, Toast.LENGTH_LONG).show();
                    Log.d("errorMessage:", errorMessage);
                }
            });

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

            } else {
                Toast.makeText(getActivity(), "No Image Chosen",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    /**
     * Auxiliary method that passed data to the interface method associated with the AdminActivity
     * This is used so AdminActivity can access the data and pass it along
     * @param processTitle
     * @param processDescription
     */
    private void onButtonPressed(CharSequence processTitle, CharSequence processDescription, CharSequence youtubeLink) {
        dataPasser.onProcessDataPass(processTitle, processDescription, youtubeLink);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProcessDataPass) {
            dataPasser = (OnProcessDataPass) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDataPass interface");
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public interface OnProcessDataPass {
        void onProcessDataPass(CharSequence title, CharSequence processTitle, CharSequence processDescription);
    }
}
