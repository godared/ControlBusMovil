package com.godared.controlbusmovil.vista;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.godared.controlbusmovil.MainActivity;
import com.godared.controlbusmovil.adapter.EnvioTarjetaAdaptadorRV;
import com.godared.controlbusmovil.pojo.TarjetaBitacoraMovil;
import com.godared.controlbusmovil.service.DigitalClock;
import com.godared.controlbusmovil.R;
import com.godared.controlbusmovil.pojo.TarjetaControl;
import com.godared.controlbusmovil.pojo.TarjetaControlDetalle;
import com.godared.controlbusmovil.service.ITarjetaService;
import com.godared.controlbusmovil.service.TarjetaService;
import com.godared.controlbusmovil.service.TimerService;
import com.godared.controlbusmovil.service.geofence.GeolocationService;
import com.godared.controlbusmovil.vista.fragment.RecyclerviewEnvioTarjetaFragment;
import com.godared.controlbusmovil.vista.fragment.RecyclerviewFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SettingActivity extends AppCompatActivity implements GeolocationService.Callbacks{
    ITarjetaService iTarjetaService;
    private ArrayList<TarjetaControlDetalle> tarjetasDetalle;
    private ArrayList<TarjetaControl> tarjetasControl;
    public int BuId=0;
    public int EmId=0;
    public int TaCoId=0;
    public String TaCoFecha;
    public Date FechaActual;
    public int Enviado;
    private Toolbar tbToolBar;
    private Menu menu;
    private Context context;
    android.support.v4.app.Fragment fragment;
    public double Latitud;
    public double Longitud;
    //VAriable para timer-------------------------------------------------
    public static final String TAG = SettingActivity.class.getSimpleName();
    private TimerService timerService;
    private boolean serviceBound;
    GeolocationService geolocationService;
    Intent geolocationServiceIntent;
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
                //updateHoraofTimerService();
                Toast.makeText(getApplicationContext(), String.valueOf(timerService.elapsedTime()), Toast.LENGTH_SHORT).show();
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
    //Esta conection es para GeolocationService
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            Toast.makeText(SettingActivity.this, "onServiceConnected called", Toast.LENGTH_SHORT).show();
            // We've binded to LocalService, cast the IBinder and get LocalService instance
            GeolocationService.LocalBinder binder = (GeolocationService.LocalBinder) service;
            geolocationService = binder.getServiceInstance(); //Get instance of your service!
            geolocationService.registerClient(SettingActivity.this); //Activity register in the service as client for callabcks!
            Toast.makeText(SettingActivity.this, "Connected to service...", Toast.LENGTH_SHORT).show();
            //tvServiceState.setText("Connected to service...");
            //tbStartTask.setEnabled(true);
        }
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Toast.makeText(SettingActivity.this, "onServiceDisconnected called", Toast.LENGTH_SHORT).show();
            //tvServiceState.setText("Service disconnected");
            //tbStartTask.setEnabled(false);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //esto es para controlar cuando gira la pantalla
        if(savedInstanceState != null){
            //cuando gira entonces recuperado el estado
            BuId=savedInstanceState.getInt("BUS_ID");
            EmId=savedInstanceState.getInt("EMP_ID");
            TaCoId=savedInstanceState.getInt("TACO_ID");
            TaCoFecha=savedInstanceState.getString("TACO_FECHA");
        }else{
            //cuando se crea una nueva instancia
            Bundle bu=getIntent().getExtras();
            BuId = bu.getInt("BUS_ID");
            EmId = bu.getInt("EMP_ID");
            TaCoFecha = bu.getString("TACO_FECHA");

        }

       // DigitalClock dc = (DigitalClock)findViewById(R.id.fragment_clock_digital);
        context=this.getApplicationContext();
       //Esta variable inciamos en 0 para que somalente visualice los no enviados
        this.Enviado=0;
        tbToolBar=(Toolbar)findViewById(R.id.miBarra);
        setSupportActionBar(tbToolBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Cargamos el Fragment
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();//getFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragment = new RecyclerviewEnvioTarjetaFragment();
        fragmentTransaction.add(R.id.flEnvioTarjeta, fragment);
        fragmentTransaction.commit();



    }
    @Override
    protected void onStart(){
        super.onStart();
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, "Starting and binding service");
        }
        Intent intentTimerService = new Intent(this, TimerService.class);
        //Este servicio esta ejecutandose desde el MainActivity, entonces ya solo obtenemos la referencia del servicio
        if(startService(intentTimerService) != null) {
            Toast.makeText(getBaseContext(), "Service is already running", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getBaseContext(), "There is no service running, starting service..", Toast.LENGTH_SHORT).show();
        }
        startService(intentTimerService);
        bindService(intentTimerService, mConnection2, 0);
        //Toast.makeText(getApplicationContext(), String.valueOf(timerService.elapsedTime()), Toast.LENGTH_SHORT).show();
        this.FechaActual=new Date();
        //Este servicio esta ejecutandose desde MainActivity, entonces solo obtenemos una referencia a este con startService()
        //le volvemos a pasar los parametros con el intent por que se vuelve a ejecutar onStartCommand() y ahi asigna los valores
        //Y ejecutamos el bindService() con su mConnection para obtener la escucha del metodo que nos devolvera la Localizacion.
        geolocationServiceIntent=new Intent(this, GeolocationService.class);
        geolocationServiceIntent.putExtra("BUS_ID",BuId);
        geolocationServiceIntent.putExtra("TACO_ID",TaCoId);
        geolocationServiceIntent.putExtra("FECHA_ACTUAL",this.FechaActual.getTime());
        startService(geolocationServiceIntent);
        bindService(geolocationServiceIntent, mConnection, Context.BIND_AUTO_CREATE); //Binding to the service!

    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        //super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("BUS_ID", BuId);
        savedInstanceState.putString("TACO_FECHA", TaCoFecha);
        super.onSaveInstanceState(savedInstanceState);

    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        BuId=savedInstanceState.getInt("BUS_ID");
        TaCoFecha=savedInstanceState.getString("TACO_FECHA");
    }

    public void VisualizarEnviados(){
        if (this.Enviado==1) {
            this.Enviado = 0;
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.icons8_invisible_48));
        }
        else{
            this.Enviado=1;
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.icons8_visible_48));
        }
        RecyclerviewEnvioTarjetaFragment recyclerviewEnvioTarjetaFragment;
        recyclerviewEnvioTarjetaFragment=(RecyclerviewEnvioTarjetaFragment)fragment;
        recyclerviewEnvioTarjetaFragment.iRecyclerviewEnvioTarjetaPresenter.obtenerEnvioTarjetasBD(this.BuId,this.TaCoFecha,this.Enviado);

    }
    public void EnviarTodo() {
        iTarjetaService=new TarjetaService(context);
        //MainActivity _actividadPrincipal = (MainActivity)getActivity();//getCallingActivity();
        iTarjetaService.EnviarTodo(BuId,TaCoFecha,0);//"16-08-2017"
       // tarjetasControl=iTarjetaService.getTarjetasControl();
        if(tarjetasDetalle!=null) {

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting,menu);
        this.menu = menu;
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mSubida:
                this.EnviarTodo();
                break;
            case R.id.mVisible:
                this.VisualizarEnviados();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void updateHoraofTimerService(){
        SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy");//("yyyy-MM-dd'T'HH:mm:ss");
        String dateFecha=format.format(timerService.elapsedTime());

        this.FechaActual.setTime(timerService.elapsedTime());
    }
    //Viene desde GeolocationService
    //Con este procedimiento guardamos la Georeferencia en el servidor rest de la nube
    public void  listenguardarGeoreferenciaGeolocationService(Location location){
        //Estos dos datos lo uso en AlertaIncidenciaFragment(se guarda ahi)
        this.Latitud=location.getLatitude();
        this.Longitud=location.getLongitude();
    }
    //Como el servicio GeolocationService se usa Tambien en MainActivity, entonces ahi se implementa este metodo de escucha
    //entonces aqui lo declaramos nomas para que no nos de error
    public void updateClient(int taCoDeId ){

    }
    //Como el servicio GeolocationService se usa Tambien en MainActivity, entonces ahi se implementa este metodo de escucha
    public void updateGeofenceGeolocationService(int taCoDeId,int puCoDeId,double latitude,double longitude){

    }


}
