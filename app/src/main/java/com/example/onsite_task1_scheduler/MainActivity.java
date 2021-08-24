package com.example.onsite_task1_scheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.onsite_task1_scheduler.databinding.ActivityMainBinding;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MaterialTimePicker picker;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        createNotificationChannel();

        binding.selectedTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        binding.setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setAlarm();
            }
        });

        binding.CancleAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setAlarm() {
        alarmManager =(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent =new Intent(this,AlarmReceiver.class);
        pendingIntent =PendingIntent.getBroadcast(this,0,intent,0);
//        alarmManager.setInexactRepeating();

    }

    private void showTimePicker() {
        picker =new MaterialTimePicker.Builder()
                .setTheme(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Alarm Time")
                .build();

        picker.show(getSupportFragmentManager(),"scheduler");

        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(picker.getHour() > 12){
                    binding.selectedTime.setText(
                            String.format("%02d",(picker.getHour()-12)+":"+String.format("%02d",picker.getMinute())+"PM"));

                }
                else {
                    binding.selectedTime.setText(picker.getHour()+":"+picker.getMinute()+"AM");
                }

                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,picker.getHour());
                calendar.set(Calendar.MINUTE,picker.getMinute());
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND,0);

            }
        });

    }

    private void createNotificationChannel() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name= "SchedulerReminderChannel";
            String description ="Channnel for Alarm Manager";
            int important= NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel=new NotificationChannel("scheduler",name,important);
            channel.setDescription(description);

            NotificationManager notificationManager =getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
        //15min after alarm
    }
}