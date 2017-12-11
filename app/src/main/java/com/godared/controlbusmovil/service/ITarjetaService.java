package com.godared.controlbusmovil.service;

import android.content.Context;

import com.godared.controlbusmovil.dao.BaseDatos;
import com.godared.controlbusmovil.pojo.TarjetaControl;
import com.godared.controlbusmovil.pojo.TarjetaControlDetalle;
import com.godared.controlbusmovil.pojo.TarjetaDetalleBitacoraMovil;

import java.util.ArrayList;

/**
 * Created by Ronald on 04/05/2017.
 */

public interface ITarjetaService {

    void obtenerTarjetasDetalleRest( int taCoId);
    ArrayList<TarjetaControl> obtenerTarjetasRest(int buId, String taCoFecha );
    void UpdateTarjetaDetalleRest(TarjetaControlDetalle tarjetaControlDetalle);
    ArrayList<TarjetaControlDetalle> GetAllTarjetaDetalleBD(int taCoId);
    TarjetaControl GetTarjetaControlBD(int taCoId);
    TarjetaControl GetTarjetaControlActivo(int buId, String taCoFecha);
    ArrayList<TarjetaControlDetalle> GetAllTarjetaDetalleBDByTaCoActivo(int buId, String taCoFecha);
    Boolean VerificarTarjetaDetalleBDByTaCoDeRegistradoEnviado(int taCoDeId);
    void VerificarActualizaTarjetaFinaliza(int taCoId);
    TarjetaControlDetalle GetTarjetaDetalleByPuCoDe(int puCoDeId);
    void insertarTarjetaBD(BaseDatos baseDatos,  TarjetaControl tarjetaControl);
    void actualizarTarjetaBD(BaseDatos baseDatos, TarjetaControl tarjetaControl);
    void insertarTarjetasDetalleBD(BaseDatos baseDatos, ArrayList<TarjetaControlDetalle> tarjetaControlDetalles);
    void actualizarTarjetaDetalleBD(TarjetaControlDetalle tarjetaControlDetalle);
    void actualizarTarjetaDetalleBitacoraMovilBD(int taCoDeId,TarjetaDetalleBitacoraMovil tarjetaDetalleBitacoraMovil);
    TarjetaDetalleBitacoraMovil obtenerTarjetaDetalleBitacoraMovilByTaCoDe(int taCoDeId);
    //ArrayList<TarjetaControlDetalle> getTarjetasDetalle();
    //ArrayList<TarjetaControl> getTarjetasControl();
}
