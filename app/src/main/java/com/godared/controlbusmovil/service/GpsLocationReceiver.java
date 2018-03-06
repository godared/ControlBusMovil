package com.godared.controlbusmovil.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Config;
import android.widget.Toast;

import com.godared.controlbusmovil.MainActivity;

/**
 * Created by Ronald on 01/03/2018.
 */

public class GpsLocationReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
            Toast.makeText(context, "Detectado in android.location.PROVIDERS_CHANGED",
                    Toast.LENGTH_SHORT).show();
            //Intent pushIntent = new Intent(context, MainActivity.class);
            //context.startService(pushIntent);
            //Bundle extras = intent.getExtras();
            Intent i = new Intent("broadCastGpsLocationReceiver");
            // Data you need to pass to activity
            i.putExtra("message","Enviar de CHANGE GPS a la Activity");// extras.getString("Hola")

            context.sendBroadcast(i);

        }
    }

}
