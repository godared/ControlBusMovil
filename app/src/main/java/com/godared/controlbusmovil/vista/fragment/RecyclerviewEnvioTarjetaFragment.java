package com.godared.controlbusmovil.vista.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.godared.controlbusmovil.adapter.EnvioTarjetaAdaptadorRV;
import com.godared.controlbusmovil.pojo.TarjetaBitacoraMovil;
import com.godared.controlbusmovil.pojo.TarjetaControl;

import java.util.ArrayList;

/**
 * Created by ronald on 17/12/2017.
 */

public class RecyclerviewEnvioTarjetaFragment extends Fragment implements IRecyclerviewEnvioTarjetaFragment {
    RecyclerView listaEnvioTarjetas;
    public void generarLinearLayoutVertical(){
        LinearLayoutManager lLM=new LinearLayoutManager(getActivity());
        lLM.setOrientation(LinearLayoutManager.VERTICAL);
        listaEnvioTarjetas.setLayoutManager(lLM);
    }
    public EnvioTarjetaAdaptadorRV crearAdaptador(ArrayList<TarjetaControl> tarjetascontrol){
        EnvioTarjetaAdaptadorRV _envioTarjetaAdaptadorRV= new EnvioTarjetaAdaptadorRV(tarjetascontrol,getActivity());
        return _envioTarjetaAdaptadorRV;
    }
    public void inicializarAdaptadorRV(EnvioTarjetaAdaptadorRV adaptador){
        listaEnvioTarjetas.setAdapter(adaptador);
    }

}
