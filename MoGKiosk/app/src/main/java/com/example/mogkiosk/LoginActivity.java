package com.example.mogkiosk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Login interface for employees
 * Currently contains secure password storage but no indications of incorrect username or password to the user
 * Will add change password feature
 */
public class LoginActivity extends AppCompatActivity {
    private EditText mNameEditText; //use m for global variables because history
    private EditText mPassEditText;
    private Button mLoginButton;
    private TextView mBadLoginTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final PrivateInfoManager manager = new PrivateInfoManager(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        //assigning variables from layout
        mNameEditText = findViewById(R.id.etName);
        mPassEditText = findViewById(R.id.etPassword);
        mLoginButton = findViewById(R.id.btnLogin);
        mBadLoginTextView = findViewById(R.id.incorrectLogin);

        mBadLoginTextView.setVisibility(View.INVISIBLE);

        //setting the login button to validate
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBadLoginTextView.setVisibility(View.INVISIBLE);

                try {
<<<<<<< HEAD
                   if (validate(mNameEditText.getText().toString(), mPassEditText.getText().toString())) {
                       mBadLoginTextView.setVisibility(View.INVISIBLE);
                       Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                       startActivity(intent);

                   }
                   else{
                        mBadLoginTextView.setVisibility(View.VISIBLE);
                   }
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
=======
                    switch (manager.validateLogin(mNameEditText.getText().toString(), mPassEditText.getText().toString())) {
                        case -1:
                            mBadLoginTextView.setVisibility(View.VISIBLE);
                            System.out.println("This should lead to the error message");
                            break;
                        case 0:
                            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                            startActivity(intent);
                            System.out.println("This should lead to the admin page");
                            break;
                        case 1:
                            System.out.println("This should lead to the change password page");
                    }
                } catch (InvalidKeySpecException e1) {
                    e1.printStackTrace();
                } catch (NoSuchAlgorithmException e1) {
                    e1.printStackTrace();
>>>>>>> master
                }
            }
        });
    }
}