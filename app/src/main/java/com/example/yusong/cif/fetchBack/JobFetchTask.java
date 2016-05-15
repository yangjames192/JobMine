package com.example.yusong.cif.fetchBack;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.yusong.cif.callback.AsyncTaskCallbackInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Yusong on 2016-05-14.
 */
public class JobFetchTask extends AsyncTask<String, Void, Bundle> {

    private Activity mActivity;
    private AsyncTaskCallbackInterface mAsyncTaskCallbackInterface;


    public JobFetchTask(Activity activity, AsyncTaskCallbackInterface asyncTaskCallbackInterface) {
        this.mActivity = activity;
        mAsyncTaskCallbackInterface = asyncTaskCallbackInterface;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bundle doInBackground(String...params) {
        Bundle result = new Bundle();

        fetchJob(result);

        return result;
    }

    private Bundle fetchJob(Bundle bundle) {

        try{
            /*bundle.putBoolean("valid_return", false);

            bundle.putString("title", data.getJSONObject(0).getString("title"));
            bundle.putString("courseName", data.getJSONObject(0).getString("subject") + " " +
                    data.getJSONObject(0).getString("catalog_number"));
            bundle.putParcelableArrayList(Constants.lectureSectionObjectListKey, lectures);
            bundle.putParcelableArrayList(Constants.tutorialObjectListKey, tutorials);
            bundle.putParcelableArrayList(Constants.testObjectListKey, tests);

            String current_term = (String) Connections.getTerms().get(1);

            // get exam JSONBObject
            String exam_url = Connections.getExamsURL(current_term);
            JSONObject examObject = Connections.getJSON_from_url(exam_url);

            if (examObject != null) {
                JSONArray examData = examObject.getJSONArray("data");

                for (int i = 0; i < examData.length(); i++) {
                    if (examData.getJSONObject(i).getString("course").equals(bundle.getString("courseName"))) {
                        bundle.putBoolean("has_finals", true);
                        JSONArray exam_sections = examData.getJSONObject(i).getJSONArray("sections");
                        for (int j = 0; j < exam_sections.length(); j++) {
                            FinalObject finalObject = new FinalObject();

                            String section = exam_sections.getJSONObject(j).getString("section");

                            finalObject.setSection(section);
                            if (!section.contains("Online")) {
                                finalObject.setTime(exam_sections.getJSONObject(j).getString("start_time") + "-" +
                                        exam_sections.getJSONObject(j).getString("end_time"));
                                finalObject.setLocation(exam_sections.getJSONObject(j).getString("location"));
                                finalObject.setDate(exam_sections.getJSONObject(j).getString("date"));
                                finalObject.setIsOnline(false);
                            } else {
                                finalObject.setIsOnline(true);
                            }
                            finals.add(finalObject);
                        }
                    }
                }
                bundle.putParcelableArrayList(Constants.finalObjectListKey, finals);
            }

            // Fetch course information (description, etc)
            JSONObject courseInfoObject = Connections.getJSON_from_url(Connections.getCourseInfoURL(input));
            JSONObject courseObject = courseInfoObject.getJSONObject("data");

            bundle.putString("description", courseObject.getString("description"));
            String prerequisites = courseObject.getString("prerequisites");
            String antirequisites = courseObject.getString("antirequisites");

            if (prerequisites != null && !prerequisites.equals("null")) {
                bundle.putString("prerequisites", prerequisites);
            }

            if (antirequisites != null && !antirequisites.equals("null")) {
                bundle.putString("antirequisites", antirequisites);
            }*/

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