package com.godared.controlbusmovil.service.geofence;

import android.text.format.DateUtils;

import com.google.android.gms.location.Geofence;

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
    private SimpleGeofenceStore() {
        geofences.put("Qali Warma", new SimpleGeofence("Qali Warma", -18.013261, -70.253336,
                100, GEOFENCE_EXPIRATION_IN_MILLISECONDS,
                Geofence.GEOFENCE_TRANSITION_ENTER
                        | Geofence.GEOFENCE_TRANSITION_DWELL
                        | Geofence.GEOFENCE_TRANSITION_EXIT));
        geofences.put("Cuarto", new SimpleGeofence("Cuarto", -18.001907, -70.251293,
                100, GEOFENCE_EXPIRATION_IN_MILLISECONDS,
                Geofence.GEOFENCE_TRANSITION_ENTER
                        | Geofence.GEOFENCE_TRANSITION_DWELL
                        | Geofence.GEOFENCE_TRANSITION_EXIT));
    }

    public HashMap<String, SimpleGeofence> getSimpleGeofences() {
        return this.geofences;
    }
}
