package com.godared.controlbusmovil.service;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.godared.controlbusmovil.dao.BaseDatos;
import com.godared.controlbusmovil.pojo.TarjetaBitacoraMovil;
import com.godared.controlbusmovil.pojo.TarjetaControl;
import com.godared.controlbusmovil.pojo.TarjetaControlDetalle;
import com.godared.controlbusmovil.pojo.TarjetaDetalleBitacoraMovil;
import com.godared.controlbusmovil.restApi.IEndpointApi;
import com.godared.controlbusmovil.restApi.adapter.RestApiAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ronald on 29/04/2017.
 */

public class TarjetaService  implements ITarjetaService{  // extends ContextWrapper
    private Context context;
  //  private IMapsFragment iSettingFragment;
    BaseDatos db;
    private  ArrayList<TarjetaControl> tarjetasControl;
    private  ArrayList<TarjetaControlDetalle> tarjetasDetalle;
    PuntoControlService puntoControlService;
/*
    public ArrayList<TarjetaControlDetalle> getTarjetasDetalle() {
        return tarjetasDetalle;
    }
    public ArrayList<TarjetaControl> getTarjetasControl() {
        return tarjetasControl;
    }*/
    /* public TarjetaService(Context base){
            super(base);
            //context=base;
        }*/
    public TarjetaService(Context context){ //IMapsFragment iSettingFragment,
       // this.iSettingFragment=iSettingFragment;
        this.context=context;
        //obtenerTarjetasDetalleRest(1);
        db=new BaseDatos(context);
        puntoControlService=new PuntoControlService(context);
    }
    public void obtenerTarjetasDetalleRest(int taCoId){

        RestApiAdapter restApiAdapter = new RestApiAdapter();
        IEndpointApi endpointApi = restApiAdapter.establecerConexionRestApi();
        Call<List<TarjetaControlDetalle>> tarjetaControlDetalleResponseCall = endpointApi.getTarjetaControlDetalle(taCoId);
        //controlamos alguns eventos de a respuesta
        tarjetaControlDetalleResponseCall.enqueue(new Callback<List<TarjetaControlDetalle>>() {
                @Override
                public void onResponse(Call<List<TarjetaControlDetalle>> call, Response<List<TarjetaControlDetalle>> response) {
                    ArrayList<TarjetaControlDetalle> tarjetaControlDetalleResponse;
                    //tarjetaControlDetalleResponse=new ArrayList<>();
                    tarjetaControlDetalleResponse = (ArrayList<TarjetaControlDetalle>) response.body();
                    tarjetasDetalle = tarjetaControlDetalleResponse;// tarjetaControlDetalle.getTarjetasDetalle();

                    insertarTarjetasDetalleBD(db,tarjetasDetalle);
                    insertarTarjetaDetalleBitacoraMovilBD(tarjetasDetalle);

                }
            @Override
            public void onFailure(Call<List<TarjetaControlDetalle>> call, Throwable t) {
               Toast.makeText(context, "Algo paso en la conexion", Toast.LENGTH_SHORT).show();
                Log.e("Fallo la conexion", t.toString());
            }
        });

    }
    int _buId=0;
    String _taCoFecha=null;
    public ArrayList<TarjetaControl> obtenerTarjetasRest(int buId, String taCoFecha ){
        _buId=buId;_taCoFecha=taCoFecha;
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        IEndpointApi endpointApi = restApiAdapter.establecerConexionRestApi();
        Call<List<TarjetaControl>> tarjetasControlCall = endpointApi.getTarjetaControl(buId,taCoFecha);
        tarjetasControlCall.enqueue(new Callback<List<TarjetaControl>>() {
            @Override
            public void onResponse(Call<List<TarjetaControl>> call, Response<List<TarjetaControl>> response) {
                ArrayList<TarjetaControl> _tarjetaControl;
                _tarjetaControl=(ArrayList<TarjetaControl>) response.body();
                tarjetasControl=_tarjetaControl;
                //insertarTarjetasBD(db,tarjetasControl);
                for (TarjetaControl tarjetaControl : tarjetasControl){
                    TarjetaControl tarjetaControl2 = null;
                    tarjetaControl2 = db.ObtenerTarjeta(tarjetaControl.getTaCoId());
                    if (tarjetaControl2.getTaCoId() <1) {
                        insertarTarjetaBD(db,tarjetaControl);
                        insertarTarjetaBitacoraMovilBD(db,tarjetaControl);
                       puntoControlService.ObtenerPuntoControlRest(tarjetaControl.getPuCoId());
                        obtenerTarjetasDetalleRest(tarjetaControl.getTaCoId());
                    }else {
                        actualizarTarjetaBD(db,tarjetaControl);
                    }
                }

                //verificamos en la base de datos para  los reg q se han eliminado en el server.
                if (tarjetasControl.size()>0) {
                    List<TarjetaControl> tarjetasControl2;
                    tarjetasControl2 = db.ObtenerTarjetas(_buId, _taCoFecha);
                    for (TarjetaControl tarjetaControl : tarjetasControl2) {
                        int sw=0;
                        for (TarjetaControl tarjetaControl2 : tarjetasControl) {
                            if (tarjetaControl.getTaCoId()==tarjetaControl2.getTaCoId()){
                                sw=1;
                            }
                        }
                        //entonces no esta y se elimina
                        if (sw==0){
                            //LLamaar eliminar cabecera y detalle
                            db.eliminarTarjetaDetalleByTaCo(tarjetaControl.getTaCoId());
                            db.eliminarTarjeta(tarjetaControl.getTaCoId());
                            db.eliminarTarjetaBitacoraMovilByTaCo(tarjetaControl.getTaCoId());
                            db.eliminarTarjetaDetalleBitacoraMovilByTaCo(tarjetaControl.getTaCoId());
                        }

                    }
                    ///activamos el primer registro como activo.
                    actualizarActivoTarjetaBitacoraMovilBD(db,tarjetasControl);
                }


            }

            @Override
            public void onFailure(Call<List<TarjetaControl>> call, Throwable t) {
               // Toast.makeText(context, "Algo paso en la conexion", Toast.LENGTH_SHORT).show();
                Log.e("Fallo la conexion", t.toString());
            }

        });


        return tarjetasControl;
    }
    public void UpdateTarjetaDetalleRest(TarjetaControlDetalle tarjetaControlDetalle){
        final TarjetaControlDetalle  tarjetaControlDetalle2=tarjetaControlDetalle;
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        IEndpointApi endpointApi = restApiAdapter.establecerConexionRestApi();
        Call<Integer> tarjetaControlDetalleSend = endpointApi.updateTarjetaControlDetalle(tarjetaControlDetalle);
        tarjetaControlDetalleSend.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                //Aqui debe guardar el codigo remoto
                Integer codEnvio=response.body();
                Toast.makeText(context, "Se actualizo correctamento", Toast.LENGTH_SHORT).show();

                if(codEnvio>0){
                    //Actualizamos el envio
                    TarjetaDetalleBitacoraMovil tarjetaDetalleBitacoraMovil;
                    tarjetaDetalleBitacoraMovil=db.ObtenerTarjetaDetalleBitacoraMovilByTaCoDe(tarjetaControlDetalle2.getTaCoDeId());
                    tarjetaDetalleBitacoraMovil.setTaDeBiMoEnviado(1);
                    tarjetaDetalleBitacoraMovil.setTaDeBiMoRemotoId(codEnvio);
                    actualizarTarjetaDetalleBitacoraMovilBD(tarjetaControlDetalle2.getTaCoDeId(),tarjetaDetalleBitacoraMovil);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(context, "Algo paso en la conexion", Toast.LENGTH_SHORT).show();
                Log.e("Fallo la conexion", t.toString());
            }
        });
    }
    public TarjetaControl GetTarjetaControlBD(int taCoId){
        return db.ObtenerTarjeta(taCoId);
    }
    public ArrayList<TarjetaControlDetalle> GetAllTarjetaDetalleBD(int taCoId){

        return db.ObtenerTarjetasDetalle(taCoId);
    }
    public TarjetaControl GetTarjetaControlActivo(int buId, String taCoFecha){
        //Primero averiguamos cual tarjeta de control esta activo
        TarjetaControl _tarjetaControl=null;
        List<TarjetaControl> tarjetasControl2;
        tarjetasControl2 = db.ObtenerTarjetas(buId, taCoFecha);
        TarjetaBitacoraMovil tarjetaBitacoraMovil;
        int _taCoId=0,sw=0;
        for(TarjetaControl tarjetaControl:tarjetasControl2 ) {
            tarjetaBitacoraMovil = db.ObtenerTarjetaBitacoraMovilByTaCo(tarjetaControl.getTaCoId());
            if(tarjetaBitacoraMovil.getTaBiMoEnviado()==0 || tarjetaBitacoraMovil.getTaBiMoFinalDetalle()==0) {
                if(tarjetaBitacoraMovil.getTaBiMoActivo()==1){
                    _taCoId=tarjetaBitacoraMovil.getTaCoId();
                    sw=1;
                }
            }
            if(sw==1)
                break; /// salimos del for
        }

        return _tarjetaControl=db.ObtenerTarjeta(_taCoId);
    }
    public ArrayList<TarjetaControlDetalle> GetAllTarjetaDetalleBDByTaCoActivo(int buId, String taCoFecha){
        TarjetaControl _tarjetaControl=null;
        _tarjetaControl=GetTarjetaControlActivo(buId, taCoFecha);
        return db.ObtenerTarjetasDetalle(_tarjetaControl.getTaCoId());
    }
    public ArrayList<TarjetaControl> GetTarjetaControlBDEnviados(int enviado){
        return db.ObtenerTarjetasEnviado(enviado);
    }
    public Boolean VerificarTarjetaDetalleBDByTaCoDeRegistradoEnviado(int taCoDeId){
        //TarjetaControlDetalle tarjetaControlDetalle;
        //tarjetaControlDetalle = db.ObtenerTarjetaDetalleByTaCoDe(taCoDeId);
        Boolean _enviado=false;
        TarjetaDetalleBitacoraMovil tarjetaDetalleBitacoraMovil;
        tarjetaDetalleBitacoraMovil=db.ObtenerTarjetaDetalleBitacoraMovilByTaCoDe(taCoDeId);
        if(tarjetaDetalleBitacoraMovil.getTaDeBiMoEnviado()==1 ||tarjetaDetalleBitacoraMovil.getTaDeBiMoRegistradoId()==1 )
            _enviado=true;
        return  _enviado;
    }
    public void VerificarActualizaTarjetaFinaliza(int taCoId){
        List<TarjetaControlDetalle> tarjetasControlDetalle;
        tarjetasControlDetalle = db.ObtenerTarjetasDetalle(taCoId);
        TarjetaDetalleBitacoraMovil tarjetaDetalleBitacoraMovil;
        int sw=1;
        for(TarjetaControlDetalle tarjetaControlDetalle:tarjetasControlDetalle){
            tarjetaDetalleBitacoraMovil=db.ObtenerTarjetaDetalleBitacoraMovilByTaCoDe(tarjetaControlDetalle.getTaCoDeId());
            if(tarjetaDetalleBitacoraMovil.getTaDeBiMoRegistradoId()==0){
                sw=0;
                break;
            }
        }
        //Actualizamos  el campo finalizado de  TarjetaControlBitacoraMovil y con esto ya no se cargara en el movil para sincronizar
        TarjetaBitacoraMovil tarjetaBitacoraMovil;
        if (sw==1){
            tarjetaBitacoraMovil=db.ObtenerTarjetaBitacoraMovilByTaCo(taCoId);
            tarjetaBitacoraMovil.setTaBiMoFinalDetalle(1);

            actualizarTarjetaBitacoraMovilBD(tarjetaBitacoraMovil);
        }
    }
    public TarjetaControlDetalle GetTarjetaDetalleByPuCoDe(int puCoDeId){
        return db.ObtenerTarjetaDetalleByPuCoDe(puCoDeId);
    }
    public void insertarTarjetaBD(BaseDatos baseDatos, TarjetaControl tarjetaControl){
        ContentValues contentValues = new ContentValues();
        contentValues.put("TaCoId", tarjetaControl.getTaCoId());
        contentValues.put("PuCoId", tarjetaControl.getPuCoId());
        contentValues.put("RuId", tarjetaControl.getRuId());
        contentValues.put("BuId", tarjetaControl.getBuId());
        contentValues.put("TaCoFecha", tarjetaControl.getTaCoFecha());
        contentValues.put("TaCoHoraSalida", tarjetaControl.getTaCoHoraSalida());
        contentValues.put("TaCoCuota", tarjetaControl.getTaCoCuota());
        contentValues.put("UsId", tarjetaControl.getUsId());
        contentValues.put("TaCoNroVuelta", tarjetaControl.getTaCoNroVuelta());
        contentValues.put("PrId", tarjetaControl.getPrId());
        contentValues.put("TiSaId", tarjetaControl.getTiSaId());
        contentValues.put("TaCoAsignado", tarjetaControl.getTaCoAsignado());
        contentValues.put("TaCoTipoHoraSalida", tarjetaControl.getTaCoTipoHoraSalida());
        contentValues.put("ReDiDeId", tarjetaControl.getReDiDeId());
        contentValues.put("TaCoFinish", tarjetaControl.getTaCoFinish());
        contentValues.put("TaCoMultiple", tarjetaControl.getTaCoMultiple());
        contentValues.put("TaCoCodEnvioMovil", tarjetaControl.getTaCoCodEnvioMovil());
        contentValues.put("TaCoCountMultiple", tarjetaControl.getTaCoCountMultiple());
        contentValues.put("CoId", tarjetaControl.getCoId());

        baseDatos.insertarTarjeta(contentValues);
    }
    public void actualizarTarjetaBD(BaseDatos baseDatos, TarjetaControl tarjetaControl){
        ContentValues contentValues = new ContentValues();
        contentValues.put("TaCoId", tarjetaControl.getTaCoId());
        contentValues.put("PuCoId", tarjetaControl.getPuCoId());
        contentValues.put("RuId", tarjetaControl.getRuId());
        contentValues.put("BuId", tarjetaControl.getBuId());
        contentValues.put("TaCoFecha", tarjetaControl.getTaCoFecha());
        contentValues.put("TaCoHoraSalida", tarjetaControl.getTaCoHoraSalida());
        contentValues.put("TaCoCuota", tarjetaControl.getTaCoCuota());
        contentValues.put("UsId", tarjetaControl.getUsId());
        contentValues.put("UsFechaReg", tarjetaControl.getUsFechaReg());
        baseDatos.actualizarTarjeta(contentValues, tarjetaControl.getTaCoId());
    }

    public void insertarTarjetasDetalleBD(BaseDatos baseDatos, ArrayList<TarjetaControlDetalle> tarjetaControlDetalles){

        for(TarjetaControlDetalle tarjetaControlDetalle:tarjetaControlDetalles) {
            ContentValues contentValues=new ContentValues();
            contentValues.put("TaCoDeId",tarjetaControlDetalle.getTaCoDeId());
            contentValues.put("TaCoId",tarjetaControlDetalle.getTaCoId());
            contentValues.put("PuCoDeId",tarjetaControlDetalle.getPuCoDeId());
            contentValues.put("TaCoDeFecha",tarjetaControlDetalle.getTaCoDeFecha());
            contentValues.put("TaCoDeHora",tarjetaControlDetalle.getTaCoDeHora());
            contentValues.put("TaCoDeLatitud",tarjetaControlDetalle.getTaCoDeLatitud());
            contentValues.put("TaCoDeLongitud",tarjetaControlDetalle.getTaCoDeLongitud());
            contentValues.put("TaCoDeTiempo",tarjetaControlDetalle.getTaCoDeTiempo());
            contentValues.put("TaCoDeDescripcion",tarjetaControlDetalle.getTaCoDeDescripcion());
            contentValues.put("UsId",tarjetaControlDetalle.getUsId());
            contentValues.put("UsFechaReg",tarjetaControlDetalle.getUsFechaReg());
            baseDatos.insertarTarjetaDetalle(contentValues);

        }

    }
    public void actualizarTarjetaDetalleBD(TarjetaControlDetalle tarjetaControlDetalle){
            ContentValues contentValues=new ContentValues();
            contentValues.put("TaCoDeId",tarjetaControlDetalle.getTaCoDeId());
            contentValues.put("TaCoId",tarjetaControlDetalle.getTaCoId());
            contentValues.put("PuCoDeId",tarjetaControlDetalle.getPuCoDeId());
            contentValues.put("TaCoDeFecha",tarjetaControlDetalle.getTaCoDeFecha());
            contentValues.put("TaCoDeHora",tarjetaControlDetalle.getTaCoDeHora());
            contentValues.put("TaCoDeLatitud",tarjetaControlDetalle.getTaCoDeLatitud());
            contentValues.put("TaCoDeLongitud",tarjetaControlDetalle.getTaCoDeLongitud());
            contentValues.put("TaCoDeTiempo",tarjetaControlDetalle.getTaCoDeTiempo());
            contentValues.put("TaCoDeDescripcion",tarjetaControlDetalle.getTaCoDeDescripcion());
            contentValues.put("UsId",tarjetaControlDetalle.getUsId());
            contentValues.put("UsFechaReg",tarjetaControlDetalle.getUsFechaReg());
            db.actualizarTarjetaDetalle(contentValues,tarjetaControlDetalle.getTaCoDeId());
    }

    ///Para controlar los registros de envio, activo y otros
    public void insertarTarjetaBitacoraMovilBD(BaseDatos baseDatos, TarjetaControl tarjetaControl){
        ContentValues contentValues = new ContentValues();
        contentValues.put("TaCoId", tarjetaControl.getTaCoId());
        contentValues.put("TaBiMoRemotoId", 0);
        contentValues.put("TaBiMoEnviado", 0);
        contentValues.put("TaBiMoActivo", 0);
        contentValues.put("TaBiMoFinalDetalle", 0);
        baseDatos.insertarTarjetaBitacoraMovil(contentValues);

    }
    public void actualizarActivoTarjetaBitacoraMovilBD(BaseDatos baseDatos, List<TarjetaControl> tarjetasControl){
        //Ordenamos por TaCoId
        Collections.sort(tarjetasControl, new Comparator<TarjetaControl>() {
            public int compare(TarjetaControl lhs, TarjetaControl rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return lhs.getTaCoId() < rhs.getTaCoId() ? -1 : (lhs.getTaCoId() > rhs.getTaCoId() ) ? 1 : 0;
            }
        });

        ///Verificamos para activar
        int c=1, sw=0;
        int _activo=1;
        TarjetaBitacoraMovil tarjetaBitacoraMovil;
        for(TarjetaControl tarjetaControl:tarjetasControl ){
            tarjetaBitacoraMovil=db.ObtenerTarjetaBitacoraMovilByTaCo(tarjetaControl.getTaCoId());
            if(tarjetaBitacoraMovil.getTaBiMoEnviado()==0 || tarjetaBitacoraMovil.getTaBiMoFinalDetalle()==0) {
                sw=1;
                ContentValues contentValues = new ContentValues();
                contentValues.put("TaCoId", tarjetaControl.getTaCoId());
                contentValues.put("TaBiMoActivo", 1);
                baseDatos.actualizarTarjetaBitacoraMovil(contentValues, tarjetaControl.getTaCoId());
                c = c + 1;
            }
            if (sw==1)
                break;
        }

    }

    public void insertarTarjetaDetalleBitacoraMovilBD(List<TarjetaControlDetalle> tarjetaControlDetalles){
        for(TarjetaControlDetalle tarjetaControlDetalle:tarjetaControlDetalles) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("TaCoDeId", tarjetaControlDetalle.getTaCoDeId());
            contentValues.put("TaCoId", tarjetaControlDetalle.getTaCoId());
            contentValues.put("TaDeBiMoRemotoId", 0);
            contentValues.put("TaDeBiMoRegistradoId", 0);
            contentValues.put("TaDeBiMoEnviado", 0);
            db.insertarTarjetaDetalleBitacoraMovil(contentValues);
        }
    }
    public void actualizarTarjetaBitacoraMovilBD(TarjetaBitacoraMovil tarjetaBitacoraMovil){
        ContentValues contentValues=new ContentValues();
        contentValues.put("TaCoId",tarjetaBitacoraMovil.getTaCoId());
        contentValues.put("TaBiMoRemotoId",tarjetaBitacoraMovil.getTaBiMoRemotoId());
        contentValues.put("TaBiMoEnviado",tarjetaBitacoraMovil.getTaBiMoEnviado());
        contentValues.put("TaBiMoActivo",tarjetaBitacoraMovil.getTaBiMoActivo());
        contentValues.put("TaBiMoFinalDetalle",tarjetaBitacoraMovil.getTaBiMoFinalDetalle());

        db.actualizarTarjetaBitacoraMovil(contentValues, tarjetaBitacoraMovil.getTaCoId());
    }
    public void actualizarTarjetaDetalleBitacoraMovilBD(int taCoDeId,TarjetaDetalleBitacoraMovil tarjetaDetalleBitacoraMovil){
        ContentValues contentValues = new ContentValues();
        contentValues.put("TaCoDeId", tarjetaDetalleBitacoraMovil.getTaCoDeId());
        contentValues.put("TaCoId", tarjetaDetalleBitacoraMovil.getTaCoId());
        contentValues.put("TaDeBiMoRemotoId", tarjetaDetalleBitacoraMovil.getTaDeBiMoRemotoId());
        contentValues.put("TaDeBiMoRegistradoId", tarjetaDetalleBitacoraMovil.getTaDeBiMoRegistradoId());
        contentValues.put("TaDeBiMoEnviado", tarjetaDetalleBitacoraMovil.getTaDeBiMoEnviado());
        db.actualizarTarjetaDetalleBitacoraMovil(contentValues,taCoDeId );
    }
    public TarjetaDetalleBitacoraMovil obtenerTarjetaDetalleBitacoraMovilByTaCoDe(int taCoDeId){
        return db.ObtenerTarjetaDetalleBitacoraMovilByTaCoDe(taCoDeId);
    }

}
