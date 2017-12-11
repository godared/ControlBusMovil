package com.godared.controlbusmovil;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.godared.controlbusmovil.adapter.PageAdapterVP;
import com.godared.controlbusmovil.dao.BaseDatos;
import com.godared.controlbusmovil.pojo.TelefonoImei;
import com.godared.controlbusmovil.presentador.IRecyclerviewFragmentPresenter;
import com.godared.controlbusmovil.presentador.RecyclerviewFragmentPresenter;
import com.godared.controlbusmovil.service.DigitalClock;
import com.godared.controlbusmovil.service.ITarjetaService;
import com.godared.controlbusmovil.service.ITelefonoService;
import com.godared.controlbusmovil.service.TarjetaService;
import com.godared.controlbusmovil.service.TelefonoService;
import com.godared.controlbusmovil.service.geofence.GeolocationService;
import com.godared.controlbusmovil.pojo.TarjetaControl;
import com.godared.controlbusmovil.pojo.TarjetaControlDetalle;
import com.godared.controlbusmovil.vista.fragment.IRecyclerviewFragment;
import com.godared.controlbusmovil.vista.fragment.RecyclerviewFragment;
import com.godared.controlbusmovil.vista.fragment.SettingActivity;
import com.godared.controlbusmovil.vista.fragment.MapsFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar tbToolBar;
    private TabLayout tlTablaLayout;
    private ViewPager vpViewPager;
    private ArrayList<TarjetaControlDetalle> tarjetasDetalle;

    public static String TAG = "lstech.aos.debug";

    static public boolean geofencesAlreadyRegistered = false;
    public int BuId;
    public int EmId;
    public int TeId;
    public int TaCoId;
    public String BuPlaca;
    public String EmConsorcio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) { //para guardar el estado en caso la pantalla cambia de orientacion

        }

        //tbToolBar=(Toolbar)findViewById(R.id.tbToolBar);

        tbToolBar=(Toolbar)findViewById(R.id.miBarra);

        setSupportActionBar(tbToolBar);
        obtenerImeiRest();
        validarImei();
        tlTablaLayout=(TabLayout)findViewById(R.id.tlTablaLayout);
        //Cargando el Reloj digital
        DigitalClock dc = (DigitalClock)findViewById(R.id.fragment_clock_digital);

        tlTablaLayout=(TabLayout)findViewById(R.id.tlTablaLayout);
        vpViewPager=(ViewPager) findViewById(R.id.vpViewPager);

        ArrayList<Fragment> fragmets=new ArrayList<>();
        fragmets.add(new RecyclerviewFragment());
        fragmets.add(new MapsFragment());
        //agremaos los fragments al viewPager
        vpViewPager.setAdapter(new PageAdapterVP(getSupportFragmentManager(),fragmets));
        tlTablaLayout.setupWithViewPager(vpViewPager);
        tlTablaLayout.getTabAt(0).setIcon(R.drawable.icons8_tarjeta_para_fichar_48);
        tlTablaLayout.getTabAt(1).setIcon(R.drawable.icons8_marcador_de_mapa_48);
        //obteniendo la tarejacontrol activo
        ITarjetaService tarjetaService=new TarjetaService(this);
        TarjetaControl _tarjetaControl=null;
        String dateNow = DateFormat.format("dd-MM-yyyy",
                new Date()).toString();
        _tarjetaControl =tarjetaService.GetTarjetaControlActivo(BuId,dateNow);//"16-08-2017"
        this.TaCoId=_tarjetaControl.getTaCoId();
        Intent i=new Intent(this, GeolocationService.class);
        i.putExtra("BUS_ID",BuId);
        i.putExtra("TACO_ID",TaCoId);
        startService(i);
        //startService(new Intent(this, GeolocationService.class));
    }
    @Override
    protected void onStart(){
        super.onStart();
        //vpViewPager.setAdapter(new PageAdapterVP(getSupportFragmentManager(),fragmets));
       // Context context = parent.getContext();
        //LayoutInflater inflater = LayoutInflater.from(context);

        IRecyclerviewFragment rf=new RecyclerviewFragment();
        ArrayList<TarjetaControlDetalle> tarjetasDetalle=new ArrayList<>();
        rf.crearAdaptador(tarjetasDetalle);

      /*  RecyclerView listaTarjetasDetalle;
        listaTarjetasDetalle=(RecyclerView)findViewById(R.id.rvTarjeta);
        IRecyclerviewFragmentPresenter recyclerviewFragmentPresenter;
        recyclerviewFragmentPresenter =new RecyclerviewFragmentPresenter(rf,getApplicationContext());
        recyclerviewFragmentPresenter.mostrarTarjetasDetalleRV();*/

    }

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
                Intent intent= new Intent(this, SettingActivity.class);
                intent.putExtra("BUS_ID",BuId);
                this.startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    public void obtenerImeiRest() {
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        int i=0;
        String imei =telephonyManager.getDeviceId();

        ITelefonoService telefonoService=new TelefonoService(getApplicationContext());
        telefonoService.ObtenerTelefonoImeiRest(imei);
        i=i+1;
     /*   for(TarjetaControl tarjetaControl:tarjetasControl){
            tarjetaService.insertarTarjetasBD(db,tarjetaControl);
            //Obteniendo y guardando el tarjetaControldetalle*/

       //  tarjetasDetalle=iTarjetaService.obtenerTarjetasDetalleRest(1);//tarjetaControl.getTaCoId()
        /*    tarjetaService.insertarTarjetasDetalleBD(db,tarjetasDetalle);
        }*/

    }
    public void validarImei() {
        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        Boolean valido=false;
        String imei =telephonyManager.getDeviceId();
        ITelefonoService telefonoService=new TelefonoService(getApplicationContext());
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
}
