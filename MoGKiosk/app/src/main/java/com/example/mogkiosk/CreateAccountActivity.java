package com.example.mogkiosk;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Pattern;

public class CreateAccountActivity extends AppCompatActivity {
    private TextInputLayout usrInput;
    private TextInputLayout passInput;
    private TextInputLayout rePassInput;
    private TextInputLayout emaInput;

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

        mUsernameEditText = findViewById(R.id.username);
        mPasswordEditText = findViewById(R.id.password);
        mRetypedPasswordEditText = findViewById(R.id.retype_password);
        mEmailEditText = findViewById(R.id.new_update_email2);

        usrInput = findViewById(R.id.usernameInputLayout);
        passInput = findViewById(R.id.passInputLayout);
        rePassInput = findViewById(R.id.repassInputLayout);
        emaInput = findViewById(R.id.emailInputLayout);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean userLengthBad = mUsernameEditText.getText().toString().length() < 5;
                boolean passLengthBad = mPasswordEditText.getText().toString().length() < 5;
                boolean validEmail = isValidEmail(mEmailEditText.getText().toString());
                boolean passMatch = mPasswordEditText.getText().toString().equals(mRetypedPasswordEditText.getText().toString());

                if(userLengthBad && passLengthBad && !validEmail)
                {
                    usrInput.setError(getString(R.string.error_usn));
                    passInput.setError(getString(R.string.error_pwd));
                    emaInput.setError(getString(R.string.error_ema));
                    System.out.println("Bad everything");
                }
                else if (userLengthBad && passLengthBad)
                {
                    usrInput.setError(getString(R.string.error_usn));
                    passInput.setError(getString(R.string.error_pwd));
                    System.out.println("Bad user and pass");
                }
                else if(userLengthBad && !validEmail)
                {
                    if(!passMatch) rePassInput.setError(getString(R.string.error_rePwd));
                    usrInput.setError(getString(R.string.error_usn));
                    emaInput.setError(getString(R.string.error_ema));
                    System.out.println("Bad user and email");
                }
                else if(passLengthBad && !validEmail)
                {
                    passInput.setError(getString(R.string.error_pwd));
                    emaInput.setError(getString(R.string.error_ema));
                    System.out.println("Bad pass and email");
                }
                else if(userLengthBad)
                {
                    if(!passMatch) rePassInput.setError(getString(R.string.error_rePwd));
                    usrInput.setError(getString(R.string.error_usn));
                    System.out.println("Bad user");
                }
                else if(passLengthBad)
                {
                    passInput.setError(getString(R.string.error_pwd));
                    System.out.println("Bad pass");
                }
                else if(!validEmail)
                {
                    if(!passMatch) rePassInput.setError(getString(R.string.error_rePwd));
                    emaInput.setError(getString(R.string.error_ema));
                    System.out.println("Bad email");
                }
                else if(!passMatch)
                {
                    passInput.setError(getString(R.string.error_pwd));
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
