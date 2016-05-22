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
        interviewCheckResultReceiver.setReceiver(new InterviewCheckResultReceiver.Receiver(){
            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                if(resultCode == 0) {
                    Log.d("serviceAlarm: ", "result received");
                    System.out.println("result received");
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(context)
                                    .setSmallIcon(R.drawable.ic_launcher)
                                    .setContentTitle("Decline Application")
                                    .setContentText(resultData.getString("company"));
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
                    mBuilder.setTicker("wocao");
                    // Sets an ID for the notification
                    int mNotificationId = 001;
                    // Gets an instance of the NotificationManager service
                    NotificationManager mNotifyMgr =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    // Builds the notification and issues it.
                    mNotifyMgr.notify(mNotificationId, mBuilder.build());
                }
            }
        });

        Log.d("serviceAlarm: ", "received alarm");

        if("declined".equals(intent.getStringExtra("type"))) {
            Intent i = new Intent(context, DeclinedApplicationCheckService.class);
            i.putExtra("receiver", interviewCheckResultReceiver);
            context.startService(i);
        } else {
            Intent i = new Intent(context, InterviewCheckService.class);
            i.putExtra("receiver", interviewCheckResultReceiver);
            context.startService(i);
        }
    }
}