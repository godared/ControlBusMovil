package com.godared.controlbusmovil.vista.fragment;


import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.godared.controlbusmovil.R;
import com.godared.controlbusmovil.pojo.TarjetaControlDetalle;
import com.godared.controlbusmovil.service.ITarjetaService;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

import java.util.ArrayList;


public class MapsFragment extends Fragment implements IMapsFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    ITarjetaService iTarjetaService;
    private ArrayList<TarjetaControlDetalle> tarjetasDetalle;
    private Context context;
    private View rootView;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    private final int MINIMUM_RECOMENDED_RADIUS=100;
    PendingIntent mGeofencePendingIntent;
    TextView mTextView;
    Button mButton;

    public MapsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //iTarjetaService=new TarjetaService(this,context);
        //return inflater.inflate(R.layout.fragment_setting, container, false);
        context=this.getActivity().getApplicationContext();
        rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        //LLamano el evento click del boton
        //Button b = (Button) rootView.findViewById(R.id.btnSincroniza);
        //b.setOnClickListener(mButtonClickListener);
        ///
        mTextView = (TextView) rootView.findViewById(R.id.txtView);
        mButton = (Button) rootView.findViewById(R.id.btnSincroniza);
        mButton.setEnabled(false);
       // mButton.setOnClickListener(mButtonClickListener);
        //setupLocationRequest();

        return rootView;
    }

   /* private View.OnClickListener mButtonClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            try {
                Location lastLocation =
                        LocationServices.FusedLocationApi.
                                getLastLocation(
                                        mGoogleApiClient);
                if (lastLocation != null) {
                    mTextView.setText(
                            DateFormat.getTimeInstance().format(
                                    lastLocation.getTime()) + "\n" +
                                    "Latitude="+lastLocation.getLatitude() +
                                    "\n" + "Longitude=" +
                                    lastLocation.getLongitude());
                } else {
                    Toast.makeText(context, "null",
                            Toast.LENGTH_LONG).show();
                }
            }
            catch (SecurityException e) {e.printStackTrace();}
        }
    };*/
}
