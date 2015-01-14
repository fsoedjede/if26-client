package fr.utt.if26.resto.AsyncTasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.utt.if26.resto.Adapters.ListCommentsAdapter;
import fr.utt.if26.resto.Interfaces.OnListTaskCompleted;
import fr.utt.if26.resto.Model.Comment;
import fr.utt.if26.resto.R;
import fr.utt.if26.resto.Service.Get;

/**
 * Created by soedjede on 20/12/14
 */
public class ListCommentsTask extends AsyncTask<String, Void, Boolean> {

    private Activity context;
    private ProgressDialog dialog;
    private String result;
    private OnListTaskCompleted listener;

    public ListCommentsTask(Activity context, OnListTaskCompleted listener) {
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
     * args[0] is the email
     * args[1] is the password
     */
    @Override
    protected Boolean doInBackground(String... params) {
        try {
            result = Get.get_string("/restaurants/" + params[0] + "/comments");
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
                    ArrayList<Comment> comments = new ArrayList<Comment>(0);
                    JSONArray comments_array = new JSONArray(result);
                    for (int i = 0; i < comments_array.length(); i++) {
                        JSONObject details = comments_array.getJSONObject(i);
                        Comment comment = new Comment();
                        comment.setText(details.getString("text"));
                        comment.setRating(details.getInt("rating"));
                        comment.setPosted_at(details.getString("posted_at"));

                        JSONObject author = details.getJSONObject("_author");
                        comment.setAuthor_name(author.getString("first_name") + " " + author.getString("last_name"));
                        comments.add(comment);
                    }
                    ListCommentsAdapter adapter = new ListCommentsAdapter(context, R.layout.item_list_resto);
                    adapter.addMultiple(comments);
                    listener.hydrateListView(adapter);
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
