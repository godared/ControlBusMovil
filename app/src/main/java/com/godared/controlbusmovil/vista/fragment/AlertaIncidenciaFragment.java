package com.godared.controlbusmovil.vista.fragment;

import android.content.Context;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


import com.godared.controlbusmovil.MainActivity;
import com.godared.controlbusmovil.R;
import com.godared.controlbusmovil.adapter.AlertaIncidenciaAdaptadorRV;
import com.godared.controlbusmovil.pojo.AlertaIncidencia;
import com.godared.controlbusmovil.presentador.IRecyclerviewAlertaIncidenciaPresenter;
import com.godared.controlbusmovil.presentador.RecyclerviewAlertaIncidenciaPresenter;
import com.godared.controlbusmovil.service.AlertaIncidenciaService;
import com.godared.controlbusmovil.service.TarjetaService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * create an instance of this fragment.
 */
public class AlertaIncidenciaFragment extends Fragment implements IAlertaIncidenciaFragment,
        NewAlertaIncidenciaDialogFragment.NewAlertaIncidenciaDialogListener {
    RecyclerView listaAlertaIncidencias;
    public IRecyclerviewAlertaIncidenciaPresenter alertaIncidenciaPresenter;
    int TaCoId;
    FloatingActionButton btnFab;
    public interface AlertaIncidenciaFragmentListerner{
        void listenNuevaIncidenciaDialog(String descripcion);
    }
    AlertaIncidenciaFragmentListerner mlistener;
    public AlertaIncidenciaFragment(){

    }
    /*public AlertaIncidenciaFragment(AlertaIncidenciaFragmentListerner alertaIncidenciaFragmentListerner){
        this.mlistener=alertaIncidenciaFragmentListerner;
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View v=inflater.inflate(R.layout.fragment_alerta_incidencia,container,false); //asignamos un layout
        listaAlertaIncidencias=(RecyclerView)v.findViewById(R.id.rvAlertaIncidencia);
        MainActivity _mainActivity=(MainActivity)getActivity();
        TaCoId=_mainActivity.TaCoId; //getArguments().getInt("TACO_ID");
        alertaIncidenciaPresenter=new RecyclerviewAlertaIncidenciaPresenter(this,getContext(),TaCoId );
        btnFab = (FloatingActionButton)v.findViewById(R.id.fab2);
        if (btnFab != null) {
            btnFab.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Hello FAB!", Toast.LENGTH_SHORT).show();
                    NewAlertaIncidenciaDialogFragment df= new NewAlertaIncidenciaDialogFragment(AlertaIncidenciaFragment.this);
                    df.show(getFragmentManager(), "NewAlertaIncidenciaDialogFragment");
                }
            });
        }

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
    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String descripcion) {
        // User touched the dialog's positive button
        AlertaIncidenciaService alertaIncidenciaService=new AlertaIncidenciaService(getContext());
        List<AlertaIncidencia> alertaIncidencias=new ArrayList<>();
        AlertaIncidencia alertaIncidencia=new AlertaIncidencia();
        alertaIncidencia.setAlInId(0);
        alertaIncidencia.setAlInTipo(false);
        alertaIncidencia.setTaCoId(this.TaCoId);
        SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");//("yyyy-MM-dd'T'HH:mm:ss");
        String dateFecha=format.format(new Date());
        alertaIncidencia.setAlInFecha(dateFecha);
        alertaIncidencia.setAlInDescripcion(descripcion);
        alertaIncidencias.add(alertaIncidencia);
        alertaIncidenciaService.GuardarAlertaIncidenciaBD(alertaIncidencias);

        //esto viene desde TarjetaService
        //AlertaIncidenciaFragment alertaIncidenciaFragment;
        //alertaIncidenciaFragment=(AlertaIncidenciaFragment) fragmets.get(2);
        alertaIncidenciaPresenter.obtenerAlertaIncidenciasBD(this.TaCoId);

        Toast.makeText(getContext(), "Se guardo", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
        Toast.makeText(getContext(), "Se cancelo la finalizacion", Toast.LENGTH_SHORT).show();
    }
}
