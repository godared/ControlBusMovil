package com.godared.controlbusmovil.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.godared.controlbusmovil.MainActivity;
import com.godared.controlbusmovil.R;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Timer service tracks the start and end time of timer; service can be placed into the
 * foreground to prevent it being killed when the activity goes away
 */

public  class TimerService extends Service {

    private static final String TAG = TimerService.class.getSimpleName();

    // Start and end times in milliseconds
    private long startTime, endTime;
    private Date fechaServer;
    private long horaTimeServer;
    // Is the service tracking time?
    private boolean isTimerRunning;

    // Foreground notification id
    private static final int NOTIFICATION_ID = 1;

    // Service binder
    private final IBinder serviceBinder = new RunServiceBinder();

    public class RunServiceBinder extends Binder {
        public TimerService getService() {
            return TimerService.this;
        }
    }

    @Override
    public void onCreate() {
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Creating service");
        }
        startTime = 0;
        endTime = 0;
        isTimerRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Starting service");
        }
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Binding service");
        }
        return serviceBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Destroying service");
        }
    }

    /**
     * Starts the timer
     */
    public void startTimer(Date date) {
        if (!isTimerRunning) {
            fechaServer=date;
            Calendar c=Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            c.setTime(date); /* whatever*/
            //c.set(1970,0,1,fechaServer.getHours(),fechaServer.getMinutes(),fechaServer.getSeconds());
            c.setTimeZone(TimeZone.getTimeZone("America/Lima")); //if necessary
            c.getTimeInMillis();
            horaTimeServer=c.getTimeInMillis();
            startTime =  System.currentTimeMillis();//
            isTimerRunning = true;
        }
        else {
            Log.e(TAG, "startTimer request for an already running timer");
        }
    }

    /**
     * Stops the timer
     */
    public void stopTimer() {
        if (isTimerRunning) {
            endTime = System.currentTimeMillis();
            isTimerRunning = false;
        }
        else {
            Log.e(TAG, "stopTimer request for a timer that isn't running");
        }
    }

    /**
     * @return whether the timer is running
     */
    public boolean isTimerRunning() {
        return isTimerRunning;
    }

    /**
     * Returns the  elapsed time
     *
     * @return the elapsed time in seconds
     */
    public long elapsedTime() {
        // If the timer is running, the end time will be zero
        return endTime > startTime ?
                (endTime - startTime) / 1000 :
                ( System.currentTimeMillis() -startTime +horaTimeServer); //
    }

    /**
     * Place the service into the foreground
     */
    public void foreground() {
        startForeground(NOTIFICATION_ID, createNotification());
    }

    /**
     * Return the service to the background
     */
    public void background() {
        stopForeground(true);
    }

    /**
     * Creates a notification for placing the service into the foreground
     *
     * @return a notification for interacting with the service when in the foreground
     */
    private Notification createNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle("CONBUSES Activo")
                .setContentText("Toca para regresar a CONBUSES")
                .setSmallIcon(R.mipmap.ic_launcher);

        Intent resultIntent = new Intent(this, MainActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(this, 0, resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        return builder.build();
    }
}

