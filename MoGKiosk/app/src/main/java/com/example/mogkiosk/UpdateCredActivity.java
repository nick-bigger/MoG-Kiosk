package com.example.mogkiosk;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import com.example.mogkiosk.activities.admin.AdminActivity;
import com.example.mogkiosk.activities.changepass.ChangePassActivity;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class UpdateCredActivity extends AppCompatActivity {
    private TextInputEditText mUpdatePassTextView;
    private TextInputEditText mUpdateEmailView;
    private TextInputEditText mUpdateUserTextView;

    private TextInputEditText mOldPassTextView;
    private TextInputEditText mOldEmailView;
    private TextInputEditText mOldUserTextView;

    private Button mSubmitUpdates;

    private TextView mNotLongEnoughUpPassTextView;
    private TextView mNotLongEnoughUpUserTextView;
    private TextView mNotLongEnoughEmailTextView;
    private TextView mNotSameUserTextView;
    private TextView mNotSamePassTextView;
    private TextView mNotSameEmailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final PrivateInfoManager manager = new PrivateInfoManager(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cred);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mUpdateUserTextView = findViewById(R.id.update_username);
        mUpdatePassTextView = findViewById(R.id.update_password);
        mUpdateEmailView = findViewById(R.id.update_email);
        mSubmitUpdates = findViewById(R.id.submit2);

//        mNotLongEnoughUpPassTextView = findViewById(R.id.pass_length);
//        mNotLongEnoughUpUserTextView = findViewById(R.id.username_length);
//        mNotLongEnoughEmailTextView = findViewById(R.id.do_not_match);
//        mNotSameUserTextView = findViewById(R.id.pass_length);
//        mNotSamePassTextView = findViewById(R.id.pass_length);
//        mNotSameEmailTextView = findViewById(R.id.pass_length);

//        mNotLongEnoughPassTextView.setVisibility(View.INVISIBLE);
//        mNotLongEnoughUserTextView.setVisibility(View.INVISIBLE);
//        mPassDontMatchTextView.setVisibility(View.INVISIBLE);
//        mNotSameUserTextView = setVisibility(View.INVISIBLE);
//        mNotSamePassTextView = setVisibility(View.INVISIBLE);
//        mNotSameEmailTextView = setVisibility(View.INVISIBLE);

        mSubmitUpdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean userLength = mUpdateUserTextView.getText().toString().length() < 5;
                boolean passLength = mUpdatePassTextView.getText().toString().length() < 5;
                boolean emailGood = isValidEmail(mUpdateEmailView.getText().toString());

                if (!userLength && !passLength && !emailGood) {
                    mNotLongEnoughUpPassTextView.setVisibility(View.VISIBLE);
                    mNotLongEnoughUpUserTextView.setVisibility(View.VISIBLE);
                    mNotLongEnoughEmailTextView.setVisibility(View.VISIBLE);
                    System.out.println("Not Long enough anything or good email");
                } else if (!userLength && !passLength) {
                    mNotLongEnoughUpPassTextView.setVisibility(View.VISIBLE);
                    mNotLongEnoughUpUserTextView.setVisibility(View.VISIBLE);
                    System.out.println("Not Long enough password or user");
                } else if (!userLength && !emailGood) {
                    //Passwords do not match
                    mNotLongEnoughUpPassTextView.setVisibility(View.VISIBLE);
                    mNotLongEnoughEmailTextView.setVisibility(View.VISIBLE);
                    System.out.println("bad user and bad email");
                } else if (!passLength && !emailGood) {
                    mNotLongEnoughUpPassTextView.setVisibility(View.VISIBLE);
                    mNotLongEnoughEmailTextView.setVisibility(View.VISIBLE);
                    System.out.println("bad pass and bad email");
                } else if (!userLength) {
                    mNotLongEnoughUpUserTextView.setVisibility(View.VISIBLE);
                    System.out.println("Bad user");
                } else if (!passLength) {
                    mNotLongEnoughUpPassTextView.setVisibility(View.VISIBLE);
                    System.out.println("Bad pass");
                } else if (!emailGood) {
                    mNotLongEnoughEmailTextView.setVisibility(View.VISIBLE);
                    System.out.println("Bad email");
                } else {
                    //Update credentials
                    try {
                        switch (manager.updateCredentials(mOldUserTextView.getText().toString(),
                                mUpdateUserTextView.getText().toString(),
                                mOldPassTextView.getText().toString(),
                                mUpdatePassTextView.getText().toString(),
                                mOldEmailView.getText().toString(),
                                mUpdateEmailView.getText().toString())) {
                            case 0:
                                break;
                            case 1:
                                mNotSameUserTextView.setVisibility(View.VISIBLE);
                                System.out.println("NotSame user");
                                break;
                            case 2:
                                mNotSamePassTextView.setVisibility(View.VISIBLE);
                                System.out.println("NotSame pass");
                                break;
                            case 3:
                                mNotSameEmailTextView.setVisibility(View.VISIBLE);
                                System.out.println("NotSame email");
                                break;
                            case 4:
                                mNotSameUserTextView.setVisibility(View.VISIBLE);
                                mNotSamePassTextView.setVisibility(View.VISIBLE);
                                System.out.println("NotSame user and pass");
                                break;
                            case 5:
                                mNotSameUserTextView.setVisibility(View.VISIBLE);
                                mNotSameEmailTextView.setVisibility(View.VISIBLE);
                                System.out.println("NotSame user and email");
                                break;
                            case 6:
                                mNotSamePassTextView.setVisibility(View.VISIBLE);
                                mNotSameEmailTextView.setVisibility(View.VISIBLE);
                                System.out.println("NotSame pass and email");
                                break;

                        }
                    } catch (InvalidKeySpecException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    //Go to Admin side

                }
            }
        });

    }
    public static boolean isValidEmail(String email)
    {
        boolean result = true;

        try{
            InternetAddress emailAddress = new InternetAddress(email, true);
            emailAddress.validate();
        } catch (AddressException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }
}

