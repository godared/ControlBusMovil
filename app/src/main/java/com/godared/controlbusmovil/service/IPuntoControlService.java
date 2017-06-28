package com.godared.controlbusmovil.service;

import com.godared.controlbusmovil.dao.BaseDatos;
import com.godared.controlbusmovil.pojo.PuntoControl;
import com.godared.controlbusmovil.pojo.PuntoControlDetalle;

import java.util.ArrayList;

/**
 * Created by ronald on 25/06/2017.
 */

public interface IPuntoControlService {
    ArrayList<PuntoControl> ObtenerPuntoControlRest(int puCoId);
    void ObtenerPuntosControlDetalleRest( int puCoId);
    ArrayList<PuntoControlDetalle> GetAllPuntoControlDetalleBD(int puCoId);
    //ArrayList<TarjetaControlDetalle> GetAllTarjetaDetalleBDByTaCoActivo(int buId, String taCoFecha);
    void InsertarPuntoControlBD(BaseDatos baseDatos, PuntoControl puntoControl);
    void ActualizarPuntoControlBD(BaseDatos baseDatos, PuntoControl puntoControl);
    void InsertarPuntosControlDetalleBD(BaseDatos baseDatos, ArrayList<PuntoControlDetalle> puntoControlDetalles);

}
