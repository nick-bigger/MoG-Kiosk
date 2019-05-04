package com.example.mogkiosk;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mogkiosk.activities.admin.AdminActivity;
import com.example.mogkiosk.activities.changepass.ChangePassActivity;

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
    private AlertDialog alertDialog;
    private AlertDialog errorDialog;

    private TextView errorText;

    private Button mLoginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final PrivateInfoManager manager = new PrivateInfoManager(this);

        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        //assigning variables from layout
        mNameEditText = findViewById(R.id.title);
        mPassEditText = findViewById(R.id.etPassword);
        mLoginButton = findViewById(R.id.btnLogin);
        TextView mForgotPassTextView = findViewById(R.id.forgotPass);
        errorText = findViewById(R.id.errorText);
        errorText.setVisibility(View.INVISIBLE);

        mNameEditText.addTextChangedListener(watcher);
        mPassEditText.addTextChangedListener(watcher);

        //setting the login button to validate
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                errorText.setVisibility(View.INVISIBLE);

                try {
                    switch (manager.validateLogin(mNameEditText.getText().toString(), mPassEditText.getText().toString())) {
                        case -1:
                            errorText.setVisibility(View.VISIBLE);
                            System.out.println("This should lead to the error message");
                            break;
                        case 0:
                            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                            startActivity(intent);
                            System.out.println("This should lead to the admin page");
                            break;
                        case 1:
                            Intent intent2 = new Intent(LoginActivity.this, ChangePassActivity.class);
                            startActivity(intent2);
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
                try {
                    if (manager.isSent()) {
                        open(manager);
                    } else {
                        error();
                    }
                } catch (NoSuchAlgorithmException e) {
                    System.out.println("Something went wrong while forgetting password with stuff");
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    System.out.println("Something went wrong while forgetting password and the keys");
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                try
//                {
//                    showMessage(manager.forgotPassword());
//
//                } catch (NoSuchAlgorithmException e) {
//                    System.out.println("Something went wrong while forgetting password with stuff");
//                    e.printStackTrace();
//                } catch (InvalidKeySpecException e) {
//                    System.out.println("Something went wrong while forgetting password and the keys");
//                    e.printStackTrace();
//                }
//                if forgotPassword(): message saying sending email
//                else message saying email already sent

            }
        });
    }

    private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        { }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {}
        @Override
        public void afterTextChanged(Editable s) {
            if (mNameEditText.getText().toString().length() == 0 || mPassEditText.getText().toString().length() == 0) {
                mLoginButton.setEnabled(false);
            } else {
                mLoginButton.setEnabled(true);
            }
        }
    };

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
        static final String DIALOG_TYPE = "dialogType";
        static final boolean EMAIL_SENDING = true;
        static final boolean EMAIL_ALREADY_SENT = false;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.email_status_layout, container, false);

            //Determine message, icon, and icon color
            ImageView icon = v.findViewById(R.id.iconImage);
            TextView messText = v.findViewById(R.id.messageTextView);

            boolean sendEmail = getArguments().getBoolean(DIALOG_TYPE);
            if (sendEmail == EMAIL_SENDING)
            {
                icon.setImageResource(R.drawable.ic_success);
                messText.setText(R.string.login_dialog_sending);
            }
            else if (sendEmail == EMAIL_ALREADY_SENT)
            {
                icon.setImageResource(R.drawable.ic_error);
                messText.setText(R.string.login_dialog_sent);
            }

            // Do all the stuff to initialize your custom view
            //setting image and text stuff
            //This is where Nick changes all the things =)

            return v;
        }
    }

    private void open(final PrivateInfoManager manager) throws Exception  {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Would you like to reset your password?");
        alertDialogBuilder.setTitle("Reset Password");
                alertDialogBuilder.setPositiveButton("Reset",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                try {
                                    manager.forgotPassword();
                                } catch (NoSuchAlgorithmException e) {
                                    System.out.println("Something went wrong while forgetting password with stuff");
                                    e.printStackTrace();
                                } catch (InvalidKeySpecException e) {
                                    System.out.println("Something went wrong while forgetting password and the keys");
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content),"A reset email has been sent.",Snackbar.LENGTH_LONG).show();
                            }
                        });

        alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.cancel();
            }
        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void error() throws Exception {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("An email has already been sent.");
        alertDialogBuilder.setTitle("Error");
        alertDialogBuilder.setNeutralButton("Dismiss",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        errorDialog.cancel();
                    }
                });
        errorDialog = alertDialogBuilder.create();
        errorDialog.show();
    }

}