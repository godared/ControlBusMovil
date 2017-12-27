package com.godared.controlbusmovil.presentador;

/**
 * Created by ronald on 17/12/2017.
 */

public interface IRecyclerviewEnvioTarjetaPresenter {
    void obtenerEnvioTarjetasBD(int buId, String taCoFecha,int enviado);
    void mostrarEnvioTarjetasRV();
}
