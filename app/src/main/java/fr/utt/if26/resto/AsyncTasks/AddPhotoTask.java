package fr.utt.if26.resto.AsyncTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import fr.utt.if26.resto.R;
import fr.utt.if26.resto.Service.MultiPartEntity;
import fr.utt.if26.resto.Service.Post;
import fr.utt.if26.resto.Tools.ImageUtility;
import fr.utt.if26.resto.Tools.Prefs;

/**
 * Created by soedjede on 14/01/15 for Resto
 */
public class AddPhotoTask extends AsyncTask<String, Void, Boolean> {

    private Activity context;
    private String result = null;

    private ProgressDialog dialog;

    public AddPhotoTask(Activity context) {
        this.context = context;
        dialog = new ProgressDialog(context);
    }

    protected void onPreExecute() {
        this.dialog.setMessage(context.getString(R.string.dialog_loading));
        this.dialog.setCanceledOnTouchOutside(false);
        this.dialog.setCancelable(false);
        this.dialog.show();
    }

    /**
     * params[0] : _restaurant
     * params[1] : image
     * params[2] : is_main_picture = false
     */
    @Override
    protected Boolean doInBackground(String... params) {
        /*List<NameValuePair> nameValuePairs = new ArrayList<>(3);
        nameValuePairs.add(new BasicNameValuePair("_restaurant", params[0]));
        nameValuePairs.add(new BasicNameValuePair("image", params[1]));
        nameValuePairs.add(new BasicNameValuePair("is_main_picture", "false"));*/

        MultiPartEntity reqEntity = new MultiPartEntity();
        Bitmap bm = ImageUtility.StringToBitMap(params[1]);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream); // convert Bitmap to ByteArrayOutputStream
        InputStream in = new ByteArrayInputStream(stream.toByteArray()); // convert ByteArrayOutputStream to ByteArrayInputStream
        reqEntity.addPart("_restaurant", params[0]);
        reqEntity.addPart("photo", System.currentTimeMillis() + ".jpg", in);
        reqEntity.addPart("is_main_picture", "false");

        Prefs prefs = new Prefs();
        result = Post.postFileData("/photos", reqEntity, prefs.getMyPrefs("token"));
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
                    if (object.has("photo")){
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
