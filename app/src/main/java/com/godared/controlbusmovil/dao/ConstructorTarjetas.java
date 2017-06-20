package com.godared.controlbusmovil.dao;


import android.content.ContentValues;
import android.content.Context;

import com.godared.controlbusmovil.pojo.TarjetaControl;
import com.godared.controlbusmovil.pojo.TarjetaControlDetalle;

import java.util.ArrayList;

/**
 * Created by ronald on 14/04/2017.
 */

public class ConstructorTarjetas {
    Context context;

    public ConstructorTarjetas(Context context) {
        this.context = context;
    }
    public TarjetaControl ObtenerDatosTarjetas(){
        BaseDatos db=new BaseDatos(context);
        //insertarTarjetas(db);
        return db.ObtenerTarjeta(1);

    }
    public ArrayList<TarjetaControlDetalle> ObtenerDatosTarjetasDetalle(){
        BaseDatos db=new BaseDatos(context);
        //insertarTarjetas(db);
        //insertarTarjetasDetalle(db);
        /*prubea con datos */
        /*ArrayList<TarjetaControlDetalle> contactos=new ArrayList<TarjetaControlDetalle>();
        contactos.add(new TarjetaControlDetalle(1,1,1,"2017-01-01","12:12:01",12.00930,-12.12,"12:00","Av. Leguia",1,"2017-01-2"));
        contactos.add(new TarjetaControlDetalle(1,1,1,"2017-01-01","12",12.00930,-12.12,"12:00","Av. Leguia2",1,"2017-01-2"));
        contactos.add(new TarjetaControlDetalle(1,1,1,"2017-01-01","13:01:30",12.00930,-12.12,"15:01","Av. Leguia3",1,"2017-01-2"));
        contactos.add(new TarjetaControlDetalle(1,1,1,"2017-01-01","12",12.00930,-12.12,"12","Av. Leguia4",1,"2017-01-2"));*/
        return db.ObtenerTarjetasDetalle(55);//contactos;//

    }
    public void insertarTarjetas(BaseDatos baseDatos){
        ContentValues contentValues=new ContentValues();
        contentValues.put("PuCoId",1);
        contentValues.put("RuId",1);
        contentValues.put("BuId",1);
        contentValues.put("TaCoFecha","2017-04-15");
        contentValues.put("TaCoHoraSalida","15");
        contentValues.put("TaCoCuota",20.43);
        contentValues.put("UsId",1);
        contentValues.put("UsFechaReg","2017");
        baseDatos.insertarTarjeta(contentValues);
    }
    public void insertarTarjetasDetalle(BaseDatos baseDatos){
        ContentValues contentValues=new ContentValues();
        contentValues.put("TaCoId",1);
        contentValues.put("PuCoDeId",1);
        contentValues.put("TaCoDeFecha","2017");
        contentValues.put("TaCoDeHora","15");
        contentValues.put("TaCoDeLatitud",20.43323232);
        contentValues.put("TaCoDeLongitud",-70.433232333);
        contentValues.put("TaCoDeTiempo","15");
        contentValues.put("TaCoDeDescripcion","Av. Lequia");
        contentValues.put("UsId",1);
        contentValues.put("UsFechaReg","2017");
        //Segundo Registro
        baseDatos.insertarTarjetaDetalle(contentValues);
        contentValues.put("TaCoId",1);
        contentValues.put("PuCoDeId",1);
        contentValues.put("TaCoDeFecha","2017-04-15");
        contentValues.put("TaCoDeHora","15");
        contentValues.put("TaCoDeLatitud",20.43323232);
        contentValues.put("TaCoDeLongitud",-70.433232333);
        contentValues.put("TaCoDeTiempo","15");
        contentValues.put("TaCoDeDescripcion","Av. dos de Mayo");
        contentValues.put("UsId",1);
        contentValues.put("UsFechaReg","2017");
        baseDatos.insertarTarjetaDetalle(contentValues);
    }
}

