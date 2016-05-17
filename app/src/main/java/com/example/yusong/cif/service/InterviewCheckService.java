package com.example.yusong.cif.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Yusong on 2016-05-16.
 */
public class InterviewCheckService extends IntentService {

    // constructor

    public InterviewCheckService() {
        super(InterviewCheckService.class.getName());
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
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        receiver.send(0, Bundle.EMPTY);
    }


}
