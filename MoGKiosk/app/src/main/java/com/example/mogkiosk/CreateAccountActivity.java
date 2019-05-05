package com.example.mogkiosk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mogkiosk.activities.admin.AdminActivity;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Pattern;

public class CreateAccountActivity extends AppCompatActivity {
    private TextView mValidEmailMessageTextView;
    private TextView mNotMAtchingPassTextView;
    private TextView mPassNotLongEnoughTextView;
    private TextView mUsernameNotLongEnoughTextView;

    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private EditText mRetypedPasswordEditText;
    private EditText mEmailEditText;

    private Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        final PrivateInfoManager manager = new PrivateInfoManager(this);

        mSubmitButton = findViewById(R.id.submit);

        mValidEmailMessageTextView = findViewById(R.id.do_not_match2);
        mNotMAtchingPassTextView = findViewById(R.id.do_not_match);
        mPassNotLongEnoughTextView = findViewById(R.id.pass_length);
        mUsernameNotLongEnoughTextView = findViewById(R.id.username_length);

        mUsernameEditText = findViewById(R.id.username);
        mPasswordEditText = findViewById(R.id.password);
        mRetypedPasswordEditText = findViewById(R.id.retype_password);
        mEmailEditText = findViewById(R.id.new_update_email2);

        mValidEmailMessageTextView.setVisibility(View.INVISIBLE);
        mNotMAtchingPassTextView.setVisibility(View.INVISIBLE);
        mPassNotLongEnoughTextView.setVisibility(View.INVISIBLE);
        mUsernameNotLongEnoughTextView.setVisibility(View.INVISIBLE);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mValidEmailMessageTextView.setVisibility(View.INVISIBLE);
                mNotMAtchingPassTextView.setVisibility(View.INVISIBLE);
                mPassNotLongEnoughTextView.setVisibility(View.INVISIBLE);
                mUsernameNotLongEnoughTextView.setVisibility(View.INVISIBLE);

                boolean userLengthBad = mUsernameEditText.getText().toString().length() < 5;
                boolean passLengthBad = mPasswordEditText.getText().toString().length() < 5;
                boolean validEmail = isValidEmail(mEmailEditText.getText().toString());
                boolean passMatch = mPasswordEditText.getText().toString().equals(mRetypedPasswordEditText.getText().toString());

                if(userLengthBad && passLengthBad && !validEmail)
                {
                    mValidEmailMessageTextView.setVisibility(View.VISIBLE);
                    mPassNotLongEnoughTextView.setVisibility(View.VISIBLE);
                    mUsernameNotLongEnoughTextView.setVisibility(View.VISIBLE);
                    System.out.println("Bad everything");
                }
                else if (userLengthBad && passLengthBad)
                {
                    mPassNotLongEnoughTextView.setVisibility(View.VISIBLE);
                    mUsernameNotLongEnoughTextView.setVisibility(View.VISIBLE);
                    System.out.println("Bad user and pass");
                }
                else if(userLengthBad && !validEmail)
                {
                    if(!passMatch) mNotMAtchingPassTextView.setVisibility(View.VISIBLE);
                    mUsernameNotLongEnoughTextView.setVisibility(View.VISIBLE);
                    mValidEmailMessageTextView.setVisibility(View.VISIBLE);
                    System.out.println("Bad user and email");
                }
                else if(passLengthBad && !validEmail)
                {
                    mValidEmailMessageTextView.setVisibility(View.VISIBLE);
                    mPassNotLongEnoughTextView.setVisibility(View.VISIBLE);
                    System.out.println("Bad pass and email");
                }
                else if(userLengthBad)
                {
                    if(!passMatch) mNotMAtchingPassTextView.setVisibility(View.VISIBLE);
                    mUsernameNotLongEnoughTextView.setVisibility(View.VISIBLE);
                    System.out.println("Bad user");
                }
                else if(passLengthBad)
                {
                    mPassNotLongEnoughTextView.setVisibility(View.VISIBLE);
                    System.out.println("Bad pass");
                }
                else if(!validEmail)
                {
                    if(!passMatch) mNotMAtchingPassTextView.setVisibility(View.VISIBLE);
                    mValidEmailMessageTextView.setVisibility(View.VISIBLE);
                    System.out.println("Bad email");
                }
                else if(!passMatch)
                {
                    mNotMAtchingPassTextView.setVisibility(View.VISIBLE);
                    System.out.println("Bad match");
                }
                else
                {
                    System.out.println("Bad NOTHING");
                    try {
                        manager.writeInitialInfo(mUsernameEditText.getText().toString(),
                                mPasswordEditText.getText().toString(),
                                mEmailEditText.getText().toString());
                        manager.printContents();

                        Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InvalidKeySpecException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }


    private static boolean isValidEmail(String email)
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
