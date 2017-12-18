package com.godared.controlbusmovil.presentador;

import android.content.Context;

import com.godared.controlbusmovil.pojo.TarjetaBitacoraMovil;
import com.godared.controlbusmovil.pojo.TarjetaControlDetalle;
import com.godared.controlbusmovil.vista.fragment.IRecyclerviewEnvioTarjetaFragment;

import java.util.ArrayList;

/**
 * Created by ronald on 17/12/2017.
 */

public class RecyclerviewEnvioTarjetaPresenter implements IRecyclerviewEnvioTarjetaPresenter {
    private IRecyclerviewEnvioTarjetaFragment iRecyclerviewEnvioTarjetaFragment;
    private Context context;
    private ArrayList<TarjetaBitacoraMovil> tarjetasBitacoraMovil;

    public RecyclerviewEnvioTarjetaPresenter(IRecyclerviewEnvioTarjetaFragment iRecyclerviewEnvioTarjetaFragment,Context context) {
        this.iRecyclerviewEnvioTarjetaFragment = iRecyclerviewEnvioTarjetaFragment;
        this.context=context;
        obtenerEnvioTarjetasBD();
    }

    public void obtenerEnvioTarjetasBD(){

    }
    public void mostrarEnvioTarjetasRV(){
        iRecyclerviewEnvioTarjetaFragment.inicializarAdaptadorRV(iRecyclerviewEnvioTarjetaFragment.crearAdaptador(tarjetasBitacoraMovil));
        iRecyclerviewEnvioTarjetaFragment.generarLinearLayoutVertical();
    }
}
