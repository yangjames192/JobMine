package com.example.yusong.cif;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.yusong.cif.adapter.DetailListViewAdapter;
import com.example.yusong.cif.fragment.DetailFragment;
import com.example.yusong.cif.model.JobShortList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {

    public String userName;
    public String pass;
    public ProgressDialog progress;
    public DetailListViewAdapter adapter;
    public ListView lv;
    public ArrayList<JobShortList> jobShortLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent(); // gets the previously created intent
        userName = intent.getStringExtra("userName");
        pass = intent.getStringExtra("pass");

        jobShortLists = new ArrayList<JobShortList>();
        adapter = new DetailListViewAdapter(this, jobShortLists);
        /*lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);*/

        //new AddStringTask().execute();

        progress = new ProgressDialog(this);
        //progress.show();

        DetailFragment mSearchFragment = new DetailFragment();
        Bundle args = new Bundle();

        getFragmentManager().beginTransaction()
                .replace(R.id.container, mSearchFragment)
                .addToBackStack("7")
                .commit();
    }

    class AddStringTask extends AsyncTask<Void, JobShortList, Void> {
        @Override
        protected Void doInBackground(Void... unused) {

            try {
                String loginUrl = "https://jobmine.ccol.uwaterloo.ca/psp/SS/?cmd=login";
                Connection.Response res = Jsoup
                        .connect(loginUrl)
                        .data("userid", userName)
                        .data("pwd", pass)
                        .data("submit", "Submit")
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .method(Connection.Method.POST).timeout(5000).execute();

                Map<String, String> loginCookies = res.cookies();

                //Now you can parse any page you want, as long as you pass the cookies
                String url = "https://jobmine.ccol.uwaterloo.ca/psp/SS/EMPLOYEE/WORK/c/UW_CO_STUDENTS.UW_CO_JOB_SLIST.GBL?pslnkid=UW_CO_JOB_SLIST_LINK&FolderPath=PORTAL_ROOT_OBJECT.UW_CO_JOB_SLIST_LINK&IsFolder=false&IgnoreParamTempl=FolderPath%2cIsFolder";
                Document doc = Jsoup
                        .connect(url)
                        .followRedirects(true)
                        .ignoreContentType(true)
                        .maxBodySize(0)
                        .timeout(6000)
                        .cookies(loginCookies)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .execute().parse();

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
                        publishProgress(job);

                    }

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(JobShortList... item) {
            adapter.add(item[0]);
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(Void unused) {
            progress.dismiss();
        }
    }
}
