package com.example.yusong.cif.model;

/**
 * Created by Yusong on 2016-05-12.
 */
public class JobShortList {
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
}
