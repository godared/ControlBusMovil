package com.godared.controlbusmovil.service;

import android.content.Context;

import com.godared.controlbusmovil.dao.BaseDatos;
import com.godared.controlbusmovil.pojo.Georeferencia;
import com.godared.controlbusmovil.pojo.TarjetaControl;

import java.util.List;

/**
 * Created by ronald on 07/12/2017.
 */

public class GeoreferenciaService {
    private Context context;
    BaseDatos db;
    ITarjetaService tarjetaService;

    public GeoreferenciaService(Context context){
        this.context=context;
        db=new BaseDatos(context);
        tarjetaService=new TarjetaService(context);
    }
    public void SaveGeoreferenciaRest(Georeferencia georeferencia){

    }
    public void SaveGeoreferenciaRest(List<Georeferencia> georeferencias){

    }
    public void InsertarGeoreferenciaBD(BaseDatos baseDatos, Georeferencia georeferencia){

    }
    public void ActualizarGeoreferenciaBD(BaseDatos baseDatos, Georeferencia georeferencia){

    }
}
