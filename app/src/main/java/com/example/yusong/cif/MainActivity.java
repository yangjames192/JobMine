package com.example.yusong.cif;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yusong.cif.utils.Connections;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("User", 0);
        if(pref.getBoolean("isLoggedIn", false)) {
            startDetailActivity(pref.getString("userName", "0"), pref.getString("pass", "0"));
        }
    }

    public void goToMain(View view) {
        EditText userName =  ((EditText)findViewById(R.id.user));
        EditText pass = ((EditText)findViewById(R.id.passID));
        String user = userName.getText().toString();
        String password = pass.getText().toString();

        SharedPreferences pref = getApplicationContext().getSharedPreferences("User", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putString("userName", user);
        editor.putString("pass", password);
        editor.commit();

        startDetailActivity(user, password);
    }

    public void startDetailActivity(String user, String password) {
        if(!Connections.isNetWorkAvailable(this)) {
            Toast.makeText(this, "Network unavailable, Please retry!",
                    Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("userName", user);
            intent.putExtra("pass", password);
            startActivity(intent);
            finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
