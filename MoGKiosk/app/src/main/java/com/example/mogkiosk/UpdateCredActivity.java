package com.example.mogkiosk;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

    private TextInputLayout newUsrInput;
    private TextInputLayout newPassInput;
    private TextInputLayout newRePassInput;
    private TextInputLayout newEmailInput;

    private TextInputLayout oldUsrInput;
    private TextInputLayout oldPassInput;
    private TextInputLayout oldEmailInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final PrivateInfoManager manager = new PrivateInfoManager(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cred);


        mUpdateUserTextView = findViewById(R.id.new_update_username);
        mUpdatePassTextView = findViewById(R.id.new_update_password);
        mUpdateEmailView = findViewById(R.id.new_update_email);
        mOldUserTextView =findViewById(R.id.old_update_username);
        mOldPassTextView =findViewById(R.id.old_update_password);
        mRetypedPassView =findViewById(R.id.retype_password);

        Button mSubmitUpdates = findViewById(R.id.submit_credentials);

        newUsrInput = findViewById(R.id.newUsernameInput);
        newPassInput = findViewById(R.id.newPasswordInput);
        newRePassInput = findViewById(R.id.newRePassInputLayout);
        newEmailInput = findViewById(R.id.newEmailInput);

        oldUsrInput = findViewById(R.id.oldUsernameInput);
        oldPassInput = findViewById(R.id.oldPasswordInput);
        oldEmailInput = findViewById(R.id.oldEmailInput);

        mSubmitUpdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean userLength = mUpdateUserTextView.getText().toString().length() < 5;
                boolean passLength = mUpdatePassTextView.getText().toString().length() < 5;
                boolean emailGood = isValidEmail(mUpdateEmailView.getText().toString());
                boolean passMatch = mUpdatePassTextView.toString().equals(mRetypedPassView.toString());
                boolean messagesShowing = true;
                if (userLength) {
                    newUsrInput.setError(getString(R.string.error_usn));
                    newUsrInput.setHint("Invalid Username");
                } else {
                    newUsrInput.setError(null);
                    newUsrInput.setHint("New Username");
                }
                if (passLength) {
                    newPassInput.setError(getString(R.string.error_pwd));
                    newPassInput.setHint("Invalid Password");
                } else {
                    newPassInput.setError(null);
                    newPassInput.setHint("New Password");
                    if (!passMatch) {
                        newRePassInput.setError(getString(R.string.error_rePwd));
                        newRePassInput.setHint("Password Mismatch");
                    } else {
                        newRePassInput.setError(null);
                        newRePassInput.setHint("Retype Password");
                    }
                }
                if (!emailGood) {
                    newEmailInput.setError(getString(R.string.error_ema));
                    newEmailInput.setHint("Invalid Email");
                } else {
                    newEmailInput.setError(null);
                    newEmailInput.setHint("New Email Address");
                }
                if (!userLength && !passLength && emailGood) {
                    messagesShowing = false;
                }
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
                            oldUsrInput.setError(getString(R.string.error_old_usn));
                            System.out.println("NotSame user");
                            break;
                        case 2:
                            oldPassInput.setError(getString(R.string.error_old_pwd));
                            System.out.println("NotSame pass");
                            break;
                        case 3:
                            oldUsrInput.setError(getString(R.string.error_old_usn));
                            oldPassInput.setError(getString(R.string.error_old_pwd));
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