package com.godared.controlbusmovil.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.godared.controlbusmovil.R;
import com.godared.controlbusmovil.pojo.TarjetaControlDetalle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by ronald on 13/04/2017.
 */

public class TarjetaAdaptadorRV extends RecyclerView.Adapter<TarjetaAdaptadorRV.TarjetaViewHolder> {
    ArrayList<TarjetaControlDetalle> tarjetasDetalle;
    Activity activity;

    public TarjetaAdaptadorRV(ArrayList<TarjetaControlDetalle> tarjetasDetalle, Activity activity) {
        this.tarjetasDetalle = tarjetasDetalle;
        this.activity = activity;
    }
    //Se genera de forma automatica al implementar RecyclerView.Adapter
    //va a inflar el layout y lo pasara al viewholder para que obtenga los view
    @Override
    public TarjetaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //asocia el  view cardview_tarjeta al Recycle
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_tarjeta,parent,false);
        return new TarjetaViewHolder(v); // esto es de la clase static para pasar a su constructor
    }

    @Override
    public void onBindViewHolder(TarjetaAdaptadorRV.TarjetaViewHolder holder, int position) {
        TarjetaControlDetalle tarjetaDetalle=tarjetasDetalle.get(position);
        //holder.imgFoto.setImageResource(tarjetaDetalle.getFoto());
        holder.txtDescripcion.setText(tarjetaDetalle.getTaCoDeDescripcion());
        //Formateando Hora
        String zona="America/Lima";
        TimeZone timeZone2 = TimeZone.getTimeZone(zona);
        String hora=tarjetaDetalle.getTaCoDeHora();//
        Calendar cal=Calendar.getInstance(timeZone2);
        cal.setTimeInMillis(Long.parseLong(hora));

        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");//"yyyy-MM-dd HH:mm:ss"
        sdf2.setTimeZone(cal.getTimeZone());
        String formatted2 = sdf2.format(cal.getTime());
        holder.txtHoraProg.setText(formatted2);//
        if(!(tarjetaDetalle.getTaCoDeTiempo().compareTo("18000000")==0)) {
            hora = tarjetaDetalle.getTaCoDeTiempo();
            cal.setTimeInMillis(Long.parseLong(hora));
            formatted2 = sdf2.format(cal.getTime());
            holder.txtHoraReg.setText(formatted2);
        }else{
            holder.txtHoraReg.setText("");
        }

    }


    @Override
    public int getItemCount() { //cantidad de elementos que contien la lista
        return tarjetasDetalle.size();
    }

    public static class TarjetaViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgFoto;
        private TextView txtDescripcion;
        private TextView lblHoraProg;
        private TextView txtHoraProg;
        private TextView lblHoraReg;
        private TextView txtHoraReg;
        private ImageButton btnTime;

        public TarjetaViewHolder(View itemView){
            super(itemView);
            imgFoto=(ImageView)itemView.findViewById(R.id.imgFoto);
            txtDescripcion=(TextView)itemView.findViewById(R.id.txtDescripcion);
            lblHoraProg=(TextView)itemView.findViewById(R.id.lblHoraProg);
            txtHoraProg=(TextView)itemView.findViewById(R.id.txtHoraProg);
            lblHoraReg=(TextView)itemView.findViewById(R.id.lblHoraReg);
            txtHoraReg=(TextView)itemView.findViewById(R.id.txtHoraReg);
            btnTime=(ImageButton) itemView.findViewById(R.id.btnTime);
        }
    }
}
