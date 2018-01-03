package com.godared.controlbusmovil.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.godared.controlbusmovil.R;
import com.godared.controlbusmovil.pojo.TarjetaBitacoraMovil;
import com.godared.controlbusmovil.pojo.TarjetaControl;
import com.godared.controlbusmovil.service.TarjetaService;
import com.godared.controlbusmovil.vista.DetalleActivity;
import com.godared.controlbusmovil.vista.SettingActivity;

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
    TarjetaService tarjetaService;

    int BuId;
    String TaCoFecha;

    public EnvioTarjetaAdaptadorRV(ArrayList<TarjetaControl> tarjetasControl, Activity activity,int buId,String taCoFecha) {
        this.tarjetasControl =tarjetasControl;
        this.activity = activity;
        tarjetaService=new TarjetaService(activity.getBaseContext());
        this.BuId=buId;
        this.TaCoFecha=taCoFecha;
    }

    @Override
    public EnvioTarjetaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_enviotarjeta,parent,false);
        return new EnvioTarjetaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EnvioTarjetaAdaptadorRV.EnvioTarjetaViewHolder holder, int position) {
        final  TarjetaControl _tarjetaControl= tarjetasControl.get(position);
        String zona="America/Lima";
        TimeZone timeZone2 = TimeZone.getTimeZone(zona);
        String fecha=_tarjetaControl.getTaCoFecha();//
        Calendar cal=Calendar.getInstance(timeZone2);
        cal.setTimeInMillis(Long.parseLong(fecha));

        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");//"yyyy-MM-dd HH:mm:ss"
        sdf2.setTimeZone(cal.getTimeZone());
        String formatted2 = sdf2.format(cal.getTime());
        holder.txtFecha.setText(formatted2);
        holder.txtNroVuelta.setText(String.valueOf(_tarjetaControl.getTaCoNroVuelta()));
        holder.txtEstado.setText(_tarjetaControl.getUsFechaReg());
        holder.txtRegistro.setText(String.valueOf(_tarjetaControl.getTaCoCodEnvioMovil()));
        holder.btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Procedimiento llamar al metodo finalizar
                tarjetaService.FinalizarTarjetaIncompleta(_tarjetaControl.getTaCoId(),BuId,TaCoFecha);
            }
        });
        holder.btnDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //llamar al fragment tarjetas de control
                Intent intent= new Intent(activity, DetalleActivity.class);
                intent.putExtra("TACO_ID",_tarjetaControl.getTaCoId());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setClassName(activity,"com.godared.controlbusmovil.vista.DetalleActivity");
               // intent.putExtra("TACO_FECHA",this.FechaActual);
                activity.startActivity(intent);
            }
        });
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
        private ImageButton btnFinalizar;
        private ImageButton btnDetalle;

        public EnvioTarjetaViewHolder(View itemView) {
            super(itemView);
            txtFecha=(TextView)itemView.findViewById(R.id.txtFecha);
            txtNroVuelta=(TextView)itemView.findViewById(R.id.txtNroVuelta);
            txtEstado=(TextView)itemView.findViewById(R.id.txtEstado);
            txtRegistro=(TextView)itemView.findViewById(R.id.txtRegistro);
            btnFinalizar=(ImageButton)itemView.findViewById(R.id.btnFinalizar);
            btnDetalle=(ImageButton)itemView.findViewById(R.id.btnDetalle);
        }
    }
}
