package com.example.yusong.cif.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Yusong on 2016-05-16.
 */
public class ServiceAlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;
    public static final String ACTION = "com.codepath.example.servicesdemo.alarm";

    // Triggered by the Alarm periodically (starts the service to run task)
    @Override
    public void onReceive(Context context, Intent intent) {
        final InterviewCheckResultReceiver interviewCheckResultReceiver = new InterviewCheckResultReceiver(new Handler());
        interviewCheckResultReceiver.setReceiver(new InterviewCheckResultReceiver.Receiver() {
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                Log.d("serviceAlarm: ", "result received");
                System.out.println("result received");
            }
        });

        Log.d("serviceAlarm: ", "received alarm");

        Intent i = new Intent(context, InterviewCheckService.class);
        i.putExtra("receiver", interviewCheckResultReceiver);
        context.startService(i);
    }
}