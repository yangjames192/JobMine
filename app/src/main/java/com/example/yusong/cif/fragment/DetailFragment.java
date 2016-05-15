package com.example.yusong.cif.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yusong.cif.R;
import com.example.yusong.cif.adapter.TabsPagerAdapter;
import com.example.yusong.cif.callback.AsyncTaskCallbackInterface;
import com.example.yusong.cif.fetchBack.JobFetchTask;

/**
 * Created by Yusong on 2016-05-14.
 */
public class DetailFragment extends Fragment {
    private String mCourse;
    private Activity mActivity;
    private View mView;
    private float defaultElevation;
    private ProgressDialog mProgDialog;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.detail_fragment, container, false);
            generateView();
        }

        return mView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Remove the shadow between the action bar and the tabs
        ActionBar actionBar = ((AppCompatActivity) mActivity).getSupportActionBar();
        if (actionBar != null) {
            defaultElevation = actionBar.getElevation();
            actionBar.setElevation(0);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        // Restore the shadow in the action bar
        ActionBar actionBar = ((AppCompatActivity) mActivity).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(defaultElevation);
        }
    }

    private void setupTabs(Bundle bundle) {
        ViewPager viewPager = (ViewPager) mView.findViewById(R.id.viewpager);
        viewPager.setAdapter(new TabsPagerAdapter(mActivity.getFragmentManager(), bundle));

        TabLayout tabLayout = (TabLayout) mView.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setVisibility(View.VISIBLE);
    }

    private void generateView() {
        showProgressDialog();


        JobFetchTask jobFetchTask = new JobFetchTask(mActivity, new AsyncTaskCallbackInterface() {
            @Override
            public void onOperationComplete(Bundle bundle) {

                setupTabs(bundle);
                LinearLayout searchLayout = (LinearLayout) mView.findViewById(R.id.search_detail_layout);
                searchLayout.setVisibility(View.VISIBLE);

                mProgDialog.dismiss();
            }
        });
        jobFetchTask.execute();
    }

    private void showProgressDialog() {
        if (mProgDialog == null) {
            mProgDialog = new ProgressDialog(mActivity);
            mProgDialog.setMessage("Loading...");
            mProgDialog.setCanceledOnTouchOutside(false);
            mProgDialog.setIndeterminate(false);
            mProgDialog.setCancelable(false);
            mProgDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }

        mProgDialog.show();
    }
}