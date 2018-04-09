package com.godared.controlbusmovil.service;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.godared.controlbusmovil.dao.BaseDatos;
import com.godared.controlbusmovil.pojo.AlertaIncidencia;
import com.godared.controlbusmovil.restApi.IEndpointApi;
import com.godared.controlbusmovil.restApi.adapter.RestApiAdapter;
import com.godared.controlbusmovil.vista.fragment.IAlertaIncidenciaFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ronald on 03/04/2018.
 */

public class AlertaIncidenciaService implements IAlertaIncidenciaService {
    BaseDatos db;
    private Context context;

    public interface AlertaIncidenciaServiceListener{
        void listenObtenerAlertaIncidenciaRest();
    }
    private AlertaIncidenciaServiceListener mListener;

    public AlertaIncidenciaService(Context context) {
        this.context = context;
        this.db=new BaseDatos(context);
    }
    public AlertaIncidenciaService(AlertaIncidenciaServiceListener mListener,Context context) {
        this.context = context;
        this.mListener=mListener;
        this.db=new BaseDatos(context);
    }

    @Override
    public ArrayList<AlertaIncidencia> ObtenerAlertaIncidenciaRest(int emId,int taCoId) {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        IEndpointApi endpointApi=restApiAdapter.establecerConexionRestApi();
        Call<List<AlertaIncidencia>> telefonoCall = endpointApi.getAllAlertaIncidenciaByEmTaCo(emId,taCoId);
        telefonoCall.enqueue(new Callback<List<AlertaIncidencia>>() {
            @Override
            public void onResponse(Call<List<AlertaIncidencia>> call, Response<List<AlertaIncidencia>> response) {
                ArrayList<AlertaIncidencia> alertaIncidencias;
                alertaIncidencias=(ArrayList<AlertaIncidencia>) response.body();
                GuardarAlertaIncidenciaBD(alertaIncidencias);
                mListener.listenObtenerAlertaIncidenciaRest();
            }

            @Override
            public void onFailure(Call<List<AlertaIncidencia>> call, Throwable t) {
                Toast.makeText(context, "No hay conexion a Internet", Toast.LENGTH_SHORT).show();
                Log.e("Fallo la conexion", t.toString());
            }
        });
        return null;
    }

    @Override
    public void GuardarAlertaIncidenciaBD( List<AlertaIncidencia> alertaIncidencias) {
           AlertaIncidencia _alertaIncidencia=null;
        for (AlertaIncidencia alertaIncidencia:alertaIncidencias) {
            ContentValues contentValues = new ContentValues();
            //si viene del server tons guarda su id
            if (alertaIncidencia.getAlInId()>0)
                contentValues.put("AlInId", alertaIncidencia.getAlInId());
            else
                contentValues.put("AlInId", 0);
            contentValues.put("EmId", alertaIncidencia.getEmId());
            contentValues.put("AlInFecha", alertaIncidencia.getAlInFecha());
            contentValues.put("AlInDescripcion", alertaIncidencia.getAlInDescripcion());
            contentValues.put("AlInTipo", alertaIncidencia.getAlInTipo() == true ? 1 : 0);
            contentValues.put("AlInLatitud", alertaIncidencia.getAlInLatitud());
            contentValues.put("AlInLongitud", alertaIncidencia.getAlInLongitud());
            contentValues.put("UsId", alertaIncidencia.getUsId());
            contentValues.put("UsFechaReg", alertaIncidencia.getUsFechaReg());
            contentValues.put("TaCoId", alertaIncidencia.getTaCoId());
            _alertaIncidencia = db.GetAllAlertaIncidenciaById(alertaIncidencia.getAlInId());
            if (_alertaIncidencia.getAlInId() < 1)
                db.InsertarAlertaIncidencia(contentValues);
            else
                db.ActualizarAlertaIncidencia(contentValues, alertaIncidencia.getAlInId());
        }
    }


    @Override
    public ArrayList<AlertaIncidencia> ObtenerAlertaIncidenciabyTaCoBD(int taCoId) {
        return db.GetAllAlertaIncidenciaByTaCo(taCoId);
    }
    @Override
    public AlertaIncidencia GetAllAlertaIncidenciaByIdBD (int alInId){
        return db.GetAllAlertaIncidenciaById(alInId);
    }
}
