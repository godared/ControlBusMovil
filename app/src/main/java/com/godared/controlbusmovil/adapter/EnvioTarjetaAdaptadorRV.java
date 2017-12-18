package com.godared.controlbusmovil.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.godared.controlbusmovil.R;
import com.godared.controlbusmovil.pojo.TarjetaBitacoraMovil;
import com.godared.controlbusmovil.pojo.TarjetaControl;

import java.util.ArrayList;

/**
 * Created by ronald on 17/12/2017.
 */

public class EnvioTarjetaAdaptadorRV extends RecyclerView.Adapter<EnvioTarjetaAdaptadorRV.EnvioTarjetaViewHolder> {
    Activity activity;
    ArrayList<TarjetaControl> tarjetasControl;

    public EnvioTarjetaAdaptadorRV(ArrayList<TarjetaControl> tarjetasControl, Activity activity) {
        this.tarjetasControl =tarjetasControl;
        this.activity = activity;
    }

    @Override
    public EnvioTarjetaAdaptadorRV.EnvioTarjetaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_enviotarjeta,parent,false);
        return new EnvioTarjetaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EnvioTarjetaAdaptadorRV.EnvioTarjetaViewHolder holder, int position) {
        TarjetaControl _tarjetaControl= tarjetasControl.get(position);
        holder.txtDescripcion.setText(_tarjetaControl.getTaCoId());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public static class EnvioTarjetaViewHolder extends RecyclerView.ViewHolder{
        private TextView txtDescripcion;
        public EnvioTarjetaViewHolder(View itemView) {
            super(itemView);
            txtDescripcion=(TextView)itemView.findViewById(R.id.txtDescripcion);
        }
    }
}
