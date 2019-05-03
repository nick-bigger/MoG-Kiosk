package com.example.mogkiosk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mogkiosk.activities.admin.AdminActivity;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Pattern;

public class UpdateCredActivity extends AppCompatActivity {
    private EditText mUpdatePassTextView;
    private EditText mUpdateEmailView;
    private EditText mUpdateUserTextView;
    private EditText mRetypedPassView; //NEW ENTRY

    private EditText mOldPassTextView;
    private EditText mOldUserTextView;

    private TextView mNotLongEnoughUpPassTextView;
    private TextView mNotLongEnoughUpUserTextView;
    private TextView mNotGoodEnoughEmailTextView;
    private TextView mRetypedPassNotSameTextView;
    private TextView mNotSameUserTextView;
    private TextView mNotSamePassTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final PrivateInfoManager manager = new PrivateInfoManager(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cred);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().hide();

        mUpdateUserTextView = findViewById(R.id.new_update_username);
        mUpdatePassTextView = findViewById(R.id.new_update_password);
        mUpdateEmailView = findViewById(R.id.new_update_email);

        mOldUserTextView =findViewById(R.id.old_update_username);
        mOldPassTextView =findViewById(R.id.old_update_password);
        //mRetypedPassView =findViewById(R.id.old_update_email); // NEW ENTRY

        Button mSubmitUpdates = findViewById(R.id.submit_credentials);

        mNotLongEnoughUpPassTextView = findViewById(R.id.pass_length3);
        mNotLongEnoughUpUserTextView = findViewById(R.id.username_length3);
        mNotGoodEnoughEmailTextView = findViewById(R.id.do_not_match3);
        mNotSameUserTextView = findViewById(R.id.old_user_mismatch);
        mNotSamePassTextView = findViewById(R.id.old_pass_mismatch);
        //mRetypedPassNotSameTextView = findViewById(R.id.old_email_mismatch); // NEW MESSAGE

        mNotLongEnoughUpPassTextView.setVisibility(View.INVISIBLE);
        mNotLongEnoughUpUserTextView.setVisibility(View.INVISIBLE);
        mNotGoodEnoughEmailTextView.setVisibility(View.INVISIBLE);
        mNotSameUserTextView.setVisibility(View.INVISIBLE);
        mNotSamePassTextView.setVisibility(View.INVISIBLE);
        //mRetypedPassNotSameTextView.setVisibility(View.INVISIBLE); //NEW MESSAGE

        mSubmitUpdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNotLongEnoughUpPassTextView.setVisibility(View.INVISIBLE);
                mNotLongEnoughUpUserTextView.setVisibility(View.INVISIBLE);
                mNotGoodEnoughEmailTextView.setVisibility(View.INVISIBLE);
                mNotSameUserTextView.setVisibility(View.INVISIBLE);
                mNotSamePassTextView.setVisibility(View.INVISIBLE);
                //mRetypedPassNotSameTextView.setVisibility(View.INVISIBLE); // NEW MESSAGE

                boolean userLength = mUpdateUserTextView.getText().toString().length() < 5;
                boolean passLength = mUpdatePassTextView.getText().toString().length() < 5;
                boolean emailGood = isValidEmail(mUpdateEmailView.getText().toString());
                boolean passMatch = mUpdatePassTextView.toString().equals(mRetypedPassView.toString());

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
                    //Passwords do not match?
                    if(!passMatch) mRetypedPassNotSameTextView.setVisibility(View.VISIBLE);
                    mNotLongEnoughUpPassTextView.setVisibility(View.VISIBLE);
                    mNotGoodEnoughEmailTextView.setVisibility(View.VISIBLE);
                    System.out.println("bad user and bad email");
                } else if (passLength && !emailGood) {
                    mNotLongEnoughUpPassTextView.setVisibility(View.VISIBLE);
                    mNotGoodEnoughEmailTextView.setVisibility(View.VISIBLE);
                    System.out.println("bad pass and bad email");
                } else if (userLength) {
                    if(!passMatch) mRetypedPassNotSameTextView.setVisibility(View.VISIBLE);
                    mNotLongEnoughUpUserTextView.setVisibility(View.VISIBLE);
                    System.out.println("Bad user");
                } else if (passLength) {
                    mNotLongEnoughUpPassTextView.setVisibility(View.VISIBLE);
                    System.out.println("Bad pass");
                } else if(!emailGood) {
                    if(!passMatch) mRetypedPassNotSameTextView.setVisibility(View.VISIBLE);
                    mNotGoodEnoughEmailTextView.setVisibility(View.VISIBLE);
                    System.out.println("Bad email");
                }
                else messagesShowing = false;
                //Update credentials
                try {
                    switch (manager.validateCredentials(mOldUserTextView.getText().toString(),
                            mOldPassTextView.getText().toString()))
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
                            mNotSameUserTextView.setVisibility(View.VISIBLE);
                            mNotSamePassTextView.setVisibility(View.VISIBLE);
                            System.out.println("NotSame user and pass");
                            break;
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

