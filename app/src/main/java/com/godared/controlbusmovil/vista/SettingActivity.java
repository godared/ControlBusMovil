package com.godared.controlbusmovil.vista;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.godared.controlbusmovil.MainActivity;
import com.godared.controlbusmovil.adapter.EnvioTarjetaAdaptadorRV;
import com.godared.controlbusmovil.pojo.TarjetaBitacoraMovil;
import com.godared.controlbusmovil.service.DigitalClock;
import com.godared.controlbusmovil.R;
import com.godared.controlbusmovil.pojo.TarjetaControl;
import com.godared.controlbusmovil.pojo.TarjetaControlDetalle;
import com.godared.controlbusmovil.service.ITarjetaService;
import com.godared.controlbusmovil.service.TarjetaService;
import com.godared.controlbusmovil.vista.fragment.RecyclerviewEnvioTarjetaFragment;

import java.util.ArrayList;
import java.util.Date;

public class SettingActivity extends AppCompatActivity{
    ITarjetaService iTarjetaService;
    private ArrayList<TarjetaControlDetalle> tarjetasDetalle;
    private ArrayList<TarjetaControl> tarjetasControl;
    int BuId;
    public Boolean Enviado;
    private Toolbar tbToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

       // DigitalClock dc = (DigitalClock)findViewById(R.id.fragment_clock_digital);
        Bundle bu=getIntent().getExtras();
       // BuId=bu.getInt("BUS_ID");
        this.Enviado=false;
        tbToolBar=(Toolbar)findViewById(R.id.miBarra);
        setSupportActionBar(tbToolBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();
        android.support.v4.app.Fragment fragment = new RecyclerviewEnvioTarjetaFragment();
        fragmentTransaction.add(R.id.your_placeholder, fragment);
        fragmentTransaction.commit();


    }

    public void Sincronizar(View view) {
        iTarjetaService=new TarjetaService(view.getContext());
        //MainActivity _actividadPrincipal = (MainActivity)getActivity();//getCallingActivity();
        String dateNow = DateFormat.format("dd-MM-yyyy",
                new Date()).toString();
        iTarjetaService.obtenerTarjetasRest(BuId,dateNow);//"16-08-2017"
       // tarjetasControl=iTarjetaService.getTarjetasControl();
        if(tarjetasDetalle!=null) {

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mSubida:

                break;
            case R.id.mVisible:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
