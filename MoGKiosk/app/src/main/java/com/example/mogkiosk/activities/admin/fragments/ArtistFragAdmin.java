package com.example.mogkiosk.activities.admin.fragments;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.mogkiosk.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ArtistFragAdmin.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ArtistFragAdmin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArtistFragAdmin extends Fragment {
    private static final int REQUEST_CODE_CHOOSE = 1;
    private OnArtistDataPass dataPasser;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int RESULT_LOAD_IMG = 1;
    private SharedPreferences prefs;


    private EditText Name;
    private EditText Tag;
    private EditText Description;
    private EditText SubBio;
    private ImageView imgView;
    private ProgressBar pb;


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
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
        prefs =  PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
    }


    /**
     * An interface that communicates with a method defined in the AdminActivity, the definition asks for arguments
     * pertinent to the EditText layout variables in the ArtistLayout xml.
     */
    public interface OnArtistDataPass {
        //Interface method declaration to pass data to Admin
        void onArtistDataPass(CharSequence name, CharSequence tag, CharSequence description, CharSequence subbio);
    }

    /**
     * Method that creates the view and registers the input given by the user. It then sends those values to onArtistDataPass method
     * through a helper method.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.frag_artist_admin, container, false);
        //get values
        Name = rootView.findViewById(R.id.title);
        Tag = rootView.findViewById(R.id.etTag);
        Description = rootView.findViewById(R.id.description);
        SubBio = rootView.findViewById(R.id.etSubBio);
        imgView = rootView.findViewById(R.id.mainImage);
        Button submit1 = rootView.findViewById(R.id.submitBtn);
        Button submit = rootView.findViewById(R.id.submitBtn);
        //set onclick method
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get text of variables
                CharSequence inputName = Name.getText();
                CharSequence inputTag = Tag.getText();
                CharSequence inputDescription = Description.getText();
                CharSequence subBio = SubBio.getText();
                //pass them to auxiliary method
                onButtonPressed(inputName, inputTag, inputDescription, subBio);
            }
        });

        Button uploadImageBtn = rootView.findViewById(R.id.browse_main_img);
        uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImageFromGallery(view);
            }
        });

        String name = prefs.getString(getString(R.string.a_artistname), "");
        String bio = prefs.getString(getString(R.string.a_bio), "");
        String tag = prefs.getString(getString(R.string.a_tag), "");
        String subbio = prefs.getString(getString(R.string.a_subbio), "");

        TextInputLayout NameIL = rootView.findViewById(R.id.workNameInputLayout);
        TextInputLayout TagIL = rootView.findViewById(R.id.taglineInputLayout);
        TextInputLayout DescIL = rootView.findViewById(R.id.bioInputLayout);
        TextInputLayout SubBioIL = rootView.findViewById(R.id.subBioInputLayout);

        NameIL.setHint(name);
        TagIL.setHint(tag);
        DescIL.setHint(bio);
        SubBioIL.setHint(subbio);

        ContextWrapper cw = new ContextWrapper(getContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        loadImageFromStorage(directory.getAbsolutePath());

        return rootView;
    }


    private void loadImageFromStorage(String path)
    {

        try {
            File f=new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            Drawable d = new BitmapDrawable(getResources(), b);
            imgView.setImageDrawable(d);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }


    private void loadImageFromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        pb = getActivity().findViewById(R.id.progressBar);
        pb.clearAnimation();
        pb.setVisibility(View.VISIBLE);
        pb.setIndeterminate(true);

        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                //Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getApplicationContext().getContentResolver().openInputStream(selectedImage));
                Bitmap bitmap =  MediaStore.Images.Media.getBitmap(Objects.requireNonNull(this.getContext()).getContentResolver(), selectedImage);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                Bitmap dummy = BitmapFactory.decodeStream(getActivity().getApplicationContext().getContentResolver().openInputStream(selectedImage));

                // Set the Image in ImageView after decoding the String
                imgView.setImageBitmap(dummy);
                //null bit

                saveToInternalStorage(bitmap);
//
                pb.clearAnimation();
                pb.setVisibility(View.INVISIBLE);
            } else {
                pb.setVisibility(View.INVISIBLE);
                Snackbar.make(getActivity().getWindow().getDecorView().findViewById(android.R.id.content), "No image chosen", Snackbar.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            pb.setVisibility(View.INVISIBLE);
            Snackbar.make(getActivity().getWindow().getDecorView().findViewById(android.R.id.content), "Something went wrong", Snackbar.LENGTH_LONG)
                    .show();
        }

    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath= new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }


    /**
     * Auxiliary method that passed data to the interface method associated with the AdminActivity
     * This is used so AdminActivity can access the data and pass it along
     * @param name
     * @param tag
     * @param description
     */
    private void onButtonPressed(CharSequence name, CharSequence tag, CharSequence description, CharSequence subbio) {
        dataPasser.onArtistDataPass(name, tag, description, subbio);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnArtistDataPass) {
            dataPasser = (OnArtistDataPass) context;
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
    }

}
