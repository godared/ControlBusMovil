package com.godared.controlbusmovil;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.res.Resources;
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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.godared.controlbusmovil.adapter.PageAdapterVP;
import com.godared.controlbusmovil.adapter.TarjetaAdaptadorRV;
import com.godared.controlbusmovil.pojo.Configura;
import com.godared.controlbusmovil.pojo.TelefonoImei;
import com.godared.controlbusmovil.service.ConfiguraService;
import com.godared.controlbusmovil.service.DigitalClock;
import com.godared.controlbusmovil.service.GeoreferenciaService;
import com.godared.controlbusmovil.service.IConfiguraService;
import com.godared.controlbusmovil.service.ITarjetaService;
import com.godared.controlbusmovil.service.ITelefonoService;
import com.godared.controlbusmovil.service.TarjetaService;
import com.godared.controlbusmovil.service.TelefonoService;
import com.godared.controlbusmovil.service.TimerService;
import com.godared.controlbusmovil.service.geofence.GeolocationService;
import com.godared.controlbusmovil.pojo.TarjetaControl;
import com.godared.controlbusmovil.pojo.TarjetaControlDetalle;
import com.godared.controlbusmovil.vista.SettingActivity;
import com.godared.controlbusmovil.vista.fragment.IRecyclerviewFragment;
import com.godared.controlbusmovil.vista.fragment.RecyclerviewFragment;
import com.godared.controlbusmovil.vista.fragment.MapsFragment;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements TarjetaService.TarjetaServiceListener,
        GeolocationService.Callbacks, TelefonoService.TelefonoServiceListener,ConfiguraService.ConfiguraServiceListener {
    //public static String TAG;
    private Toolbar tbToolBar;
    private TabLayout tlTablaLayout;
    private ViewPager vpViewPager;
    ArrayList<Fragment> fragmets;
    private ArrayList<TarjetaControlDetalle> tarjetasDetalle;

    public static String TAG2 = "lstech.aos.debug";

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
    //VAriable para timer-------------------------------------------------
    public static final String TAG = MainActivity.class.getSimpleName();
    private TimerService timerService;
    private boolean serviceBound;
    // Handler to update the UI every second when the timer is running
    private final Handler mUpdateTimeHandler = new UIUpdateHandler(this);
    // Message type for the handler
    private final static int MSG_UPDATE_TIME = 0;
    private TextView timerTextView;
    /**
     * Callback for service binding, passed to bindService()
     */
    private ServiceConnection mConnection2 = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "Service bound");
            }
            TimerService.RunServiceBinder binder = (TimerService.RunServiceBinder) service;
            timerService = binder.getService();
            serviceBound = true;
            // Ensure the service is not in the foreground when bound
            timerService.background();
            // Update the UI if the service is already running the timer
            if (timerService.isTimerRunning()) {
                updateUIStartRun();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "Service disconnect");
            }
            serviceBound = false;
        }
    };

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //recuperamos los datos de la listview cuando gira la pantalla
        if (savedInstanceState != null) { //para guardar el estado en caso la pantalla cambia de orientacion
            BuId=savedInstanceState.getInt("BUS_ID");
            TaCoId=savedInstanceState.getInt("TACO_ID");

        }
        //timer
        timerTextView = (TextView)findViewById(R.id.timer_text_view);
        //Esto es para enlazar en broadcast de CHANGE GPS
        registerReceiver(broadcastReceiverChangeGps, new IntentFilter("broadCastGpsLocationReceiver"));


        //tbToolBar=(Toolbar)findViewById(R.id.tbToolBar);
        String dateNow = DateFormat.format("dd-M-yyyy hh:mm:ss",
                new Date()).toString();
        this.FechaActual=dateNow;
        tbToolBar=(Toolbar)findViewById(R.id.miBarra);

        setSupportActionBar(tbToolBar);
        //obtiene el IMEI desde el servidor
        obtenerImeiRest();
        obtenerFechaServer();
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
            Toast.makeText(this, "AUTO_TIME esta habilitado en tu dispositivo", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(this, "AUTO_TIME  no esta habilitado en tu dispositivo", Toast.LENGTH_SHORT).show();
            showDateHourDisabledAlertToUser();
        }
        //Verificamos si la TimeZone esta en automatico
        if (isTimeZoneAutomatic(getApplicationContext())==true)
            Toast.makeText(this, "AUTO_TIME_ZONE esta habilitado en tu dispositivo", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(this, "AUTO_TIME_ZONE  no esta habilitado en tu dispositivo", Toast.LENGTH_SHORT).show();
            showDateHourDisabledAlertToUser();
        }
        final Resources res = this.getResources();
        final int id = Resources.getSystem().getIdentifier(
                "config_ntpServer", "string","android");
        final String defaultServer = res.getString(id);
        Toast.makeText(this, defaultServer, Toast.LENGTH_SHORT).show();
    }

    public static boolean isTimeAutomatic(Context c) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(c.getContentResolver(), Settings.Global.AUTO_TIME, 0) == 1;
        } else {
            return android.provider.Settings.System.getInt(c.getContentResolver(), android.provider.Settings.System.AUTO_TIME, 0) == 1;
        }
    }
    public static boolean isTimeZoneAutomatic(Context c) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(c.getContentResolver(), Settings.Global.AUTO_TIME_ZONE, 0) == 1;
        } else {
            return android.provider.Settings.System.getInt(c.getContentResolver(), Settings.System.AUTO_TIME_ZONE, 0) == 1;
        }
    }
    private void showDateHourDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Auto_Time-Zone esta desactivado en tu dispositivo. quieres activarlo?")
                .setCancelable(false)
                .setPositiveButton("Ir a configuracion para activar el Auto_Time",
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
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Starting and binding service");
        }
        Intent i = new Intent(this, TimerService.class);
        startService(i);
        bindService(i, mConnection2, 0);

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
                runButtonClick();

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
    public void obtenerFechaServer(){
        IConfiguraService configuraService=new ConfiguraService(getApplicationContext());
        configuraService.GetAllConfiguraByEmPeriodoRest(this.EmId,(new Date().getYear()));
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
            //unbindService(mConnection);
            //stopService(geolocationServiceIntent);
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
        //DigitalClock dc = (DigitalClock)findViewById(R.id.fragment_clock_digital);

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
    public void listenObtenerConfiguraRest(Date dateServer){
        String dateNow = DateFormat.format("dd-M-yyyy hh:mm:ss",
                dateServer).toString();
        this.FechaActual=dateNow;
    }
    protected void onStop() {
        super.onStop();
        updateUIStopRun();
        if (serviceBound) {
            // If a timer is active, foreground the service, otherwise kill the service
            if (timerService.isTimerRunning()) {
                timerService.foreground();
            }
            else {
                stopService(new Intent(this, TimerService.class));
            }
            // Unbind the service
            unbindService(mConnection2);
            serviceBound = false;
        }
    }
    ///-------------------------------------------------
    //Metodos de timer -------------------------
    public void runButtonClick() //View v
    {
        if (serviceBound && !timerService.isTimerRunning()) {
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "Starting timer");
            }
            //String myDate = new String("22-01-2015 23:58:56+05:00");//2013-09-19T03:27:23+01:00");
            String myDate=this.FechaActual;
            SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");//("yyyy-MM-dd'T'HH:mm:ss");
            Date date=null;
            try {
                date = format.parse(myDate);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            date.getTime();
            timerService.startTimer(date);
            updateUIStartRun();
        }
        else if (serviceBound && timerService.isTimerRunning()) {
            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "Stopping timer");
            }
            timerService.stopTimer();
            updateUIStopRun();
        }
    }

    /**
     * Updates the UI when a run starts
     */
    private void updateUIStartRun() {
        mUpdateTimeHandler.sendEmptyMessage(MSG_UPDATE_TIME);
        //timerButton.setText(R.string.timer_stop_button);
    }

    /**
     * Updates the UI when a run stops
     */
    private void updateUIStopRun() {
        mUpdateTimeHandler.removeMessages(MSG_UPDATE_TIME);
       // timerButton.setText(R.string.timer_start_button);
    }

    /**
     * Updates the timer readout in the UI; the service must be bound
     */
    private void updateUITimer() {
        if (serviceBound) {
            Calendar cSchedStartCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            SimpleDateFormat formatDate = new SimpleDateFormat("hh:mm:ss a"); //"dd-M-yyyy hh:mm:ss a"
            long valorTime=timerService.elapsedTime();
            cSchedStartCal.setTimeZone(TimeZone.getTimeZone("America/Lima"));
            cSchedStartCal.setTimeInMillis(valorTime);
            String formattedDate = formatDate.format(cSchedStartCal.getTime()).toString();
            timerTextView.setText( formattedDate);//timerService.elapsedTime()
        }
    }

    /**
     * When the timer is running, use this handler to update
     * the UI every second to show timer progress
     */
    static class UIUpdateHandler extends Handler {

        private final static int UPDATE_RATE_MS = 1000;
        private final WeakReference<MainActivity> activity;

        UIUpdateHandler(MainActivity activity) {
            this.activity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message message) {
            if (MSG_UPDATE_TIME == message.what) {
                if (Log.isLoggable(TAG, Log.VERBOSE)) {
                    Log.v(TAG, "updating time");
                }
                activity.get().updateUITimer();
                sendEmptyMessageDelayed(MSG_UPDATE_TIME, UPDATE_RATE_MS);
            }
        }
    }



}
