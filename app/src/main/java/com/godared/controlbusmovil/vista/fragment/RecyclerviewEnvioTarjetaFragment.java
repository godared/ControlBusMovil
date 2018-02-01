package com.godared.controlbusmovil.vista.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.godared.controlbusmovil.R;
import com.godared.controlbusmovil.adapter.EnvioTarjetaAdaptadorRV;
import com.godared.controlbusmovil.pojo.TarjetaControl;
import com.godared.controlbusmovil.presentador.IRecyclerviewEnvioTarjetaPresenter;
import com.godared.controlbusmovil.presentador.RecyclerviewEnvioTarjetaPresenter;
import com.godared.controlbusmovil.service.TarjetaService;
import com.godared.controlbusmovil.vista.SettingActivity;

import java.util.ArrayList;

/**
 * Created by ronald on 17/12/2017.
 */

public class RecyclerviewEnvioTarjetaFragment extends Fragment implements IRecyclerviewEnvioTarjetaFragment,
        EnvioTarjetaAdaptadorRV.OnItemClickListener,FinalizaTarjetaDialogFragment.FinalizaTarjetaDialogListener {
    RecyclerView listaEnvioTarjetas;
    public IRecyclerviewEnvioTarjetaPresenter iRecyclerviewEnvioTarjetaPresenter;
    int enviado;
    int buId;
    String taCoFecha;
    int taCoId;
    private EnvioTarjetaAdaptadorRV.OnItemClickListener listener;//////LISTNER FOR DIALOG???
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null){
            buId=savedInstanceState.getInt("BUS_ID");
            taCoFecha=savedInstanceState.getString("TACO_FECHA");
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("BUS_ID", buId);
        savedInstanceState.putString("TACO_FECHA", taCoFecha);
        super.onSaveInstanceState(savedInstanceState);

    }
    @Override
    public void generarLinearLayoutVertical(){
        LinearLayoutManager lLM=new LinearLayoutManager(getActivity());
        lLM.setOrientation(LinearLayoutManager.VERTICAL);
        listaEnvioTarjetas.setLayoutManager(lLM);
    }
    @Override
    public EnvioTarjetaAdaptadorRV crearAdaptador(ArrayList<TarjetaControl> tarjetascontrol){
        listener=this;
        EnvioTarjetaAdaptadorRV _envioTarjetaAdaptadorRV= new EnvioTarjetaAdaptadorRV(listener,tarjetascontrol,getActivity(),buId,taCoFecha);
        return _envioTarjetaAdaptadorRV;
    }
    @Override
    public void inicializarAdaptadorRV(EnvioTarjetaAdaptadorRV adaptador){
        listaEnvioTarjetas.setAdapter(adaptador);

    }
    @Override
    public void onItemClicked(View v, int taCoId) {
        this.taCoId=taCoId; //esto viene desde el adaptador EnvioTarjetaAdaptadorRV
        FinalizaTarjetaDialogFragment df= new FinalizaTarjetaDialogFragment(this);
        //Bundle args = new Bundle();
       // args.putBinder("FragmentoEnvioTarjeta",this);
        //df.setArguments(args);
        df.show(getFragmentManager(), "FinalizarDialogFragment");
    }
    //aqui capturamos los metodos de la interfaz definida en FinalizaTarjetaDialogFragment
    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the FinalizaTarjetaDialogFragment.FinalizaTarjetaDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button
        TarjetaService tarjetaService=new TarjetaService(getContext());
        tarjetaService.FinalizarTarjetaIncompleta(taCoId,buId,taCoFecha);
        Toast.makeText(getContext(), "Se finalizo correctamente", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
        Toast.makeText(getContext(), "Se cancelo la finalizacion", Toast.LENGTH_SHORT).show();
    }

}
