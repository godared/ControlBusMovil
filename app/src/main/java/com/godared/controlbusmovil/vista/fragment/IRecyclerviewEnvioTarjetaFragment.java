package com.godared.controlbusmovil.vista.fragment;

import com.godared.controlbusmovil.adapter.EnvioTarjetaAdaptadorRV;
import com.godared.controlbusmovil.pojo.TarjetaBitacoraMovil;
import com.godared.controlbusmovil.pojo.TarjetaControl;

import java.util.ArrayList;

/**
 * Created by ronald on 17/12/2017.
 */

public interface IRecyclerviewEnvioTarjetaFragment {
    void generarLinearLayoutVertical();
    EnvioTarjetaAdaptadorRV crearAdaptador(ArrayList<TarjetaControl> tarjetascontrol);
    void inicializarAdaptadorRV(EnvioTarjetaAdaptadorRV adaptador);
}
