package com.godared.controlbusmovil.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.godared.controlbusmovil.R;
import com.godared.controlbusmovil.pojo.AlertaIncidencia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Ronald on 03/04/2018.
 */

public class AlertaIncidenciaAdaptadorRV extends RecyclerView.Adapter<AlertaIncidenciaAdaptadorRV.AlertaIncidenciaViewHolder>{
    ArrayList<AlertaIncidencia> alertaIncidencias;
    Activity activity;

    public AlertaIncidenciaAdaptadorRV(ArrayList<AlertaIncidencia> alertaIncidencias, Activity activity) {
        this.alertaIncidencias = alertaIncidencias;
        this.activity = activity;
    }
    //Se genera de forma automatica al implementar RecyclerView.Adapter
    //va a inflar el layout y lo pasara al viewholder para que obtenga los view
    @Override
    public AlertaIncidenciaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //asocia el  view cardview_tarjeta al Recycle
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_alertaincidencia,parent,false);
        return new AlertaIncidenciaViewHolder(v); // esto es de la clase static para pasar a su constructor
    }
    @Override
    public void onBindViewHolder(AlertaIncidenciaAdaptadorRV.AlertaIncidenciaViewHolder holder,int position){
        AlertaIncidencia alertaIncidencia=alertaIncidencias.get(position);
        holder.txtTipoDescripcion.setText(alertaIncidencia.getAlInTipo()==true?" : ALERTA":" : INCIDENCIA");
        holder.txtDescripcion.setText(alertaIncidencia.getAlInDescripcion());
        //Formateando Hora
        String zona="America/Lima";
        TimeZone timeZone2 = TimeZone.getTimeZone(zona);
        String fecha1=alertaIncidencia.getAlInFecha();//
        Calendar cal=Calendar.getInstance(timeZone2);
        //cal.setTime(hora);//.setTimeInMillis(Long.parseLong(hora));

        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");//"yyyy-MM-dd HH:mm:ss"
        try {
            cal.setTime(sdf2.parse(fecha1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf2.setTimeZone(cal.getTimeZone());
        String formatted2 = sdf2.format(cal.getTime());
        holder.txtFecha.setText(formatted2);//
    }
    @Override
    public int getItemCount() { //cantidad de elementos que contien la lista
        return alertaIncidencias.size();
    }

    public static class AlertaIncidenciaViewHolder extends RecyclerView.ViewHolder{
        private TextView txtDescripcion;
        private TextView txtTipoDescripcion;
        private TextView txtFecha;
        public AlertaIncidenciaViewHolder(View itemView) {
            super(itemView);
            txtTipoDescripcion=(TextView)itemView.findViewById(R.id.txtTipoDescripcion);
            txtFecha=(TextView)itemView.findViewById(R.id.txtFecha);
            txtDescripcion=(TextView)itemView.findViewById(R.id.txtDescripcion);
        }
    }

}
