package com.godared.controlbusmovil.presentador;

import android.content.Context;

import com.godared.controlbusmovil.pojo.AlertaIncidencia;
import com.godared.controlbusmovil.service.AlertaIncidenciaService;
import com.godared.controlbusmovil.service.IAlertaIncidenciaService;
import com.godared.controlbusmovil.vista.fragment.IAlertaIncidenciaFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronald on 03/04/2018.
 */

public class RecyclerviewAlertaIncidenciaPresenter implements IRecyclerviewAlertaIncidenciaPresenter {
    private IAlertaIncidenciaFragment alertaIncidenciaFragment;
    private Context context;
    private ArrayList<AlertaIncidencia> alertaIncidencias;
    private int EmId;
    private int TaCoId;

    public RecyclerviewAlertaIncidenciaPresenter(IAlertaIncidenciaFragment alertaIncidenciaFragment, Context context, ArrayList<AlertaIncidencia> alertaIncidencias, int taCoId) {
        this.alertaIncidenciaFragment = alertaIncidenciaFragment;
        this.context = context;
        this.alertaIncidencias = alertaIncidencias;
        TaCoId = taCoId;
        obtenerAlertaIncidenciasBD(this.TaCoId);
    }

    @Override
    public void obtenerAlertaIncidenciasBD(int tacoId) {
        IAlertaIncidenciaService alertaIncidenciaService=new AlertaIncidenciaService(this.context);
        alertaIncidencias=alertaIncidenciaService.ObtenerAlertaIncidenciabyTaCoBD(tacoId);
        mostrarAlertaIncidenciasRV();
    }

    @Override
    public void mostrarAlertaIncidenciasRV() {
        alertaIncidenciaFragment.inicializarAdaptadorRV(alertaIncidenciaFragment.crearAdaptador(alertaIncidencias));
        alertaIncidenciaFragment.generarLinearLayoutVertical();
    }
}
