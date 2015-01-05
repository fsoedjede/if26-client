package fr.utt.if26.resto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import fr.utt.if26.resto.Adapters.ListRestoAdapter;
import fr.utt.if26.resto.AsyncTasks.ListRestoTask;
import fr.utt.if26.resto.Interfaces.OnListRestoTaskCompleted;

public class MainActivity extends ActionBarActivity implements OnListRestoTaskCompleted{

    // UI references.
    private ListView lv_restos;
    static final String LIST_RESTO_STATE = "LIST_RESTO_STATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.title_restaurant_near_user);

        lv_restos = (ListView) findViewById(R.id.list_view_restos);
        lv_restos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsResto.class);
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
        if(Resto.user == null)
            getMenuInflater().inflate(R.menu.menu_main, menu);
        else
            getMenuInflater().inflate(R.menu.menu_main_connected, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_menu_login_register:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                return true;
            case R.id.action_menu_search:
                return true;
            case R.id.action_menu_about:
                // refresh
                return true;
            case R.id.action_menu_help:
                // help action
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void hydrateListView(ListRestoAdapter adapter) {
        lv_restos.setAdapter(adapter);
    }
}



