package com.godared.controlbusmovil.service;

import com.godared.controlbusmovil.dao.BaseDatos;
import com.godared.controlbusmovil.pojo.Ruta;
import com.godared.controlbusmovil.pojo.RutaDetalle;

import java.util.ArrayList;

/**
 * Created by ronald on 15/08/2017.
 */

public interface IRutaService {
    ArrayList<Ruta> ObtenerRutaRest(int ruId);
    void ObtenerRutasDetalleRest( int ruId);
    ArrayList<RutaDetalle> GetAllRutaDetalleBD(int ruId);
    void InsertarRutaBD(BaseDatos baseDatos, Ruta ruta);
    void ActualizarRutaBD(BaseDatos baseDatos, Ruta ruta);
    void InsertarRutasDetalleBD(BaseDatos baseDatos, ArrayList<RutaDetalle> rutaDetalles);
}
