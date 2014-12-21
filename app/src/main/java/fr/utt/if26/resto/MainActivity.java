package fr.utt.if26.resto;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);*/

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_actions, menu);
        return true;

    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu_login_register:
                Intent intent = new Intent(Resto.getContext(), RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Resto.getContext().startActivity(intent);
                //finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                //ShowLoginForm();
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
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_search:
                // search action
                return true;
            case R.id.action_location_found:
                // location found
                LocationFound();
                return true;
            case R.id.action_refresh:
                // refresh
                return true;
            case R.id.action_help:
                // help action
                return true;
            case R.id.action_check_updates:
                // check for updates action
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void LocationFound() {
        /*Intent i = new Intent(MainActivity.this, LocationFound.class);
        startActivity(i);*/
    }
}
