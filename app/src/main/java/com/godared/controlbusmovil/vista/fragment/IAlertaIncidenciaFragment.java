package com.godared.controlbusmovil.vista.fragment;

import com.godared.controlbusmovil.adapter.AlertaIncidenciaAdaptadorRV;
import com.godared.controlbusmovil.pojo.AlertaIncidencia;

import java.util.ArrayList;

/**
 * Created by Ronald on 03/04/2018.
 */

public interface IAlertaIncidenciaFragment {
    void generarLinearLayoutVertical();
    AlertaIncidenciaAdaptadorRV crearAdaptador(ArrayList<AlertaIncidencia> alertaIncidencias);
    void inicializarAdaptadorRV(AlertaIncidenciaAdaptadorRV adaptadorRV);

}
