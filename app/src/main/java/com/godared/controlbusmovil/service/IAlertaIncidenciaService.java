package com.godared.controlbusmovil.service;

import com.godared.controlbusmovil.dao.BaseDatos;
import com.godared.controlbusmovil.pojo.AlertaIncidencia;
import com.godared.controlbusmovil.pojo.Telefono;
import com.godared.controlbusmovil.pojo.TelefonoImei;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronald on 03/04/2018.
 */

public interface IAlertaIncidenciaService {
    ArrayList<AlertaIncidencia> ObtenerAlertaIncidenciaRest(int emId,int taCoId);
    void GuardarAlertaIncidenciaBD(BaseDatos baseDatos, List<AlertaIncidencia> alertaIncidencia);
    //void ActualizarAlertaIncidenciaBD(BaseDatos baseDatos, AlertaIncidencia alertaIncidencia);
    ArrayList<AlertaIncidencia> ObtenerAlertaIncidenciabyTaCoBD(int taCoId);
    AlertaIncidencia GetAllAlertaIncidenciaByIdBD (int alInId);
}
