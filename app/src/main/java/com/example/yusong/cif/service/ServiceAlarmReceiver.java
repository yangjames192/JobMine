package com.example.yusong.cif.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.yusong.cif.R;
import com.example.yusong.cif.ResultActivity;

import java.util.ArrayList;

/**
 * Created by Yusong on 2016-05-16.
 */
public class ServiceAlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;
    public static final String ACTION = "com.codepath.example.servicesdemo.alarm";

    public void sendNotification() {

    }
    // Triggered by the Alarm periodically (starts the service to run task)
    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.d("serviceAlarm: ", "start");

        InterviewCheckResultReceiver interviewCheckResultReceiver = new InterviewCheckResultReceiver(new Handler());


        Log.d("serviceAlarm: ", "received alarm");

        Intent i = null;

        if("declined".equals(intent.getStringExtra("type"))) {
            i = new Intent(context, DeclinedApplicationCheckService.class);
            interviewCheckResultReceiver.setReceiver(new InterviewCheckResultReceiver.Receiver(){
                @Override
                public void onReceiveResult(int resultCode, Bundle resultData) {
                    if(resultCode == 0) {
                        ArrayList<String> result = resultData.getStringArrayList("Declined");

                        if(result == null) return;  //result is null

                        for(int i = 0; i < result.size(); ++i) {
                            Log.d("serviceAlarm: ", "result received");
                            System.out.println("result received");
                            NotificationCompat.Builder mBuilder =
                                    new NotificationCompat.Builder(context)
                                            .setSmallIcon(R.drawable.ic_launcher)
                                            .setContentTitle("Decline Application")
                                            .setContentText(result.get(i));
                            Intent resultIntent = new Intent(context, ResultActivity.class);

                            PendingIntent resultPendingIntent =
                                    PendingIntent.getActivity(
                                            context,
                                            i,
                                            resultIntent,
                                            PendingIntent.FLAG_UPDATE_CURRENT
                                    );

                            mBuilder.setContentIntent(resultPendingIntent);

                            mBuilder.setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL)
                                    .setWhen(System.currentTimeMillis());
                            mBuilder.setTicker("declined");
                            // Sets an ID for the notification
                            int mNotificationId = i;
                            // Gets an instance of the NotificationManager service
                            NotificationManager mNotifyMgr =
                                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                            // Builds the notification and issues it.
                            mNotifyMgr.notify(mNotificationId, mBuilder.build());
                        }
                    }
                }
            });
        } else {
            i = new Intent(context, InterviewCheckService.class);
            interviewCheckResultReceiver.setReceiver(new InterviewCheckResultReceiver.Receiver(){
                @Override
                public void onReceiveResult(int resultCode, Bundle resultData) {
                    if(resultCode == 0) {
                        ArrayList<String> result = resultData.getStringArrayList("Interview");
                        if(result == null) return;

                        for(int i = 0; i < result.size(); ++i) {
                            Log.d("serviceAlarm: ", "result received");
                            System.out.println("result received");
                            NotificationCompat.Builder mBuilder =
                                    new NotificationCompat.Builder(context)
                                            .setSmallIcon(R.drawable.ic_launcher)
                                            .setContentTitle("Interview")
                                            .setContentText(result.get(i));
                            Intent resultIntent = new Intent(context, ResultActivity.class);

                            // Because clicking the notification opens a new ("special") activity, there's
                            // no need to create an artificial back stack.
                            PendingIntent resultPendingIntent =
                                    PendingIntent.getActivity(
                                            context,
                                            0,
                                            resultIntent,
                                            PendingIntent.FLAG_UPDATE_CURRENT
                                    );

                            mBuilder.setContentIntent(resultPendingIntent);

                            mBuilder.setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL)
                                    .setWhen(System.currentTimeMillis());
                            mBuilder.setTicker("Accept");
                            // Sets an ID for the notification
                            int mNotificationId = 001;
                            // Gets an instance of the NotificationManager service
                            NotificationManager mNotifyMgr =
                                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                            // Builds the notification and issues it.
                            mNotifyMgr.notify(mNotificationId, mBuilder.build());
                        }
                    }
                }
            });
        }

        i.putExtra("receiver", interviewCheckResultReceiver);
        context.startService(i);
    }
}