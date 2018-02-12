package com.godared.controlbusmovil.service.geofence;

import android.app.IntentService;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;

import com.godared.controlbusmovil.MainActivity;
import com.godared.controlbusmovil.R;
import com.godared.controlbusmovil.adapter.TarjetaAdaptadorRV;
import com.godared.controlbusmovil.pojo.TarjetaControl;
import com.godared.controlbusmovil.pojo.TarjetaControlDetalle;
import com.godared.controlbusmovil.pojo.TarjetaDetalleBitacoraMovil;
import com.godared.controlbusmovil.service.ITarjetaService;
import com.godared.controlbusmovil.service.TarjetaService;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class GeofenceReceiver extends IntentService {

    public static final int NOTIFICATION_ID = 1;
    ITarjetaService tarjetaService;
    int BuId;
    public GeofenceReceiver() {
        super("GeofenceReceiver");

    }
    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geoEvent = GeofencingEvent.fromIntent(intent);
        Bundle extra_buId=intent.getExtras();
        BuId=extra_buId.getInt("BUS_ID");
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
                            .getSimpleGeofences(this,BuId).get(geofence.getRequestId());

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
                    //verificamos si es que no se ha registrado o enviado la geofence
                    if (!tarjetaService.VerificarTarjetaDetalleBDByTaCoDeRegistradoEnviado(tarjetaControlDetalle.getTaCoDeId())) {
                        //tarjetaControlDetalle.setTaCoDeId(sg.getPuCoDeId());
                        Long value = cal.getTimeInMillis();
                        tarjetaControlDetalle.setTaCoDeFecha(value.toString());
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
                        value = cal.getTimeInMillis();
                        tarjetaControlDetalle.setTaCoDeTiempo(value.toString());
                        //actualizamos en la base de datos local
                        tarjetaService.actualizarTarjetaDetalleBD(tarjetaControlDetalle);
                        //Actualizamos el Registro
                        TarjetaDetalleBitacoraMovil tarjetaDetalleBitacoraMovil;
                        tarjetaDetalleBitacoraMovil=tarjetaService.obtenerTarjetaDetalleBitacoraMovilByTaCoDe(tarjetaControlDetalle.getTaCoDeId());
                        tarjetaDetalleBitacoraMovil.setTaDeBiMoRegistradoId(1);
                        tarjetaService.actualizarTarjetaDetalleBitacoraMovilBD(tarjetaControlDetalle.getTaCoDeId(),tarjetaDetalleBitacoraMovil);
                        //Actualizamos en el servidor
                        tarjetaService.UpdateTarjetaDetalleRest(tarjetaControlDetalle);
                        //verificamos si todo el detalle ya tienen registros para activar finaliza en la cabecera TarjetaCOntrol
                        tarjetaService.VerificarActualizaTarjetaFinaliza(tarjetaControlDetalle.getTaCoId());

                    }
                    GeofenceNotification geofenceNotification = new GeofenceNotification(
                            this);
                    geofenceNotification.displayNotification(sg, transitionType);
                    //////
                    ArrayList<TarjetaControlDetalle> tarjetaControls;
                    tarjetaControls=tarjetaService.GetAllTarjetaDetalleBDById(tarjetaControlDetalle.getTaCoId());
                }
            }
        }
    }
}
