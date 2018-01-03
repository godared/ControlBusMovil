package com.godared.controlbusmovil.presentador;

import android.content.Context;

import com.godared.controlbusmovil.pojo.TarjetaBitacoraMovil;
import com.godared.controlbusmovil.pojo.TarjetaControl;
import com.godared.controlbusmovil.pojo.TarjetaControlDetalle;
import com.godared.controlbusmovil.service.ITarjetaService;
import com.godared.controlbusmovil.service.TarjetaService;
import com.godared.controlbusmovil.vista.fragment.IRecyclerviewEnvioTarjetaFragment;

import java.util.ArrayList;

/**
 * Created by ronald on 17/12/2017.
 */

public class RecyclerviewEnvioTarjetaPresenter implements IRecyclerviewEnvioTarjetaPresenter {
    private IRecyclerviewEnvioTarjetaFragment iRecyclerviewEnvioTarjetaFragment;
    private Context context;
    private ArrayList<TarjetaControl> tarjetasControl;

    public RecyclerviewEnvioTarjetaPresenter(IRecyclerviewEnvioTarjetaFragment iRecyclerviewEnvioTarjetaFragment,Context context,
                                             int buId, String taCoFecha,int enviado) {
        this.iRecyclerviewEnvioTarjetaFragment = iRecyclerviewEnvioTarjetaFragment;
        this.context=context;
        obtenerEnvioTarjetasBD(buId,taCoFecha,enviado);
    }

    public void obtenerEnvioTarjetasBD(int buId, String taCoFecha,int enviado){
        ITarjetaService tarjetaService=new TarjetaService(context);
        tarjetasControl=tarjetaService.GetTarjetaControlBDEnviados(buId,taCoFecha,enviado);
        mostrarEnvioTarjetasRV();

    }
    public void mostrarEnvioTarjetasRV(){
        iRecyclerviewEnvioTarjetaFragment.inicializarAdaptadorRV(iRecyclerviewEnvioTarjetaFragment.crearAdaptador(tarjetasControl));
        iRecyclerviewEnvioTarjetaFragment.generarLinearLayoutVertical();
    }
}
