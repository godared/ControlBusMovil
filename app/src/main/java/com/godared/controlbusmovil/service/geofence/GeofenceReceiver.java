package com.godared.controlbusmovil.service.geofence;

import android.app.Activity;
import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
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
    int TaCoId;
    private final IBinder mBinder = new LocalBinder();
    Callbacks listenerOrigen;
    public interface Callbacks{
        public void updateOrigen(int taCoDeId,int puCoDeId,double latitude,double longitude);
    }
    public GeofenceReceiver() {
        super("GeofenceReceiver");

    }
    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geoEvent = GeofencingEvent.fromIntent(intent);
        Bundle extra_buId=intent.getExtras();
        BuId=extra_buId.getInt("BUS_ID");
        TaCoId=extra_buId.getInt("TACO_ID");

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
                    ///Guardar en la DB
                    /*EventDataSource eds = new EventDataSource(
                            getApplicationContext());
                    eds.create(transitionName, date, geofence.getRequestId());
                    eds.close();*/
                   tarjetaService=new TarjetaService(getApplicationContext());
                    TarjetaControlDetalle tarjetaControlDetalle;
                    tarjetaControlDetalle=tarjetaService.GetTarjetaDetalleByTaCoPuCoDe(TaCoId,sg.getPuCoDeId());
                    //verificamos si es que no se ha registrado o enviado la geofence
                    if (!tarjetaService.VerificarTarjetaDetalleBDByTaCoDeRegistradoEnviado(tarjetaControlDetalle.getTaCoDeId())) {
                        //aqui notificamos o no dependieto si ya esta registrado

                    }
                    GeofenceNotification geofenceNotification = new GeofenceNotification(
                            this);
                    geofenceNotification.displayNotification(sg, transitionType);

                    listenerOrigen.updateOrigen(tarjetaControlDetalle.getTaCoId(),sg.getPuCoDeId(),sg.getLatitude(),sg.getLongitude());
                }
            }
        }
    }
    //esto es para conectar con la actividad y devolver un valor

    //Here Activity register to the service as Callbacks client
    public void registerClient(Service activity){
        this.listenerOrigen = (Callbacks)activity;
    }
    //returns the instance of the service
    public class LocalBinder extends Binder {
        public GeofenceReceiver getServiceInstance(){
            return GeofenceReceiver.this;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
