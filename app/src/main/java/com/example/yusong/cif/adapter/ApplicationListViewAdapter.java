package com.example.yusong.cif.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.yusong.cif.R;
import com.example.yusong.cif.model.ApplicationModel;
import com.example.yusong.cif.model.JobShortList;

import java.util.ArrayList;

/**
 * Created by Yusong on 2016-05-15.
 */
public class ApplicationListViewAdapter extends BaseAdapter {
    // instance variables
    ArrayList<ApplicationModel> applicationList;
    Context context;

    private static LayoutInflater inflater=null;

    // constructor
    public ApplicationListViewAdapter(Context context, ArrayList<ApplicationModel> applicationList) {
        this.applicationList = applicationList;
        this.context=context;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return applicationList.size();
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
        rowView = inflater.inflate(R.layout.application_list_view, null);

        ApplicationModel app = applicationList.get(position);
        TextView jobTitle = (TextView)rowView.findViewById(R.id.jobTitle);
        TextView company = (TextView)rowView.findViewById(R.id.company);
        TextView lastDay = (TextView)rowView.findViewById(R.id.lastDay);
        TextView numApp = (TextView)rowView.findViewById(R.id.numApp);

        jobTitle.setText(app.jobTitle);
        company.setText(app.employerName);
        lastDay.setText(app.lastDateApply);
        numApp.setText(app.numApps);

        if("Not Selected".equals(app.appStatus)) {
            rowView.setBackgroundColor(Color.parseColor("#FF0000"));
        } else if("Selected".equals(app.appStatus)) {
            rowView.setBackgroundColor(Color.parseColor("#008000"));
        }

        return rowView;
    }

    public void add(ApplicationModel app) {
        applicationList.add(app);
    }
}
