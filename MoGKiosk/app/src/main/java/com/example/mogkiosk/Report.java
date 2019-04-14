package com.example.mogkiosk;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Class that sends an email from upsmogkioskautomatic@gmail.com to the email specified in the credentials.
 * MUST BE ON A NEW THREAD.
 */
public class Report
{
    private static final String EMAIL_SENDER = "upsmogkioskautomatic@gmail.com";
    private static final String EMAIL_SENDER_PASS = "museumofglass";

    /**
     * Starts and sends the email
     * @param sendToEmail email recipient
     * @param passForEmail password to send to recipient
     * @param userForEmail username to send to recipient
     */
    public void sendMail(String sendToEmail, String passForEmail, String userForEmail)
    {
        String bodyMessage = "Below are your new credentials:\n\n" + "Username: " + userForEmail + "\nPassword: " + passForEmail +
                "\n\nOnce you sign in with these credentials, you will be prompted to change your password.\n\n If you did not ask" +
                " for this email, please sign in with regular credentials and these temporary credentials will disappear.";

        // Setting up config
        // Durations for the email connection to the Google SMTP server using TLS
        Properties props = new Properties();
        props.put("mail.smtp.host", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        //Establishing a session with required user details
        Session session = Session.getInstance(props, new javax.mail.Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(EMAIL_SENDER, EMAIL_SENDER_PASS);
            }
        });

        try {
            //Creating a Message object to set the email content
            final MimeMessage msg = new MimeMessage(session);
            //Storing the comma seperated values to email addresses
            String to = sendToEmail;
            /*Parsing the String with defualt delimiter as a comma by marking the boolean as true and storing the email
            addresses in an array of InternetAddress objects*/
            InternetAddress[] address = InternetAddress.parse(to, true);
            //Setting the recepients from the address variable
            msg.setRecipients(Message.RecipientType.TO, address);
            //String timeStamp = new SimpleDateFormat("yyyymmdd_hh-mm-ss").format(new Date());
            //Change this stuff to be better
            msg.setSubject("New Credentials for MoG Kiosk");
            msg.setSentDate(new Date());
            msg.setText(bodyMessage);
            msg.setHeader("XPriority", "1");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(msg);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            System.out.println("Mail has been sent successfully");
        } catch (MessagingException mex) {
            System.out.println("Unable to send an email" + mex);
        }
    }
}