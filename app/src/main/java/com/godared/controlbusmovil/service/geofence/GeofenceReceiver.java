package com.godared.controlbusmovil.service.geofence;

import android.app.IntentService;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;

import com.godared.controlbusmovil.MainActivity;
import com.godared.controlbusmovil.pojo.TarjetaControlDetalle;
import com.godared.controlbusmovil.service.ITarjetaService;
import com.godared.controlbusmovil.service.TarjetaService;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class GeofenceReceiver extends IntentService {

    public static final int NOTIFICATION_ID = 1;
    ITarjetaService tarjetaService;
    public GeofenceReceiver() {
        super("GeofenceReceiver");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geoEvent = GeofencingEvent.fromIntent(intent);
        if (geoEvent.hasError()) {
            Log.d(MainActivity.TAG, "Error GeofenceReceiver.onHandleIntent");
        } else {
            Log.d(MainActivity.TAG, "GeofenceReceiver : Transition -> "
                    + geoEvent.getGeofenceTransition());

            int transitionType = geoEvent.getGeofenceTransition();

            if (transitionType == Geofence.GEOFENCE_TRANSITION_ENTER
                    || transitionType == Geofence.GEOFENCE_TRANSITION_DWELL
                    || transitionType == Geofence.GEOFENCE_TRANSITION_EXIT) {
                List<Geofence> triggerList = geoEvent.getTriggeringGeofences();

                for (Geofence geofence : triggerList) {
                    SimpleGeofence sg = SimpleGeofenceStore.getInstance()
                            .getSimpleGeofences(this).get(geofence.getRequestId());

                    String transitionName = "";
                    switch (transitionType) {
                        case Geofence.GEOFENCE_TRANSITION_DWELL:
                            transitionName = "dwell";
                            break;

                        case Geofence.GEOFENCE_TRANSITION_ENTER:
                            transitionName = "enter";
                            break;

                        case Geofence.GEOFENCE_TRANSITION_EXIT:
                            transitionName = "exit";
                            break;
                    }
                    String date = DateFormat.format("dd-MM-yyyy",
                            new Date()).toString();
                    String zona="America/Lima";
                    TimeZone timeZone2 = TimeZone.getTimeZone(zona);
                    Calendar cal = Calendar.getInstance(timeZone2);
                    //Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date());

                    ///Guardar en la DB
                    /*EventDataSource eds = new EventDataSource(
                            getApplicationContext());
                    eds.create(transitionName, date, geofence.getRequestId());
                    eds.close();*/
                   tarjetaService=new TarjetaService(getApplicationContext());
                    TarjetaControlDetalle tarjetaControlDetalle;
                    tarjetaControlDetalle=tarjetaService.GetTarjetaDetalleByPuCoDe(sg.getPuCoDeId());
                    //tarjetaControlDetalle.setTaCoDeId(sg.getPuCoDeId());
                    Long value=cal.getTimeInMillis();
                    tarjetaControlDetalle.setTaCoDeFecha( value.toString());
                    tarjetaControlDetalle.setTaCoDeLatitud(sg.getLatitude());
                    tarjetaControlDetalle.setTaCoDeLongitud(sg.getLongitude());

                    String hora = DateFormat.format("HH:mm:ss",
                            new Date()).toString();
                    SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
                    try {
                        cal.setTime(sdf2.parse(hora));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    value=cal.getTimeInMillis();
                    tarjetaControlDetalle.setTaCoDeTiempo(value.toString());
                    tarjetaService.actualizarTarjetaDetalleBD(tarjetaControlDetalle);
                    GeofenceNotification geofenceNotification = new GeofenceNotification(
                            this);
                    geofenceNotification.displayNotification(sg, transitionType);
                }
            }
        }
    }
}
