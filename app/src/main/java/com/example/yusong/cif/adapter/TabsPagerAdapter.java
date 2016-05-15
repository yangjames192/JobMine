package com.example.yusong.cif.adapter;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;


import com.example.yusong.cif.R;
import com.example.yusong.cif.fragment.ApplicationFragment;
import com.example.yusong.cif.fragment.InterviewFragment;
import com.example.yusong.cif.fragment.JobShortListFragment;

/**
 * Created by Yusong on 2016-05-14.
 */
public class TabsPagerAdapter extends FragmentStatePagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitle[];
    private Context mContext;
    private Bundle mFetchedResult;

    public TabsPagerAdapter(android.app.FragmentManager fragmentManager, Bundle bundle) {
        super(fragmentManager);
        this.mFetchedResult = bundle;
        this.tabTitle = new String[] {
                "Job Short List",
                "Application",
                "Interviews"
        };
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public android.app.Fragment getItem(int position) {
        switch (position) {
            case 0:
                JobShortListFragment jobShortListFragment = new JobShortListFragment();
                jobShortListFragment.setArguments(mFetchedResult);
                return jobShortListFragment;
            case 1:
                ApplicationFragment applicationFragment = new ApplicationFragment();
                applicationFragment.setArguments(mFetchedResult);
                return applicationFragment;
            case 2:
                InterviewFragment interviewFragment = new InterviewFragment();
                interviewFragment.setArguments(mFetchedResult);
                return interviewFragment;
        }
        return new android.app.Fragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}