package com.godared.controlbusmovil.service;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.godared.controlbusmovil.dao.BaseDatos;
import com.godared.controlbusmovil.pojo.Ruta;
import com.godared.controlbusmovil.pojo.RutaDetalle;
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

public class RutaService implements IRutaService {
    BaseDatos db;
    private Context context;
    public RutaService(Context context){
        this.context=context;
        db=new BaseDatos(context);
    }
    @Override
    public ArrayList<Ruta> ObtenerRutaRest(int ruId){
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        IEndpointApi endpointApi = restApiAdapter.establecerConexionRestApi();
        Call<Ruta> rutaCall = endpointApi.getRuta(ruId);
        rutaCall.enqueue(new Callback<Ruta>() {
            @Override
            public void onResponse(Call<Ruta> call, Response<Ruta> response) {
                Ruta _ruta;
                _ruta=(Ruta) response.body();
                Ruta _ruta2 = null;
                //puntoControl2=_tarjetaControl.get(0);
                _ruta2=db.ObtenerRuta(_ruta.getRuId());
                if(_ruta2.getRuId()<1){
                    InsertarRutaBD(db,_ruta);
                    ObtenerRutasDetalleRest(_ruta.getRuId());
                }else{
                    ActualizarRutaBD(db,_ruta);
                }
            }

            @Override
            public void onFailure(Call<Ruta> call, Throwable t) {
                Log.e("Fallo la conexion", t.toString());
            }
        });
        return null;
    }
    @Override
    public void ObtenerRutasDetalleRest( int ruId){
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        IEndpointApi endpointApi = restApiAdapter.establecerConexionRestApi();
        Call<List<RutaDetalle>> rutaDetalleResponseCall = endpointApi.getRutaDetalle(ruId);
        //controlamos alguns eventos de a respuesta
        rutaDetalleResponseCall.enqueue(new Callback<List<RutaDetalle>>() {
            @Override
            public void onResponse(Call<List<RutaDetalle>> call, Response<List<RutaDetalle>> response) {
                ArrayList<RutaDetalle> rutaDetalleResponse;
                rutaDetalleResponse = (ArrayList<RutaDetalle>) response.body();
                ArrayList<RutaDetalle> _rutasDetalle=null;
                _rutasDetalle = rutaDetalleResponse;
                InsertarRutasDetalleBD(db,_rutasDetalle);
            }

            @Override
            public void onFailure(Call<List<RutaDetalle>> call, Throwable t) {
                Toast.makeText(context, "Algo paso en la conexion", Toast.LENGTH_SHORT).show();
                Log.e("Fallo la conexion", t.toString());
            }
        });

    }
    @Override
    public ArrayList<RutaDetalle> GetAllRutaDetalleBD(int ruId){
        return db.ObtenerRutaDetalle(ruId);
    }
    @Override
    public void InsertarRutaBD(BaseDatos baseDatos, Ruta ruta){
        ContentValues contentValues = new ContentValues();
        contentValues.put("RuId", ruta.getRuId());
        contentValues.put("EmId", ruta.getEmId());
        contentValues.put("RuDescripcion", ruta.getRuDescripcion());
        contentValues.put("RuFechaCreacion", ruta.getRuFechaCreacion());
        contentValues.put("RuRegMunicipal", ruta.getRuRegMunicipal());
        contentValues.put("RuKilometro", ruta.getRuKilometro());
        //contentValues.put("RuActivo", ruta.getRuActivo());
        contentValues.put("UsId", ruta.getUsId());
        contentValues.put("UsFechaReg", ruta.getUsFechaReg());
        baseDatos.InsertarRuta(contentValues);
    }
    @Override
    public void ActualizarRutaBD(BaseDatos baseDatos, Ruta ruta){
        ContentValues contentValues = new ContentValues();
        contentValues.put("RuId", ruta.getRuId());
        contentValues.put("EmId", ruta.getEmId());
        contentValues.put("RuDescripcion", ruta.getRuDescripcion());
        contentValues.put("RuFechaCreacion", ruta.getRuFechaCreacion());
        contentValues.put("RuRegMunicipal", ruta.getRuRegMunicipal());
        contentValues.put("RuKilometro", ruta.getRuKilometro());
        contentValues.put("RuActivo", ruta.getRuActivo());
        contentValues.put("UsId", ruta.getUsId());
        contentValues.put("UsFechaReg", ruta.getUsFechaReg());
        baseDatos.ActualizarRuta(contentValues,ruta.getRuId());
    }
    @Override
    public void InsertarRutasDetalleBD(BaseDatos baseDatos, ArrayList<RutaDetalle> rutaDetalles){
        for(RutaDetalle rutaDetalle:rutaDetalles) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("RuDeId", rutaDetalle.getRuDeId());
            contentValues.put("RuId", rutaDetalle.getRuId());
            contentValues.put("RuDeOrden", rutaDetalle.getRuDeOrden());
            contentValues.put("RuDeLatitud", rutaDetalle.getRuDeLatitud());
            contentValues.put("RuDeLongitud", rutaDetalle.getRuDeLongitud());
            contentValues.put("UsId", rutaDetalle.getUsId());
            contentValues.put("UsFechaReg", rutaDetalle.getUsFechaReg());
            baseDatos.InsertarRutaDetalle(contentValues);
        }
    }
}
