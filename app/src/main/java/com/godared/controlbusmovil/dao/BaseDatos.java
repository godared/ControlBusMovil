package com.godared.controlbusmovil.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.godared.controlbusmovil.pojo.PuntoControl;
import com.godared.controlbusmovil.pojo.PuntoControlDetalle;
import com.godared.controlbusmovil.pojo.Ruta;
import com.godared.controlbusmovil.pojo.RutaDetalle;
import com.godared.controlbusmovil.pojo.TarjetaBitacoraMovil;
import com.godared.controlbusmovil.pojo.TarjetaControl;
import com.godared.controlbusmovil.pojo.TarjetaControlDetalle;
import com.godared.controlbusmovil.pojo.TarjetaDetalleBitacoraMovil;
import com.godared.controlbusmovil.pojo.Telefono;
import com.godared.controlbusmovil.pojo.TelefonoImei;

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
        //Tarjeta de Control
        String queryCrearTablaTarjetaControl="CREATE TABLE TarjetaControl(TaCoId INTEGER PRIMARY KEY,"+
                "PuCoId INTEGER, RuId INTEGER, BuId INTEGER, TaCoFecha TEXT, TaCoHoraSalida TEXT,"+
                "TaCoCuota REAL, UsId INTEGER, UsFechaReg TEXT) ";
        String queryCrearTablaTarjetaControlDetalle="CREATE TABLE TarjetaControlDetalle(TaCoDeId INTEGER,"+
                "TaCoId INTEGER, PuCoDeId INTEGER, TaCoDeFecha TEXT, TaCoDeHora TEXT,"+
                "TaCoDeLatitud REAL,TaCoDeLongitud REAL,TaCoDeTiempo TEXT,TaCoDeDescripcion TEXT,"+
                "UsId INTEGER, UsFechaReg TEXT, FOREIGN KEY(TaCoId) REFERENCES TarjetaControl(TaCoId)) ";

                //Punto de Control
        String queryCrearTablaPuntoControl="CREATE TABLE PuntoControl(PuCoId INTEGER PRIMARY KEY,"+
                "RuId INTEGER, PuCoTiempoBus TEXT, PuCoClase TEXT,"+
                "UsId INTEGER, UsFechaReg TEXT,PuCoDescripcion TEXT) ";
        String queryCrearTablaPuntoControlDetalle="CREATE TABLE PuntoControlDetalle(PuCoDeId INTEGER,"+
                "PuCoId INTEGER, PuCoDeLatitud REAL, PuCoDeLongitud REAL, PuCoDeDescripcion TEXT,"+
                "PuCoDeHora TEXT,UsId INTEGER, UsFechaReg TEXT,"+
                "PuCoDeOrden INTEGER, FOREIGN KEY(PuCoId) REFERENCES PuntoControl(PuCoId)) ";
        //Ruta
        String queryCrearTablaRuta="CREATE TABLE Ruta(RuId INTEGER PRIMARY KEY,"+
                "EmId INTEGER, RuDescripcion TEXT, RuFechaCreacion TEXT,"+
                "RuRegMunicipal TEXT, RuKilometro REAL,RuActivo INTEGER, UsId INTEGER, UsFechaReg TEXT) ";
        String queryCrearTablaRutaDetalle="CREATE TABLE RutaDetalle(RuDeId INTEGER,"+
                "RuId INTEGER, RuDeOrden INTEGER,RuDeLatitud REAL, RuDeLongitud REAL,"+
                "UsId INTEGER, UsFechaReg TEXT, "+
                "FOREIGN KEY(RuId) REFERENCES Ruta(RuId))";
        //Telefono
        String queryCrearTablaTelefono="CREATE TABLE Telefono(TeId INTEGER PRIMARY KEY,"+
                "BuId INTEGER, TeMarca TEXT, TeModelo TEXT,TeVersionAndroid TEXT,TeActivo INTEGER,"+
                "TeImei TEXT, UsId INTEGER, UsFechaReg TEXT) ";
        String queryCrearTablaTelefonoImei="CREATE TABLE TelefonoImei(TeId INTEGER PRIMARY KEY,"+
                "BuId INTEGER, EmId INTEGER, SuEmRSocial TEXT,BuPlaca TEXT,TeMarca TEXT,"+
                "TeImei TEXT, EmConsorcio TEXT) ";

        //Bitacora, estas tablas es para el control de envios
        String queryCrearTablaTarjetaBitacoraMovil="CREATE TABLE TarjetaBitacoraMovil(TaCoId INTEGER,"+
                "TaBiMoRemotoId INTEGER, TaBiMoEnviado INTEGER, TaBiMoActivo integer, TaBiMoFinalDetalle INTEGER) ";
        String queryCrearTablaTarjetaDetalleBitacoraMovil="CREATE TABLE TarjetaDetalleBitacoraMovil(TaCoDeId INTEGER, TaCoId INTEGER,"+
                "TaDeBiMoRemotoId INTEGER,TaDeBiMoRegistradoId INTEGER, TaDeBiMoEnviado INTEGER) ";

        db.execSQL(queryCrearTablaTarjetaControl);
        db.execSQL(queryCrearTablaTarjetaControlDetalle);
        db.execSQL(queryCrearTablaPuntoControl);
        db.execSQL(queryCrearTablaPuntoControlDetalle);
        db.execSQL(queryCrearTablaRuta);
        db.execSQL(queryCrearTablaRutaDetalle);
        db.execSQL(queryCrearTablaTelefono);
        db.execSQL(queryCrearTablaTelefonoImei);
        db.execSQL(queryCrearTablaTarjetaBitacoraMovil);
        db.execSQL(queryCrearTablaTarjetaDetalleBitacoraMovil);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST TarjetaControl");
        db.execSQL("DROP TABLE IF EXIST TarjetaControlDetalle");
        db.execSQL("DROP TABLE IF EXIST PuntoControl");
        db.execSQL("DROP TABLE IF EXIST PuntoControlDetalle");
        db.execSQL("DROP TABLE IF EXIST Ruta");
        db.execSQL("DROP TABLE IF EXIST RutaDetalle");
        db.execSQL("DROP TABLE IF EXIST Telefono");
        db.execSQL("DROP TABLE IF EXIST TelefonoImei");
        db.execSQL("DROP TABLE IF EXIST TarjetaBitacoraMovil");
        db.execSQL("DROP TABLE IF EXIST TarjetaDetalleBitacoraMovil");
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
    public TarjetaControlDetalle ObtenerTarjetaDetalleByPuCoDe(int puCoDeId){
        TarjetaControlDetalle tarjetaDetalleActual=new TarjetaControlDetalle();
        String query="SELECT * FROM TarjetaControlDetalle where PuCoDeId="+puCoDeId;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor registros=db.rawQuery(query,null);
        while(registros.moveToNext()){
            tarjetaDetalleActual.setTaCoDeId(registros.getInt(0));
            tarjetaDetalleActual.setTaCoId(registros.getInt(1));
            tarjetaDetalleActual.setPuCoDeId(registros.getInt(2));
            tarjetaDetalleActual.setTaCoDeFecha(registros.getString(3));
            tarjetaDetalleActual.setTaCoDeHora(registros.getString(4));
            tarjetaDetalleActual.setTaCoDeLatitud(registros.getDouble(5));
            tarjetaDetalleActual.setTaCoDeLongitud(registros.getDouble(6));
            tarjetaDetalleActual.setTaCoDeTiempo(registros.getString(7));
            tarjetaDetalleActual.setTaCoDeDescripcion(registros.getString(8));
            tarjetaDetalleActual.setUsId(registros.getInt(9));
            tarjetaDetalleActual.setUsFechaReg(registros.getString(10));
        }
        db.close();
        return tarjetaDetalleActual;
    }
    public TarjetaControlDetalle ObtenerTarjetaDetalleByTaCoDe(int taCoDeId){
        TarjetaControlDetalle tarjetaDetalleActual=new TarjetaControlDetalle();
        String query="SELECT * FROM TarjetaControlDetalle where TaCoDeId="+taCoDeId;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor registros=db.rawQuery(query,null);
        while(registros.moveToNext()){
            tarjetaDetalleActual.setTaCoDeId(registros.getInt(0));
            tarjetaDetalleActual.setTaCoId(registros.getInt(1));
            tarjetaDetalleActual.setPuCoDeId(registros.getInt(2));
            tarjetaDetalleActual.setTaCoDeFecha(registros.getString(3));
            tarjetaDetalleActual.setTaCoDeHora(registros.getString(4));
            tarjetaDetalleActual.setTaCoDeLatitud(registros.getDouble(5));
            tarjetaDetalleActual.setTaCoDeLongitud(registros.getDouble(6));
            tarjetaDetalleActual.setTaCoDeTiempo(registros.getString(7));
            tarjetaDetalleActual.setTaCoDeDescripcion(registros.getString(8));
            tarjetaDetalleActual.setUsId(registros.getInt(9));
            tarjetaDetalleActual.setUsFechaReg(registros.getString(10));
        }
        db.close();
        return tarjetaDetalleActual;
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
    //PuntoControl
    public PuntoControl ObtenerPuntoControl(int puCoId){
        // ArrayList<TarjetaControl> tarjetas=new ArrayList<>();
        PuntoControl puntoActual=new PuntoControl();
        String query="SELECT * FROM PuntoControl where PuCoId="+puCoId;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor registros=db.rawQuery(query,null);
        while(registros.moveToNext()){
            puntoActual.setPuCoId(registros.getInt(0));
            puntoActual.setRuId(registros.getInt(1));
            puntoActual.setPuCoTiempoBus(registros.getString(2));
            puntoActual.setPuCoClase(registros.getString(3));
            puntoActual.setUsId(registros.getInt(4));
            puntoActual.setUsFechaReg(registros.getString(5));
            puntoActual.setPuCoDescripcion(registros.getString(6));
        }
        db.close();
        return puntoActual;
    }
    public void InsertarPuntoControl(ContentValues contentValues){
        SQLiteDatabase db=this.getWritableDatabase();
        db.insert("PuntoControl",null,contentValues);
        db.close();
    }
    public void ActualizarPuntoControl(ContentValues contentValues,int puCoId){
        SQLiteDatabase db=this.getWritableDatabase();
        db.update("PuntoControl",contentValues,"PuCoId="+puCoId,null);
        db.close();
    }
    public void EliminarPuntoControl(int puCoId){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("PuntoControl","PuCoId="+puCoId, null);
        db.close();
    }
    public ArrayList<PuntoControlDetalle> ObtenerPuntoControlDetalle(int puCoId){
        ArrayList<PuntoControlDetalle> puntosDetalle=new ArrayList<>();
        String query="SELECT * FROM PuntoControlDetalle where PuCoId=?";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor registros=db.rawQuery(query,new String []{String.valueOf(puCoId)});//
        while(registros.moveToNext()){
            PuntoControlDetalle puntoDetalleActual=new PuntoControlDetalle();
            puntoDetalleActual.setPuCoDeId(registros.getInt(0));
            puntoDetalleActual.setPuCoId(registros.getInt(1));
            puntoDetalleActual.setPuCoDeLatitud(registros.getDouble(2));
            puntoDetalleActual.setPuCoDeLongitud(registros.getDouble(3));//formatted2
            puntoDetalleActual.setPuCoDeDescripcion(registros.getString(4));
            puntoDetalleActual.setPuCoDeHora(registros.getString(5));
            puntoDetalleActual.setUsId(registros.getInt(6));
            puntoDetalleActual.setUsFechaReg(registros.getString(7));
            puntoDetalleActual.setPuCoDeOrden(registros.getInt(8));
            puntosDetalle.add(puntoDetalleActual);
        }
        db.close();
        return puntosDetalle;
    }
    public void InsertarPuntoControlDetalle(ContentValues contentValues){
        SQLiteDatabase db=this.getWritableDatabase();
        db.insert("PuntoControlDetalle",null,contentValues);
        db.close();
    }
    public void EliminarPuntoControlDetalle(int puCoDeId){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("PuntoControlDetalle","PuCoDeId="+puCoDeId, null);
        db.close();
    }
    public void EliminarPuntoControlDetalleByPuCo(int puCoId){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("PuntoControlDetalle","PuCoId="+puCoId, null);
        db.close();
    }
    //Ruta
    public Ruta ObtenerRuta(int ruId){
        // ArrayList<TarjetaControl> tarjetas=new ArrayList<>();
        Ruta rutaActual=new Ruta();
        String query="SELECT * FROM Ruta where RuId="+ruId;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor registros=db.rawQuery(query,null);
        while(registros.moveToNext()){
            rutaActual.setRuId(registros.getInt(0));
            rutaActual.setEmId(registros.getInt(1));
            rutaActual.setRuDescripcion(registros.getString(2));
            rutaActual.setRuFechaCreacion(registros.getString(3));
            rutaActual.setRuRegMunicipal(registros.getString(4));
            rutaActual.setRuKilometro(registros.getFloat(5));
            //rutaActual.setRuActivo(registros.getInt(6));
            rutaActual.setUsId(registros.getInt(7));
            rutaActual.setUsFechaReg(registros.getString(8));
        }
        db.close();
        return rutaActual;
    }
    public void InsertarRuta(ContentValues contentValues){
        SQLiteDatabase db=this.getWritableDatabase();
        db.insert("Ruta",null,contentValues);
        db.close();
    }
    public void ActualizarRuta(ContentValues contentValues,int ruId){
        SQLiteDatabase db=this.getWritableDatabase();
        db.update("Ruta",contentValues,"RuId="+ruId,null);
        db.close();
    }
    public void EliminarRuta(int ruId){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("Ruta","RuId="+ruId, null);
        db.close();
    }
    public ArrayList<RutaDetalle> ObtenerRutaDetalle(int ruId){
        ArrayList<RutaDetalle> rutasDetalle=new ArrayList<>();
        String query="SELECT * FROM RutaDetalle where RuId=?";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor registros=db.rawQuery(query,new String []{String.valueOf(ruId)});//
        while(registros.moveToNext()){
            RutaDetalle rutaDetalleActual=new RutaDetalle();
            rutaDetalleActual.setRuDeId(registros.getInt(0));
            rutaDetalleActual.setRuId(registros.getInt(1));
            rutaDetalleActual.setRuDeOrden(registros.getInt(2));
            rutaDetalleActual.setRuDeLatitud(registros.getDouble(3));
            rutaDetalleActual.setRuDeLongitud(registros.getDouble(4));//formatted2
            rutaDetalleActual.setUsId(registros.getInt(5));
            rutaDetalleActual.setUsFechaReg(registros.getString(6));
            rutasDetalle.add(rutaDetalleActual);
        }
        db.close();
        return rutasDetalle;
    }
    public void InsertarRutaDetalle(ContentValues contentValues){
        SQLiteDatabase db=this.getWritableDatabase();
        db.insert("RutaDetalle",null,contentValues);
        db.close();
    }
    public void EliminarRutaDetalle(int ruDeId){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("RutaDetalle","RuDeId="+ruDeId, null);
        db.close();
    }
    public void EliminarRutaDetalleByRu(int ruId){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("RutaDetalle","RuId="+ruId, null);
        db.close();
    }
    //Telefono
    public Telefono ObtenerTelefono(int teId){
        // ArrayList<TarjetaControl> tarjetas=new ArrayList<>();
        Telefono telefonoActual=new Telefono();
        String query="SELECT * FROM Telefono where TeId="+teId;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor registros=db.rawQuery(query,null);
        while(registros.moveToNext()){
            telefonoActual.setTeId(registros.getInt(0));
            telefonoActual.setBuId(registros.getInt(1));
            telefonoActual.setTeMarca(registros.getString(2));
            telefonoActual.setTeModelo(registros.getString(3));
            telefonoActual.setTeVersionAndroid(registros.getString(4));
            telefonoActual.setTeActivo(registros.getInt(5));
            telefonoActual.setTeImei(registros.getString(6));
            telefonoActual.setUsId(registros.getInt(7));
            telefonoActual.setUsFechaReg(registros.getString(8));
        }
        db.close();
        return telefonoActual;
    }
    public void InsertarTelefono(ContentValues contentValues){
        SQLiteDatabase db=this.getWritableDatabase();
        db.insert("Telefono",null,contentValues);
        db.close();
    }
    public void ActualizarTelefono(ContentValues contentValues,int teId){
        SQLiteDatabase db=this.getWritableDatabase();
        db.update("Telefono",contentValues,"TeId="+teId,null);
        db.close();
    }
    public void EliminarTelefono(int teId){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("Telefono","TeId="+teId, null);
        db.close();
    }
    public TelefonoImei ObtenerTelefonoImei(int teId){
        // ArrayList<TarjetaControl> tarjetas=new ArrayList<>();
        TelefonoImei telefonoImeiActual=new TelefonoImei();
        String query="SELECT * FROM TelefonoImei where TeId="+teId;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor registros=db.rawQuery(query,null);
        while(registros.moveToNext()){
            telefonoImeiActual.setTeId(registros.getInt(0));
            telefonoImeiActual.setBuId(registros.getInt(1));
            telefonoImeiActual.setEmId(registros.getInt(2));
            telefonoImeiActual.setSuEmRSocial(registros.getString(3));
            telefonoImeiActual.setBuPlaca(registros.getString(4));
            telefonoImeiActual.setTeMarca(registros.getString(5));
            telefonoImeiActual.setTeImei(registros.getString(6));
            telefonoImeiActual.setEmConsorcio(registros.getString(7));
        }
        db.close();
        return telefonoImeiActual;
    }
    public List<TelefonoImei> ObtenerTelefonoImeibyImei(String teImei){
        ArrayList<TelefonoImei> telefonoImeis=new ArrayList<>();
        String query="SELECT * FROM TelefonoImei where TeImei =?";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor registros=db.rawQuery(query,new String []{teImei});
        while(registros.moveToNext()){
            TelefonoImei telefonoImeiActual=new TelefonoImei();
            telefonoImeiActual.setTeId(registros.getInt(0));
            telefonoImeiActual.setBuId(registros.getInt(1));
            telefonoImeiActual.setEmId(registros.getInt(2));
            telefonoImeiActual.setSuEmRSocial(registros.getString(3));
            telefonoImeiActual.setBuPlaca(registros.getString(4));
            telefonoImeiActual.setTeMarca(registros.getString(5));
            telefonoImeiActual.setTeImei(registros.getString(6));
            telefonoImeiActual.setEmConsorcio(registros.getString(7));
            telefonoImeis.add(telefonoImeiActual);
        }
        db.close();
        return telefonoImeis;
    }
    public void InsertarTelefonoImei(ContentValues contentValues){
        SQLiteDatabase db=this.getWritableDatabase();
        db.insert("TelefonoImei",null,contentValues);
        db.close();
    }
    public void ActualizarTelefonoImei(ContentValues contentValues,int teId){
        SQLiteDatabase db=this.getWritableDatabase();
        db.update("TelefonoImei",contentValues,"TeId="+teId,null);
        db.close();
    }
    public void EliminarTelefonoImei(int teId){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("TelefonoImei","TeId="+teId, null);
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
    //TarjetaDetalleBitacoraMovil
    public void insertarTarjetaDetalleBitacoraMovil(ContentValues contentValues){
        SQLiteDatabase db=this.getWritableDatabase();
        db.insert("TarjetaDetalleBitacoraMovil",null,contentValues);
        db.close();
    }
    public void eliminarTarjetaDetalleBitacoraMovilByTaCo(int taCoId){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("TarjetaDetalleBitacoraMovil","TaCoId="+taCoId, null);
        db.close();
    }
    public void actualizarTarjetaDetalleBitacoraMovil(ContentValues contentValues,int taCoDeId){
        SQLiteDatabase db=this.getWritableDatabase();
        db.update("TarjetaDetalleBitacoraMovil",contentValues,"TaCoDeId="+taCoDeId,null);
        db.close();
    }
    public TarjetaDetalleBitacoraMovil ObtenerTarjetaDetalleBitacoraMovilByTaCoDe(int taCoDeId) {
        List<TarjetaDetalleBitacoraMovil> tarjetasDetalleBitacoraMovil = new ArrayList<>();
        String query = "SELECT * FROM TarjetaDetalleBitacoraMovil where TaCoDeId=" + taCoDeId;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor registros = db.rawQuery(query, null);
        while (registros.moveToNext()) {
            TarjetaDetalleBitacoraMovil tarjetaDetalleBitacoraMovil = new TarjetaDetalleBitacoraMovil();
            tarjetaDetalleBitacoraMovil.setTaCoDeId(registros.getInt(0));
            tarjetaDetalleBitacoraMovil.setTaCoId(registros.getInt(1));
            tarjetaDetalleBitacoraMovil.setTaDeBiMoRemotoId(registros.getInt(2));
            tarjetaDetalleBitacoraMovil.setTaDeBiMoRegistradoId(registros.getInt(3));
            tarjetaDetalleBitacoraMovil.setTaDeBiMoEnviado(registros.getInt(4));
            tarjetasDetalleBitacoraMovil.add(tarjetaDetalleBitacoraMovil);
        }
        db.close();

        //if (tarjetasBitacoraMovil.size()>0)
        return tarjetasDetalleBitacoraMovil.get(0);
        //else
        //  return tarjetaBitacoraMovil;
    }
}
