package com.godared.controlbusmovil;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.godared.controlbusmovil.adapter.PageAdapterVP;
import com.godared.controlbusmovil.dao.BaseDatos;
import com.godared.controlbusmovil.service.geofence.GeolocationService;
import com.godared.controlbusmovil.pojo.TarjetaControl;
import com.godared.controlbusmovil.pojo.TarjetaControlDetalle;
import com.godared.controlbusmovil.vista.fragment.RecyclerviewFragment;
import com.godared.controlbusmovil.vista.fragment.SettingActivity;
import com.godared.controlbusmovil.vista.fragment.MapsFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar tbToolBar;
    private TabLayout tlTablaLayout;
    private ViewPager vpViewPager;
    private ArrayList<TarjetaControlDetalle> tarjetasDetalle;

    public static String TAG = "lstech.aos.debug";

    static public boolean geofencesAlreadyRegistered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tbToolBar=(Toolbar)findViewById(R.id.tbToolBar);
        setSupportActionBar(tbToolBar);

        tlTablaLayout=(TabLayout)findViewById(R.id.tlTablaLayout);
        vpViewPager=(ViewPager) findViewById(R.id.vpViewPager);

        ArrayList<Fragment> fragmets=new ArrayList<>();
d:        fragmets.add(new RecyclerviewFragment());
        fragmets.add(new MapsFragment());
        //agremaos los fragments al viewPager
        vpViewPager.setAdapter(new PageAdapterVP(getSupportFragmentManager(),fragmets));
        tlTablaLayout.setupWithViewPager(vpViewPager);
        tlTablaLayout.getTabAt(0).setIcon(R.drawable.tiempo_de_propiedades_48);
        tlTablaLayout.getTabAt(1).setIcon(R.drawable.tiempo_de_propiedades_48);

        startService(new Intent(this, GeolocationService.class));
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
                this.startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    public void obtenerGuardarTarjetasControlRest() {
        BaseDatos db=new BaseDatos(getApplicationContext());
        List<TarjetaControl> tarjetasControl=null;


     /*   for(TarjetaControl tarjetaControl:tarjetasControl){
            tarjetaService.insertarTarjetasBD(db,tarjetaControl);
            //Obteniendo y guardando el tarjetaControldetalle*/

       //  tarjetasDetalle=iTarjetaService.obtenerTarjetasDetalleRest(1);//tarjetaControl.getTaCoId()
        /*    tarjetaService.insertarTarjetasDetalleBD(db,tarjetasDetalle);
        }*/

    }
}
