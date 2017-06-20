package com.godared.controlbusmovil.restApi.model;

import com.godared.controlbusmovil.pojo.TarjetaControlDetalle;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Ronald on 19/04/2017.
 */

public class TarjetaControlDetalleResponse {
    @SerializedName("Data")
    @Expose
    ArrayList<TarjetaControlDetalle> tarjetasDetalle;

    public ArrayList<TarjetaControlDetalle> getTarjetasDetalle() {
        return tarjetasDetalle;
    }

    public void setTarjetasDetalle(ArrayList<TarjetaControlDetalle> tarjetasDetalle) {
        this.tarjetasDetalle = tarjetasDetalle;
    }
}
