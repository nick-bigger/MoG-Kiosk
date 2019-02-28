package com.example.mogkiosk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Login interface for employees
 * Currently contains secure password storage but no indications of incorrect username or password to the user
 * Will add change password feature
 */
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
                try {
                    validate(Name.getText().toString(), Password.getText().toString());
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    /**
     * Used to validate the username and password given by the user against the admin credentials on
     * file, if they match it will pass the user on to the admin activity
     *
     * @param userName the username to login
     * @param userPassword the user password
     */
    private void validate(String userName, String userPassword) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String passKey = "1000:3a4d05d77bda836207a9371cb9b83f20:4070110c8a7c5fba1250b9614b04a98566765ebd0565cb765f71a9920be5433b860a02f512211f249d53ac032065651e0e2db80628e7570cbb3e05b477b2c604";
        if((userName.equals("admin")) && (passKey.equals(generatePasswordHash(userPassword)))) {
            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
            startActivity(intent);
        }
    }

//    //To be used later when we make a change password feature
//    private void testKey() throws NoSuchAlgorithmException, InvalidKeySpecException
//    {
//        String  originalPassword = "glassiscool";
//        String generatedSecuredPasswordHash = generatePasswordHash(originalPassword);
//        System.out.println(generatedSecuredPasswordHash);
//    }

    /**
     * Generates hash from user input password
     * @param password user input password
     * @return Hash to be stored or checked against
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private static String generatePasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        //This should be a constant that can be changed later using the same method if they want to change their password or something
        // This would be more important if we had more users **future
        byte[] salt = new byte[] {58, 77, 5, -41, 123, -38, -125, 98, 7, -87, 55, 28, -71, -72, 63, 32}; //absolutely move somewhere else

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    /**
     * Generates salt to append to password before the hash
     * @return salt
     * @throws NoSuchAlgorithmException
     */
    private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        System.out.println(Arrays.toString(salt));
        return salt;
    }

    /**
     * Convert byte array to string
     * @param array array to be converted
     * @return converted array
     * @throws NoSuchAlgorithmException
     */
    private static String toHex(byte[] array) throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }
}
