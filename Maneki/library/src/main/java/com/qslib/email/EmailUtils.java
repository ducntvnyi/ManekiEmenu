package com.qslib.email;

import android.os.AsyncTask;
import android.util.Log;

public class EmailUtils {
    // default
    private static final String port = "465";
    private static final String sport = "465";
    private static final String smtpHost = "smtp.gmail.com";
    private static final String userName = "abc@gmail.com";
    private static final String password = "xxx";
    private static final String sender = "abc@gmail.com";

    private String[] emailTo = null;
    private String subject = null;
    private String body = null;
    private EmailListener emailListener = null;

    /**
     * instance to sent email
     *
     * @return
     */
    public static EmailUtils getInstance() {
        return new EmailUtils();
    }

    /**
     * @param emailTo
     * @return
     */
    public EmailUtils setEmailTo(String[] emailTo) {
        this.emailTo = emailTo;
        return this;
    }

    /**
     * @param subject
     * @return
     */
    public EmailUtils setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    /**
     * @param body
     * @return
     */
    public EmailUtils setBody(String body) {
        this.body = body;
        return this;
    }

    /**
     * @param emailListener
     * @return
     */
    public EmailUtils setEmailListener(EmailListener emailListener) {
        this.emailListener = emailListener;
        return this;
    }

    /**
     * AsyncTask send email
     */
    private class EmailAsyncTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (emailListener != null) emailListener.onStarted();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                return Email.getInstance(userName, password)
                        .setPort(port).setSport(sport).setHost(smtpHost)
                        .setTo(emailTo).setFrom(sender).setSubject(subject)
                        .setBody(body).send();
            } catch (Exception e) {
                Log.e("MailApp", "Could not send email", e);
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            try {
                // raise event
                if (emailListener != null) {
                    // finish
                    emailListener.onFinished();

                    if (result) emailListener.onSuccess();// sent successful
                    else emailListener.onFail();// sent fail
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * send email
     */
    public void sendEmail() {
        try {
            new EmailAsyncTask().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
