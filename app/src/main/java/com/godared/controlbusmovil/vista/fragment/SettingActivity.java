package com.godared.controlbusmovil.vista.fragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.godared.controlbusmovil.R;
import com.godared.controlbusmovil.pojo.TarjetaControl;
import com.godared.controlbusmovil.pojo.TarjetaControlDetalle;
import com.godared.controlbusmovil.service.ITarjetaService;
import com.godared.controlbusmovil.service.TarjetaService;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {
    ITarjetaService iTarjetaService;
    private ArrayList<TarjetaControlDetalle> tarjetasDetalle;
    private ArrayList<TarjetaControl> tarjetasControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }
    public void Sincronizar(View view) {
        iTarjetaService=new TarjetaService(view.getContext());

        iTarjetaService.obtenerTarjetasRest(1,"31-03-2017");
       // tarjetasControl=iTarjetaService.getTarjetasControl();
        if(tarjetasDetalle!=null) {

        }
    }
}
