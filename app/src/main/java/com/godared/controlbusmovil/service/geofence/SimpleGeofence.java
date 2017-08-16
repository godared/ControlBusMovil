package com.godared.controlbusmovil.service.geofence;

import com.google.android.gms.location.Geofence;

/**
 * Created by ronald on 19/06/2017.
 */

public class SimpleGeofence {
    private final String id;
    private final double latitude;
    private final double longitude;
    private final float radius;
    private long expirationDuration;
    private int transitionType;
    private int loiteringDelay = 60000;
    private int puCoDeId;
    private int ruId;
    public SimpleGeofence(String geofenceId, double latitude, double longitude,
                          float radius, long expiration, int transition, int puCoDeId,int ruId) {
        this.id = geofenceId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.expirationDuration = expiration;
        this.transitionType = transition;
        this.puCoDeId=puCoDeId;
        this.ruId=ruId;
    }
    public String getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public float getRadius() {
        return radius;
    }

    public long getExpirationDuration() {
        return expirationDuration;
    }

    public int getTransitionType() {
        return transitionType;
    }
    public int getPuCoDeId() {
        return puCoDeId;
    }
    public int getRuId() {
        return ruId;
    }
    public Geofence toGeofence() {
        Geofence g = new Geofence.Builder().setRequestId(getId())
                .setTransitionTypes(transitionType)
                .setCircularRegion(getLatitude(), getLongitude(), getRadius())
                .setExpirationDuration(expirationDuration)
                .setLoiteringDelay(loiteringDelay).build();
        return g;
    }

}
