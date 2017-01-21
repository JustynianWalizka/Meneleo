package com.aoxo.meneleo;

import android.content.Intent;
import android.os.AsyncTask;

/**
 * Created by tomek on 21.01.2017.
 */

public class UserManager extends AsyncTask<Void, Void, Boolean> {

    private final String mEmail;
    private final String mPassword;
    UserLoginInterface uli;
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "tomek@:hello", "bar@example.com:world"
    };


    UserManager(String email, String password, UserLoginInterface uli) {
        mEmail = email;
        mPassword = password;
        this.uli = uli;
    }

    public void login()
    {
        execute((Void) null);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            // Simulate network access.
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            return false;
        }

        for (String credential : DUMMY_CREDENTIALS) {
            String[] pieces = credential.split(":");
            if (pieces[0].equals(mEmail)) {
                // Account exists, return true if the password matches.
                return pieces[1].equals(mPassword);
            }
        }

        // TODO: register the new account here.
        return true;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        uli.onLoginResult(success);
    }

    /*@Override
    protected void onPostExecute(final Boolean success) {
        mAuthTask = null;
        showProgress(false);

        if (success) {
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            mPasswordView.setError(getString(R.string.error_incorrect_password));
            mPasswordView.requestFocus();
        }
    }
*/
    @Override
    protected void onCancelled() {
        uli.onLoginCancelled();
    }


}
