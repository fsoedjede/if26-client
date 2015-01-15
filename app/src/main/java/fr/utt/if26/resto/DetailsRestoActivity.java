package fr.utt.if26.resto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DecodingType;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.FailReason;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoadingListener;

import fr.utt.if26.resto.AsyncTasks.AddCommentTask;
import fr.utt.if26.resto.AsyncTasks.ListCommentsTask;
import fr.utt.if26.resto.Interfaces.OnListTaskCompleted;
import fr.utt.if26.resto.Model.Position;
import fr.utt.if26.resto.Model.Restaurant;
import fr.utt.if26.resto.Tools.Utility;


public class DetailsRestoActivity extends Activity implements View.OnClickListener, OnListTaskCompleted {

    private ListView lv_comments;
    private Restaurant restaurant;
    private Context mContext = this;
    int user_rate = 0;
    public static Position actual_position;
    private AlertDialog mAlertDialog;
    private String comment;

    protected ImageLoader imageLoader = ImageLoader.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_resto);

        if (Resto.user == null) {
            LinearLayout layout = (LinearLayout)findViewById(R.id.layout_write_comment);
            layout.setVisibility(View.GONE);
            ImageButton image_add_picture = (ImageButton) findViewById(R.id.image_add_picture);
            image_add_picture.setVisibility(View.GONE);
        }

        restaurant = MainActivity.selected_restaurant;
        setTitle(restaurant.getName());
        lv_comments = (ListView) findViewById(R.id.list_comments);

        final ImageView image_resto = (ImageView) findViewById(R.id.image_resto);
        //new LoadImage().execute(Resto.server_address + "/photos/" + restaurant.get_main_picture() + "/data");*/
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ajax_loader)
                .cacheInMemory()
                .cacheOnDisc()
                .decodingType(DecodingType.FAST)
                .build();
        imageLoader.displayImage(Resto.server_address + "/photos/" + restaurant.get_main_picture() + "/data", image_resto, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted() {
                // do nothing
            }

            @Override
            public void onLoadingFailed(FailReason failReason) {
                image_resto.setImageResource(R.drawable.restaurant_1);
                switch (failReason) {
                    case MEMORY_OVERFLOW:
                        imageLoader.clearMemoryCache();
                        break;
                }
            }

            @Override
            public void onLoadingComplete() {
                // do nothing
                imageLoader.stop();
            }
        });

        try {
            if (restaurant.getRatings().getReceived() == 0) {
                throw new NullPointerException();
            } else {
                TextView resto_content_rate = (TextView) findViewById(R.id.resto_content_rate);
                TextView resto_content_rate_count = (TextView) findViewById(R.id.resto_content_rate_count);
                RatingBar resto_image_rate = (RatingBar) findViewById(R.id.resto_image_rate);
                resto_content_rate.setText(String.valueOf(restaurant.getRatings().getReceived()));
                resto_content_rate_count.setText("(" + restaurant.getRatings().getTotal() + " " + getString(R.string.content_rates) + ")");
                resto_image_rate.setRating((float) restaurant.getRatings().getReceived());
                //resto_image_rate.setImageResource(Rating.RatingStars(restaurant.getRatings().getTotal()));
            }
        } catch (NullPointerException npe) {
            TextView resto_content_rate = (TextView) findViewById(R.id.resto_content_rate);
            resto_content_rate.setText(R.string.resto_content_no_rate);
            resto_content_rate.setTextSize(14);
            RatingBar resto_image_rate = (RatingBar) findViewById(R.id.resto_image_rate);
            resto_image_rate.setVisibility(View.GONE);
        }

        TextView resto_content_description = (TextView) findViewById(R.id.resto_content_description);
        resto_content_description.setText(restaurant.getName() + "\n" + restaurant.getPosition().getAddress());

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
    }

    @Override
    protected void onDestroy() {
        imageLoader.stop();
        super.onDestroy();
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if(Resto.user != null){
            getMenuInflater().inflate(R.menu.menu_main_connected, menu);
            LinearLayout layout = (LinearLayout)findViewById(R.id.layout_write_comment);
            layout.setVisibility(View.VISIBLE);
            ImageButton image_add_picture = (ImageButton) findViewById(R.id.image_add_picture);
            image_add_picture.setVisibility(View.VISIBLE);
        } else {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
        menu.getItem(0).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.closeButton:
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
                break;
            case R.id.action_menu_login_register:
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                break;
            case R.id.action_menu_profile:
                Intent intent2 = new Intent(this, AccountActivity.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                break;
            case R.id.action_menu_about:
                // about
                break;
            case R.id.action_menu_help:
                // help action
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void hydrateListView(ArrayAdapter adapter) {
        lv_comments.setAdapter(adapter);
        if(lv_comments.getAdapter().getCount() == 0) {
            lv_comments.setVisibility(View.GONE);
            TextView tv_no_comment = (TextView) findViewById(R.id.list_comments_none);
            tv_no_comment.setText("No comment found. Add your own now ...");
        }
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
                EditText resto_input_comment = (EditText) findViewById(R.id.resto_input_comment);
                if(resto_input_comment.getText().toString().length() < 3) {
                    Toast.makeText(this, R.string.content_comment_missing, Toast.LENGTH_LONG).show();
                } else {
                    comment = resto_input_comment.getText().toString();
                    LayoutInflater inflater = LayoutInflater.from(mContext);
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle(null);
                    View customDialogView = inflater.inflate(R.layout.custom_rating_dialog, null, false);
                    RatingBar ratingBar = (RatingBar) customDialogView.findViewById(R.id.resto_ratingBar);
                    ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                            user_rate = (int) rating;
                        }
                    });
                    Button btn_ok = (Button) customDialogView.findViewById(R.id.btn_ok);
                    btn_ok.setOnClickListener(this);
                    builder.setView(customDialogView);
                    mAlertDialog = builder.create();
                    mAlertDialog.show();
                }
                break;
            case R.id.image_add_picture:
                Intent intent2 = new Intent(DetailsRestoActivity.this, GalleryActivity.class);
                intent2.putExtra("fr.utt.if26._id", restaurant.get_id());
                startActivity(intent2);
                //Toast.makeText(v.getContext(), "You can add a new picture of the restaurant soon.", Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_ok:
                if (Resto.isInternetconnected()) {
                    new AddCommentTask(this).execute(restaurant.get_id(), comment, String.valueOf(user_rate));
                } else {
                    Toast.makeText(this, R.string.network_no_access, Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(v.getContext(), "You should be able to add your comment soon.", Toast.LENGTH_LONG).show();
                mAlertDialog.dismiss();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

}
