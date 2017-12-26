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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

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
    public EnvioTarjetaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_enviotarjeta,parent,false);
        return new EnvioTarjetaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EnvioTarjetaAdaptadorRV.EnvioTarjetaViewHolder holder, int position) {
        TarjetaControl _tarjetaControl= tarjetasControl.get(position);
        String zona="America/Lima";
        TimeZone timeZone2 = TimeZone.getTimeZone(zona);
        String fecha=_tarjetaControl.getTaCoFecha();//
        Calendar cal=Calendar.getInstance(timeZone2);
        cal.setTimeInMillis(Long.parseLong(fecha));

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");//"yyyy-MM-dd HH:mm:ss"
        sdf2.setTimeZone(cal.getTimeZone());
        String formatted2 = sdf2.format(cal.getTime());
        holder.txtFecha.setText(formatted2);
        holder.txtNroVuelta.setText(String.valueOf(_tarjetaControl.getTaCoNroVuelta()));
        holder.txtRegistro.setText(String.valueOf(_tarjetaControl.getTaCoNroVuelta()));
    }

    @Override
    public int getItemCount() {
        return tarjetasControl.size();
    }
    public static class EnvioTarjetaViewHolder extends RecyclerView.ViewHolder{
        private TextView txtFecha;
        private TextView txtNroVuelta;
        private TextView txtEstado;
        private TextView txtRegistro;
        public EnvioTarjetaViewHolder(View itemView) {
            super(itemView);
            txtFecha=(TextView)itemView.findViewById(R.id.txtFecha);
            txtNroVuelta=(TextView)itemView.findViewById(R.id.txtNroVuelta);
            txtEstado=(TextView)itemView.findViewById(R.id.txtEstado);
            txtRegistro=(TextView)itemView.findViewById(R.id.txtRegistro);
        }
    }
}
