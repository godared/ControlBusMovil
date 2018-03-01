package com.godared.controlbusmovil.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by Ronald on 01/03/2018.
 */

public class GpsLocationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
            Toast.makeText(context, "Detectado in android.location.PROVIDERS_CHANGED",
                    Toast.LENGTH_SHORT).show();
            //Intent pushIntent = new Intent(context, LocalService.class);
            //context.startService(pushIntent);
        }
    }

}
