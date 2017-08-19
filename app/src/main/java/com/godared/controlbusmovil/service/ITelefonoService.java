package com.godared.controlbusmovil.service;

import com.godared.controlbusmovil.dao.BaseDatos;
import com.godared.controlbusmovil.pojo.Ruta;
import com.godared.controlbusmovil.pojo.Telefono;
import com.godared.controlbusmovil.pojo.TelefonoImei;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ronald on 15/08/2017.
 */

public interface ITelefonoService {
    ArrayList<Telefono> ObtenerTelefonoRest(int teId);
    void InsertarTelefonoBD(BaseDatos baseDatos, Telefono telefono);
    void ActualizarTelefonoBD(BaseDatos baseDatos, Telefono telefono);
    //TelefonoImei
    void ObtenerTelefonoImeiRest(String teImei);
    List<TelefonoImei> ObtenerTelefonoImeibyImeiBD(String teImei);
    void InsertarTelefonoImeiBD(BaseDatos baseDatos, List<TelefonoImei> telefonosImei);
    void ActualizarTelefonoImeiBD(BaseDatos baseDatos, TelefonoImei telefonoImei);
}
