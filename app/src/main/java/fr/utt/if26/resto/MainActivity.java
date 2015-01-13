package fr.utt.if26.resto;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Field;

import fr.utt.if26.resto.AsyncTasks.ListRestoTask;
import fr.utt.if26.resto.Interfaces.OnListTaskCompleted;
import fr.utt.if26.resto.Model.Position;
import fr.utt.if26.resto.Model.Restaurant;

public class MainActivity extends Activity implements OnListTaskCompleted, LocationListener {

    // UI references.
    private ListView lv_restos;
    public static Restaurant selected_restaurant = null;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.title_restaurant_near_user);
        //getOverflowMenu();
        /*ActionBar bar = getSupportActionBar();
        //Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ab_background_textured);
        bar.setBackgroundDrawable(getResources().getDrawable(R.drawable.ab_background_textured));*/

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                Resto.userPosition = new Position(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), "");
            }
        }

        lv_restos = (ListView) findViewById(R.id.list_view_restos);
        lv_restos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected_restaurant = (Restaurant) parent.getAdapter().getItem(position);
                Intent intent = new Intent(MainActivity.this, DetailsRestoActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            }
        });
        if (Resto.isInternetconnected()) {
            new ListRestoTask(this, MainActivity.this).execute();
        } else {
            Toast.makeText(this, R.string.network_no_access, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, this);
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    private void getOverflowMenu() {

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(Resto.user == null)
            getMenuInflater().inflate(R.menu.menu_main, menu);
        else
            getMenuInflater().inflate(R.menu.menu_main_connected, menu);
        return true;
    }*/

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if (Resto.user == null)
            getMenuInflater().inflate(R.menu.menu_main, menu);
        else
            getMenuInflater().inflate(R.menu.menu_main_connected, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
        lv_restos.setAdapter(adapter);
    }

    @Override
    public void onLocationChanged(Location location) {
        Resto.userPosition = new Position(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), "");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}



