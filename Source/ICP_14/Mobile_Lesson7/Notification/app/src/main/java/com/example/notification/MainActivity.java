// Created by Vijaya Yeruva on 11/20/2020
// Reference: https://developer.android.com/guide/topics/ui/notifiers/notifications

package com.example.notification;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    // notificationId is a unique int for each notification that you must define
    public int notificationId = 1;
    public String CHANNEL_ID = "one";
    CalendarView calendar;
    TextView displayDate;
    int selectedMonth;
    int selectedDay;
    int selectedYear;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // This is to display the current date in the mobile app.
        displayDate = findViewById(R.id.dateDisplay);
        LocalDate today = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        displayDate.setText("Todays Date: " + format.format(today));
        setDate(today.getMonthValue(), today.getDayOfMonth(), today.getYear());

        // This is the calendar view in the mobile app.
        calendar = findViewById(R.id.calendarView);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                displayDate.setText("Selected Date: " + (month+1) + "/" + dayOfMonth + "/" + year);
                setDate((month+1), dayOfMonth, year);
            }
        });

        // This is the add to calendar button.
        FloatingActionButton addToCal = findViewById(R.id.addToCal);
        addToCal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addCal();
            }
        });

        // Create the NotificationChannel
        // you should execute this code as soon as your app starts
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }

    public void sendNotification(View view) {
        // Set the notification's tap action
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, AlertDetails.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Create a basic notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "one");
        builder.setSmallIcon(R.drawable.notification_icon);
        builder.setContentTitle("Message");
        builder.setContentText("This is an example for notification app");
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText("This is an example for notification app"));
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());
    }

    // Method to add a new event into the calendar.
    public void addCal(){
        Intent intent = new Intent (Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.Events.TITLE, " ");
        Calendar startTime = Calendar.getInstance();
        startTime.set(selectedYear, selectedMonth-1, selectedDay);
        startActivity(intent);
    }

    // Method that helps sets the date.
    public void setDate(int m, int d, int y){
        selectedMonth = m;
        selectedDay = d;
        selectedYear = y;
    }
}