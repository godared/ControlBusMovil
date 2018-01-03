package com.godared.controlbusmovil.vista.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.godared.controlbusmovil.MainActivity;
import com.godared.controlbusmovil.R;
import com.godared.controlbusmovil.adapter.TarjetaAdaptadorRV;
import com.godared.controlbusmovil.pojo.TarjetaControl;
import com.godared.controlbusmovil.pojo.TarjetaControlDetalle;
import com.godared.controlbusmovil.presentador.IRecyclerviewFragmentPresenter;
import com.godared.controlbusmovil.presentador.RecyclerviewFragmentPresenter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 */
public class RecyclerviewFragment extends Fragment implements  IRecyclerviewFragment {
    //ArrayList<TarjetaControl> tarjetas;
    RecyclerView listaTarjetasDetalle;
    IRecyclerviewFragmentPresenter recyclerviewFragmentPresenter;
    int BuId;
    int TaCoId;
    String TaCoFecha;
    Boolean IndicaGetDetalleActivo;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View v=inflater.inflate(R.layout.fragment_recyclerview,container,false); //asignamos un layout
        listaTarjetasDetalle=(RecyclerView)v.findViewById(R.id.rvTarjeta);
        //Obteniendo dato del main
        //MainActivity _actividadPrincipal = (MainActivity)getActivity();
        BuId=getArguments().getInt("BUS_ID");
        TaCoId=getArguments().getInt("TACO_ID");
        TaCoFecha=getArguments().getString("TACO_FECHA");
        IndicaGetDetalleActivo=getArguments().getBoolean("INDICA_GETDETALLEACTIVO");
        //BuId=_actividadPrincipal.BuId;
        recyclerviewFragmentPresenter =new RecyclerviewFragmentPresenter(this,getContext(),BuId,TaCoId,TaCoFecha,IndicaGetDetalleActivo);
        return v;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null) {
            BuId = savedInstanceState.getInt("BUS_ID");
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("BUS_ID", BuId);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void generarLinearLayoutVertical() {
        LinearLayoutManager lLM=new LinearLayoutManager(getActivity());
        lLM.setOrientation(LinearLayoutManager.VERTICAL);
        listaTarjetasDetalle.setLayoutManager(lLM);
    }

    @Override
    public TarjetaAdaptadorRV crearAdaptador(ArrayList<TarjetaControlDetalle> tarjetasDetalle) {
        //Inicializar el dapatador
        TarjetaAdaptadorRV tarjetaAdaptador=new TarjetaAdaptadorRV(tarjetasDetalle,getActivity());
        return tarjetaAdaptador;
    }

    @Override
    public void inicializarAdaptadorRV(TarjetaAdaptadorRV adaptador) {
        listaTarjetasDetalle.setAdapter(adaptador);
    }
}
