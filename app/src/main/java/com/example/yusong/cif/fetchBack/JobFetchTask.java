package com.example.yusong.cif.fetchBack;

import android.app.Activity;
import android.app.Application;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.yusong.cif.callback.AsyncTaskCallbackInterface;
import com.example.yusong.cif.model.ApplicationModel;
import com.example.yusong.cif.model.JobShortList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Yusong on 2016-05-14.
 */
public class JobFetchTask extends AsyncTask<String, Void, Bundle> {

    private Activity mActivity;
    private AsyncTaskCallbackInterface mAsyncTaskCallbackInterface;
    public ArrayList<JobShortList> jobShortLists;
    public ArrayList<ApplicationModel> applicationList;

    public JobFetchTask(Activity activity, AsyncTaskCallbackInterface asyncTaskCallbackInterface) {
        this.mActivity = activity;
        mAsyncTaskCallbackInterface = asyncTaskCallbackInterface;
        jobShortLists = new ArrayList<JobShortList>();
        applicationList = new ArrayList<ApplicationModel>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bundle doInBackground(String...params) {
        Bundle result = new Bundle();

        fetchJob(result, params[0], params[1]);

        return result;
    }

    private Bundle fetchJob(Bundle bundle, String user, String pass) {
        String shortListUrl = "https://jobmine.ccol.uwaterloo.ca/psp/SS/EMPLOYEE/WORK/c/UW_CO_STUDENTS.UW_CO_JOB_SLIST.GBL?pslnkid=UW_CO_JOB_SLIST_LINK&FolderPath=PORTAL_ROOT_OBJECT.UW_CO_JOB_SLIST_LINK&IsFolder=false&IgnoreParamTempl=FolderPath%2cIsFolder";
        String applicationUrl = "https://jobmine.ccol.uwaterloo.ca/psp/SS/EMPLOYEE/WORK/c/UW_CO_STUDENTS.UW_CO_APP_SUMMARY.GBL?pslnkid=UW_CO_APP_SUMMARY_LINK&FolderPath=PORTAL_ROOT_OBJECT.UW_CO_APP_SUMMARY_LINK&IsFolder=false&IgnoreParamTempl=FolderPath%2cIsFolder";

        try{
            String loginUrl = "https://jobmine.ccol.uwaterloo.ca/psp/SS/?cmd=login";
            Connection.Response res = Jsoup
                    .connect(loginUrl)
                    .data("userid", user)
                    .data("pwd", pass)
                    .data("submit", "Submit")
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .method(Connection.Method.POST).timeout(5000).execute();

            Map<String, String> loginCookies = res.cookies();

            //parse Job short list page
            Document doc = Jsoup
                    .connect(shortListUrl)
                    .followRedirects(true)
                    .ignoreContentType(true)
                    .maxBodySize(0)
                    .timeout(6000)
                    .cookies(loginCookies)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .get();

            Element iframe = doc.select("iframe").first();
            String iframeSrc = iframe.attr("src");

            if(iframeSrc != null)
            {
                Document iframeContentDoc = Jsoup.connect(iframeSrc).followRedirects(true)
                        .ignoreContentType(true)
                        .maxBodySize(0)
                        .timeout(6000)
                        .cookies(loginCookies)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6").get();

                Element numApp = iframeContentDoc.select("span[class=PSGRIDCOUNTER]").first();

                int numApps = Integer.parseInt(numApp.text().split(" ")[2]);

                Element table = iframeContentDoc.select("table[id=UW_CO_STUJOBLST$scrolli$0]").get(1);


                for (int i = 0; i < numApps; ++i) {
                    JobShortList job = new JobShortList(
                            table.select("span[id=UW_CO_STUJOBLST_UW_CO_JOB_ID$" + i + "]").text(),
                            table.select("span[id=UW_CO_JOBTITLE_HL$span$" + i + "]").text(),
                            table.select("span[id=UW_CO_JSLIST_VW_UW_CO_PARENT_NAME$" + i + "]").text(),
                            table.select("span[id=UW_CO_JSLIST_VW_UW_CO_EMPLYR_NAME1$" + i + "]").text(),
                            table.select("span[id=UW_CO_JSLIST_VW_UW_CO_WORK_LOCATN$" + i + "]").text(),
                            table.select("span[id=UW_CO_APPLY_HL$span$" + i + "]").text(),
                            table.select("span[id=UW_CO_JSLIST_VW_UW_CO_CHAR_DATE$" + i + "]").text(),
                            table.select("span[id=UW_CO_JOBAPP_CT_UW_CO_MAX_RESUMES$" + i + "]").text());

                    jobShortLists.add(job);
                }

            } //end parse job short list page

            // parse application page
            doc = Jsoup
                    .connect(applicationUrl)
                    .followRedirects(true)
                    .ignoreContentType(true)
                    .maxBodySize(0)
                    .timeout(6000)
                    .cookies(loginCookies)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .get();

            iframe = doc.select("iframe").first();
            iframeSrc = iframe.attr("src");

            if(iframeSrc != null)
            {
                Document iframeContentDoc = Jsoup.connect(iframeSrc).followRedirects(true)
                        .ignoreContentType(true)
                        .maxBodySize(0)
                        .timeout(6000)
                        .cookies(loginCookies)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6").get();

                Element numApp = iframeContentDoc.select("span[class=PSGRIDCOUNTER]").get(1);

                int numApps = Integer.parseInt(numApp.text().split(" ")[2]);

                Element table = iframeContentDoc.select("table[id=UW_CO_APPS_VW2$scrolli$0]").get(1);

                for (int i = 0; i < numApps; ++i) {
                    ApplicationModel app = new ApplicationModel(
                            table.select("span[id=UW_CO_APPS_VW2_UW_CO_JOB_ID$" + i + "]").text(),
                            table.select("span[id=UW_CO_JB_TITLE2$span$" + i + "]").text(),
                            table.select("span[id=UW_CO_JOBINFOVW_UW_CO_PARENT_NAME$26$$" + i + "]").text(),
                            table.select("span[id=UW_CO_JOBINFOVW_UW_CO_CHAR_DATE$34$$" + i + "]").text(),
                            table.select("span[id=UW_CO_JOBAPP_CT_UW_CO_MAX_RESUMES$35$$" + i + "]").text(),
                            table.select("span[id=UW_CO_TERMCALND_UW_CO_DESCR_30$29$$" + i + "]").text(),
                            table.select("span[id=UW_CO_JOBSTATVW_UW_CO_JOB_STATUS$30$$" + i + "]").text(),
                            table.select("span[id=UW_CO_APPSTATVW_UW_CO_APPL_STATUS$31$$" + i + "]").text());

                    applicationList.add(app);
                }
            }

            bundle.putParcelableArrayList("jobShortList", jobShortLists);
            bundle.putParcelableArrayList("applicationList", applicationList);

            return bundle;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(final Bundle bundle) {
        mAsyncTaskCallbackInterface.onOperationComplete(bundle);
    }


}