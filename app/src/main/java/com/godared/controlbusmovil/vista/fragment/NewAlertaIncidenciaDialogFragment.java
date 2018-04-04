package com.godared.controlbusmovil.vista.fragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.godared.controlbusmovil.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewAlertaIncidenciaDialogFragment extends DialogFragment {


    public NewAlertaIncidenciaDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_alerta_incidencia, container, false);
    }

}
