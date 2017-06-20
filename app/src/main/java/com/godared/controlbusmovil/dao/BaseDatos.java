package com.godared.controlbusmovil.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.godared.controlbusmovil.pojo.TarjetaBitacoraMovil;
import com.godared.controlbusmovil.pojo.TarjetaControl;
import com.godared.controlbusmovil.pojo.TarjetaControlDetalle;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by ronald on 14/04/2017.
 */

public class BaseDatos extends SQLiteOpenHelper{
    Context context;
    public BaseDatos(Context context ) {
        super(context, ConstanteBaseDatos.DATABASE_NAME, null, ConstanteBaseDatos.DATABASE_VERSION);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCrearTablaTarjetaControl="CREATE TABLE TarjetaControl(TaCoId INTEGER PRIMARY KEY,"+
                "PuCoId INTEGER, RuId INTEGER, BuId INTEGER, TaCoFecha TEXT, TaCoHoraSalida TEXT,"+
                "TaCoCuota REAL, UsId INTEGER, UsFechaReg TEXT) ";
        String queryCrearTablaTarjetaControlDetalle="CREATE TABLE TarjetaControlDetalle(TaCoDeId INTEGER,"+
                "TaCoId INTEGER, PuCoDeId INTEGER, TaCoDeFecha TEXT, TaCoDeHora TEXT,"+
                "TaCoDeLatitud REAL,TaCoDeLongitud REAL,TaCoDeTiempo TEXT,TaCoDeDescripcion TEXT,"+
                "UsId INTEGER, UsFechaReg TEXT, FOREIGN KEY(TaCoId) REFERENCES TarjetaControl(TaCoId)) ";
        String queryCrearTablaTarjetaBitacoraMovil="CREATE TABLE TarjetaBitacoraMovil(TaCoId INTEGER,"+
                "TaBiMoRemotoId INTEGER, TaBiMoEnviado INTEGER, TaBiMoActivo integer, TaBiMoFinalDetalle INTEGER) ";

        db.execSQL(queryCrearTablaTarjetaControl);
        db.execSQL(queryCrearTablaTarjetaControlDetalle);
        db.execSQL(queryCrearTablaTarjetaBitacoraMovil);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST TarjetaControl");
        db.execSQL("DROP TABLE IF EXIST TarjetaControlDetalle");
        db.execSQL("DROP TABLE IF EXIST TarjetaBitacoraMovil");
        onCreate(db);
    }
    public TarjetaControl ObtenerTarjeta(int taCoId){
       // ArrayList<TarjetaControl> tarjetas=new ArrayList<>();
        TarjetaControl tarjetaActual=new TarjetaControl();
        String query="SELECT * FROM TarjetaControl where TaCoId="+taCoId;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor registros=db.rawQuery(query,null);
        while(registros.moveToNext()){
            tarjetaActual.setTaCoId(registros.getInt(0));
            tarjetaActual.setPuCoId(registros.getInt(1));
            tarjetaActual.setRuId(registros.getInt(2));
            tarjetaActual.setBuId(registros.getInt(3));
            //Formateando Fecha
          /*  String fecha=registros.getString(4);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formatted = sdf.format(new Date(fecha));
            tarjetaActual.setTaCoFecha(formatted);*/
            //Formateando Hora
            /*String hora=registros.getString(5);
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH:m:ss");//"HH:m:ss dd-MM-yyyy"
            String formatted2 = sdf2.format(new Date(hora));
            tarjetaActual.setTaCoFecha(formatted2);*/
            tarjetaActual.setTaCoCuota(registros.getFloat(6));
            tarjetaActual.setUsId(registros.getInt(7));
            //Formateando Fecha y Hora
            /*String fecha3=registros.getString(8);
            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:m:ss");
            String formatted3 = sdf3.format(new Date(fecha3));
            tarjetaActual.setUsFechaReg(formatted3);*/
           // tarjetas.add(tarjetaActual);
        }
        db.close();
        return tarjetaActual;
    }
    public List<TarjetaControl> ObtenerTarjetas(int buId, String fecha){

        List<TarjetaControl> tarjetasActual=new ArrayList<>();
        //convirtiendo a date
        TimeZone timeZone2 = TimeZone.getTimeZone("America/Lima");
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        formatter.setTimeZone(timeZone2);
        Date date1=new Date();
        try {
            date1 = formatter.parse(fecha);
        }catch (ParseException e) {
            e.printStackTrace();
        }
        Timestamp long1=new java.sql.Timestamp(date1.getTime());

        String query="SELECT * FROM TarjetaControl where BuId="+buId+" and TaCoFecha="+ long1.getTime();
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor registros=db.rawQuery(query,null);
        while(registros.moveToNext()) {
            TarjetaControl tarjetaActual=new TarjetaControl();
            tarjetaActual.setTaCoId(registros.getInt(0));
            tarjetaActual.setPuCoId(registros.getInt(1));
            tarjetaActual.setRuId(registros.getInt(2));
            tarjetaActual.setBuId(registros.getInt(3));
            tarjetaActual.setTaCoFecha(registros.getString(4));
            tarjetaActual.setTaCoCuota(registros.getFloat(6));
            tarjetaActual.setUsId(registros.getInt(7));
            tarjetasActual.add(tarjetaActual);
        }
        db.close();
        return tarjetasActual;

    }
    public ArrayList<TarjetaControlDetalle> ObtenerTarjetasDetalle(int taCoId){
        ArrayList<TarjetaControlDetalle> tarjetasDetalle=new ArrayList<>();
        String query="SELECT * FROM TarjetaControlDetalle where TaCoId=?";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor registros=db.rawQuery(query,new String []{String.valueOf(taCoId)});//
        while(registros.moveToNext()){
            TarjetaControlDetalle tarjetaDetalleActual=new TarjetaControlDetalle();
            tarjetaDetalleActual.setTaCoDeId(registros.getInt(0));
            tarjetaDetalleActual.setTaCoId(registros.getInt(1));
            tarjetaDetalleActual.setPuCoDeId(registros.getInt(2));
            //Formateando Fecha
            /*String fecha=registros.getString(3);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formatted = sdf.format(new Date(fecha));
            tarjetaDetalleActual.setTaCoDeFecha(formatted);*/
            //Formateando Hora
            /*String hora=registros.getString(4);
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH:m:ss");//"HH:m:ss dd-MM-yyyy"
            String formatted2 = sdf2.format(new Date(hora));*/
            tarjetaDetalleActual.setTaCoDeHora(registros.getString(4));//formatted2
            tarjetaDetalleActual.setTaCoDeLatitud(registros.getDouble(5));
            tarjetaDetalleActual.setTaCoDeLongitud(registros.getDouble(6));
            tarjetaDetalleActual.setTaCoDeTiempo(registros.getString(7));
            tarjetaDetalleActual.setTaCoDeDescripcion(registros.getString(8));
            tarjetaDetalleActual.setUsId(registros.getInt(9));
            //Formateando Fecha y Hora
            /*String fecha3=registros.getString(10);
            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:m:ss");
            String formatted3 = sdf3.format(new Date(fecha3));*
            tarjetaDetalleActual.setUsFechaReg(formatted3);*/
            tarjetasDetalle.add(tarjetaDetalleActual);
        }
        db.close();
        return tarjetasDetalle;
    }
    public void insertarTarjeta(ContentValues contentValues){
        SQLiteDatabase db=this.getWritableDatabase();
        db.insert("TarjetaControl",null,contentValues);
        db.close();
    }
    public void actualizarTarjeta(ContentValues contentValues,int taCoId){
        SQLiteDatabase db=this.getWritableDatabase();
        db.update("TarjetaControl",contentValues,"TaCoId="+taCoId,null);
        db.close();
    }
    public void eliminarTarjeta(int taCoId){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("TarjetaControl","TaCoId="+taCoId, null);
        db.close();
    }
    public void insertarTarjetaDetalle(ContentValues contentValues){
        SQLiteDatabase db=this.getWritableDatabase();
        db.insert("TarjetaControlDetalle",null,contentValues);
        db.close();
    }
    public void eliminarTarjetaDetalle(int taCoDeId){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("TarjetaControlDetalle","TaCoDeId="+taCoDeId, null);
        db.close();
    }
    public void eliminarTarjetaDetalleByTaCo(int taCoId){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("TarjetaControlDetalle","TaCoId="+taCoId, null);
        db.close();
    }
    public void actualizarTarjetaDetalle(ContentValues contentValues,int taCoDeId){
        SQLiteDatabase db=this.getWritableDatabase();
        db.update("TarjetaControlDetalle",contentValues,"TaCoDeId="+taCoDeId,null);
        db.close();
    }
    ///TarjetaBitacoraMovil
    public void insertarTarjetaBitacoraMovil(ContentValues contentValues){
        SQLiteDatabase db=this.getWritableDatabase();
        db.insert("TarjetaBitacoraMovil",null,contentValues);
        db.close();
    }
    public void actualizarTarjetaBitacoraMovil(ContentValues contentValues,int taCoId){
        SQLiteDatabase db=this.getWritableDatabase();
        db.update("TarjetaBitacoraMovil",contentValues,"TaCoId="+taCoId,null);
        db.close();
    }
    public void eliminarTarjetaBitacoraMovilByTaCo(int taCoId){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("TarjetaBitacoraMovil","TaCoId="+taCoId, null);
        db.close();
    }
    public TarjetaBitacoraMovil ObtenerTarjetaBitacoraMovilByTaCo(int taCoId) {
        List<TarjetaBitacoraMovil> tarjetasBitacoraMovil = new ArrayList<>();

        String query = "SELECT * FROM TarjetaBitacoraMovil where TaCoId=" + taCoId;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);
        while (registros.moveToNext()) {
            TarjetaBitacoraMovil tarjetaBitacoraMovil = new TarjetaBitacoraMovil();
            tarjetaBitacoraMovil.setTaCoId(registros.getInt(0));
            tarjetaBitacoraMovil.setTaBiMoRemotoId(registros.getInt(1));
            tarjetaBitacoraMovil.setTaBiMoEnviado(registros.getInt(2));
            tarjetaBitacoraMovil.setTaBiMoActivo(registros.getInt(3));
            tarjetaBitacoraMovil.setTaBiMoFinalDetalle(registros.getInt(4));

            tarjetasBitacoraMovil.add(tarjetaBitacoraMovil);
        }
        db.close();
        //if (tarjetasBitacoraMovil.size()>0)
            return tarjetasBitacoraMovil.get(0);
        //else
          //  return tarjetaBitacoraMovil;
    }
}
