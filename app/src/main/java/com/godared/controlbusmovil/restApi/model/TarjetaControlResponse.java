package com.godared.controlbusmovil.restApi.model;

import com.godared.controlbusmovil.pojo.TarjetaControl;

import java.util.ArrayList;

/**
 * Created by Ronald on 19/04/2017.
 * Creamos un modelo de respuesta
 */

public class TarjetaControlResponse {
    ArrayList<TarjetaControl> tarjetas;

    public ArrayList<TarjetaControl> getTarjetas() {
        return tarjetas;
    }

    public void setTarjetas(ArrayList<TarjetaControl> tarjetas) {
        this.tarjetas = tarjetas;
    }
}
