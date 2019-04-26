package com.example.mogkiosk;

import android.content.Context;
import android.os.health.SystemHealthManager;
import android.util.Log;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * This class allows for easy management of secret info file stored on internal storage.
 * Methods allow read, writing, and verification of file info.
 */
public class PrivateInfoManager
{
    private static final String PASS_HASH = "hash";
    private static final String SALT = "salt";
    private static final String TEMP_HASH = "tempHash";
    private static final String TEMP_SALT = "tempSalt";
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String TEMP_USERNAME = "tempUsername";

    private static final int GOTOERRORMSG = -1;
    private static final int GOTOADMIN = 0;
    private static final int GOTOCHANGEPW = 1;

    private static final int EVERYTHINGSAME = 0;
    private static final int NOTSAMEUSER  = 1;
    private static final int NOTSAMEPASS = 2;
    private static final int NOTSAMEEMAIL = 3;
    private static final int NOTSAMEUSERPASS = 4;
    private static final int NOTSAMEUSEREMAIL = 5;
    private static final int NOTSAMEPASSEMAIL = 6;
    private static final int NOTHINGSAME = 7;


    private static final String ERROR = "PrivateInfoManager";
    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static  final String[] WORDS = {"beautiful", "mango", "bizarre", "platypus", "jazz", "bluejayrox", "hydrogen", "squeakycelery", "purple"};

    private Context context;
    private JSONObject infoManager;
    private String legiblePass = "";

    /**
     * Constructor for PrivateInfoManager object
     * @param context context needed to kick-off all methods
     */
    public PrivateInfoManager(Context context)
    {
        this.context = context;
        try { readJSONfromFile(this.context);}
        catch (Exception e) { Log.d(ERROR, e.toString()); }
    }

    /**
     * Initializes infoManager JSONObject using data from info file.
     * If the current device does not contain secret file, it creates a basic one to be filled in.
     * @param context the context of the app
     */
    private void readJSONfromFile(Context context) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        //Read in file
        try
        {
            FileInputStream fis = context.openFileInput("pw.pw");
            System.out.println("Found file!");

            byte[] barray = new byte[0124];
            StringBuffer fc = new StringBuffer();
            int n;

            while ((n = fis.read(barray)) != -1) fc.append(new String(barray, 0, n));
            System.out.println("FC is " + fc.toString());
            if (fc.toString().equals(""))
            {
                System.out.println("IN CREATING VALUES");
                String data = "{\n" +
                        "        \"username\": \"\",\n" +
                        "        \"tempUsername\": \"\",\n" +
                        "        \"salt\": \"\",\n" +
                        "        \"tempSalt\": \"\",\n" +
                        "        \"hash\": \"\",\n" +
                        "        \"tempHash\": \"\",\n" +
                        "        \"email\": \"\"" +
                        "    }";
                try {
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("pw.pw", Context.MODE_PRIVATE));
                    outputStreamWriter.write(data);
                    outputStreamWriter.close();
                }
                catch (IOException e) { Log.e("Exception", "File write failed: " + e.toString()); }

                while ((n = fis.read(barray)) != -1) fc.append(new String(barray, 0, n));
            }
            //System.out.println(fc.toString());
            //Make file a jsonobject
            infoManager = new JSONObject(fc.toString());
        }
        catch (FileNotFoundException f)
        {
            System.out.println("IN EXCEPTION");
            File file = new File(context.getFilesDir(), "pw.pw");
            file.createNewFile();
            readJSONfromFile(context);
            writeInitialInfo();
        }
        catch (Exception e) {Log.d(ERROR, e.toString());}
    }

    /**
     * Creates initial values for when app first installed on a new device
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private void writeInitialInfo() throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        // Initial username glassAdmin
        // Initial password glassiscool
        addKVpair(USERNAME, "glassAdmin");
        addKVpair(TEMP_USERNAME, "");
        addKVpair(SALT, "");
        updateSalt();
        addKVpair(TEMP_SALT, "");
        addKVpair(PASS_HASH, generatePasswordHash("glassiscool", false));
        addKVpair(TEMP_HASH, "");
        addKVpair(EMAIL, "upsmogkioskautomatic@gmail.com");
        // printContents();
    }

    /**
     * Get the hash from the info file
     * @return the hash or null upon exception
     */
     private String getHash()
    {
        String hash;

        try {hash = (String)infoManager.get(PASS_HASH);}
        catch (Exception e)
        {
            Log.d(ERROR, e.toString());
            return null;
        }
        return hash;
    }

    /**
     * Get the salt from the info file
     * @return the salt or null upon exception
     */
    private byte[] getSalt()
    {
        String stringSalt;
        byte[] salt;

        try
        {
            stringSalt = (String)infoManager.get(SALT);
            String[] splitSalt;
            splitSalt = (stringSalt.split(", "));

            salt = new byte[16];
            for (int i = 0; i < splitSalt.length; i++) salt[i] = Byte.parseByte(splitSalt[i]);
        }
        catch (Exception e)
        {
            Log.d(ERROR, e.toString());
            return null;
        }

        return salt;
    }

    /**
     * Get the temporary hash from the info file
     * @return the temporary hash or null upon exception
     */
    private String getTempHash()
    {
        String tempHash;

        try {tempHash = (String)infoManager.get(TEMP_HASH);}
        catch (Exception e)
        {
            Log.d(ERROR, e.toString());
            return null;
        }
        return tempHash;
    }

    /**
     * Get the temporary salt from the info file
     * @return the temporary salt or null upon exception
     */
    private byte[] getTempSalt()
    {
        String stringSalt;
        byte[] salt;

        try {
            stringSalt = (String)infoManager.get(TEMP_SALT);
            String[] splitSalt;
            splitSalt = (stringSalt.split(", "));

            salt = new byte[16];
            for (int i = 0; i < splitSalt.length; i++) salt[i] = Byte.parseByte(splitSalt[i]);
        }
        catch (Exception e)
        {
            Log.d(ERROR, e.toString());
            return null;
        }
        return salt;
    }

    /**
     * Get the email from the info file
     * @return the email or null upon exception
     */
    private String getEmail()
    {
        String email;

        try {email = (String)infoManager.get(EMAIL);}
        catch (Exception e)
        {
            Log.d(ERROR, e.toString());
            return null;
        }
        return email;
    }

    /**
     * Get the username from the info file
     * @return the username or null upon exception
     */
    private String getUsername()
    {
        String username;

        try {username = (String)infoManager.get(USERNAME);}
        catch (Exception e)
        {
            Log.d(ERROR, e.toString());
            return null;
        }
        return username;
    }

    /**
     * Get the  temporary username from the info file
     * @return the temporary username or null upon exception
     */
    private String getTempUsername()
    {
        String tempUsername;

        try {tempUsername = (String)infoManager.get(TEMP_USERNAME);}
        catch (Exception e)
        {
            Log.d(ERROR, e.toString());
            return null;
        }
        return tempUsername;
    }

    /**
     * Change the specified key to specified value in info file.
     * @param key key to change
     * @param value value to update key with
     */
    private void writeToJSON(String key, String value)
    {
        try
        {
            infoManager.put(key, value);
            try
            {
                FileOutputStream fos = context.openFileOutput("pw.pw", Context.MODE_PRIVATE);
                fos.write(infoManager.toString(4).getBytes());
//                System.out.println("Writing" + infoManager.toString(4));
                fos.close();
            }
            catch (Exception e) {Log.d(ERROR, e.toString()); System.out.println("problem with file");}
        }
        catch (Exception e) {Log.d(ERROR, e.toString()); System.out.println("problem with json");}
    }

    /**
     * Updates the current hash with the user's new desired hash
     * @param input what the user would like to be hashed and become new hash
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    private void updateHash(String input) throws InvalidKeySpecException, NoSuchAlgorithmException
    {
        String newHash = generatePasswordHash(input,false);
        writeToJSON(PASS_HASH, newHash);
        //printContents();
    }

    /**
     * Update salt with new, generated salt
     * @throws NoSuchAlgorithmException
     */
    private void updateSalt() throws NoSuchAlgorithmException
    {
        byte[] newSalt = generateSalt();
        String stringSalt = Arrays.toString(newSalt).replace("]", "").replace("[", "");
        writeToJSON(SALT, stringSalt);
    }

    /**
     * Update/create the temporary hash
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    private void updateTempHash() throws InvalidKeySpecException, NoSuchAlgorithmException
    {
        String newHash = generatePasswordHash(generateRandomPassword(), true);
        writeToJSON(TEMP_HASH, newHash);
    }

    /**
     * Generate temporary salt and update info
     * @throws NoSuchAlgorithmException
     */
    private void updateTempSalt() throws NoSuchAlgorithmException
    {
        byte[] newSalt = generateSalt();
        String stringSalt = Arrays.toString(newSalt).replace("]", "").replace("[", "");
        writeToJSON(TEMP_SALT, stringSalt);
    }

    /**
     * Update current email to new email
     * @param email email to update to
     */
    private void updateEmail(String email)
    {
        writeToJSON(EMAIL, email);
    }

    /**
     * Update current username to new username
     * @param username username to be updated to
     */
    private void updateUsername(String username)
    {
        writeToJSON(USERNAME, username);
    }

    /**
     * Update  temporary username to new temp username
     * @param username username to be updated to
     */
    private void updateTempUsername(String username)
    {
        writeToJSON(TEMP_USERNAME, username);
    }

    /**
     * Reset temporary hash to empty string
     */
    private void clearTempHash()
    {
        writeToJSON(TEMP_HASH, "");
    }

    /**
     * Reset temporary salt to empty string
     */
    private void clearTempSalt()
    {
        writeToJSON(TEMP_SALT, "");
    }

    void clearTempUsername()
    {
        writeToJSON(TEMP_USERNAME, "");
    }

    /**
     * Determines if the temporary hash contains a hash
     * @return whether it has a hash or not
     */
    private boolean isEmptyTempHash()
    {
        System.out.println("Temp hash is: " + "\"" + getTempHash() + "\"");
        return getTempHash() == null || getTempHash().equals("");
    }

    /**
     * Determines if the temporary salt contains a value
     * @return whether there is salt or not
     */
    private boolean isEmptyTempSalt()
    {
        System.out.println("Temp salt is: " + "\"" + getTempSalt() + "\"");
        System.out.println("the thing" + (getTempSalt() == null));
        return getTempSalt() == null || getTempSalt().equals("");
    }

    /**
     * Generate a random password of length 10
     * @return a string of alphanumeric combinations
     */
    private String generateRandomPassword()
    {
        SecureRandom rnd = new SecureRandom();

        StringBuilder sb = new StringBuilder( 10 );
        for( int i = 0; i < 10; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        legiblePass = sb.toString();
        return sb.toString();
    }

    /**
     * Generates hash from user input password
     * @param password user input password
     * @return Hash to be stored or checked against
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private String generatePasswordHash(String password, boolean saltFlag) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        System.out.println("The password is \"" + password + "\"");


        byte[] salt;
        if (saltFlag)salt = getTempSalt();
        else salt = getSalt();
        System.out.println("The salt is " + Arrays.toString(salt));


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
    private static byte[] generateSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    /**
     * Convert byte array to string
     * @param array array to be converted
     * @return converted array
     */
    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    /**
     * Add a new key value pair to JSON file
     * PROGRAMMER ONLY FUNCTION
     * @param key key to create
     * @param value value to assign to key
     */
    private void addKVpair(String key, String value)
    {
        writeToJSON(key, value);
        //printContents();
    }

    /**
     * Delete a key value pair
     * PROGRAMMER ONLY FUNCTION
     * @param key key to be deleted along with its value(s)
     */
    private void deleteKVpair(String key)
    {
        infoManager.remove(key);
        try
        {
            FileOutputStream fos = context.openFileOutput("pw.pw", Context.MODE_PRIVATE);
            fos.write(infoManager.toString(4).getBytes());
        }
        catch (Exception e) {Log.d(ERROR, e.toString());}
        printContents();
    }

    /**
     * Print the contents of the JSON file
     * TESTING PURPOSES ONLY
     */
     void printContents()
    {
        try {System.out.println(infoManager.toString(4));}
        catch (Exception e) { Log.d(ERROR, e.toString());}
        System.out.println("Temp password:" + legiblePass);
    }

    /**
     * Validate the login credentials against the info file data
     * @param username user-entered username
     * @param password user-entered password
     * @return int indicating which action to take next in the app
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public int validateLogin(String username, String password) throws InvalidKeySpecException, NoSuchAlgorithmException
    {
        printContents();
        if (isEmptyTempHash() && isEmptyTempSalt())
        {
            if ((getHash().equals(generatePasswordHash(password, false))) && (getUsername().equals(username))) return GOTOADMIN;
        }
        else
        {
            if ((getTempHash().equals(generatePasswordHash(password, true))) && (getTempUsername().equals(username))) return GOTOCHANGEPW;
            if ((getHash().equals(generatePasswordHash(password, false))) && (getUsername().equals(username)))
            {
                clearTemps();
                return GOTOADMIN;
            }
        }

        return GOTOERRORMSG;
    }

    /**
     * Generate all temporary values
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private void generateTemps() throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        updateTempSalt();
        updateTempHash();
        updateTempUsername(generateTempUser());
    }

    /**
     * Choose a random element from WORDS to be temporary username
     * @return new temporary username
     */
    private String generateTempUser()
    {
        Random rand = new Random();
        return WORDS[rand.nextInt(WORDS.length)];
    }

    /**
     * Clear all the temporary variables
     */
    private void clearTemps()
    {
        clearTempSalt();
        clearTempHash();
        clearTempUsername();
    }

    /**
     * If a temporary hash does not exist, all temps will be generated and an email will be sent to email in info file
     * containing new username and password.
     * If a temp hash does exist, it means this process has already been executed and a message will be reported
     * @return whether or not this process went through all the steps
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public boolean forgotPassword() throws Exception {
        if (isEmptyTempHash() && isEmptyTempSalt())
        {
            generateTemps();
            System.out.println("I generated Temps");
            sendEmail();
            System.out.println("I finished sending the email");
            return true;
        }
        //indicates that there is already an email sent
        return false;
    }

    /**
     * If a temporary hash does not exist, all temps will be generated and an email will be sent to email in info file
     * containing new username and password.
     * If a temp hash does exist, it means this process has already been executed and a message will be reported
     * @return whether or not this process went through all the steps
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public boolean isSent() throws Exception {
        if (isEmptyTempHash() && isEmptyTempSalt())
        {
            return true;
        }
        //indicates that there is already an email sent
        return false;
    }

    /**
     * Change the hash and username
     * @param username username to be updated
     * @param password hash to be updated
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public void changeCredentials(String username, String password) throws InvalidKeySpecException, NoSuchAlgorithmException
    {
        updateSalt();
        updateHash(password);
        updateUsername(username);
        clearTemps();
    }

    /**
     * Update the username, password, and email
     * @param username username to be updated
     * @param password password to be updated
     * @param email email ot be updated
     * @return whether or not credentials could be updated
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public int validateCredentials(String username, String password,  String email) throws InvalidKeySpecException, NoSuchAlgorithmException
    {
        String oldUsername = getUsername();
        String oldPassHash = getHash();
        String oldEmail = getEmail();

        //empty password breaks the hash generator so just assigning to impossible password
        if (password.equals("")) password = "a";
        String newPassHash = generatePasswordHash(password, false);

       //if nothing the same
        if (! oldUsername.equals(username) && ! oldPassHash.equals(newPassHash) && ! oldEmail.equals(email)) return NOTHINGSAME;
        //if not same user and not same pass
        if (! oldUsername.equals(username) && !! oldPassHash.equals(newPassHash)) return NOTSAMEUSERPASS;
        //if not same user and not same email
        if(! oldUsername.equals(username) && ! oldEmail.equals(email)) return NOTSAMEUSEREMAIL;
        //if not same password and not same email

        if (! oldEmail.equals(email) && ! oldPassHash.equals(newPassHash)) return NOTSAMEPASSEMAIL;
        //Single not same fields
        if (! oldUsername.equals(username)) return NOTSAMEUSER;
        if (! oldPassHash.equals(newPassHash)) return NOTSAMEPASS;
        if (! oldEmail.equals(email)) return NOTSAMEEMAIL;

        return EVERYTHINGSAME;
    }


    public void updateCredentials(String newUsername, String newPassword, String newEmail) throws InvalidKeySpecException, NoSuchAlgorithmException {
        updateHash(newPassword);
        updateUsername(newUsername);
        updateEmail(newEmail);
    }

    // More difficult than I was expecting
    public void sendEmail() {
        updateEmail("museumofglasskiosk@gmail.com");
        String email = getEmail();
        String pass = legiblePass;
        String user = getTempUsername();

        Report report = new Report();
        report.sendMail(email, pass, user);
    }
}
