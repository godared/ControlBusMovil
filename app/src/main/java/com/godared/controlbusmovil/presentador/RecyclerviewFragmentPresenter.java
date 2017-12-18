package com.godared.controlbusmovil.presentador;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import com.godared.controlbusmovil.MainActivity;
import com.godared.controlbusmovil.dao.ConstructorTarjetas;
import com.godared.controlbusmovil.pojo.TarjetaControlDetalle;
import com.godared.controlbusmovil.restApi.IEndpointApi;
import com.godared.controlbusmovil.restApi.adapter.RestApiAdapter;
import com.godared.controlbusmovil.restApi.model.TarjetaControlDetalleResponse;
import com.godared.controlbusmovil.service.ITarjetaService;
import com.godared.controlbusmovil.service.TarjetaService;
import com.godared.controlbusmovil.vista.fragment.IRecyclerviewFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ronald on 13/04/2017.
 */

public class RecyclerviewFragmentPresenter implements IRecyclerviewFragmentPresenter {
    private IRecyclerviewFragment iRecyclerviewFragment;
    private Context context;
    private ConstructorTarjetas constructorTarjetas;
    private ArrayList<TarjetaControlDetalle> tarjetasDetalle;
    int BuId;
    public RecyclerviewFragmentPresenter(IRecyclerviewFragment iRecyclerviewFragment, Context context, int buId) {
        this.iRecyclerviewFragment=iRecyclerviewFragment;
        this.context=context;
        this.BuId=buId;
        obtenerTarjetasDetalleBD();
       // obtenerTarjetasDetalleRest();
    }
    @Override
    public void obtenerTarjetasDetalleBD() {
        //constructorTarjetas =new ConstructorTarjetas(context);
        //tarjetasDetalle = constructorTarjetas.ObtenerDatosTarjetasDetalle();

        ITarjetaService tarjetaService=new TarjetaService(context);
        String dateNow = DateFormat.format("dd-MM-yyyy",
                new Date()).toString();
        tarjetasDetalle =tarjetaService.GetAllTarjetaDetalleBDByTaCoActivo(BuId,dateNow);//"16-08-2017"
        mostrarTarjetasDetalleRV();
    }

    @Override
    public void obtenerTarjetasDetalleRest() {
      RestApiAdapter restApiAdapter=new RestApiAdapter();
       IEndpointApi endpointApi= restApiAdapter.establecerConexionRestApi();
        Call<List<TarjetaControlDetalle>> tarjetaControlDetalleResponseCall=endpointApi.getTarjetaControlDetalle(1);
        //controlamos alguns eventos de a respuesta
        tarjetaControlDetalleResponseCall.enqueue(new Callback<List<TarjetaControlDetalle>>() {
            @Override
            public void onResponse(Call<List<TarjetaControlDetalle>> call, Response<List<TarjetaControlDetalle>> response) {
                ArrayList<TarjetaControlDetalle> tarjetaControlDetalleResponse;
                //tarjetaControlDetalleResponse=new ArrayList<>();
                tarjetaControlDetalleResponse=(ArrayList<TarjetaControlDetalle>) response.body();
                tarjetasDetalle= tarjetaControlDetalleResponse;// tarjetaControlDetalle.getTarjetasDetalle();
                mostrarTarjetasDetalleRV();
            }

            @Override
            public void onFailure(Call<List<TarjetaControlDetalle>> call, Throwable t) {
                Toast.makeText(context, "Algo paso en la conexion", Toast.LENGTH_SHORT).show();
                Log.e("Fallo la conexion",t.toString());
            }
        });

    }

    @Override
    public void mostrarTarjetasDetalleRV() {
        iRecyclerviewFragment.inicializarAdaptadorRV(iRecyclerviewFragment.crearAdaptador(tarjetasDetalle));
        iRecyclerviewFragment.generarLinearLayoutVertical();
    }
}
