package com.example.yusong.cif.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.yusong.cif.R;
import com.example.yusong.cif.adapter.ApplicationListViewAdapter;
import com.example.yusong.cif.adapter.JobShortListViewAdapter;
import com.example.yusong.cif.model.ApplicationModel;
import com.example.yusong.cif.model.JobShortList;

import java.util.ArrayList;

/**
 * Created by Yusong on 2016-05-14.
 */
public class ApplicationFragment extends Fragment {
    private Activity mActivity;
    private View mView;
    private Bundle mFetchedResult;

    public ArrayList<ApplicationModel> applicationList;
    public ApplicationListViewAdapter adapter;
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
        mView = inflater.inflate(R.layout.application_fragment, container, false);
        applicationList = new ArrayList<ApplicationModel>();
        adapter = new ApplicationListViewAdapter(getActivity(), applicationList);
        lv = (ListView) mView.findViewById(R.id.listView);
        lv.setAdapter(adapter);
        setupLists();
        return mView;
    }

    private void setupLists() {
        applicationList = mFetchedResult.getParcelableArrayList("applicationList");
        for(int i = 0; i < applicationList.size(); ++i) {
            adapter.add(applicationList.get(i));
            adapter.notifyDataSetChanged();
        }
    }


}
