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

import java.util.Date;
import java.util.List;

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
                    String hora = DateFormat.format("hh:mm:ss",
                            new Date()).toString();
                    ///Guardar en la DB
                    /*EventDataSource eds = new EventDataSource(
                            getApplicationContext());
                    eds.create(transitionName, date, geofence.getRequestId());
                    eds.close();*/
                    tarjetaService=new TarjetaService(getApplicationContext());
                    TarjetaControlDetalle tarjetaControlDetalle;
                    tarjetaControlDetalle=tarjetaService.GetTarjetaDetalleByPuCoDe(sg.getId());
                    tarjetaControlDetalle.setTaCoDeId(sg.getId());
                    tarjetaControlDetalle.setTaCoDeFecha(date);
                    tarjetaControlDetalle.setTaCoDeLatitud(sg.getLatitude());
                    tarjetaControlDetalle.setTaCoDeLongitud(sg.getLongitude());
                    tarjetaControlDetalle.setTaCoDeTiempo(hora);
                    tarjetaService.actualizarTarjetaDetalleBD(tarjetaControlDetalle);
                    GeofenceNotification geofenceNotification = new GeofenceNotification(
                            this);
                    geofenceNotification.displayNotification(sg, transitionType);
                }
            }
        }
    }
}
