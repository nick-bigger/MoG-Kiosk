
package com.example.mogkiosk.activities.changepass;

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

import com.example.mogkiosk.LoginActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final PrivateInfoManager manager = new PrivateInfoManager(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNewPassTextView = findViewById(R.id.password);
        mRetypePassTextView = findViewById(R.id.retype_password);
        mNewUserTextView = findViewById(R.id.username);
        mSubmitChanges = findViewById(R.id.submit);

        mSubmitChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (mNewUserTextView.getText().toString().length() < 5)
                {
                    //Do a you need at least 5 character message
                    System.out.println("Not Long enough username");
                }

                else if (mNewPassTextView.getText().toString().length() < 5)
                {
                    //Do a you need at least 5 character message
                    System.out.println("Not Long enough password");
                }
                else if (!mNewPassTextView.getText().toString().equals(mRetypePassTextView.getText().toString()))
                {
                    //Passwords do not match
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
