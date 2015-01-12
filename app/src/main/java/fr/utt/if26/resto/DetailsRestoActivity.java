package fr.utt.if26.resto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import fr.utt.if26.resto.Adapters.ListCommentsAdapter;
import fr.utt.if26.resto.AsyncTasks.ListCommentsTask;
import fr.utt.if26.resto.Interfaces.OnListCommentsTaskCompleted;
import fr.utt.if26.resto.Model.Position;
import fr.utt.if26.resto.Model.Rating;
import fr.utt.if26.resto.Model.Restaurant;
import fr.utt.if26.resto.Tools.Utility;


public class DetailsRestoActivity extends ActionBarActivity implements View.OnClickListener, OnListCommentsTaskCompleted {

    private ListView lv_comments;
    private Restaurant restaurant;
    private Context mContext = this;
    int user_rate = 0;
    public static Position actual_position;
    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_resto);

        /*if (Resto.user == null){
            LinearLayout layout = (LinearLayout)findViewById(R.id.layout_write_comment);
            layout.setVisibility(View.GONE);
        }*/

        restaurant = MainActivity.selected_restaurant;
        setTitle(restaurant.getName());
        lv_comments = (ListView) findViewById(R.id.list_comments);

        try {
            //TODO : Change the API data returned and check received & total right value
            if (restaurant.getRatings().getTotal() == 0) {
                throw new NullPointerException();
            } else {
                TextView resto_content_rate = (TextView) findViewById(R.id.resto_content_rate);
                TextView resto_content_rate_count = (TextView) findViewById(R.id.resto_content_rate_count);
                ImageView resto_image_rate = (ImageView) findViewById(R.id.resto_image_rate);
                resto_content_rate.setText(String.valueOf(restaurant.getRatings().getTotal()));
                resto_content_rate_count.setText("(" + restaurant.getRatings().getReceived() + " " + getString(R.string.content_rates) + ")");
                resto_image_rate.setImageResource(Rating.RatingStars(restaurant.getRatings().getTotal()));


            }
        } catch (NullPointerException npe) {
            TextView resto_content_rate = (TextView) findViewById(R.id.resto_content_rate);
            resto_content_rate.setText(R.string.resto_content_no_rate);
            resto_content_rate.setTextSize(14);
            ImageView resto_image_rate = (ImageView) findViewById(R.id.resto_image_rate);
            resto_image_rate.setVisibility(View.GONE);
        }

        TextView resto_content_description = (TextView) findViewById(R.id.resto_content_description);
        resto_content_description.setText(restaurant.getName() + "\nAddress : " + restaurant.getPosition().getAddress());

        LinearLayout resto_action_call = (LinearLayout) findViewById(R.id.resto_action_call);
        resto_action_call.setOnClickListener(this);

        LinearLayout resto_action_website = (LinearLayout) findViewById(R.id.resto_action_website);
        resto_action_website.setOnClickListener(this);

        ImageButton resto_action_comment = (ImageButton) findViewById(R.id.resto_action_comment);
        resto_action_comment.setOnClickListener(this);

        LinearLayout resto_action_direction = (LinearLayout) findViewById(R.id.resto_action_direction);
        resto_action_direction.setOnClickListener(this);

        ImageButton image_add_picture = (ImageButton) findViewById(R.id.image_add_picture);
        image_add_picture.setOnClickListener(this);

        if (Resto.isInternetconnected()) {
            new ListCommentsTask(this, DetailsRestoActivity.this).execute(restaurant.get_id());
        } else {
            Toast.makeText(this, R.string.network_no_access, Toast.LENGTH_LONG).show();
        }

        /*Bundle bundle = new Bundle();
        bundle.putString("r_id", restaurant.get_id());
        ListCommentsFragment fragment = new ListCommentsFragment();
        fragment.setArguments(bundle);*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(Resto.user != null){
            getMenuInflater().inflate(R.menu.menu_main_connected, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
        menu.getItem(0).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Resto.menuActions(this, item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void hydrateListView(ListCommentsAdapter adapter) {
        lv_comments.setAdapter(adapter);
        Utility.setListViewHeightBasedOnChildren(lv_comments);
    }

    public void openWebPage(String url) {
        Uri website = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, website);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void dialPhoneNumber(String tel) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + tel));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.resto_action_call:
                if (restaurant.getTel().length() > 6)
                    dialPhoneNumber(restaurant.getTel());
                else
                    Toast.makeText(v.getContext(), "No available phone number", Toast.LENGTH_LONG).show();
                break;
            case R.id.resto_action_website:
                if (restaurant.getUrl().length() > 12)
                    openWebPage(restaurant.getUrl());
                else
                    Toast.makeText(v.getContext(), "No available website", Toast.LENGTH_LONG).show();
                break;
            case R.id.resto_action_direction:
                Intent intent = new Intent(DetailsRestoActivity.this, DirectionsActivity.class);
                actual_position = restaurant.getPosition();
                startActivity(intent);
                break;
            case R.id.resto_action_comment:
                LayoutInflater inflater = LayoutInflater.from(mContext);
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(null);
                View customDialogView = inflater.inflate(R.layout.custom_rating_dialog, null, false);
                RatingBar ratingBar = (RatingBar) customDialogView.findViewById(R.id.resto_ratingBar);
                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        user_rate = (int) rating;
                        //TODO : Do add comment action
                    }
                });
                Button btn_ok = (Button) customDialogView.findViewById(R.id.btn_ok);
                btn_ok.setOnClickListener(this);
                builder.setView(customDialogView);
                mAlertDialog = builder.create();
                mAlertDialog.show();
                break;
            case R.id.image_add_picture:
                Intent intent2 = new Intent(DetailsRestoActivity.this, GalleryActivity.class);
                startActivity(intent2);
                //Toast.makeText(v.getContext(), "You can add a new picture of the restaurant soon.", Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_ok:
                Toast.makeText(v.getContext(), "You should be able to add your comment soon.", Toast.LENGTH_LONG).show();
                mAlertDialog.dismiss();
                break;
        }
    }
}
