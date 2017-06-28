package com.godared.controlbusmovil.service;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.godared.controlbusmovil.dao.BaseDatos;
import com.godared.controlbusmovil.pojo.PuntoControl;
import com.godared.controlbusmovil.pojo.PuntoControlDetalle;
import com.godared.controlbusmovil.restApi.IEndpointApi;
import com.godared.controlbusmovil.restApi.adapter.RestApiAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ronald on 25/06/2017.
 */

public class PuntoControlService implements IPuntoControlService {
    BaseDatos db;
    private Context context;
    public PuntoControlService(Context context){
        this.context=context;
        db=new BaseDatos(context);
    }
    @Override
    public ArrayList<PuntoControl> ObtenerPuntoControlRest(int puCoId) {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        IEndpointApi endpointApi = restApiAdapter.establecerConexionRestApi();
        Call<List<PuntoControl>> puntoControlCall = endpointApi.getPuntoControl(puCoId);
        puntoControlCall.enqueue(new Callback<List<PuntoControl>>() {

            @Override
            public void onResponse(Call<List<PuntoControl>> call, Response<List<PuntoControl>> response) {
                ArrayList<PuntoControl> _puntoControl;
                _puntoControl=(ArrayList<PuntoControl>) response.body();
                PuntoControl _puntoControl2 = null;
                //puntoControl2=_tarjetaControl.get(0);
                _puntoControl2=db.ObtenerPuntoControl(_puntoControl.get(0).getPuCoId());
                if(_puntoControl2.getPuCoId()>1){
                    InsertarPuntoControlBD(db,_puntoControl.get(0));
                    ObtenerPuntosControlDetalleRest(_puntoControl.get(0).getPuCoId());
                }else{
                    ActualizarPuntoControlBD(db,_puntoControl.get(0));
                }
            }

            @Override
            public void onFailure(Call<List<PuntoControl>> call, Throwable t) {
                Log.e("Fallo la conexion", t.toString());
            }
        });
        return null;
    }
    @Override
    public void ObtenerPuntosControlDetalleRest(int puCoId) {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        IEndpointApi endpointApi = restApiAdapter.establecerConexionRestApi();
        Call<List<PuntoControlDetalle>> puntoControlDetalleResponseCall = endpointApi.getPuntoControlDetalle(puCoId);
        //controlamos alguns eventos de a respuesta
        puntoControlDetalleResponseCall.enqueue(new Callback<List<PuntoControlDetalle>>(){

            @Override
            public void onResponse(Call<List<PuntoControlDetalle>> call, Response<List<PuntoControlDetalle>> response) {
                ArrayList<PuntoControlDetalle> puntoControlDetalleResponse;
                puntoControlDetalleResponse = (ArrayList<PuntoControlDetalle>) response.body();
                ArrayList<PuntoControlDetalle> _puntosDetalle=null;
                _puntosDetalle = puntoControlDetalleResponse;
                InsertarPuntosControlDetalleBD(db,_puntosDetalle);
            }

            @Override
            public void onFailure(Call<List<PuntoControlDetalle>> call, Throwable t) {
                Toast.makeText(context, "Algo paso en la conexion", Toast.LENGTH_SHORT).show();
                Log.e("Fallo la conexion", t.toString());
            }
        });
    }

    @Override
    public ArrayList<PuntoControlDetalle> GetAllPuntoControlDetalleBD(int puCoId) {
        return db.ObtenerPuntoControlDetalle(puCoId);
    }

    @Override
    public void InsertarPuntoControlBD(BaseDatos baseDatos, PuntoControl puntoControl) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("PuCoId", puntoControl.getPuCoId());
        contentValues.put("RuId", puntoControl.getRuId());
        contentValues.put("PuCoTiempoBus", puntoControl.getPuCoTiempoBus());
        contentValues.put("PuCoClase", puntoControl.getPuCoClase());
        contentValues.put("UsId", puntoControl.getUsId());
        contentValues.put("UsFechaReg", puntoControl.getUsFechaReg());
        contentValues.put("PuCoDescripcion", puntoControl.getPuCoDescripcion());
        baseDatos.InsertarPuntoControl(contentValues);
    }

    @Override
    public void ActualizarPuntoControlBD(BaseDatos baseDatos, PuntoControl puntoControl) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("PuCoId", puntoControl.getPuCoId());
        contentValues.put("RuId", puntoControl.getRuId());
        contentValues.put("PuCoTiempoBus", puntoControl.getPuCoTiempoBus());
        contentValues.put("PuCoClase", puntoControl.getPuCoClase());
        contentValues.put("UsId", puntoControl.getUsId());
        contentValues.put("UsFechaReg", puntoControl.getUsFechaReg());
        contentValues.put("PuCoDescripcion", puntoControl.getPuCoDescripcion());
        baseDatos.ActualizarPuntoControl(contentValues,puntoControl.getPuCoId());
    }

    @Override
    public void InsertarPuntosControlDetalleBD(BaseDatos baseDatos, ArrayList<PuntoControlDetalle> puntoControlDetalles) {
        for(PuntoControlDetalle puntoControlDetalle:puntoControlDetalles) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("PuCoDeId", puntoControlDetalle.getPuCoDeId());
            contentValues.put("PuCoId", puntoControlDetalle.getPuCoId());
            contentValues.put("PuCoDeLatitud", puntoControlDetalle.getPuCoDeLatitud());
            contentValues.put("PuCoDeLongitud", puntoControlDetalle.getPuCoDeLongitud());
            contentValues.put("PuCoDeDescripcion", puntoControlDetalle.getPuCoDeDescripcion());
            contentValues.put("PuCoDeHora", puntoControlDetalle.getPuCoDeHora());
            contentValues.put("UsId", puntoControlDetalle.getUsId());
            contentValues.put("UsFechaReg", puntoControlDetalle.getUsFechaReg());
            contentValues.put("PuCoDeOrden", puntoControlDetalle.getPuCoDeOrden());
            baseDatos.InsertarPuntoControlDetalle(contentValues);
        }
    }
}
