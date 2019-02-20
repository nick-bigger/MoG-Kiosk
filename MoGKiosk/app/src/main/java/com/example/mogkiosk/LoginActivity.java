package com.example.mogkiosk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LoginActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Password;
    private Button Login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        //assigning variables from layout
        Name = findViewById(R.id.etName);
        Password = findViewById(R.id.etPassword);
        Login = findViewById(R.id.btnLogin);

        //setting the login button to validate
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });


    }

    /**
     * Used to validate the username and password given by the user against the admin credentials on
     * file, if they match it will pass the user on to the admin activity
     *
     * @param userName the user name to login
     * @param userPassword the user password
     */
    private void validate(String userName, String userPassword) {
        if((userName.equals("admin")) && (userPassword.equals("glassiscool"))) {
            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
            startActivity(intent);
        }
    }

}
