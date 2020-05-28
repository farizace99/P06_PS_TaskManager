package com.myapplicationdev.android.p06_ps_taskmanager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class Notification extends BroadcastReceiver {

    int rcode = 12345;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new
                    NotificationChannel("default", "Default Channel",
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.setDescription("This is for default notification");
            notificationManager.createNotificationChannel(channel);
        }

        String data = intent.getStringExtra("task");

        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity
                (context, rcode, i,
                        PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new
                NotificationCompat.Builder(context, "default");
        builder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        builder.setContentTitle("Task Manager Reminder");
        builder.setContentText(data);
        builder.setSmallIcon(android.R.drawable.ic_dialog_info);
        builder.setContentIntent(pIntent);
        builder.setAutoCancel(true);

        android.app.Notification n = builder.build();
        notificationManager.notify(123, n);
    }
}
