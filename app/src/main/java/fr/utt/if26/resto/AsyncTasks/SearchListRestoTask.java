package fr.utt.if26.resto.AsyncTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import fr.utt.if26.resto.Adapters.ListRestoAdapter;
import fr.utt.if26.resto.Interfaces.OnListTaskCompleted;
import fr.utt.if26.resto.Model.Position;
import fr.utt.if26.resto.Model.Rating;
import fr.utt.if26.resto.Model.Restaurant;
import fr.utt.if26.resto.R;
import fr.utt.if26.resto.Resto;
import fr.utt.if26.resto.Service.Get;
import fr.utt.if26.resto.Tools.MapsUtility;

/**
 * Created by soedjede on 20/12/14
 */
public class SearchListRestoTask extends AsyncTask<String, Void, Boolean> {

    private Activity context;
    private ProgressDialog dialog;
    private String result;
    private OnListTaskCompleted listener;

    public SearchListRestoTask(Activity context, OnListTaskCompleted listener) {
        this.listener = listener;
        this.context = context;
        dialog = new ProgressDialog(context);
    }

    protected void onPreExecute() {
        this.dialog.setCanceledOnTouchOutside(false);
        this.dialog.setMessage(context.getString(R.string.dialog_loading));
        this.dialog.show();
    }

    /**
     * params[0] is the query string
     */
    @Override
    protected Boolean doInBackground(String... params) {
        result = Get.get_string("/restaurants?q=" + params[0]);
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
                    ArrayList<Restaurant> restos = new ArrayList<>(2);
                    JSONArray restaurants_array = new JSONArray(result);
                    for (int i = 0; i < restaurants_array.length(); i++) {
                        JSONObject details = restaurants_array.getJSONObject(i);
                        Restaurant resto = new Restaurant();
                        resto.set_id(details.getString("_id"));
                        resto.setName(details.getString("name"));
                        resto.setDescription(details.getString("description"));
                        resto.setTel(details.getString("tel"));
                        resto.setUrl(details.getString("url"));
                        JSONObject position = details.getJSONObject("position");
                        JSONArray coordinates = position.getJSONArray("coordinates");
                        resto.setPosition(
                                new Position(
                                        coordinates.getString(0),
                                        coordinates.getString(1),
                                        position.getString("name")
                                )
                        );
                        JSONObject ratings = details.getJSONObject("ratings");
                        resto.setRatings(new Rating(ratings.getDouble("received"), ratings.getInt("total")) );
                        resto.set_main_picture(details.getString("_main_picture"));
                        restos.add(resto);
                    }
                    try {
                        if(Resto.userPosition == null) {
                            Resto.userPosition = new Position("4.0672518", "48.2690151", "12 rue Marie Curie - 10 000 TROYES");
                        }
                        Collections.sort(restos, new Comparator<Restaurant>() {
                            @Override
                            public int compare(Restaurant lhs, Restaurant rhs) {
                                double distance1 = MapsUtility.haversine(lhs.getPosition(), Resto.userPosition);
                                double distance2 = MapsUtility.haversine(rhs.getPosition(), Resto.userPosition);
                                return Double.compare(distance1, distance2);
                            }
                        });//Sort the distance to show first nearby restaurants
                        ListRestoAdapter adapter = new ListRestoAdapter(context, R.layout.item_list_resto);
                        adapter.addMultiple(restos);
                        listener.hydrateListView(adapter);
                    } catch (Exception ex) {

                        Toast.makeText(context, R.string.error_unknown_source, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
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
