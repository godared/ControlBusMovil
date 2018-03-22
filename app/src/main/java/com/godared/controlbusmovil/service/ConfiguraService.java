package com.godared.controlbusmovil.service;

import android.content.Context;

import com.godared.controlbusmovil.dao.BaseDatos;
import com.godared.controlbusmovil.pojo.Configura;

import java.util.List;

/**
 * Created by Ronald on 22/03/2018.
 */

public class ConfiguraService implements IConfiguraService {
    private Context context;
    BaseDatos db;
    public ConfiguraService(Context context){
        this.context=context;
        db=new BaseDatos(context);
        //tarjetaService=new TarjetaService(context);
    }
    public void GetAllConfiguraByEmPeriodoRest(int emId, int coPeriodo){

    }
}
