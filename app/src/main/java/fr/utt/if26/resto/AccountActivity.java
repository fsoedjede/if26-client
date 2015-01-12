package fr.utt.if26.resto;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AccountActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        EditText account_last_name = (EditText) findViewById(R.id.account_lastname);
        EditText account_first_name = (EditText) findViewById(R.id.account_firstname);
        EditText account_email = (EditText) findViewById(R.id.account_email);

        account_last_name.setKeyListener(null);
        account_first_name.setKeyListener(null);
        account_email.setKeyListener(null);

        account_first_name.setText(Resto.user.getFirst_name());
        account_last_name.setText(Resto.user.getLast_name());
        account_email.setText(Resto.user.getEmail());

        Button account_logout = (Button) findViewById(R.id.logout_button);
        account_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : Logout action
                Toast.makeText(getApplicationContext(), "Logout trying failed... Try later", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_connected, menu);
        menu.getItem(0).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Resto.menuActions(this, item);
        return super.onOptionsItemSelected(item);
    }
}
