package com.example.mogkiosk;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mogkiosk.activities.admin.AdminActivity;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Pattern;

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
    private TextView mNotGoodEnoughEmailTextView;
    private TextView mNotSameUserTextView;
    private TextView mNotSamePassTextView;
    private TextView mNotSameEmailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTitle("Update Credentials");

        final PrivateInfoManager manager = new PrivateInfoManager(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cred);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mUpdateUserTextView = findViewById(R.id.new_update_username);
        mUpdatePassTextView = findViewById(R.id.new_update_password);
        mUpdateEmailView = findViewById(R.id.new_update_email);

        mOldUserTextView =findViewById(R.id.old_update_username);
        mOldPassTextView =findViewById(R.id.old_update_password);
        mOldEmailView =findViewById(R.id.old_update_email);

        mSubmitUpdates = findViewById(R.id.submit_credentials);

        mNotLongEnoughUpPassTextView = findViewById(R.id.pass_length3);
        mNotLongEnoughUpUserTextView = findViewById(R.id.username_length3);
        mNotGoodEnoughEmailTextView = findViewById(R.id.do_not_match3);
        mNotSameUserTextView = findViewById(R.id.old_user_mismatch);
        mNotSamePassTextView = findViewById(R.id.old_pass_mismatch);
        mNotSameEmailTextView = findViewById(R.id.old_email_mismatch);

        mNotLongEnoughUpPassTextView.setVisibility(View.INVISIBLE);
        mNotLongEnoughUpUserTextView.setVisibility(View.INVISIBLE);
        mNotGoodEnoughEmailTextView.setVisibility(View.INVISIBLE);
        mNotSameUserTextView.setVisibility(View.INVISIBLE);
        mNotSamePassTextView.setVisibility(View.INVISIBLE);
        mNotSameEmailTextView.setVisibility(View.INVISIBLE);

        mSubmitUpdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNotLongEnoughUpPassTextView.setVisibility(View.INVISIBLE);
                mNotLongEnoughUpUserTextView.setVisibility(View.INVISIBLE);
                mNotGoodEnoughEmailTextView.setVisibility(View.INVISIBLE);
                mNotSameUserTextView.setVisibility(View.INVISIBLE);
                mNotSamePassTextView.setVisibility(View.INVISIBLE);
                mNotSameEmailTextView.setVisibility(View.INVISIBLE);

                boolean userLength = mUpdateUserTextView.getText().toString().length() < 5;
                boolean passLength = mUpdatePassTextView.getText().toString().length() < 5;
                boolean emailGood = isValidEmail(mUpdateEmailView.getText().toString());

                boolean messagesShowing = true;
                if (userLength && passLength && !emailGood) {
                    mNotLongEnoughUpPassTextView.setVisibility(View.VISIBLE);
                    mNotLongEnoughUpUserTextView.setVisibility(View.VISIBLE);
                    mNotGoodEnoughEmailTextView.setVisibility(View.VISIBLE);
                    System.out.println("Not Long enough anything or good email");
                } else if (userLength && passLength) {
                    mNotLongEnoughUpPassTextView.setVisibility(View.VISIBLE);
                    mNotLongEnoughUpUserTextView.setVisibility(View.VISIBLE);
                    System.out.println("Not Long enough password or user");
                } else if (userLength && !emailGood) {
                    //Passwords do not match
                    mNotLongEnoughUpPassTextView.setVisibility(View.VISIBLE);
                    mNotGoodEnoughEmailTextView.setVisibility(View.VISIBLE);
                    System.out.println("bad user and bad email");
                } else if (passLength && !emailGood) {
                    mNotLongEnoughUpPassTextView.setVisibility(View.VISIBLE);
                    mNotGoodEnoughEmailTextView.setVisibility(View.VISIBLE);
                    System.out.println("bad pass and bad email");
                } else if (userLength) {
                    mNotLongEnoughUpUserTextView.setVisibility(View.VISIBLE);
                    System.out.println("Bad user");
                } else if (passLength) {
                    mNotLongEnoughUpPassTextView.setVisibility(View.VISIBLE);
                    System.out.println("Bad pass");
                } else if(!emailGood) {
                    mNotGoodEnoughEmailTextView.setVisibility(View.VISIBLE);
                    System.out.println("Bad email");
                }
                else messagesShowing = false;
                //Update credentials
                try {
                    switch (manager.validateCredentials(mOldUserTextView.getText().toString(),
                            mOldPassTextView.getText().toString(),
                            mOldEmailView.getText().toString()))
                    {
                        case 0:
                            if(! messagesShowing)
                            {
                             manager.updateCredentials(mUpdateUserTextView.getText().toString(),
                                     mUpdatePassTextView.getText().toString(),
                                     mUpdateEmailView.getText().toString());
                             Intent intent = new Intent(UpdateCredActivity.this, AdminActivity.class);
                             startActivity(intent);
                            }
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
                        case 7:
                            mNotSameUserTextView.setVisibility(View.VISIBLE);
                            mNotSamePassTextView.setVisibility(View.VISIBLE);
                            mNotSameEmailTextView.setVisibility(View.VISIBLE);
                            System.out.println("NotSame anything");
                    }
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

            }
        });

    }
    public static boolean isValidEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}

