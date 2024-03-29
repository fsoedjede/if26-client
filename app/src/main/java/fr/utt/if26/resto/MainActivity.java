package fr.utt.if26.resto;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.List;

import fr.utt.if26.resto.AsyncTasks.ListRestoTask;
import fr.utt.if26.resto.AsyncTasks.SearchListRestoTask;
import fr.utt.if26.resto.Interfaces.OnListTaskCompleted;
import fr.utt.if26.resto.Model.Position;
import fr.utt.if26.resto.Model.Restaurant;

public class MainActivity extends BaseActivity implements OnListTaskCompleted, LocationListener, SearchView.OnQueryTextListener {

    // UI references.
    private ListView lv_restos;
    public static Restaurant selected_restaurant = null;
    private LocationManager locationManager;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.title_restaurant_near_user);

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
        invalidateOptionsMenu();
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if (Resto.user == null)
            getMenuInflater().inflate(R.menu.menu_main, menu);
        else
            getMenuInflater().inflate(R.menu.menu_main_connected, menu);

        MenuItem searchItem = menu.findItem(R.id.action_menu_search);
        mSearchView = (SearchView) searchItem.getActionView();
        setupSearchView(searchItem);
        return super.onPrepareOptionsMenu(menu);
    }

    private void setupSearchView(MenuItem searchItem) {

        if (isAlwaysExpanded()) {
            mSearchView.setIconifiedByDefault(false);
        } else {
            searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
                    | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        }

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();

            SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
            for (SearchableInfo inf : searchables) {
                if (inf.getSuggestAuthority() != null
                        && inf.getSuggestAuthority().startsWith("applications")) {
                    info = inf;
                }
            }
            mSearchView.setSearchableInfo(info);
        }

        mSearchView.setOnQueryTextListener(this);
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
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}

    public boolean onQueryTextChange(String newText) {
        return false;
    }

    public boolean onQueryTextSubmit(String query) {
        if (Resto.isInternetconnected()) {
            new SearchListRestoTask(this, MainActivity.this).execute(query);
        } else {
            Toast.makeText(this, R.string.network_no_access, Toast.LENGTH_LONG).show();
        }
        //mStatusView.setText("Query = " + query + " : submitted");
        return false;
    }

    protected boolean isAlwaysExpanded() {
        return false;
    }
}



