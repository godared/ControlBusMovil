package com.godared.controlbusmovil.vista;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.godared.controlbusmovil.R;
import com.godared.controlbusmovil.vista.fragment.RecyclerviewEnvioTarjetaFragment;
import com.godared.controlbusmovil.vista.fragment.RecyclerviewFragment;

public class DetalleActivity extends AppCompatActivity {
    private Toolbar tbToolBar;
    private Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        tbToolBar=(Toolbar)findViewById(R.id.miBarra);
        setSupportActionBar(tbToolBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle args = new Bundle();
        args.putInt("BUS_ID",0);

        args.putString("TACO_FECHA","");
        args.putBoolean("INDICA_GETDETALLEACTIVO",false);
        //Obtenemos el argumento del instent
        Bundle bu=getIntent().getExtras();
        int TaCoId = bu.getInt("TACO_ID");
        args.putInt("TACO_ID",TaCoId);
        //Cargamos el Fragment
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();//getFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        android.support.v4.app.Fragment fragment = new RecyclerviewFragment();
        fragment.setArguments(args);
        fragmentTransaction.add(R.id.flDetalleTarjeta, fragment);
        fragmentTransaction.commit();
    }
    //Este no es un menu si no es para el boton atras(back)
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home) {
            Intent intent = NavUtils.getParentActivityIntent(this);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
