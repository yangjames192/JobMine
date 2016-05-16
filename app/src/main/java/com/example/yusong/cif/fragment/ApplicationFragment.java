package com.example.yusong.cif.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yusong.cif.R;

/**
 * Created by Yusong on 2016-05-14.
 */
public class ApplicationFragment extends Fragment {
    private Activity mActivity;
    private View mView;
    private Bundle mFetchedResult;
    private boolean mCourseTaken;
    private String mTakenCourseNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = getActivity();
        this.mFetchedResult = this.getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.application_fragment, container, false);

        //setupLists();


        return mView;
    }

    private void setupLists() {

    }


}
