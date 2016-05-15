package com.example.yusong.cif.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Yusong on 2016-05-12.
 */
public class JobShortList implements Parcelable{
    // instance variables

    public String jobID; // job identifier
    public String jobTitle; //job title
    public String employerName;
    public String unitName;
    public String location;
    public String apply;
    public String lastDateApply;
    public String numApps;

    // constructor

    public JobShortList(String jobID, String jobTitle, String employerName,
                        String unitName, String location, String apply,
                        String lastDateApply, String numApps) {
        this.jobID = jobID;
        this.jobTitle = jobTitle;
        this.employerName = employerName;
        this.unitName = unitName;
        this.location = location;
        this.apply = apply;
        this.lastDateApply = lastDateApply;
        this.numApps = numApps;
    }

    public String toString() {
        return jobID + " " + jobTitle + " " + employerName + unitName + " "
                + location + " " + apply + " " + lastDateApply + " " + numApps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(jobID);
        dest.writeString(jobTitle);
        dest.writeString(employerName);
        dest.writeString(unitName);
        dest.writeString(location);
        dest.writeString(apply);
        dest.writeString(lastDateApply);
        dest.writeString(numApps);
    }

    public JobShortList(Parcel src) {
        this.jobID = src.readString();
        this.jobTitle = src.readString();
        this.employerName = src.readString();
        this.unitName = src.readString();
        this.location = src.readString();
        this.apply = src.readString();
        this.lastDateApply = src.readString();
        this.numApps = src.readString();
    }

    public static final Creator CREATOR = new Creator() {
        @Override
        public JobShortList createFromParcel(Parcel source) {
            return new JobShortList(source);
        }

        @Override
        public JobShortList[] newArray(int size) {
            return new JobShortList[size];
        }
    };
}
