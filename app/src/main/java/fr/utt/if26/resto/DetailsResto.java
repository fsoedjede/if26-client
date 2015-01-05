package fr.utt.if26.resto;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;


public class DetailsResto extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_resto);

        if (Resto.user == null){
            LinearLayout layout = (LinearLayout)findViewById(R.id.layout_write_comment);
            layout.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.closeButton :
            case android.R.id.home :
                finish();
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
                return true;
            case R.id.action_menu_login_register:
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                return true;
            case R.id.action_menu_help:
                //showHelp();
                return true;
            case R.id.action_menu_about:
                //showAbout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
