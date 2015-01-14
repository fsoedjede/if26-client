package fr.utt.if26.resto.AsyncTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fr.utt.if26.resto.Model.User;
import fr.utt.if26.resto.R;
import fr.utt.if26.resto.Resto;
import fr.utt.if26.resto.Service.Post;
import fr.utt.if26.resto.Tools.JSONUtility;

/**
 * Created by soedjede on 20/12/14 for Resto
 * <p/>
 * Represents an asynchronous registration task used to authenticate the user.
 */

public class UserRegisterTask extends AsyncTask<String, Void, Boolean> {

    private Activity context;
    private String result = null;

    private ProgressDialog dialog;

    public UserRegisterTask(Activity context) {
        this.context = context;
        dialog = new ProgressDialog(context);
    }

    protected void onPreExecute() {
        this.dialog.setCanceledOnTouchOutside(false);
        this.dialog.setMessage(context.getString(R.string.dialog_loading));
        this.dialog.show();
    }


    /**
     * args[0] is the last name
     * args[1] is the first name
     * args[2] is the email
     * args[3] is the username
     * args[4] is the password
     * args[5] is the position
     */
    @Override
    protected Boolean doInBackground(String... args) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);
        nameValuePairs.add(new BasicNameValuePair("email", args[2]));
        nameValuePairs.add(new BasicNameValuePair("username", args[3]));
        nameValuePairs.add(new BasicNameValuePair("first_name", args[1]));
        nameValuePairs.add(new BasicNameValuePair("last_name", args[0]));
        nameValuePairs.add(new BasicNameValuePair("password", args[4]));
        nameValuePairs.add(new BasicNameValuePair("position['lat']", args[5]));
        nameValuePairs.add(new BasicNameValuePair("position['lng']", args[6]));
        result = Post.postData("/users", nameValuePairs);
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
                    Log.d("result", result);
                    if(JSONUtility.isJSONValid(result)){
                        JSONObject json_user = new JSONObject(result);
                        try {
                            User user = new User();
                            user.JsonUserParse(json_user.getJSONObject("user"));
                            Resto.user = user;
                            Toast.makeText(context, R.string.content_register_ok, Toast.LENGTH_SHORT).show();
                            context.finish();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            Toast.makeText(context, R.string.error_unknown_source, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Toast.makeText(context, R.string.content_invalid_login_settings, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, R.string.error_unknown_source, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, R.string.error_unknown_source, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCancelled() {
    }
}
