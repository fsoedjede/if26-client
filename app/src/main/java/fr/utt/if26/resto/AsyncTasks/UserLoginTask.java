package fr.utt.if26.resto.AsyncTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import fr.utt.if26.resto.R;

/**
 * Created by soedjede on 20/12/14
 *
 * Represents an asynchronous login task used to authenticate
 * the user.
 */
public class UserLoginTask extends AsyncTask<String, Void, Boolean> {

    private Activity context;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    private ProgressDialog dialog;

    public UserLoginTask(Activity context) {
        this.context = context;
        dialog = new ProgressDialog(context);
    }

    protected void onPreExecute() {
        this.dialog.setMessage(context.getString(R.string.dialog_loading));
        this.dialog.show();
    }

    /**
     * args[0] is the email
     * args[1] is the password
     */
    @Override
    protected Boolean doInBackground(String... params) {
        // TODO: attempt authentication against a network service.
        try {
            // Simulate network access.
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            return false;
        }

        for (String credential : DUMMY_CREDENTIALS) {
            String[] pieces = credential.split(":");
            if (pieces[0].equals(params[0])) {
                // Account exists, return true if the password matches.
                return pieces[1].equals(params[1]);
            }
        }
        // TODO: register the new account here.
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
