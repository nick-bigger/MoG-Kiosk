
package com.example.mogkiosk.activities.changepass;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mogkiosk.PrivateInfoManager;
import com.example.mogkiosk.R;
import com.example.mogkiosk.activities.admin.AdminActivity;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class ChangePassActivity extends AppCompatActivity {
    private EditText mNewPassTextView;
    private EditText mRetypePassTextView;
    private EditText mNewUserTextView;

    private TextInputLayout usrInput;
    private TextInputLayout passInput;
    private TextInputLayout rePassInput;

    private Drawable lockIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final PrivateInfoManager manager = new PrivateInfoManager(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().hide();

        mNewPassTextView = findViewById(R.id.password);
        mRetypePassTextView = findViewById(R.id.retype_password);
        mNewUserTextView = findViewById(R.id.username);

        usrInput = findViewById(R.id.usernameInputLayout);
        passInput = findViewById(R.id.passInputLayout);
        rePassInput = findViewById(R.id.retypePassInputLayout);

        Button mSubmitChanges = findViewById(R.id.submit);

        mSubmitChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                boolean userLength = mNewUserTextView.getText().toString().length() < 5;
                boolean passLength = mNewPassTextView.getText().toString().length() < 5;
                boolean retypeGood = mRetypePassTextView.getText().toString().equals(mNewPassTextView.getText().toString());

                if (userLength) {
                    usrInput.setError(getString(R.string.error_usn));
                    usrInput.setHint("Invalid Username");

                    //Do a you need at least 5 character message
                    System.out.println("Not Long enough username");
                } else {
                    usrInput.setError(null);
                    usrInput.setHint("New Username");
                }

                if (passLength) {
                    passInput.setHint("Invalid Password");
                    passInput.setError(getString(R.string.error_pwd));

                    //Do a you need at least 5 character message
                    System.out.println("Not Long enough password");
                } else {
                    passInput.setError(null);
                    passInput.setHint("New Password");
                }

                if (!retypeGood) {
                    rePassInput.setHint("Retype Mismatch");
                    rePassInput.setError("Passwords do not match.");

                    //Passwords do not match
                    System.out.println("Do not match");
                } else {
                    rePassInput.setError(null);
                    rePassInput.setHint("Retype New Password");
                }

                if (!userLength && !passLength && retypeGood) {
                    usrInput.setErrorEnabled(false);
                    passInput.setErrorEnabled(false);
                    rePassInput.setErrorEnabled(false);

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
