package com.godared.controlbusmovil.service;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.godared.controlbusmovil.dao.BaseDatos;
import com.godared.controlbusmovil.pojo.Telefono;
import com.godared.controlbusmovil.pojo.TelefonoImei;
import com.godared.controlbusmovil.restApi.IEndpointApi;
import com.godared.controlbusmovil.restApi.adapter.RestApiAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ronald on 15/08/2017.
 */

public class TelefonoService implements  ITelefonoService {
    BaseDatos db;
    private Context context;
    public interface TelefonoServiceListener {
        public void listenObtenerTelefonoImeiRest();
        //public void onDialogNegativeClick();
    }
    TelefonoServiceListener mListener;

    public TelefonoService(TelefonoServiceListener mListener,Context context){
        this.context=context;
        db=new BaseDatos(context);
        this.mListener=mListener;
    }
    @Override
    public ArrayList<Telefono> ObtenerTelefonoRest(int teId){
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        IEndpointApi endpointApi = restApiAdapter.establecerConexionRestApi();
        Call<Telefono> telefonoCall = endpointApi.getTelefono(teId);
        telefonoCall.enqueue(new Callback<Telefono>() {
            @Override
            public void onResponse(Call<Telefono> call, Response<Telefono> response) {
                Telefono _telefono;
                _telefono=(Telefono) response.body();
                Telefono _telefono2 = null;
                //puntoControl2=_tarjetaControl.get(0);
                _telefono2=db.ObtenerTelefono(_telefono.getTeId());
                if(_telefono2.getTeId()<1){
                    InsertarTelefonoBD(db,_telefono);
                    //ObtenerRutaRest(_telefono.getRuId());
                }else{
                    ActualizarTelefonoBD(db,_telefono);
                }
            }

            @Override
            public void onFailure(Call<Telefono> call, Throwable t) {
                Log.e("Fallo la conexion", t.toString());
            }
        });
        return null;
    }
    @Override
    public void InsertarTelefonoBD(BaseDatos baseDatos, Telefono telefono){
        ContentValues contentValues = new ContentValues();
        contentValues.put("TeId", telefono.getTeId());
        contentValues.put("BuId", telefono.getBuId());
        contentValues.put("TeMarca", telefono.getTeMarca());
        contentValues.put("TeModelo", telefono.getTeModelo());
        contentValues.put("TeVersionAndroid", telefono.getTeVersionAndroid());
        contentValues.put("TeActivo", telefono.getTeActivo());
        contentValues.put("TeImei", telefono.getTeImei());
        contentValues.put("UsId", telefono.getUsId());
        contentValues.put("UsFechaReg", telefono.getUsFechaReg());
        baseDatos.InsertarTelefono(contentValues);
    }
    @Override
    public void ActualizarTelefonoBD(BaseDatos baseDatos, Telefono telefono){
        ContentValues contentValues = new ContentValues();
        contentValues.put("TeId", telefono.getTeId());
        contentValues.put("BuId", telefono.getBuId());
        contentValues.put("TeMarca", telefono.getTeMarca());
        contentValues.put("TeModelo", telefono.getTeModelo());
        contentValues.put("TeVersionAndroid", telefono.getTeVersionAndroid());
        contentValues.put("TeActivo", telefono.getTeActivo());
        contentValues.put("TeImei", telefono.getTeImei());
        contentValues.put("UsId", telefono.getUsId());
        contentValues.put("UsFechaReg", telefono.getUsFechaReg());
        baseDatos.ActualizarTelefono(contentValues,telefono.getTeId());
    }
    //TelefonoImei
    @Override
    public void ObtenerTelefonoImeiRest(String teImei){
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        IEndpointApi endpointApi = restApiAdapter.establecerConexionRestApi();
        Call<List<TelefonoImei>> telefonoResponseCall = endpointApi.getAllTelefonoImei(teImei);
        telefonoResponseCall.enqueue(new Callback<List<TelefonoImei>>() {
            @Override
            public void onResponse(Call<List<TelefonoImei>> call, Response<List<TelefonoImei>> response) {
                ArrayList<TelefonoImei> telefonosImeiResponse;
                ArrayList<TelefonoImei> telefonosImeiDetalle;
                //tarjetaControlDetalleResponse=new ArrayList<>();
                telefonosImeiResponse = (ArrayList<TelefonoImei>) response.body();
                telefonosImeiDetalle=telefonosImeiResponse;
                InsertarTelefonoImeiBD(db,telefonosImeiDetalle);
                //aqui devuelve la escucha en a clase que implementa esta interfaz
                mListener.listenObtenerTelefonoImeiRest();
            }

            @Override
            public void onFailure(Call<List<TelefonoImei>> call, Throwable t) {
                Toast.makeText(context, "Algo paso en la conexion", Toast.LENGTH_SHORT).show();
                Log.e("Fallo la conexion", t.toString());
            }
        });
    }
    @Override
    public List<TelefonoImei> ObtenerTelefonoImeibyImeiBD(String teImei){
        return db.ObtenerTelefonoImeibyImei(teImei);
    }
    @Override
    public void InsertarTelefonoImeiBD(BaseDatos baseDatos, List<TelefonoImei> telefonosImei){
        TelefonoImei _telefonoImei=null;
        for(TelefonoImei telefonoImei:telefonosImei) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("TeId", telefonoImei.getTeId());
            contentValues.put("BuId", telefonoImei.getBuId());
            contentValues.put("EmId", telefonoImei.getEmId());
            contentValues.put("SuEmRSocial", telefonoImei.getSuEmRSocial());
            contentValues.put("BuPlaca", telefonoImei.getBuPlaca());
            contentValues.put("TeMarca", telefonoImei.getTeMarca());
            contentValues.put("TeImei", telefonoImei.getTeImei());
            contentValues.put("EmConsorcio", telefonoImei.getEmConsorcio());
            _telefonoImei=baseDatos.ObtenerTelefonoImei(telefonoImei.getTeId());
            if(_telefonoImei.getTeId()<1)
                baseDatos.InsertarTelefonoImei(contentValues);
            else
                baseDatos.ActualizarTelefonoImei(contentValues,telefonoImei.getTeId());

        }
    }
    @Override
    public void ActualizarTelefonoImeiBD(BaseDatos baseDatos, TelefonoImei telefonoImei){

    }
}
