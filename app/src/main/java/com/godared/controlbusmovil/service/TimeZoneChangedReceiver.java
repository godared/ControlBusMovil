package com.godared.controlbusmovil.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Ronald on 06/03/2018.
 */

public class TimeZoneChangedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();

        if (action.equals(Intent.ACTION_TIME_CHANGED) |
                action.equals(Intent.ACTION_TIMEZONE_CHANGED))
        {
            Toast.makeText(context, "Detectado in android.ACTION_TIME_CHANGED",
                    Toast.LENGTH_SHORT).show();
            Intent i = new Intent("broadCastTimeZoneChangedReceiver");
            // Data you need to pass to activity
            i.putExtra("message","Enviar Change TIme a la Activity Main");// extras.getString("Hola")

            context.sendBroadcast(i);
        }
    }

}
