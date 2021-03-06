package com.example.onsite_task1_scheduler;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.net.URI;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        //String Event_Name=MainActivity.getInstance().getEvent_Name();
//        MediaPlayer ringTone;
//        ringTone =MediaPlayer.create(MainActivity.getInstance(),R.raw.nice_alarm_sound);
//        ringTone.start();
        Uri alarmSound = RingtoneManager. getDefaultUri (RingtoneManager. TYPE_NOTIFICATION );
        Drawable myDrawable = context.getDrawable(R.drawable.event);
        Bitmap Image      = ((BitmapDrawable) myDrawable).getBitmap();
//        MediaPlayer mp = MediaPlayer. create (MainActivity.getInstance().getApplicationContext(), alarmSound);
//        mp.start();

        Intent i=new Intent(context,DestinationActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent= PendingIntent.getActivity(context,0,i,0);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,"scheduler")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Scheduler Alarm Manager")
                .setContentText(" Time Over")
                .setLargeIcon(Image)
                .setSound(alarmSound)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123, builder.build());
    }
}
