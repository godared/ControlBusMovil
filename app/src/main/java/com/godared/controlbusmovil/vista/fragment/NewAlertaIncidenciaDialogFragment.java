package com.godared.controlbusmovil.vista.fragment;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.godared.controlbusmovil.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewAlertaIncidenciaDialogFragment extends DialogFragment {

    EditText edtDescripcion;
    String TituloDialogo;
    int TaCoId;
    public interface NewAlertaIncidenciaDialogListener {
        void onDialogPositiveClick(DialogFragment dialog, String edtDescripcion,int taCoId);
        void onDialogNegativeClick(DialogFragment dialog);
    }
    public NewAlertaIncidenciaDialogFragment() {
        // Required empty public constructor
    }
    @SuppressLint("ValidFragment")
    public NewAlertaIncidenciaDialogFragment(NewAlertaIncidenciaDialogListener mListener, String mTitulo, int mtaCoId) {
        // Required empty public constructor
        this.mListener = mListener;
        this.TituloDialogo=mTitulo;
        this.TaCoId=mtaCoId;
    }
    NewAlertaIncidenciaDialogListener mListener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_new_alerta_incidencia, new LinearLayout(getActivity()), false);
        // Build dialog
        /*Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GREEN));
        builder.setContentView(view);
        return builder;*/

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle(this.TituloDialogo);
        // Add the buttons

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Send the negative button event back to the host activity
                mListener.onDialogNegativeClick(NewAlertaIncidenciaDialogFragment.this);

            }
        });
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                edtDescripcion= (EditText) view.findViewById(R.id.edtDescripcion);
                mListener.onDialogPositiveClick(NewAlertaIncidenciaDialogFragment.this,edtDescripcion.getText().toString(),TaCoId);
            }
        });
        return builder.create();
    }

}
