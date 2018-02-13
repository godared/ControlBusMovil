package com.godared.controlbusmovil.service.geofence;

import android.Manifest;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import com.godared.controlbusmovil.MainActivity;
import com.godared.controlbusmovil.R;
import com.godared.controlbusmovil.pojo.Georeferencia;
import com.godared.controlbusmovil.service.GeoreferenciaService;
import com.godared.controlbusmovil.service.IGeoreferenciaService;
import com.godared.controlbusmovil.service.ITarjetaService;
import com.godared.controlbusmovil.service.TarjetaService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GeolocationService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, ResultCallback<Status> {
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 5000; //1000 es un segundo
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 3;
    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    int BuId;
    int TaCoId;
    private PendingIntent mPendingIntent;
    //Variable para enlazar con la atividad
    private int counter = 0, incrementby = 1;
    private static boolean isRunning = false;

    ArrayList<Messenger> mClients = new ArrayList<Messenger>(); // Keeps track of all current registered clients.
    int mValue = 0; // Holds last value set by a client.
    static final int MSG_REGISTER_CLIENT = 1;
    static final int MSG_UNREGISTER_CLIENT = 2;
    static final int MSG_SET_INT_VALUE = 3;
    static final int MSG_SET_STRING_VALUE = 4;
    final Messenger mMessenger = new Messenger(new IncomingHandler()); // Target we publish for clients to send messages to IncomingHandler.
    class IncomingHandler extends Handler { // Handler of incoming messages from clients.
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_REGISTER_CLIENT:
                    mClients.add(msg.replyTo);
                    break;
                case MSG_UNREGISTER_CLIENT:
                    mClients.remove(msg.replyTo);
                    break;
                case MSG_SET_INT_VALUE:
                    incrementby = msg.arg1;
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }


    public GeolocationService() {
    }

    @Override
    public void onStart(Intent intent, int startId) {
        buildGoogleApiClient();
        Bundle extra_buId=intent.getExtras();
        BuId=extra_buId.getInt("BUS_ID");
        TaCoId=extra_buId.getInt("TACO_ID");
        //BuId=11;
        //TaCoId=5238;
        mGoogleApiClient.connect();

    }
   /* @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (isLocationPermissionGranted() && intent != null) {
            registerGeofences();
        } else {
            this.stopSelf(startId);
        }
        return START_REDELIVER_INTENT;
    }*/
    private boolean isLocationPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("MyService", "Service Started.");
        isRunning = true;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        Log.i("MyService", "Service Stopped.");
        isRunning = false;
    }

    protected void registerGeofences() {
        if (MainActivity.geofencesAlreadyRegistered) {
            return;
        }

        Log.d(MainActivity.TAG, "Registering Geofences");

        HashMap<String, SimpleGeofence> geofences = SimpleGeofenceStore
                .getInstance().getSimpleGeofences(this,BuId);
        if(geofences.size()>0) {//Tiene que ver algun Geofence sino da error
            GeofencingRequest.Builder geofencingRequestBuilder = new GeofencingRequest.Builder();
            for (Map.Entry<String, SimpleGeofence> item : geofences.entrySet()) {
                SimpleGeofence sg = item.getValue();

                geofencingRequestBuilder.addGeofence(sg.toGeofence());
            }

            GeofencingRequest geofencingRequest = geofencingRequestBuilder.build();

            mPendingIntent = requestPendingIntent();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            LocationServices.GeofencingApi.addGeofences(mGoogleApiClient,
                    geofencingRequest, mPendingIntent).setResultCallback(this);
        }
            MainActivity.geofencesAlreadyRegistered = true;

    }
    private void removeGeoFences() {
        mPendingIntent = requestPendingIntent();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            try {
                LocationServices.GeofencingApi.removeGeofences(
                        mGoogleApiClient,
                        mPendingIntent
                ).setResultCallback(this);
            } catch (SecurityException securityException) {
                Log.e("fallo", securityException.toString());
            }
        }
    }
    private PendingIntent requestPendingIntent() {

        if (null != mPendingIntent) {

            return mPendingIntent;
        } else {

            Intent intent = new Intent(this, GeofenceReceiver.class);
            intent.putExtra("BUS_ID",BuId);
            return PendingIntent.getService(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

        }
    }

    public void broadcastLocationFound(Location location) {

        Intent intent = new Intent("com.godared.controlbusmovil.service.geofence");//me.hoen.geofence_21.geolocation.service
        intent.putExtra("latitude", location.getLatitude());
        intent.putExtra("longitude", location.getLongitude());
        intent.putExtra("done", 1);
        //Esto envia un bradcast al MasPsFragment tener en cuenta la ruta del intent
        sendBroadcast(intent);
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
        //podemos obtener la ultima localizacion conocida al conectarse
        //Location mlastLocation=LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }
    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }


    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(MainActivity.TAG, "Connected to GoogleApiClient");
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(MainActivity.TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(MainActivity.TAG,
                "Connection failed: ConnectionResult.getErrorCode() = "
                        + connectionResult.getErrorCode());
    }

    @Override
    public void onResult(@NonNull Status status) {
        if (status.isSuccess()) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.geofences_added), Toast.LENGTH_SHORT)
                    .show();
        } else {
            MainActivity.geofencesAlreadyRegistered = false;
            String errorMessage = getErrorString(this, status.getStatusCode());
            Toast.makeText(getApplicationContext(), errorMessage,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(MainActivity.TAG,
                "new location : " + location.getLatitude() + ", "
                        + location.getLongitude() + ". "
                        + location.getAccuracy());
        broadcastLocationFound(location);
        if (this.TaCoId>0) {
            if (!MainActivity.geofencesAlreadyRegistered) {
                registerGeofences();
            }
        }
        else{
            removeGeoFences();
        }
        //es aqui que vamos a guardar la georeferencia(movimientos del bus
        GuardarGeoreferenciaRest(this,location);


    }
    //Con este procedimiento guardamos en el servidor rest de la nube
    //sino en la base de datos del movil
    private void GuardarGeoreferenciaRest(Context context,Location location){
        IGeoreferenciaService _georeferenciaService=new GeoreferenciaService(context);
        Georeferencia _georeferencia=new Georeferencia();
        if (this.TaCoId>0){
            //_georeferencia.setGeId(0);
            _georeferencia.setTaCoId(this.TaCoId);
            _georeferencia.setGeLatitud(location.getLatitude());
            _georeferencia.setGeLongitud(location.getLongitude());
            String dateNow = DateFormat.format("yyyy-dd-MM",
                    new Date()).toString();
            _georeferencia.setGeFechaHora(dateNow);
            int cantidad=_georeferenciaService.GetCountGeoreferenciadByTaCo(this.TaCoId);
            _georeferencia.setGeOrden(cantidad+1);
            _georeferencia.setGeEnviadoMovil(false);
            _georeferencia.setUsId(1);

            //Verificamos si el ultimo registro no ha variado con respecto al actual
            //primero obtenemos el ultimo registro
            Georeferencia georeferencia=null;
            georeferencia=_georeferenciaService.GetLastGeoreferenciaByTaCo(this.TaCoId);
            //redondeamos a 3 digitos, debido que es ahi varia cuando varia de posicion auna distancia prudente de 30mts
            DecimalFormat df = new DecimalFormat("####0.000");
            //System.out.println("Value: " + df.format(value));
            df.setRoundingMode(RoundingMode.CEILING);
            double _latitudActual=Double.valueOf(df.format(location.getLatitude()));
            double _longitudActual=Double.valueOf(df.format(location.getLongitude()));
            double _latitudLastBD=Double.valueOf(df.format(georeferencia.getGeLatitud()));
            double _longitudLastBD=Double.valueOf(df.format(georeferencia.getGeLongitud()));
            //if(_latitudActual!=_latitudLastBD || _longitudActual!=_longitudLastBD)
            //Calculamos la distancia en metros
            double diferencia;
            diferencia=Math.sqrt(Math.pow(_latitudLastBD-_latitudActual,2)+Math.pow(_longitudLastBD-_longitudActual,2));
            if(diferencia>150 |_latitudLastBD==0)
                _georeferenciaService.SaveGeoreferenciaRest(_georeferencia);
        }
    }
    protected synchronized void buildGoogleApiClient() {
        Log.i(MainActivity.TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        createLocationRequest();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
    public static String getErrorString(Context context, int errorCode) {
        Resources mResources = context.getResources();
        switch (errorCode) {
            case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
                return mResources.getString(R.string.geofence_not_available);
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                return mResources.getString(R.string.geofence_too_many_geofences);
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                return mResources
                        .getString(R.string.geofence_too_many_pending_intents);
            default:
                return mResources.getString(R.string.unknown_geofence_error);
        }
    }
//estos metodos son lapar enlazar el service con la actividad
private void sendMessageToUI(int intvaluetosend) {
    for (int i=mClients.size()-1; i>=0; i--) {
        try {
            // Send data as an Integer
            mClients.get(i).send(Message.obtain(null, MSG_SET_INT_VALUE, intvaluetosend, 0));

            //Send data as a String
            Bundle b = new Bundle();
            b.putString("str1", "ab" + intvaluetosend + "cd");
            Message msg = Message.obtain(null, MSG_SET_STRING_VALUE);
            msg.setData(b);
            mClients.get(i).send(msg);

        }
        catch (RemoteException e) {
            // The client is dead. Remove it from the list; we are going through the list from back to front so this is safe to do inside the loop.
            mClients.remove(i);
        }
    }
}
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //para en lazar con la actividad
        return mMessenger.getBinder();
    }
    public static boolean isRunning()
    {
        return isRunning;
    }

}
