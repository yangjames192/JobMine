package com.example.yusong.cif.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.example.yusong.cif.model.ApplicationModel;
import com.example.yusong.cif.model.JobShortList;
import com.example.yusong.cif.utils.Connections;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Map;

/**
 * Created by Yusong on 2016-05-21.
 */
public class DeclinedApplicationCheckService extends IntentService {

    // constructor

    public DeclinedApplicationCheckService() {
        super(DeclinedApplicationCheckService.class.getName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ResultReceiver receiver = intent.getParcelableExtra("receiver");
        //String url = intent.getStringExtra("url");
        Log.d("check service: ", "check service");

        Bundle bundle = new Bundle();

        if(!Connections.isNetWorkAvailable(getApplicationContext())) {
            receiver.send(-1, bundle);  //return code -1
            return;
        }

        String shortListUrl = "https://jobmine.ccol.uwaterloo.ca/psp/SS/EMPLOYEE/WORK/c/UW_CO_STUDENTS.UW_CO_JOB_SLIST.GBL?pslnkid=UW_CO_JOB_SLIST_LINK&FolderPath=PORTAL_ROOT_OBJECT.UW_CO_JOB_SLIST_LINK&IsFolder=false&IgnoreParamTempl=FolderPath%2cIsFolder";
        String applicationUrl = "https://jobmine.ccol.uwaterloo.ca/psp/SS/EMPLOYEE/WORK/c/UW_CO_STUDENTS.UW_CO_APP_SUMMARY.GBL?pslnkid=UW_CO_APP_SUMMARY_LINK&FolderPath=PORTAL_ROOT_OBJECT.UW_CO_APP_SUMMARY_LINK&IsFolder=false&IgnoreParamTempl=FolderPath%2cIsFolder";

        try{
            long start = System.currentTimeMillis();
            SharedPreferences pref = getApplicationContext().getSharedPreferences("User", 0);

            String loginUrl = "https://jobmine.ccol.uwaterloo.ca/psp/SS/?cmd=login";
            Connection.Response res = Jsoup
                    .connect(loginUrl)
                    .data("userid", pref.getString("userName", null))
                    .data("pwd", pref.getString("pass", null))
                    .data("submit", "Submit")
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .method(Connection.Method.POST).timeout(5000).execute();

            Map<String, String> loginCookies = res.cookies();

            long end= System.currentTimeMillis();
            Log.d("time1", " "+(end-start));

            start = System.currentTimeMillis();
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
            end = System.currentTimeMillis();
            Log.d("time2", " "+(end-start));

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

                            /*//table.select("span[id=UW_CO_APPS_VW2_UW_CO_JOB_ID$" + i + "]").text(),
                            table.select("span[id=UW_CO_JB_TITLE2$span$" + i + "]").text(),
                            table.select("span[id=UW_CO_JOBINFOVW_UW_CO_PARENT_NAME$26$$" + i + "]").text(),
                            table.select("span[id=UW_CO_JOBINFOVW_UW_CO_CHAR_DATE$34$$" + i + "]").text(),
                            table.select("span[id=UW_CO_JOBAPP_CT_UW_CO_MAX_RESUMES$35$$" + i + "]").text(),
                            table.select("span[id=UW_CO_TERMCALND_UW_CO_DESCR_30$29$$" + i + "]").text(),
                            table.select("span[id=UW_CO_JOBSTATVW_UW_CO_JOB_STATUS$30$$" + i + "]").text(),
                            table.select("span[id=UW_CO_APPSTATVW_UW_CO_APPL_STATUS$31$$" + i + "]").text()*/

                    String employer = table.select("span[id=UW_CO_JOBINFOVW_UW_CO_PARENT_NAME$26$$" + i + "]").text();
                    String appStatus = table.select("span[id=UW_CO_APPSTATVW_UW_CO_APPL_STATUS$31$$" + i + "]").text();

                    if("Not Selected".equals(appStatus)) {
                        bundle.putString("company", employer);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        if(bundle.isEmpty()) {
            receiver.send(1, bundle);  //return code 1, no matches
            return;
        }

        receiver.send(0, bundle);  //return code 0
    }
}
