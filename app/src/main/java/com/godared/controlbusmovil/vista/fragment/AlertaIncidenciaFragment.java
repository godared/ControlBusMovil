package com.godared.controlbusmovil.vista.fragment;

import android.content.Context;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.godared.controlbusmovil.R;
import com.godared.controlbusmovil.adapter.AlertaIncidenciaAdaptadorRV;
import com.godared.controlbusmovil.pojo.AlertaIncidencia;
import com.godared.controlbusmovil.presentador.IRecyclerviewAlertaIncidenciaPresenter;
import com.godared.controlbusmovil.presentador.RecyclerviewAlertaIncidenciaPresenter;

import java.util.ArrayList;

/**
 * create an instance of this fragment.
 */
public class AlertaIncidenciaFragment extends Fragment implements IAlertaIncidenciaFragment {
    RecyclerView listaAlertaIncidencias;
    public IRecyclerviewAlertaIncidenciaPresenter alertaIncidenciaPresenter;
    int TaCoId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View v=inflater.inflate(R.layout.fragment_alerta_incidencia,container,false); //asignamos un layout
        listaAlertaIncidencias=(RecyclerView)v.findViewById(R.id.rvAlertaIncidencia);
        TaCoId=getArguments().getInt("TACO_ID");
        alertaIncidenciaPresenter=new RecyclerviewAlertaIncidenciaPresenter(this,getContext(),TaCoId );
        return v;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null) {
            TaCoId = savedInstanceState.getInt("TACO_ID");
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("TACO_ID", TaCoId);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void generarLinearLayoutVertical() {
        LinearLayoutManager lLM=new LinearLayoutManager(getActivity());
        lLM.setOrientation(LinearLayoutManager.VERTICAL);
        listaAlertaIncidencias.setLayoutManager(lLM);
    }

    @Override
    public AlertaIncidenciaAdaptadorRV crearAdaptador(ArrayList<AlertaIncidencia> alertaIncidencias) {
        AlertaIncidenciaAdaptadorRV incidenciaAdaptadorRV=new AlertaIncidenciaAdaptadorRV(alertaIncidencias,getActivity());
        return incidenciaAdaptadorRV;
    }

    @Override
    public void inicializarAdaptadorRV(AlertaIncidenciaAdaptadorRV adaptadorRV) {
        listaAlertaIncidencias.setAdapter(adaptadorRV);
    }
}
