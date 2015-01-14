package fr.utt.if26.resto.AsyncTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import fr.utt.if26.resto.Model.User;
import fr.utt.if26.resto.R;
import fr.utt.if26.resto.Resto;
import fr.utt.if26.resto.Service.Get;
import fr.utt.if26.resto.Tools.Prefs;

/**
 * Created by soedjede on 20/12/14
 * <p/>
 * Represents an asynchronous login task used to authenticate
 * the user.
 */
public class UserLoginTask extends AsyncTask<String, Void, Boolean> {

    private Activity context;
    private String result = null;

    private ProgressDialog dialog;

    public UserLoginTask(Activity context) {
        this.context = context;
        dialog = new ProgressDialog(context);
    }

    protected void onPreExecute() {
        this.dialog.setMessage(context.getString(R.string.dialog_loading));
        this.dialog.setCanceledOnTouchOutside(false);
        this.dialog.show();
    }

    /**
     * args[0] is the email
     * args[1] is the password
     */
    @Override
    protected Boolean doInBackground(String... params) {
        try {
            // Simulate network access.
            result = Get.get_string("/login/" + params[0] + "/" + params[1]);
        } catch (Exception e) {
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
            if (result != null) {
                try {
                    JSONObject object = new JSONObject(result);
                    try {
                        String token = object.getString("token");
                        String expires = object.getString("expires");
                        JSONObject json_user = object.getJSONObject("user");
                        User user = new User();
                        user.JsonUserParse(json_user);
                        Resto.user = user;

                        Prefs prefs = new Prefs();
                        prefs.setMyPrefs("token", token);
                        prefs.setMyPrefs("expires", expires);
                        prefs.setMyPrefs("user", json_user.toString());
                        Toast.makeText(context, R.string.content_valid_login_settings, Toast.LENGTH_SHORT).show();
                        context.finish();
                    } catch (Exception ex) {
                        Toast.makeText(context, R.string.error_unknown_source, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    Toast.makeText(context, R.string.content_invalid_login_settings, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, R.string.error_unknown_source, Toast.LENGTH_SHORT).show();
            }
            //context.finish();
        } else {
            Toast.makeText(context, R.string.content_invalid_login_settings, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCancelled() {
    }
}
