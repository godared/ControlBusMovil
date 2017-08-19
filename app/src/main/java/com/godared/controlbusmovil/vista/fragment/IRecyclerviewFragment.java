package com.godared.controlbusmovil.vista.fragment;

import android.support.v7.widget.RecyclerView;

import com.godared.controlbusmovil.adapter.TarjetaAdaptadorRV;
import com.godared.controlbusmovil.pojo.TarjetaControlDetalle;

import java.util.ArrayList;

/**
 * Created by ronald on 13/04/2017.
 */

public interface IRecyclerviewFragment {
    public void generarLinearLayoutVertical();
    public TarjetaAdaptadorRV crearAdaptador(ArrayList<TarjetaControlDetalle> tarjetasDetalle);
    public void inicializarAdaptadorRV(TarjetaAdaptadorRV adaptador);
}
