package com.example.yusong.cif.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.yusong.cif.R;
import com.example.yusong.cif.adapter.DetailListViewAdapter;
import com.example.yusong.cif.model.JobShortList;

import java.util.ArrayList;

/**
 * Created by Yusong on 2016-05-14.
 */
public class JobShortListFragment extends Fragment {
    private Activity mActivity;
    private View mView;
    private Bundle mFetchedResult;
    public ArrayList<JobShortList> jobShortLists;
    public DetailListViewAdapter adapter;
    public ListView lv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = getActivity();
        this.mFetchedResult = this.getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.job_short_list_fragment, container, false);

        jobShortLists = new ArrayList<JobShortList>();
        adapter = new DetailListViewAdapter(getActivity(), jobShortLists);
        lv = (ListView) mView.findViewById(R.id.listView);
        lv.setAdapter(adapter);
        setupLists();

        return mView;
    }

    private void setupLists() {
        jobShortLists = mFetchedResult.getParcelableArrayList("jobShortList");
        for(int i = 0; i < jobShortLists.size(); ++i) {
            adapter.add(jobShortLists.get(i));
            adapter.notifyDataSetChanged();
        }
    }


}
