package fr.utt.if26.resto.AsyncTasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.utt.if26.resto.Interfaces.OnRestoPhotoLinksTaskCompleted;
import fr.utt.if26.resto.R;
import fr.utt.if26.resto.Resto;
import fr.utt.if26.resto.Service.Get;

/**
 * Created by soedjede on 20/12/14
 */
public class RestoPhotosLinksTask extends AsyncTask<String, Void, Boolean> {

    private Activity context;
    private String result;
    private OnRestoPhotoLinksTaskCompleted listener;

    public RestoPhotosLinksTask(Activity context, OnRestoPhotoLinksTaskCompleted listener) {
        this.context = context;
        this.listener = listener;
    }

    /**
     * params[0] is the restaurant id
     */
    @Override
    protected Boolean doInBackground(String... params) {
        result = Get.get_string("/restaurants/" + params[0] + "/photos");
        return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        if (success) {
            if (result != null) {
                try {
                    JSONArray photos_array = new JSONArray(result);
                    String[] imageUrls = new String[photos_array.length()];
                    for (int i = 0; i < photos_array.length(); i++) {
                        JSONObject photos = photos_array.getJSONObject(i);
                        imageUrls[0] = Resto.server_address + "/photos/" + photos.getString("_image") + "/data";
                    }
                    listener.getPhotosURLs(imageUrls);
                } catch (Exception ex) {
                    Toast.makeText(context, R.string.error_unknown_source, Toast.LENGTH_SHORT).show();
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
