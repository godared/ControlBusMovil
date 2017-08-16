package com.godared.controlbusmovil.service;

import com.godared.controlbusmovil.dao.BaseDatos;
import com.godared.controlbusmovil.pojo.Ruta;
import com.godared.controlbusmovil.pojo.Telefono;

import java.util.ArrayList;

/**
 * Created by ronald on 15/08/2017.
 */

public interface ITelefonoService {
    ArrayList<Telefono> ObtenerTelefonoRest(int teId);
    void InsertarTelefonoBD(BaseDatos baseDatos, Telefono telefono);
    void ActualizarTelefonoBD(BaseDatos baseDatos, Telefono telefono);
}
