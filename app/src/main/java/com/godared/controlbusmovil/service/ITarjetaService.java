package com.godared.controlbusmovil.service;

import android.content.Context;
import com.godared.controlbusmovil.dao.BaseDatos;
import com.godared.controlbusmovil.pojo.TarjetaControl;
import com.godared.controlbusmovil.pojo.TarjetaControlDetalle;
import com.godared.controlbusmovil.pojo.TarjetaDetalleBitacoraMovil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronald on 04/05/2017.
 */

public interface ITarjetaService {

    void obtenerTarjetasDetalleRest( int taCoId);
    ArrayList<TarjetaControl> obtenerTarjetasRest(int emId,int buId, String taCoFecha );
    void UpdateTarjetaRest(TarjetaControl tarjetaControl);
    void UpdateTarjetaDetalleRest(TarjetaControlDetalle tarjetaControlDetalle);
    void UpdateTarjetaDetallesRest(List<TarjetaControlDetalle> tarjetaControlDetalles);
    ArrayList<TarjetaControlDetalle> GetAllTarjetaDetalleBDById(int taCoId);
    TarjetaControl GetTarjetaControlBD(int taCoId);
    TarjetaControl GetTarjetaControlActivo(int buId, String taCoFecha);
    ArrayList<TarjetaControlDetalle> GetAllTarjetaDetalleBDByTaCoActivo(int buId, String taCoFecha);
    ArrayList<TarjetaControl> GetTarjetaControlBDEnviados(int buId, String taCoFecha,int enviado);
    Boolean VerificarTarjetaDetalleBDByTaCoDeRegistradoEnviado(int taCoDeId);
    void VerificarActualizaTarjetaFinaliza(int taCoId);
    void FinalizarTarjetaIncompleta(int taCoId,int buId,String taCoFecha);
    void EnviarTodo(int buId, String taCoFecha, int enviado);
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
