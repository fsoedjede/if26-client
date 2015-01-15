package fr.utt.if26.resto;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

/**
 * Created by soedjede on 15/01/15 for Resto
 */
public class BaseActivity extends Activity {

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
                return true;
            case R.id.action_menu_login_register:
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                return true;
            case R.id.action_menu_profile:
                Intent intent2 = new Intent(this, AccountActivity.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                return true;
            case R.id.action_menu_about:
                // about
                return true;
            case R.id.action_menu_help:
                // help action
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
