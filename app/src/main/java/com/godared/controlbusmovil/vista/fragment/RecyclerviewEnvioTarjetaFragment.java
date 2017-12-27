package com.godared.controlbusmovil.vista.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.godared.controlbusmovil.R;
import com.godared.controlbusmovil.adapter.EnvioTarjetaAdaptadorRV;
import com.godared.controlbusmovil.pojo.TarjetaControl;
import com.godared.controlbusmovil.presentador.IRecyclerviewEnvioTarjetaPresenter;
import com.godared.controlbusmovil.presentador.RecyclerviewEnvioTarjetaPresenter;
import com.godared.controlbusmovil.vista.SettingActivity;

import java.util.ArrayList;

/**
 * Created by ronald on 17/12/2017.
 */

public class RecyclerviewEnvioTarjetaFragment extends Fragment implements IRecyclerviewEnvioTarjetaFragment {
    RecyclerView listaEnvioTarjetas;
    IRecyclerviewEnvioTarjetaPresenter iRecyclerviewEnvioTarjetaPresenter;
    int enviado;
    int buId;
    String taCoFecha;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View v=inflater.inflate(R.layout.fragment_recyclerviewenviotarjeta,container,false); //asignamos un layout
        listaEnvioTarjetas=(RecyclerView)v.findViewById(R.id.rvEnvioTarjeta);
        SettingActivity _settingActivity= (SettingActivity)getActivity();
        enviado=_settingActivity.Enviado;
        buId=_settingActivity.BuId;
        taCoFecha=_settingActivity.TaCoFecha;
        iRecyclerviewEnvioTarjetaPresenter=new RecyclerviewEnvioTarjetaPresenter(this,getContext(),buId,taCoFecha,enviado);
        return v;
    }

    @Override
    public void generarLinearLayoutVertical(){
        LinearLayoutManager lLM=new LinearLayoutManager(getActivity());
        lLM.setOrientation(LinearLayoutManager.VERTICAL);
        listaEnvioTarjetas.setLayoutManager(lLM);
    }
    @Override
    public EnvioTarjetaAdaptadorRV crearAdaptador(ArrayList<TarjetaControl> tarjetascontrol){
        EnvioTarjetaAdaptadorRV _envioTarjetaAdaptadorRV= new EnvioTarjetaAdaptadorRV(tarjetascontrol,getActivity());
        return _envioTarjetaAdaptadorRV;
    }
    @Override
    public void inicializarAdaptadorRV(EnvioTarjetaAdaptadorRV adaptador){
        listaEnvioTarjetas.setAdapter(adaptador);
    }

}
