package com.godared.controlbusmovil.service.geofence;

import android.content.Context;
import android.text.format.DateFormat;
import android.text.format.DateUtils;

import com.godared.controlbusmovil.pojo.PuntoControl;
import com.godared.controlbusmovil.pojo.PuntoControlDetalle;
import com.godared.controlbusmovil.pojo.TarjetaControl;
import com.godared.controlbusmovil.pojo.TarjetaControlDetalle;
import com.godared.controlbusmovil.service.IPuntoControlService;
import com.godared.controlbusmovil.service.ITarjetaService;
import com.godared.controlbusmovil.service.PuntoControlService;
import com.godared.controlbusmovil.service.TarjetaService;
import com.google.android.gms.location.Geofence;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by ronald on 19/06/2017.
 */

public class SimpleGeofenceStore {
    private static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;
    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = GEOFENCE_EXPIRATION_IN_HOURS* DateUtils.HOUR_IN_MILLIS;
    protected HashMap<String, SimpleGeofence> geofences = new HashMap<String, SimpleGeofence>();
    private static SimpleGeofenceStore instance = new SimpleGeofenceStore();

    public static SimpleGeofenceStore getInstance() {
        return instance;
    }

    public SimpleGeofenceStore() {
        /*
      geofences.put("CUARTO 01", new SimpleGeofence("CUARTO 01", -18.002054, -70.251264,
                100, GEOFENCE_EXPIRATION_IN_MILLISECONDS,
                Geofence.GEOFENCE_TRANSITION_ENTER
                        | Geofence.GEOFENCE_TRANSITION_DWELL
                        | Geofence.GEOFENCE_TRANSITION_EXIT));

       geofences.put("QALIWARMA 02", new SimpleGeofence("QALIWARMA 02", -18.012587, -70.253336,
                100, GEOFENCE_EXPIRATION_IN_MILLISECONDS,
                Geofence.GEOFENCE_TRANSITION_ENTER
                        | Geofence.GEOFENCE_TRANSITION_DWELL
                        | Geofence.GEOFENCE_TRANSITION_EXIT));*/
    }


    public HashMap<String, SimpleGeofence> getSimpleGeofences(Context context,int buId) {
        ArrayList<TarjetaControlDetalle> tarjetasDetalle;
        ArrayList<PuntoControlDetalle> puntoControlDetalles;
        TarjetaControl tarjetaControl;
        PuntoControl puntoControl;
        ITarjetaService tarjetaService=new TarjetaService(context);
        IPuntoControlService puntoControlService=new PuntoControlService(context);
        String dateNow = DateFormat.format("dd-MM-yyyy",
                new Date()).toString();
        tarjetasDetalle =tarjetaService.GetAllTarjetaDetalleBDByTaCoActivo(buId,dateNow);//"16-08-2017"
        if (tarjetasDetalle.size()>0) {
            tarjetaControl = tarjetaService.GetTarjetaControlBD(tarjetasDetalle.get(0).getTaCoId());
            puntoControl=puntoControlService.GetPuntoControlBD(tarjetaControl.getPuCoId());
            puntoControlDetalles = puntoControlService.GetAllPuntoControlDetalleBD(tarjetaControl.getPuCoId());
            for (PuntoControlDetalle puntoControlDetalle : puntoControlDetalles) {
                geofences.put(Integer.toString(puntoControlDetalle.getPuCoDeId()),
                        new SimpleGeofence(Integer.toString(puntoControlDetalle.getPuCoDeId()), //puntoControlDetalle.getPuCoDeDescripcion()+"-"+
                        puntoControlDetalle.getPuCoDeLatitud(), puntoControlDetalle.getPuCoDeLongitud(),
                        100, GEOFENCE_EXPIRATION_IN_MILLISECONDS,
                        Geofence.GEOFENCE_TRANSITION_ENTER
                                | Geofence.GEOFENCE_TRANSITION_DWELL
                                | Geofence.GEOFENCE_TRANSITION_EXIT,puntoControlDetalle.getPuCoDeId(),puntoControl.getRuId()));
            }
        }

        return this.geofences;
    }
}
