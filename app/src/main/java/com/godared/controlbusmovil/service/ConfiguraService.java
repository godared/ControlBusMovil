package com.godared.controlbusmovil.service;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import com.godared.controlbusmovil.dao.BaseDatos;

import com.godared.controlbusmovil.pojo.Configura;
import com.godared.controlbusmovil.pojo.TarjetaControlDetalle;
import com.godared.controlbusmovil.restApi.IEndpointApi;
import com.godared.controlbusmovil.restApi.adapter.RestApiAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ronald on 22/03/2018.
 */

public class ConfiguraService implements IConfiguraService {
    private Context context;
    BaseDatos db;
    public ConfiguraService(ConfiguraServiceListener configuraServiceListener, Context context){
        this.context=context;
        db=new BaseDatos(context);
        this.configuraServiceListener=configuraServiceListener;
        //tarjetaService=new TarjetaService(context);
    }
    public interface ConfiguraServiceListener {
        void listenObtenerConfiguraRest(String dateServer,boolean isDateServer);
        //public void onDialogNegativeClick();
    }
    ConfiguraServiceListener configuraServiceListener;

    public void GetAllConfiguraByEmPeriodoRest(int emId, int coPeriodo){
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        IEndpointApi endpointApi = restApiAdapter.establecerConexionRestApi();
        Call<List<Configura>> configuraResponseCall = endpointApi.getAllConfiguraByEmPeriodo(emId,coPeriodo);
        configuraResponseCall.enqueue(new Callback<List<Configura>>() {
            @Override
            public void onResponse(Call<List<Configura>> call, Response<List<Configura>> response) {
                ArrayList<Configura> configuraResponse;
                configuraResponse = (ArrayList<Configura>) response.body();

                configuraServiceListener.listenObtenerConfiguraRest(configuraResponse.get(0).getCoTiempoActual(),true);
            }

            @Override
            public void onFailure(Call<List<Configura>> call, Throwable t) {
                Toast.makeText(context, "Algo paso en la conexion", Toast.LENGTH_SHORT).show();
                Log.e("Fallo la conexion", t.toString());

                Date fecha=new Date();
                configuraServiceListener.listenObtenerConfiguraRest(String.valueOf(fecha.getTime()),false);
            }
        });
    }
}
