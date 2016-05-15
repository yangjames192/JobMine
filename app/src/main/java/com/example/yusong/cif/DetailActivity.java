package com.example.yusong.cif;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.example.yusong.cif.adapter.DetailListViewAdapter;
import com.example.yusong.cif.fragment.DetailFragment;
import com.example.yusong.cif.model.JobShortList;

import java.util.ArrayList;

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

        DetailFragment mSearchFragment = new DetailFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.container, mSearchFragment)
                .addToBackStack("7")
                .commit();
    }

}
