package com.godared.controlbusmovil.service;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.godared.controlbusmovil.dao.BaseDatos;
import com.godared.controlbusmovil.pojo.Georeferencia;
import com.godared.controlbusmovil.pojo.TarjetaControl;
import com.godared.controlbusmovil.restApi.IEndpointApi;
import com.godared.controlbusmovil.restApi.adapter.RestApiAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ronald on 07/12/2017.
 */

public class GeoreferenciaService implements IGeoreferenciaService {
    private Context context;
    BaseDatos db;
    //private Georeferencia georeferencia;
    //ITarjetaService tarjetaService;

    public GeoreferenciaService(Context context){
        this.context=context;
        db=new BaseDatos(context);
        //tarjetaService=new TarjetaService(context);
    }
    public void SaveGeoreferenciaRest(Georeferencia georeferencia1){
        final Georeferencia georeferencia=georeferencia1;
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        IEndpointApi endpointApi = restApiAdapter.establecerConexionRestApi();
        Call<Boolean> georeferenciaSend = endpointApi.saveGeoreferenciaOne(georeferencia);
        georeferenciaSend.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                //se registra en la nube
                Boolean _enviado=(Boolean)response.body();
                if(_enviado!=null){
                    if (_enviado){
                        Toast.makeText(context, "Se envio correctamento", Toast.LENGTH_SHORT).show();
                        georeferencia.setGeEnviadoMovil(_enviado);
                        InsertarGeoreferenciaBD(db,georeferencia);
                    }
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(context, "Algo paso en la conexion", Toast.LENGTH_SHORT).show();
                //asi falle igual tiene que guardar
                InsertarGeoreferenciaBD(db,georeferencia);
                Log.e("Fallo la conexion", t.toString());
            }
        });
    }
    public void SaveGeoreferenciaRest(List<Georeferencia> georeferencias){
        final List<Georeferencia> _georeferencias=georeferencias;
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        IEndpointApi endpointApi = restApiAdapter.establecerConexionRestApi();
        Call<Boolean> georeferenciaSend = endpointApi.saveGeoreferencia(georeferencias);
        georeferenciaSend.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Boolean val=response.body();
                if (val){
                    Toast.makeText(context, "Georeferencia se envio correctamento", Toast.LENGTH_SHORT).show();
                    for(Georeferencia georeferencia:_georeferencias){
                        georeferencia.setGeEnviadoMovil(true);
                        ActualizarGeoreferenciaBD(db,georeferencia);
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(context, "Algo paso en la conexion", Toast.LENGTH_SHORT).show();
                Log.e("Fallo la conexion", t.toString());
            }
        });
    }

    public void InsertarGeoreferenciaBD(BaseDatos baseDatos, Georeferencia georeferencia){
        ContentValues contentValues = new ContentValues();
        contentValues.put("GeId", georeferencia.getGeId());
        contentValues.put("TaCoId", georeferencia.getTaCoId());
        contentValues.put("GeLatitud", georeferencia.getGeLatitud());
        contentValues.put("GeLongitud", georeferencia.getGeLongitud());
        contentValues.put("GeFechaHora", georeferencia.getGeFechaHora());
        contentValues.put("UsId", georeferencia.getUsId());
        contentValues.put("UsFechaReg", georeferencia.getUsFechaReg());
        contentValues.put("GeOrden", georeferencia.getGeOrden());
        contentValues.put("GeEnviadoMovil", georeferencia.getGeEnviadoMovil()==true?1:0);
        baseDatos.InsertarGeoreferencia(contentValues);
    }
    public void ActualizarGeoreferenciaBD(BaseDatos baseDatos, Georeferencia georeferencia){
        ContentValues contentValues = new ContentValues();
        contentValues.put("GeId", georeferencia.getGeId());
        contentValues.put("TaCoId", georeferencia.getTaCoId());
        contentValues.put("GeLatitud", georeferencia.getGeLatitud());
        contentValues.put("GeLongitud", georeferencia.getGeLongitud());
        contentValues.put("GeFechaHora", georeferencia.getGeFechaHora());
        contentValues.put("UsId", georeferencia.getUsId());
        contentValues.put("UsFechaReg", georeferencia.getUsFechaReg());
        contentValues.put("GeOrden", georeferencia.getGeOrden());
        contentValues.put("GeEnviadoMovil", georeferencia.getGeEnviadoMovil()==true?1:0);
        baseDatos.ActualizarGeoreferencia(contentValues,georeferencia.getGeId());
    }
    public int GetCountGeoreferenciadByTaCo(int taCoId){
        int count= db.GetCountGeoreferenciadByTaCo(taCoId);
        return count;
    }
    public Georeferencia GetLastGeoreferenciaByTaCo(int taCoId){
        return db.GetLastGeoreferenciaByTaCo(taCoId);
    }
    public Georeferencia GetAllGeoreferenciaById(int geId){
        return db.GetAllGeoreferenciaById(geId);
    }
    public List<Georeferencia> GetAllGeoreferenciaByTaCoNoEnviado(int taCoId){
        return db.GetAllGeoreferenciaByTaCoNoEnviado(taCoId);
    }
}
