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
/*
        // get default divider
        int[] attrs = {android.R.attr.listDivider};
        TypedArray ta = mActivity.obtainStyledAttributes(attrs);
        Drawable divider = ta.getDrawable(0);
        ta.recycle();

        LinearLayout mLecLinearLayout = (LinearLayout) mView.findViewById(R.id.lec_list);
        SectionListAdapter LecAdapter = new SectionListAdapter(mActivity, R.layout.section_item, mFetchedResult);
        for (int i = 0; i < LecAdapter.getCount(); i++) {
            View view = LecAdapter.getView(i, null, mLecLinearLayout);
            mLecLinearLayout.addView(view);
            mLecLinearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE | LinearLayout.SHOW_DIVIDER_BEGINNING);
            mLecLinearLayout.setDividerDrawable(divider);
        }

        TextView lec = (TextView) mView.findViewById(R.id.lec);
        if (lec != null) {
            lec.setVisibility(View.VISIBLE);
        }

        if (mFetchedResult.getBoolean("has_tst", false)) {
            TextView tst = (TextView) mView.findViewById(R.id.tst);
            if (tst != null) {
                tst.setVisibility(View.VISIBLE);
            }

            LinearLayout mTstLinearLayout = (LinearLayout) mView.findViewById(R.id.tst_list);
            TstListAdapter TstAdapter = new TstListAdapter(mActivity, R.layout.section_item, mFetchedResult);
            for (int i = 0; i < TstAdapter.getCount(); i++) {
                View view = TstAdapter.getView(i, null, mTstLinearLayout);
                mTstLinearLayout.addView(view);
                mTstLinearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE | LinearLayout.SHOW_DIVIDER_BEGINNING);
                mTstLinearLayout.setDividerDrawable(divider);
            }
        }

        if (mFetchedResult.getBoolean("has_tut", false)) {
            TextView tut = (TextView) mView.findViewById(R.id.tut);
            if (tut != null) {
                tut.setVisibility(View.VISIBLE);
            }

            LinearLayout mTutLinearLayout = (LinearLayout) mView.findViewById(R.id.tut_list);
            TutListAdapter TutAdapter = new TutListAdapter(mActivity, R.layout.section_item, mFetchedResult);
            for (int i = 0; i < TutAdapter.getCount(); i++) {
                View view = TutAdapter.getView(i, null, mTutLinearLayout);
                mTutLinearLayout.addView(view);
                mTutLinearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE | LinearLayout.SHOW_DIVIDER_BEGINNING);
                mTutLinearLayout.setDividerDrawable(divider);
            }
        }*/
    }


}
