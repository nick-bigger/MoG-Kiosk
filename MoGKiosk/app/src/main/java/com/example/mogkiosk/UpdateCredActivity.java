package com.example.mogkiosk;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
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
    private TextView mNotSameUserTextView;
    private TextView mNotSamePassTextView;
    private TextInputLayout usrInput;
    private TextInputLayout passInput;
    private TextInputLayout rePassInput;
    private TextInputLayout emailInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final PrivateInfoManager manager = new PrivateInfoManager(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cred);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();

        mUpdateUserTextView = findViewById(R.id.new_update_user);
        mUpdatePassTextView = findViewById(R.id.new_update_password);
        mUpdateEmailView = findViewById(R.id.new_update_email);
        mOldUserTextView =findViewById(R.id.old_update_username);
        mOldPassTextView =findViewById(R.id.old_update_password);
        mRetypedPassView =findViewById(R.id.retype_password);

        Button mSubmitUpdates = findViewById(R.id.submit_credentials);
        mNotSameUserTextView = findViewById(R.id.old_user_mismatch);
        mNotSamePassTextView = findViewById(R.id.old_pass_mismatch);
        usrInput = findViewById(R.id.newUsernameInputLayout);
        passInput = findViewById(R.id.passInputLayout);
        rePassInput = findViewById(R.id.retypePassInputLayout);
        emailInput = findViewById(R.id.newEmailInputLayout);
        mNotSameUserTextView.setVisibility(View.INVISIBLE);
        mNotSamePassTextView.setVisibility(View.INVISIBLE);
        mSubmitUpdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNotSameUserTextView.setVisibility(View.INVISIBLE);
                mNotSamePassTextView.setVisibility(View.INVISIBLE);
                boolean userLength = mUpdateUserTextView.getText().toString().length() < 5;
                boolean passLength = mUpdatePassTextView.getText().toString().length() < 5;
                boolean emailGood = isValidEmail(mUpdateEmailView.getText().toString());
                boolean passMatch = mUpdatePassTextView.toString().equals(mRetypedPassView.toString());
                boolean messagesShowing = true;
                if (userLength) {
                    usrInput.setError(getString(R.string.error_usn));
                    usrInput.setHint("Invalid Username");
                } else {
                    usrInput.setError(null);
                    usrInput.setHint("New Username");
                }
                if (passLength) {
                    passInput.setError(getString(R.string.error_pwd));
                    passInput.setHint("Invalid Password");
                } else {
                    passInput.setError(null);
                    passInput.setHint("New Password");
                    if (!passMatch) {
                        rePassInput.setError(getString(R.string.error_rePwd));
                        rePassInput.setHint("Password Mismatch");
                    } else {
                        rePassInput.setError(null);
                        rePassInput.setHint("Retype Password");
                    }
                }
                if (!emailGood) {
                    emailInput.setError(getString(R.string.error_ema));
                    emailInput.setHint("Invalid Email");
                } else {
                    emailInput.setError(null);
                    emailInput.setHint("New Email Address");
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