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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mogkiosk.R;

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
    private static final int RESULT_LOAD_IMG = 1;
    private static final int RESULT_LOAD_IMG1 = 2;
    private static final int RESULT_LOAD_IMG2 = 3;
    private static final int RESULT_LOAD_IMG3 = 4;


    private SharedPreferences prefs;

    private TextView artist;
    private TextView piecedate;
    private TextView title;
    private TextView date;
    private TextView collection;
    private TextView medium;
    private TextView dimensions;
    private TextView photoBy;
    private ImageView mainImage;
    private int viewId;

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
        View rootView = inflater.inflate(R.layout.frag_work_admin, container, false);

        artist = rootView.findViewById(R.id.artist);
        date = rootView.findViewById(R.id.label_date);
        medium = rootView.findViewById(R.id.medium);
        dimensions = rootView.findViewById(R.id.dimensions);
        collection = rootView.findViewById(R.id.collection_info);
        mainImage = rootView.findViewById(R.id.mainImage);
        title = rootView.findViewById(R.id.title);
        piecedate = rootView.findViewById(R.id.date);
        Button submit = rootView.findViewById(R.id.submitBtn);
        photoBy = rootView.findViewById(R.id.photo_credit);

        Button mainImageBtn = rootView.findViewById(R.id.browse_main_img);
        Button related1ImageBtn = rootView.findViewById(R.id.browse_main_img2);
        Button related2ImageBtn = rootView.findViewById(R.id.browse_main_img3);
        Button related3ImageBtn = rootView.findViewById(R.id.browse_main_img4);



        mainImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    viewId = R.id.mainImage;
                    loadImageFromGalleryMain(view);

            }
        });

        related1ImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewId = R.id.related_work_1;
                loadFirstImage(view);
            }
        });

        related2ImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewId = R.id.related_work_2;
                loadSecondImage(view);
            }
        });

        related3ImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewId = R.id.related_work_3;
                loadThirdImage(view);
            }
        });

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
                CharSequence photo = photoBy.getText();
                //pass them to auxiliary method
                onButtonPressed(artistInput, piecedateInput, titleInput, dateInput, collectionInput,
                        dimensionsInput, mediumInput, photo);
            }
        });

        String title = prefs.getString(getString(R.string.a_title), "");
        String tempDate = prefs.getString(getString(R.string.a_date), "");
        String tempCollection = prefs.getString(getString(R.string.a_collection), "");
        String medium = prefs.getString(getString(R.string.a_medium), "");
        String artist = prefs.getString(getString(R.string.a_workartist), "");
        String pieceDate = prefs.getString(getString(R.string.a_piecedate), "");
        String dim= prefs.getString(getString(R.string.a_dimension),"");
        String photo = prefs.getString(getString(R.string.a_photo), "");

        TextInputLayout TitleIL = rootView.findViewById(R.id.workTitleInputLayout);
        TextInputLayout DateIL = rootView.findViewById(R.id.workDateInputLayout);
        TextInputLayout ArtistIL = rootView.findViewById(R.id.artistNameInputLayout);
        TextInputLayout LocIL = rootView.findViewById(R.id.workLabelInputLayout);
        TextInputLayout MedIL = rootView.findViewById(R.id.workMedInputLayout);
        TextInputLayout DimIL = rootView.findViewById(R.id.workDimInputLayout);
        TextInputLayout CollIL = rootView.findViewById(R.id.workCollecInputLayout);
        TextInputLayout CredIL = rootView.findViewById(R.id.photoCredInputLayout);

        TitleIL.setHint(title);
        LocIL.setHint(tempDate);
        ArtistIL.setHint(artist);
        DateIL.setHint(pieceDate);
        MedIL.setHint(medium);
        DimIL.setHint(dim);
        CollIL.setHint(tempCollection);
        CredIL.setHint(photo);


        ContextWrapper cw = new ContextWrapper(getContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        loadImageFromStorage(directory.getAbsolutePath(), "main.png");


        return rootView;
    }

    private void loadImageFromStorage(String path, String imageName)
    {
        if(imageName == "main.png") {

            try {
                File f=new File(path, "main.png");
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                Drawable d = new BitmapDrawable(getResources(), b);
                mainImage.setImageDrawable(d);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }

    }


    /**
     * Auxiliary method that passed data to the interface method associated with the AdminActivity
     * This is used so AdminActivity can access the data and pass it along
     * @param artist
     * @param piecedate
     * @param title
     * @param date
     * @param collection
     * @param dimensions
     * @param medium
     * @param photo
     */
    private void onButtonPressed(CharSequence artist, CharSequence piecedate, CharSequence title,
                                 CharSequence date, CharSequence collection, CharSequence dimensions, CharSequence medium, CharSequence photo) {
        dataPasser.onWorkDataPass(artist, piecedate, title,
                date, collection, dimensions, medium, photo);
    }

    private void loadImageFromGalleryMain(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    private void loadFirstImage(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG1);
    }


    private void loadSecondImage(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG2);
    }


    private void loadThirdImage(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG3);
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
                Bitmap bitmap =  MediaStore.Images.Media.getBitmap(Objects.requireNonNull(this.getContext()).getContentResolver(), selectedImage);
                Bitmap placeholder = BitmapFactory.decodeStream(getActivity().getApplicationContext().getContentResolver().openInputStream(selectedImage));

                // Set the Image in ImageView after decoding the String
                ImageView imgView = getView().findViewById(R.id.mainImage);
                imgView.setImageBitmap(placeholder);
                //null bit
                saveToInternalStorage(bitmap, "main.png");
            } else if (requestCode == RESULT_LOAD_IMG1 && resultCode == RESULT_OK) {
                // Get the Image from data
                Uri selectedImage = data.getData();
                Bitmap bitmap =  MediaStore.Images.Media.getBitmap(Objects.requireNonNull(this.getContext()).getContentResolver(), selectedImage);
                Bitmap placeholder = BitmapFactory.decodeStream(getActivity().getApplicationContext().getContentResolver().openInputStream(selectedImage));

                // Set the Image in ImageView after decoding the String
                ImageView imgView = getView().findViewById(R.id.related_work_1);
                imgView.setImageBitmap(placeholder);
                //null bit
                saveToInternalStorage(bitmap, "art1.png");
            } else if(requestCode == RESULT_LOAD_IMG2 && resultCode == RESULT_OK) {
                // Get the Image from data
                assert data != null;
                Uri selectedImage = data.getData();
                Bitmap bitmap =  MediaStore.Images.Media.getBitmap(Objects.requireNonNull(this.getContext()).getContentResolver(), selectedImage);
                Bitmap placeholder = BitmapFactory.decodeStream(getActivity().getApplicationContext().getContentResolver().openInputStream(selectedImage));

                // Set the Image in ImageView after decoding the String
                ImageView imgView = Objects.requireNonNull(getView()).findViewById(R.id.related_work_2);
                imgView.setImageBitmap(placeholder);
                //null bit
                saveToInternalStorage(bitmap, "art2.png");
            } else if(requestCode == RESULT_LOAD_IMG3 && resultCode == RESULT_OK) {
                // Get the Image from data
                assert data != null;
                Uri selectedImage = data.getData();
                Bitmap bitmap =  MediaStore.Images.Media.getBitmap(Objects.requireNonNull(this.getContext()).getContentResolver(), selectedImage);
                Bitmap placeholder = BitmapFactory.decodeStream(getActivity().getApplicationContext().getContentResolver().openInputStream(selectedImage));

                // Set the Image in ImageView after decoding the String
                ImageView imgView = Objects.requireNonNull(getView()).findViewById(R.id.related_work_3);
                imgView.setImageBitmap(placeholder);
                //null bit
                saveToInternalStorage(bitmap, "art3.png");
            } else {
                Snackbar.make(getActivity().getWindow().getDecorView().findViewById(android.R.id.content), "No image chosen", Snackbar.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Snackbar.make(getActivity().getWindow().getDecorView().findViewById(android.R.id.content), "Something went wrong", Snackbar.LENGTH_LONG)
                    .show();
        }

    }

    private String saveToInternalStorage(Bitmap bitmapImage, String pathName){
        ContextWrapper cw = new ContextWrapper(getContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath= new File(directory, pathName);

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

    /**
     * An interface that communicates with a method defined in the AdminActivity, the definition asks for arguments
     * pertinent to the EditText layout variables in the ArtistLayout xml.
     */
    public interface OnWorkDataPass {
        //Interface method declaration to pass data to Admin
        void onWorkDataPass(CharSequence artist, CharSequence piecedate, CharSequence title,
                            CharSequence date, CharSequence collection, CharSequence dimensions, CharSequence medium, CharSequence photo);
    }
}
