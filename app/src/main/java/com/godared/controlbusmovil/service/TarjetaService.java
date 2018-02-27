package com.godared.controlbusmovil.service;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.godared.controlbusmovil.adapter.TarjetaAdaptadorRV;
import com.godared.controlbusmovil.dao.BaseDatos;
import com.godared.controlbusmovil.pojo.Georeferencia;
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
    GeoreferenciaService georeferenciaService;
    TarjetaControl tarjetaControl222;
    public interface TarjetaServiceListener {
        public void listenObtenerTarjetasDetalleRest();
        //public void onDialogNegativeClick();
    }
    TarjetaServiceListener mListener;
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
    public TarjetaService(TarjetaServiceListener mListener,Context context){
        this.context=context;
        this.mListener=mListener;
        db=new BaseDatos(context);
        puntoControlService=new PuntoControlService(context);
        georeferenciaService=new GeoreferenciaService(context);
    }
    public TarjetaService(Context context){ //IMapsFragment iSettingFragment,this.iSettingFragment=iSettingFragment;
        this.context=context;
        //obtenerTarjetasDetalleRest(1);
        db=new BaseDatos(context);
        puntoControlService=new PuntoControlService(context);
        georeferenciaService=new GeoreferenciaService(context);
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

                    //////

                    //ArrayList<TarjetaControlDetalle> tarjetaControls;
                    //tarjetaControls=GetAllTarjetaDetalleBDById(tarjetasDetalle.get(0).getTaCoId());
                    mListener.listenObtenerTarjetasDetalleRest();

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
    public ArrayList<TarjetaControl> obtenerTarjetasRest(int emId,int buId, String taCoFecha ){
        _buId=buId;_taCoFecha=taCoFecha;
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        IEndpointApi endpointApi = restApiAdapter.establecerConexionRestApi();
        Call<List<TarjetaControl>> tarjetasControlCall = endpointApi.getTarjetaControl(emId,buId,taCoFecha);
        tarjetasControlCall.enqueue(new Callback<List<TarjetaControl>>() {
            @Override
            public void onResponse(Call<List<TarjetaControl>> call, Response<List<TarjetaControl>> response) {
                ArrayList<TarjetaControl> _tarjetaControl=null;
                _tarjetaControl=(ArrayList<TarjetaControl>) response.body();
                tarjetasControl=_tarjetaControl;
                //insertarTarjetasBD(db,tarjetasControl);
                if(tarjetasControl!=null ) { // a veces no se porque devuelve null
                    for (TarjetaControl tarjetaControl : tarjetasControl) {
                        TarjetaControl tarjetaControl2 = null;
                        tarjetaControl2 = db.ObtenerTarjeta(tarjetaControl.getTaCoId());
                        if (tarjetaControl2.getTaCoId() < 1) {
                            //verificamos que no sea ausente o castigado
                            if (tarjetaControl.getTaCoAsignado().compareTo("1")==0) {
                                insertarTarjetaBD(db, tarjetaControl);
                                insertarTarjetaBitacoraMovilBD(db, tarjetaControl);
                                puntoControlService.ObtenerPuntoControlRest(tarjetaControl.getPuCoId());
                                obtenerTarjetasDetalleRest(tarjetaControl.getTaCoId());
                            }
                        } else {
                            actualizarTarjetaBD(db, tarjetaControl);
                        }
                    }

                    //verificamos en la base de datos para  los reg q se han eliminado en el server.
                    if (tarjetasControl.size() > 0) {
                        List<TarjetaControl> tarjetasControl2;
                        tarjetasControl2 = db.ObtenerTarjetas(_buId, _taCoFecha);
                        for (TarjetaControl tarjetaControl : tarjetasControl2) {
                            int sw = 0;
                            for (TarjetaControl tarjetaControl2 : tarjetasControl) {
                                //verificamos que no sea ausente o castigado
                                if (tarjetaControl2.getTaCoAsignado().compareTo("1")==0) {
                                    if (tarjetaControl.getTaCoId() == tarjetaControl2.getTaCoId()) {
                                        sw = 1;
                                    }
                                }
                            }
                            //entonces no esta y se elimina
                            if (sw == 0) {
                                //LLamaar eliminar cabecera y detalle
                                db.eliminarTarjetaDetalleByTaCo(tarjetaControl.getTaCoId());
                                db.eliminarTarjeta(tarjetaControl.getTaCoId());
                                db.eliminarTarjetaBitacoraMovilByTaCo(tarjetaControl.getTaCoId());
                                db.eliminarTarjetaDetalleBitacoraMovilByTaCo(tarjetaControl.getTaCoId());
                            }

                        }
                        ///activamos el primer registro como activo.
                        actualizarActivoTarjetaBitacoraMovilBD(db, tarjetasControl);
                    }

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
    public void UpdateTarjetaRest(TarjetaControl tarjetaControl){
        final TarjetaControl tarjetaControl1=tarjetaControl;
        //tarjetaControl222=tarjetaControl;
        tarjetaControl1.setUsFechaReg(null);
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        IEndpointApi endpointApi = restApiAdapter.establecerConexionRestApi();
        Call<Integer> tarjetaControlSend=endpointApi.updateTarjetaControl(tarjetaControl1);
        tarjetaControlSend.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Integer codEnvio=(Integer)response.body();
                Toast.makeText(context, "Se actualizo correctamento", Toast.LENGTH_SHORT).show();
                if(codEnvio>0){
                    //Actualizamos el envio
                    TarjetaBitacoraMovil _tarjetaBitacoraMovil;
                    _tarjetaBitacoraMovil=db.ObtenerTarjetaBitacoraMovilByTaCo(tarjetaControl1.getTaCoId());
                    _tarjetaBitacoraMovil.setTaBiMoEnviado(1);
                    _tarjetaBitacoraMovil.setTaBiMoRemotoId(codEnvio);
                    actualizarTarjetaBitacoraMovilBD(_tarjetaBitacoraMovil);
                    //Ahora Actualizamos el Codigo de envio en la Tarjeta Control
                    tarjetaControl1.setTaCoCodEnvioMovil(codEnvio);
                    actualizarTarjetaBD(db,tarjetaControl1);

                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(context, "Algo paso en la conexion", Toast.LENGTH_SHORT).show();
                Log.e("Fallo la conexion", t.toString());
            }
        });
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
    public void UpdateTarjetaDetallesRest(List<TarjetaControlDetalle> tarjetaControlDetalles){
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        IEndpointApi endpointApi = restApiAdapter.establecerConexionRestApi();
        Call<List<TarjetaControlDetalle>> tarjetaControlDetallesResponseCall = endpointApi.updateTarjetaControlDetalles(tarjetaControlDetalles);
        tarjetaControlDetallesResponseCall.enqueue(new Callback<List<TarjetaControlDetalle>>() {
            @Override
            public void onResponse(Call<List<TarjetaControlDetalle>> call, Response<List<TarjetaControlDetalle>> response) {
                ArrayList<TarjetaControlDetalle> tarjetaControlDetallesResponse;
                tarjetaControlDetallesResponse = (ArrayList<TarjetaControlDetalle>) response.body();
                for(TarjetaControlDetalle tarjetaControlDetalle:tarjetaControlDetallesResponse){
                    if(tarjetaControlDetalle.getTaCoDeCodEnvioMovil()>0){
                        //Actualizamos el envio
                        TarjetaDetalleBitacoraMovil tarjetaDetalleBitacoraMovil;
                        tarjetaDetalleBitacoraMovil=db.ObtenerTarjetaDetalleBitacoraMovilByTaCoDe(tarjetaControlDetalle.getTaCoDeId());
                        tarjetaDetalleBitacoraMovil.setTaDeBiMoEnviado(1);
                        tarjetaDetalleBitacoraMovil.setTaDeBiMoRemotoId(tarjetaControlDetalle.getTaCoDeCodEnvioMovil());
                        actualizarTarjetaDetalleBitacoraMovilBD(tarjetaControlDetalle.getTaCoDeId(),tarjetaDetalleBitacoraMovil);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<TarjetaControlDetalle>> call, Throwable t) {
                Toast.makeText(context, "Algo paso en la conexion", Toast.LENGTH_SHORT).show();
                Log.e("Fallo la conexion", t.toString());
            }
        });
    }
    public TarjetaControl GetTarjetaControlBD(int taCoId){
        return db.ObtenerTarjeta(taCoId);
    }
    public ArrayList<TarjetaControlDetalle> GetAllTarjetaDetalleBDById(int taCoId){
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
            if(tarjetaBitacoraMovil.getTaBiMoEnviado()==0 && tarjetaBitacoraMovil.getTaBiMoFinalDetalle()==0) {
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
    public ArrayList<TarjetaControl> GetTarjetaControlBDEnviados(int buId, String taCoFecha, int enviado){
        //Primero obtenemos la posicion del registro activo,
        // y solamente obtendremos los anteriores a este y no los posteriores

        TarjetaControl _tarjetaControl=null;
        _tarjetaControl=GetTarjetaControlActivo(buId, taCoFecha);
        ArrayList<TarjetaControl> _tarjetaControls=null;
        //Obtenemos las tarjetas de control enviados y por enviar
        _tarjetaControls=db.ObtenerTarjetasEnviado(_tarjetaControl.getTaCoId(),enviado);
        ArrayList<TarjetaControl> tarjetaControls=new ArrayList<>();
        TarjetaControl _tarjetaControl1;
        TarjetaBitacoraMovil _tarjetaBitacoraMovil;
        for(TarjetaControl tarjetaControl:_tarjetaControls){
            _tarjetaControl1=new TarjetaControl();
            _tarjetaControl1.setTaCoId(tarjetaControl.getTaCoId());
            _tarjetaControl1.setPuCoId(tarjetaControl.getPuCoId());
            _tarjetaControl1.setRuId(tarjetaControl.getRuId());
            _tarjetaControl1.setBuId(tarjetaControl.getBuId());
            _tarjetaControl1.setTaCoFecha(tarjetaControl.getTaCoFecha());
            _tarjetaControl1.setTaCoHoraSalida(tarjetaControl.getTaCoHoraSalida());
            _tarjetaControl1.setTaCoCuota(tarjetaControl.getTaCoCuota());
            _tarjetaControl1.setUsId(tarjetaControl.getUsId());
            //_tarjetaControl1.setUsFechaReg(tarjetaControl.getUsFechaReg());
            _tarjetaControl1.setTaCoNroVuelta(tarjetaControl.getTaCoNroVuelta());
            _tarjetaControl1.setPrId(tarjetaControl.getPrId());
            _tarjetaControl1.setTiSaId(tarjetaControl.getTiSaId());
            _tarjetaControl1.setTaCoAsignado(tarjetaControl.getTaCoAsignado());
            _tarjetaControl1.setTaCoTipoHoraSalida(tarjetaControl.getTaCoTipoHoraSalida());
            _tarjetaControl1.setReDiDeId(tarjetaControl.getReDiDeId());
            _tarjetaControl1.setTaCoFinish(tarjetaControl.getTaCoFinish());
            _tarjetaControl1.setTaCoMultiple(tarjetaControl.getTaCoMultiple());
            _tarjetaControl1.setTaCoCodEnvioMovil(tarjetaControl.getTaCoCodEnvioMovil());
            _tarjetaControl1.setTaCoCountMultiple(tarjetaControl.getTaCoCountMultiple());
            _tarjetaControl1.setCoId(tarjetaControl.getCoId());
            //Verificamos y Actualizamos para finalizardetalle
            this.VerificarActualizaTarjetaFinaliza(tarjetaControl.getTaCoId());

            //Obtenemos la tarjeta ControlMovil, para obtener el estado
            _tarjetaBitacoraMovil=db.ObtenerTarjetaBitacoraMovilByTaCo(tarjetaControl.getTaCoId());
            if(_tarjetaBitacoraMovil.getTaBiMoEnviado()==1){
                //vamos a usar el campo setUsFechaReg para el estado
                _tarjetaControl1.setUsFechaReg("ENVIADO");
            }else{
                if(_tarjetaBitacoraMovil.getTaBiMoFinalDetalle()==0) {
                    _tarjetaControl1.setUsFechaReg("INCOMPLETO");
                }
                else {
                    if (_tarjetaBitacoraMovil.getTaBiMoFinalDetalle()==1) {
                        _tarjetaControl1.setUsFechaReg("COMPLETO");
                    }else{
                        _tarjetaControl1.setUsFechaReg("FINALIZADO");
                    }
                }


            }

            tarjetaControls.add(_tarjetaControl1);

        }
        return tarjetaControls;
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
    public void FinalizarTarjetaIncompleta(int taCoId,int buId,String taCoFecha){
        TarjetaBitacoraMovil tarjetaBitacoraMovil;
        tarjetaBitacoraMovil=db.ObtenerTarjetaBitacoraMovilByTaCo(taCoId);
        tarjetaBitacoraMovil.setTaBiMoFinalDetalle(2);

        actualizarTarjetaBitacoraMovilBD(tarjetaBitacoraMovil);
        //actualizamos la tarjeta activa
        List<TarjetaControl> tarjetasControl2;
        tarjetasControl2 = db.ObtenerTarjetas(buId, taCoFecha);

        actualizarActivoTarjetaBitacoraMovilBD(db,tarjetasControl2);

    }
    public void EnviarTodo(int buId, String taCoFecha, int enviado){
        //primero obtenemos los registros no enviados
        ArrayList<TarjetaControl> _tarjetaControls=null;
        ArrayList<TarjetaControlDetalle> _tarjetaControlDetalles=null;
        TarjetaBitacoraMovil _tarjetaBitacoraMovil;
        _tarjetaControls=this.GetTarjetaControlBDEnviados(buId, taCoFecha, enviado);
        for(TarjetaControl tarjetaControl:_tarjetaControls) {
            //Verificamos el detalleTarjeta para actualizar el finaldetalle
            VerificarActualizaTarjetaFinaliza(tarjetaControl.getTaCoId());
            //enviamos la tarjetaDetalle
            _tarjetaControlDetalles=GetAllTarjetaDetalleBDById(tarjetaControl.getTaCoId());
            UpdateTarjetaDetallesRest(_tarjetaControlDetalles);
            //Ahora verificamos los finalizados y completados
            //Obtenemos la tarjeta ControlMovil, para obtener el estado
            _tarjetaBitacoraMovil = db.ObtenerTarjetaBitacoraMovilByTaCo(tarjetaControl.getTaCoId());
            //Solamente enviamos los que estan en estado completado y finalizado
            if (_tarjetaBitacoraMovil.getTaBiMoFinalDetalle()==1 || _tarjetaBitacoraMovil.getTaBiMoFinalDetalle()==2){
                //Actualizamos al servidor
                this.UpdateTarjetaRest(tarjetaControl);
            }
            //Ahora si enviamos la georeferencia
            List<Georeferencia> georeferencias=null;
            georeferencias=georeferenciaService.GetAllGeoreferenciaByTaCoNoEnviado(tarjetaControl.getTaCoId());
            georeferenciaService.SaveGeoreferenciaRest(georeferencias);
        }
    }
    public TarjetaControlDetalle GetTarjetaDetalleByPuCoDe(int puCoDeId){
        return db.ObtenerTarjetaDetalleByPuCoDe(puCoDeId);
    }
    public TarjetaControlDetalle GetTarjetaDetalleByTaCoPuCoDe(int taCoId, int puCoDeId){
        return db.ObtenerTarjetaDetalleByTaCoPuCoDe(taCoId, puCoDeId);
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
        contentValues.put("TaCoTipoHoraSalida", tarjetaControl.getTaCoTipoHoraSalida()==true?1:0);
        contentValues.put("ReDiDeId", tarjetaControl.getReDiDeId());
        contentValues.put("TaCoFinish", tarjetaControl.getTaCoFinish()==true?1:0);
        contentValues.put("TaCoMultiple", tarjetaControl.getTaCoMultiple()==true?1:0);
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
        contentValues.put("TaCoNroVuelta", tarjetaControl.getTaCoNroVuelta());
        contentValues.put("PrId", tarjetaControl.getPrId());
        contentValues.put("TiSaId", tarjetaControl.getTiSaId());
        contentValues.put("TaCoAsignado", tarjetaControl.getTaCoAsignado());
        contentValues.put("TaCoTipoHoraSalida", tarjetaControl.getTaCoTipoHoraSalida()==true?1:0);
        contentValues.put("ReDiDeId", tarjetaControl.getReDiDeId());
        contentValues.put("TaCoFinish", tarjetaControl.getTaCoFinish()==true?1:0);
        contentValues.put("TaCoMultiple", tarjetaControl.getTaCoMultiple()==true?1:0);
        contentValues.put("TaCoCodEnvioMovil", tarjetaControl.getTaCoCodEnvioMovil());
        contentValues.put("TaCoCountMultiple", tarjetaControl.getTaCoCountMultiple());
        contentValues.put("CoId", tarjetaControl.getCoId());
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
            if(tarjetaBitacoraMovil.getTaBiMoEnviado()==0 && tarjetaBitacoraMovil.getTaBiMoFinalDetalle()==0) {
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
