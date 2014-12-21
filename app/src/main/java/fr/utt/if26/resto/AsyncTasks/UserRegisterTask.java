package fr.utt.if26.resto.AsyncTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import fr.utt.if26.resto.R;

/**
 * Created by soedjede on 20/12/14 for Resto
 *
 * Represents an asynchronous registration task used to authenticate the user.
 */

public class UserRegisterTask extends AsyncTask<String, Void, Boolean> {

    private Activity context;

    private ProgressDialog dialog;

    public UserRegisterTask(Activity context) {
        this.context = context;
        dialog = new ProgressDialog(context);
    }

    protected void onPreExecute() {
        this.dialog.setMessage(context.getString(R.string.dialog_loading));
        this.dialog.show();
    }


        /**
         * args[0] is the last name
         * args[1] is the first name
         * args[2] is the email
         * args[3] is the password
         */
    @Override
    protected Boolean doInBackground(String... args) {
        // TODO: register the new account here.
        try {
            // Simulate network access.
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }

        if (success) {
            context.finish();
        } else {
            Toast.makeText(context, R.string.content_invalid_login_settings, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCancelled() {
    }
}
