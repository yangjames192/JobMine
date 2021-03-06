package com.example.yusong.cif;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.yusong.cif.fragment.DetailFragment;

public class DetailActivity extends AppCompatActivity {

    public String userName;
    public String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent(); // gets the previously created intent
        userName = intent.getStringExtra("userName");
        pass = intent.getStringExtra("pass");

        Bundle bundle = new Bundle();
        bundle.putString("userName", userName);
        bundle.putString("pass", pass);

        DetailFragment mDetailFragment = new DetailFragment();
        mDetailFragment.setArguments(bundle);

        getFragmentManager().beginTransaction()
                .replace(R.id.container, mDetailFragment)
                .addToBackStack("7")
                .commit();
    }

}
