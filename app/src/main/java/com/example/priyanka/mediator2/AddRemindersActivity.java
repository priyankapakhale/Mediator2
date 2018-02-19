package com.example.priyanka.mediator2;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.priyanka.mediator2.Helpers.AlarmReceiver;
import com.example.priyanka.mediator2.Helpers.MyNotificationPublisher;
import com.example.priyanka.mediator2.Network.ApiClient;
import com.example.priyanka.mediator2.Network.ApiInterface;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

/**
 * Created by priyanka on 1/30/18.
 */

public class AddRemindersActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private String email ;
    private String name, barcode, expirydate, end_date;
    private boolean morning,noon,night;
    private int userid, dosage;

    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TimePicker alarmTimePicker;
    private static AddRemindersActivity inst;
    private TextView alarmTextView;
    Calendar calendar = null;

    private LinearLayout layout_morning, layout_noon, layout_night;
    private TimePicker tm_morning, tm_noon, tm_night;


    public static AddRemindersActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminders);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        email = currentUser.getEmail();

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmTextView = findViewById(R.id.alarmtext);

        userid = getIntent().getIntExtra("userid",0);
        name = getIntent().getStringExtra("name");
        barcode = getIntent().getStringExtra("barcode");
        expirydate = getIntent().getStringExtra("expirydate");
        end_date = getIntent().getStringExtra("end_date");
        dosage = getIntent().getIntExtra("dosage",0);
        morning = getIntent().getBooleanExtra("morning",false);
        noon = getIntent().getBooleanExtra("noon",false);
        night = getIntent().getBooleanExtra("night",false);


        layout_morning = findViewById(R.id.morning_layout);
        layout_noon = findViewById(R.id.noon_layout);
        layout_night = findViewById(R.id.night_layout);

        tm_morning = findViewById(R.id.morning_time);
        tm_noon = findViewById(R.id.noon_time);
        tm_night = findViewById(R.id.night_time);

        tm_night.setIs24HourView(true);
        tm_morning.setIs24HourView(true);
        tm_noon.setIs24HourView(true);

        Log.d("morning",morning+"");
        if(morning)
            layout_morning.setVisibility(View.VISIBLE);
        if(noon)
            layout_noon.setVisibility(View.VISIBLE);
        if(night)
            layout_night.setVisibility(View.VISIBLE);



    }

    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.B_add_reminders: addReminders(); break;
        }
    }

    public void addReminders()
    {
        if(morning)
        {
            int hour,minute;
            hour = tm_morning.getHour();
            minute = tm_morning.getMinute();
            String time = hour+":"+minute;

            Log.d("time",time);

            setAlarm(hour,minute);
        }
        if(noon)
        {
            int hour,minute;
            hour = tm_noon.getHour();
            minute = tm_noon.getMinute();
            String time = hour+":"+minute;

            Log.d("time",time);

            setAlarm(hour,minute);
        }
        if(night)
        {
            int hour,minute;
            hour = tm_night.getHour();
            minute = tm_night.getMinute();
            String time = hour+":"+minute;

            Log.d("time",time);

            setAlarm(hour,minute);
        }


    }

    public void setAlarm(int hour, int minute)
    {
        Intent myIntent = new Intent(AddRemindersActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, myIntent, 0);

        calendar = Calendar.getInstance();


        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);


        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

    }
    public void setAlarmText() {
        String alarmText = "It's time to take "+name;
        alarmTextView.setText(alarmText);
    }





}
