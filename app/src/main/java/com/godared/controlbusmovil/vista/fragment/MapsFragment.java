package com.godared.controlbusmovil.vista.fragment;


import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.godared.controlbusmovil.R;
import com.godared.controlbusmovil.pojo.TarjetaControlDetalle;
import com.godared.controlbusmovil.service.ITarjetaService;
import com.godared.controlbusmovil.service.geofence.SimpleGeofence;
import com.godared.controlbusmovil.service.geofence.SimpleGeofenceStore;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MapsFragment extends Fragment implements IMapsFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    protected SupportMapFragment mapFragment;
    protected GoogleMap map;
    protected Marker myPositionMarker;
    protected int sw=0;
    private Context context;

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            //Captutra lo que esta en Geolocationservice intent
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                int resultCode = bundle.getInt("done");
                if (resultCode == 1) {
                    Double latitude = bundle.getDouble("latitude");
                    Double longitude = bundle.getDouble("longitude");

                    updateMarker(latitude, longitude);
                }
            }
        }
    };
    public MapsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        setHasOptionsMenu(true);

        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //iTarjetaService=new TarjetaService(this,context);
        //return inflater.inflate(R.layout.fragment_maps, container, false);
        context=this.getActivity().getApplicationContext();
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);
        mapFragment = SupportMapFragment.newInstance();
        FragmentTransaction fragmentTransaction = getChildFragmentManager()
                .beginTransaction();
        fragmentTransaction.add(R.id.map_container, mapFragment);
        fragmentTransaction.commit();

        return rootView;
    }
    @Override
    public void onPause() {
        super.onPause();
        sw=0;
        getActivity().unregisterReceiver(receiver);

    }
    @Override
    public void onResume() {
        super.onResume();
        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {

                @Override
                public void onMapReady(GoogleMap googleMap) {
                    map = googleMap;
                   // map.animateCamera(CameraUpdateFactory.zoomTo(15));
                    displayGeofences();

                }
            });
        }

        getActivity().registerReceiver(receiver,
                new IntentFilter("me.hoen.geofence_21.geolocation.service"));
    }
    protected void displayGeofences() {
        HashMap<String, SimpleGeofence> geofences = SimpleGeofenceStore
                .getInstance().getSimpleGeofences(getActivity());

        for (Map.Entry<String, SimpleGeofence> item : geofences.entrySet()) {
            SimpleGeofence sg = item.getValue();

            CircleOptions circleOptions1 = new CircleOptions()
                    .center(new LatLng(sg.getLatitude(), sg.getLongitude()))
                    .radius(sg.getRadius()).strokeColor(Color.BLACK)
                    .strokeWidth(2).fillColor(0x500000ff);
            map.addCircle(circleOptions1);

        }

    }
    protected void createMarker(Double latitude, Double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);

        myPositionMarker = map.addMarker(new MarkerOptions().position(latLng));
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

    }
    protected void updateMarker(Double latitude, Double longitude) {
        if (myPositionMarker == null) {
            createMarker(latitude, longitude);
        }

        LatLng latLng = new LatLng(latitude, longitude);
        myPositionMarker.setPosition(latLng);
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        if (sw==0) {
            map.animateCamera(CameraUpdateFactory.zoomTo(15));
            sw=1;
        }
    }


}
