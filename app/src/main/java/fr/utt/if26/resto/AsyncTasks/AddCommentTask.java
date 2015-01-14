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

import fr.utt.if26.resto.R;
import fr.utt.if26.resto.Resto;
import fr.utt.if26.resto.Service.Post;
import fr.utt.if26.resto.Tools.Prefs;

/**
 * Created by soedjede on 14/01/15 for Resto
 */
public class AddCommentTask extends AsyncTask<String, Void, Boolean> {

    private Activity context;
    private String result = null;

    private ProgressDialog dialog;

    public AddCommentTask(Activity context) {
        this.context = context;
        dialog = new ProgressDialog(context);
    }

    protected void onPreExecute() {
        this.dialog.setMessage(context.getString(R.string.dialog_loading));
        this.dialog.setCanceledOnTouchOutside(false);
        this.dialog.show();
    }

    /**
     * params[0] : _restaurant
     * params[1] : text = comment text
     * params[2] : rating
     */
    @Override
    protected Boolean doInBackground(String... params) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);
        nameValuePairs.add(new BasicNameValuePair("_restaurant", params[0]));
        nameValuePairs.add(new BasicNameValuePair("text", params[1]));
        nameValuePairs.add(new BasicNameValuePair("rating", params[2]));
        Prefs prefs = new Prefs();
        result = Post.postData("/comments", nameValuePairs, prefs.getMyPrefs("token"));
        return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }

        if (success) {
            if (result != null) {
                Log.d("result", result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.has("comment")){
                        Toast.makeText(context, R.string.content_comment_added, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, R.string.error_unknown_source, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    Toast.makeText(context, R.string.error_unknown_source, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, R.string.error_unknown_source, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, R.string.content_invalid_login_settings, Toast.LENGTH_SHORT).show();
        }
    }
}
