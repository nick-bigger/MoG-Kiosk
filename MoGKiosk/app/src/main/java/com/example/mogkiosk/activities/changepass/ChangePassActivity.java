
package com.example.mogkiosk.activities.changepass;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mogkiosk.PrivateInfoManager;
import com.example.mogkiosk.R;
import com.example.mogkiosk.activities.admin.AdminActivity;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class ChangePassActivity extends AppCompatActivity {
    private TextInputEditText mNewPassTextView;
    private TextInputEditText mRetypePassTextView;
    private TextInputEditText mNewUserTextView;
    private Button mSubmitChanges;

    private TextView mNotLongEnoughPassTextView;
    private TextView mNotLongEnoughUserTextView;
    private TextView mPassDontMatchTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTitle("Change Password");

        final PrivateInfoManager manager = new PrivateInfoManager(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNewPassTextView = findViewById(R.id.password);
        mRetypePassTextView = findViewById(R.id.retype_password);
        mNewUserTextView = findViewById(R.id.username);
        mSubmitChanges = findViewById(R.id.submit);

        mNotLongEnoughPassTextView = findViewById(R.id.pass_length);
        mNotLongEnoughUserTextView = findViewById(R.id.username_length);
        mPassDontMatchTextView = findViewById(R.id.do_not_match);

        mNotLongEnoughPassTextView.setVisibility(View.INVISIBLE);
        mNotLongEnoughUserTextView.setVisibility(View.INVISIBLE);
        mPassDontMatchTextView.setVisibility(View.INVISIBLE);

        mSubmitChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mNotLongEnoughPassTextView.setVisibility(View.INVISIBLE);
                mNotLongEnoughUserTextView.setVisibility(View.INVISIBLE);
                mPassDontMatchTextView.setVisibility(View.INVISIBLE);

                boolean userLength = mNewUserTextView.getText().toString().length() < 5;
                boolean passLength = mNewPassTextView.getText().toString().length() < 5;
                boolean retypeGood = mRetypePassTextView.getText().toString().equals(mNewPassTextView);

                if(userLength && passLength && !retypeGood)
                {
                    mNotLongEnoughUserTextView.setVisibility(View.VISIBLE);
                    mNotLongEnoughPassTextView.setVisibility(View.VISIBLE);
                    mPassDontMatchTextView.setVisibility(View.VISIBLE);
                    System.out.println("Not Long enough user and password and does not match");
                }
                else if (userLength && passLength)
                {
                    mNotLongEnoughUserTextView.setVisibility(View.VISIBLE);
                    mNotLongEnoughPassTextView.setVisibility(View.VISIBLE);
                    System.out.println("Not Long enough username and pass");
                }
                else if(userLength && !retypeGood)
                {
                    mNotLongEnoughUserTextView.setVisibility(View.VISIBLE);
                    mPassDontMatchTextView.setVisibility(View.VISIBLE);
                    System.out.println("Not Long enough username and pass dont match");
                }
                else if(passLength && ! retypeGood)
                {
                    mNotLongEnoughPassTextView.setVisibility(View.VISIBLE);
                    mPassDontMatchTextView.setVisibility(View.VISIBLE);
                    System.out.println("Not Long enough password and does not match");

                }
                else if (userLength)
                {
                    //Do a you need at least 5 character message
                    mNotLongEnoughUserTextView.setVisibility(View.VISIBLE);
                    System.out.println("Not Long enough username");
                }
                else if (passLength)
                {
                    //Do a you need at least 5 character message
                    mNotLongEnoughPassTextView.setVisibility(View.VISIBLE);
                    System.out.println("Not Long enough password");
                }
                else if (! retypeGood)
                {
                    //Passwords do not match
                    mPassDontMatchTextView.setVisibility(View.VISIBLE);
                    System.out.println("Do not match");
                }
                else {
                    //Change credentials
                    try {
                        manager.changeCredentials(mNewUserTextView.getText().toString(), mNewPassTextView.getText().toString());
                    } catch (InvalidKeySpecException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    //Go to Admin side
                        Intent intent = new Intent(ChangePassActivity.this, AdminActivity.class);
                        startActivity(intent);
                    }
            }
        });




    }

}
