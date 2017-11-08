package com.godared.controlbusmovil.vista.fragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;

import com.godared.controlbusmovil.MainActivity;
import com.godared.controlbusmovil.service.DigitalClock;
import com.godared.controlbusmovil.R;
import com.godared.controlbusmovil.pojo.TarjetaControl;
import com.godared.controlbusmovil.pojo.TarjetaControlDetalle;
import com.godared.controlbusmovil.service.ITarjetaService;
import com.godared.controlbusmovil.service.TarjetaService;

import java.util.ArrayList;
import java.util.Date;

public class SettingActivity extends AppCompatActivity {
    ITarjetaService iTarjetaService;
    private ArrayList<TarjetaControlDetalle> tarjetasDetalle;
    private ArrayList<TarjetaControl> tarjetasControl;
    int BuId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        DigitalClock dc = (DigitalClock)findViewById(R.id.fragment_clock_digital);
        Bundle bu=getIntent().getExtras();
        BuId=bu.getInt("BUS_ID");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
}
