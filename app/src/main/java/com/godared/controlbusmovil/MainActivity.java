package com.godared.controlbusmovil;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.godared.controlbusmovil.adapter.PageAdapterVP;
import com.godared.controlbusmovil.adapter.TarjetaAdaptadorRV;
import com.godared.controlbusmovil.pojo.TelefonoImei;
import com.godared.controlbusmovil.service.DigitalClock;
import com.godared.controlbusmovil.service.GeoreferenciaService;
import com.godared.controlbusmovil.service.ITarjetaService;
import com.godared.controlbusmovil.service.ITelefonoService;
import com.godared.controlbusmovil.service.TarjetaService;
import com.godared.controlbusmovil.service.TelefonoService;
import com.godared.controlbusmovil.service.geofence.GeolocationService;
import com.godared.controlbusmovil.pojo.TarjetaControl;
import com.godared.controlbusmovil.pojo.TarjetaControlDetalle;
import com.godared.controlbusmovil.vista.SettingActivity;
import com.godared.controlbusmovil.vista.fragment.IRecyclerviewFragment;
import com.godared.controlbusmovil.vista.fragment.RecyclerviewFragment;
import com.godared.controlbusmovil.vista.fragment.MapsFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TarjetaService.TarjetaServiceListener,
        GeolocationService.Callbacks, TelefonoService.TelefonoServiceListener {
    private Toolbar tbToolBar;
    private TabLayout tlTablaLayout;
    private ViewPager vpViewPager;
    ArrayList<Fragment> fragmets;
    private ArrayList<TarjetaControlDetalle> tarjetasDetalle;

    public static String TAG = "lstech.aos.debug";

    static public boolean geofencesAlreadyRegistered = false;
    public int BuId;
    public int EmId;
    public int TeId;
    public int TaCoId;
    public String BuPlaca;
    public String EmConsorcio;
    public String FechaActual;
    ITarjetaService iTarjetaService;
    //variablea para el servicio Geolocation
    Intent geolocationServiceIntent;
    GeolocationService geolocationService;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            Toast.makeText(MainActivity.this, "onServiceConnected called", Toast.LENGTH_SHORT).show();
            // We've binded to LocalService, cast the IBinder and get LocalService instance
            GeolocationService.LocalBinder binder = (GeolocationService.LocalBinder) service;
            geolocationService = binder.getServiceInstance(); //Get instance of your service!
            geolocationService.registerClient(MainActivity.this); //Activity register in the service as client for callabcks!
            Toast.makeText(MainActivity.this, "Connected to service...", Toast.LENGTH_SHORT).show();
            //tvServiceState.setText("Connected to service...");
            //tbStartTask.setEnabled(true);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Toast.makeText(MainActivity.this, "onServiceDisconnected called", Toast.LENGTH_SHORT).show();
            //tvServiceState.setText("Service disconnected");
            //tbStartTask.setEnabled(false);
        }
    };
    //Este el receptos de chageGPS
    BroadcastReceiver broadcastReceiverChangeGps =  new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle b = intent.getExtras();
            String message = b.getString("message");
            Log.e("newmesage", "" + message);
            onResume();
        }
    };
    //Este es el receptor de changeTime
    BroadcastReceiver broadcastReceiverChangeTime =  new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle b = intent.getExtras();
            String message = b.getString("message");
            Log.e("newmesage", "" + message);
            onResume();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //recuperamos los datos de la listview cuando gira la pantalla
        if (savedInstanceState != null) { //para guardar el estado en caso la pantalla cambia de orientacion
            BuId=savedInstanceState.getInt("BUS_ID");
            TaCoId=savedInstanceState.getInt("TACO_ID");

        }
        //Esto es para enlazar en broadcast de CHANGE GPS
        registerReceiver(broadcastReceiverChangeGps, new IntentFilter("broadCastGpsLocationReceiver"));
        //Esto es para enlazar en broadcast de CHANGE TIME
        registerReceiver(broadcastReceiverChangeTime, new IntentFilter("broadCastTimeZoneChangedReceiver"));

        //tbToolBar=(Toolbar)findViewById(R.id.tbToolBar);
        String dateNow = DateFormat.format("dd-MM-yyyy",
                new Date()).toString();
        this.FechaActual=dateNow;
        tbToolBar=(Toolbar)findViewById(R.id.miBarra);

        setSupportActionBar(tbToolBar);
        //obtiene el IMEI desde el servidor
        obtenerImeiRest();


    }

    @Override
    public void onResume(){
        super.onResume();
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //verificamos si el GPS esta activo
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
        }else{
            showGPSDisabledAlertToUser();
        }
        //Verificamos si la fecha y hora esta en automatico
        if (isTimeAutomatic(getApplicationContext())==true)
            Toast.makeText(this, "AUTO_TIME is Enabled in your devide", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(this, "AUTO_TIME  is not Enabled in your devide", Toast.LENGTH_SHORT).show();
            showDateHourDisabledAlertToUser();
        }
    }

    public static boolean isTimeAutomatic(Context c) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(c.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 1;
        } else {
            return android.provider.Settings.System.getInt(c.getContentResolver(), android.provider.Settings.System.AUTO_TIME, 0) == 1;
        }
    }
    private void showDateHourDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS esta desactivado en tu dispositivo. quieres activarlo?")
                .setCancelable(false)
                .setPositiveButton("Ir a configuracion para activar el GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callDateHourSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_DATE_SETTINGS);
                                startActivity(callDateHourSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS esta desactivado en tu dispositivo. quieres activarlo?")
                .setCancelable(false)
                .setPositiveButton("Ir a configuracion para activar el GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    //guardamos el estado de los controles(posicion de la grilla
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putInt("BUS_ID", BuId);
        savedInstanceState.putInt("TACO_ID", TaCoId);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onStart(){
        super.onStart();
        //vpViewPager.setAdapter(new PageAdapterVP(getSupportFragmentManager(),fragmets));
       // Context context = parent.getContext();
        //LayoutInflater inflater = LayoutInflater.from(context);

       // IRecyclerviewFragment rf=new RecyclerviewFragment();
       // ArrayList<TarjetaControlDetalle> tarjetasDetalle=new ArrayList<>();
       // rf.crearAdaptador(tarjetasDetalle);

          }

    //Se te han mejor
    /* sobrescribimos para agregar el menu, estos metodos vienes ya
        para implementar implementar o agragr a otra clase*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inicial,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mDescarga:
                Sincronizar(this.getBaseContext());
                iTarjetaService=new TarjetaService(this.getBaseContext());


                break;
            case R.id.mSetting:
                Intent intent= new Intent(this, SettingActivity.class);
                intent.putExtra("BUS_ID",BuId);

                intent.putExtra("TACO_FECHA",this.FechaActual);
                this.startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    public void Sincronizar(Context context) {
        iTarjetaService=new TarjetaService(this,context);
        //MainActivity _actividadPrincipal = (MainActivity)getActivity();//getCallingActivity();

        iTarjetaService.obtenerTarjetasRest(EmId,BuId,this.FechaActual);//"16-08-2017"
        // tarjetasControl=iTarjetaService.getTarjetasControl();
        if(tarjetasDetalle!=null) {

        }
    }
    public void obtenerImeiRest() {
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        int i=0;
        String imei =telephonyManager.getDeviceId();

        ITelefonoService telefonoService=new TelefonoService(this,getApplicationContext());
        telefonoService.ObtenerTelefonoImeiRest(imei);
        i=i+1;
        // aqui llama listenObtenerTelefonoImeiRest, devido a que implementa la interfzar
        //TelefonoService.TelefonoServiceListener, y cuando lla al metodo ObtenerTelefonoImeiRest esta actividad escucha
        // ahi en el servicio esta enlazado


    }
    public void validarImei() {
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        Boolean valido=false;
        String imei =telephonyManager.getDeviceId();
        ITelefonoService telefonoService=new TelefonoService(this,getApplicationContext());
        List<TelefonoImei> telefonoImeis=telefonoService.ObtenerTelefonoImeibyImeiBD(imei);
        for(TelefonoImei telefonoImei:telefonoImeis){
            if (telefonoImei.getTeImei().compareTo(imei)==0){
                BuId=telefonoImei.getBuId();
                EmId=telefonoImei.getEmId();
                TeId=telefonoImei.getTeId();
                BuPlaca=telefonoImei.getBuPlaca();
                EmConsorcio=telefonoImei.getEmConsorcio();
                valido=true;
                break;
            }
        }
        if (!valido){
            Toast.makeText(getApplicationContext(), "Equipo IMEI no registrado", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    //esto viene de la escucha de la interfaz TarjetaService.TarjetaServiceListener
    public void listenObtenerTarjetasDetalleRest(){
        //esto viene desde TarjetaService
        RecyclerviewFragment recyclerviewFragment;
        recyclerviewFragment=(RecyclerviewFragment)fragmets.get(0);
        recyclerviewFragment.recyclerviewFragmentPresenter.obtenerTarjetasDetalleBD();

        //VOlvemos a llamar al servicio para agregar el geofence
        //geofencesAlreadyRegistered = false;
        //startService(geolocationServiceIntent);
        //bindService(geolocationServiceIntent, mConnection, Context.BIND_AUTO_CREATE); //Binding to the service!
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unbindService(mConnection);
            stopService(geolocationServiceIntent);
        }
        catch (Throwable t) {
            Log.e("MainActivity", "Failed to unbind from the service", t);
        }
    }

    //Esto son metos para conectar con el service GeolocationService, implementa su interza
    @Override
    public void updateClient(int taCoDeId) {
        //unOnUiThread , no se exactamente que es lo que hacer pero como listenObtenerTarjetasDetalleRest se deriva
        //de variso servicio y estos generan varios hilos tonces me daba un error que estavista no se peude actualizar
        //desde otro hilo y con este metodo lo solucione
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listenObtenerTarjetasDetalleRest();
            }
        });

        Toast.makeText(MainActivity.this, "Valor de retorno de servicio"+ taCoDeId, Toast.LENGTH_SHORT).show();
    }
    //esto es de a interfaz TelefonoServiceListen
    public void listenObtenerTelefonoImeiRest(){
        validarImei(); // aqui obtenemos el BuId  y EmId
        //panel el titulo a la actividad
        setTitle("PlacaBus:"+BuPlaca);


        tlTablaLayout=(TabLayout)findViewById(R.id.tlTablaLayout);
        //Cargando el Reloj digital
        DigitalClock dc = (DigitalClock)findViewById(R.id.fragment_clock_digital);

        tlTablaLayout=(TabLayout)findViewById(R.id.tlTablaLayout);
        vpViewPager=(ViewPager) findViewById(R.id.vpViewPager);

        Bundle args = new Bundle();
        args.putInt("BUS_ID",BuId);
        args.putInt("TACO_ID",0);
        args.putString("TACO_FECHA",FechaActual);
        args.putBoolean("INDICA_GETDETALLEACTIVO",true);

        fragmets=new ArrayList<>();
        fragmets.add(new RecyclerviewFragment());
        fragmets.add(new MapsFragment());
        fragmets.get(0).setArguments(args);
        //agremaos los fragments al viewPager
        vpViewPager.setAdapter(new PageAdapterVP(getSupportFragmentManager(),fragmets));
        tlTablaLayout.setupWithViewPager(vpViewPager);
        tlTablaLayout.getTabAt(0).setIcon(R.drawable.icons8_tarjeta_para_fichar_48);
        tlTablaLayout.getTabAt(1).setIcon(R.drawable.icons8_marcador_de_mapa_48);
        //obteniendo la tarejacontrol activo
        ITarjetaService tarjetaService=new TarjetaService(this);
        TarjetaControl _tarjetaControl=null;

        _tarjetaControl =tarjetaService.GetTarjetaControlActivo(BuId,this.FechaActual);//"16-08-2017"
        this.TaCoId=_tarjetaControl.getTaCoId();
        geolocationServiceIntent=new Intent(this, GeolocationService.class);
        geolocationServiceIntent.putExtra("BUS_ID",BuId);
        geolocationServiceIntent.putExtra("TACO_ID",TaCoId);
        startService(geolocationServiceIntent);
        bindService(geolocationServiceIntent, mConnection, Context.BIND_AUTO_CREATE); //Binding to the service!
        //startService(new Intent(this, GeolocationService.class));}

    }
}
