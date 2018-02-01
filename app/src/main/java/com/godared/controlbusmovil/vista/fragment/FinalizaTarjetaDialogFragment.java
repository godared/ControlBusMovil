package com.godared.controlbusmovil.vista.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.godared.controlbusmovil.R;

/**
 * Created by Ronald on 31/01/2018.
 */

public class FinalizaTarjetaDialogFragment extends DialogFragment {
    /* The activity that creates an instance of this dialog fragment must
         * implement this interface in order to receive event callbacks.
         * Each method passes the DialogFragment in case the host needs to query it. */
    public interface FinalizaTarjetaDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
    public FinalizaTarjetaDialogFragment(){}
    //dicese que no deberia usarse un contructor con argumentos para fragmentos pero no se como obtener
    //el objeto fragment
    public FinalizaTarjetaDialogFragment(FinalizaTarjetaDialogListener mListener) {
        this.mListener = mListener;
    }

    // Use this instance of the interface to deliver action events
    FinalizaTarjetaDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener

   /* @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            FragmentManager fragmentManager = activity.findViewById(R.id.flEnvioTarjeta);

            mListener = (FinalizaTarjetaDialogListener) activity.getFragmentManager().findFragmentById(R.id.fragment_container);
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " Deberia implement FinalizaTarjetaDialogListener");
        }
    }*/
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
       // View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_recyclerviewenviotarjeta, new LinearLayout(getActivity()), false);
        // Build dialog
        /*Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GREEN));
        builder.setContentView(view);
        return builder;*/

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("¿Esta seguro de finalizar la Tarjeta?");
// Add the buttons
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                mListener.onDialogPositiveClick(FinalizaTarjetaDialogFragment.this);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Send the negative button event back to the host activity
                mListener.onDialogNegativeClick(FinalizaTarjetaDialogFragment.this);

            }
        });
        return builder.create();
    }
}
