package com.example.yusong.cif.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yusong.cif.R;
import com.example.yusong.cif.model.JobShortList;

import java.util.ArrayList;

/**
 * Created by Yusong on 2016-05-13.
 */
public class JobShortListViewAdapter extends BaseAdapter {
    // instance variables
    ArrayList<JobShortList> jobShortLists;
    Context context;

    private static LayoutInflater inflater=null;

    // constructor
    public JobShortListViewAdapter(Context context, ArrayList<JobShortList> jobShortLists) {
        this.jobShortLists = jobShortLists;
        this.context=context;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return jobShortLists.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView;
        rowView = inflater.inflate(R.layout.job_short_list_view, null);

        JobShortList job = jobShortLists.get(position);
        TextView jobTitle = (TextView)rowView.findViewById(R.id.jobTitle);
        TextView company = (TextView)rowView.findViewById(R.id.company);
        TextView location = (TextView)rowView.findViewById(R.id.location);
        TextView lastDay = (TextView)rowView.findViewById(R.id.lastDay);
        TextView numApp = (TextView)rowView.findViewById(R.id.numApp);

        jobTitle.setText(job.jobTitle);
        company.setText(job.employerName);
        location.setText(job.location);
        lastDay.setText(job.lastDateApply);
        numApp.setText(job.numApps);

        if("Already Applied".equals(job.apply)) {
            rowView.setBackgroundColor(Color.parseColor("#008000"));
        }

        return rowView;
    }

    public void add(JobShortList job) {
        jobShortLists.add(job);
    }
}