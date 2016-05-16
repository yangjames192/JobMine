package com.example.yusong.cif.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Yusong on 2016-05-15.
 */
public class ApplicationModel implements Parcelable {
    // instance variables

    public String jobID; // job identifier
    public String jobTitle; //job title
    public String employerName;
    public String lastDateApply;
    public String numApps;
    public String term;
    public String jobStatus;
    public String appStatus;


    // constructor

    public ApplicationModel(String jobID, String jobTitle, String employerName,
                            String lastDateApply, String numApps, String term, String jobStatus, String appStatus) {
        this.jobID = jobID;
        this.jobTitle = jobTitle;
        this.employerName = employerName;
        this.lastDateApply = lastDateApply;
        this.numApps = numApps;
        this.term = term;
        this.jobStatus = jobStatus;
        this.appStatus = appStatus;
    }

    public String toString() {
        return jobID + " " + jobTitle + " " + employerName + lastDateApply + " " + numApps + term + jobStatus + appStatus;
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
        dest.writeString(lastDateApply);
        dest.writeString(numApps);
        dest.writeString(term);
        dest.writeString(jobStatus);
        dest.writeString(appStatus);
    }

    public ApplicationModel(Parcel src) {
        this.jobID = src.readString();
        this.jobTitle = src.readString();
        this.employerName = src.readString();
        this.lastDateApply = src.readString();
        this.numApps = src.readString();
        this.term = src.readString();
        this.jobStatus = src.readString();
        this.appStatus = src.readString();
    }

    public static final Creator CREATOR = new Creator() {
        @Override
        public ApplicationModel createFromParcel(Parcel source) {
            return new ApplicationModel(source);
        }

        @Override
        public ApplicationModel[] newArray(int size) {
            return new ApplicationModel[size];
        }
    };
}