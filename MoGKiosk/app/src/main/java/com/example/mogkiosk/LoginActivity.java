package com.example.mogkiosk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    private TextView mForgotPassTextView;


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
        mForgotPassTextView = findViewById(R.id.forgotPass);

        mBadLoginTextView.setVisibility(View.INVISIBLE);

        //setting the login button to validate
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBadLoginTextView.setVisibility(View.INVISIBLE);

                try {
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
                }
            }
        });

        mForgotPassTextView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    showMessage(manager.forgotPassword());

                } catch (NoSuchAlgorithmException e) {
                    System.out.println("Something went wrong while forgetting password with stuff");
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    System.out.println("Something went wrong while forgetting password and the keys");
                    e.printStackTrace();
                }
                //if forgotPassword(): message saying sending email
                //else message saying email already sent

            }
        });
    }

    @Override
    protected void onResume() {
        //Lifecycle code should stick together
        super.onResume();
        mNameEditText.setText("");
        mPassEditText.setText("");
    }

    private void showMessage(boolean messageType)
    {
        DialogFragment dialogFragment = new MyCustomDialogFragment();
        Bundle b =  new Bundle();
        b.putBoolean(MyCustomDialogFragment.DIALOG_TYPE, messageType);
        dialogFragment.setArguments(b);
        //allows us to set view for dialog fragment
        dialogFragment.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.MessageBackground);
//        dialogFragment.getLayoutInflater().inflate(R.layout.login_main, null);
//        dialogFragment.onGetLayoutInflater(new Bundle()).inflate(R.layout.login_main, null);

        dialogFragment.show(getSupportFragmentManager(), "message");
    }


    public static class MyCustomDialogFragment extends DialogFragment {
        public static final String DIALOG_TYPE = "dialogType";
        public static final boolean EMAIL_SENDING = true;
        public static final boolean EMAIL_ALREADY_SENT = false;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.email_status_layout, container, false);

            //Determine message, icon, and icon color
            ImageView icon = v.findViewById(R.id.iconImage);
            TextView messText = v.findViewById(R.id.messageTextView);

            boolean sendEmail = getArguments().getBoolean(DIALOG_TYPE);
            if (sendEmail == EMAIL_SENDING)
            {
                icon.setImageResource(R.drawable.good);
                messText.setText(R.string.email_sending);
            }
            else if (sendEmail == EMAIL_ALREADY_SENT)
            {
                icon.setImageResource(R.drawable.bad);
                messText.setText(R.string.email_sent);
            }

            // Do all the stuff to initialize your custom view
            //setting image and text stuff
            //This is where Nick changes all the things =)

            return v;
        }
    }

}

