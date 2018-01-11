package com.godared.controlbusmovil.restApi;

import com.godared.controlbusmovil.pojo.Georeferencia;
import com.godared.controlbusmovil.pojo.PuntoControl;
import com.godared.controlbusmovil.pojo.PuntoControlDetalle;
import com.godared.controlbusmovil.pojo.Ruta;
import com.godared.controlbusmovil.pojo.RutaDetalle;
import com.godared.controlbusmovil.pojo.TarjetaControl;
import com.godared.controlbusmovil.pojo.TarjetaControlDetalle;
import com.godared.controlbusmovil.pojo.Telefono;
import com.godared.controlbusmovil.pojo.TelefonoImei;
import com.godared.controlbusmovil.restApi.model.TarjetaControlDetalleResponse;
import com.godared.controlbusmovil.restApi.model.TarjetaControlResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Ronald on 19/04/2017.
 */

public interface IEndpointApi {
    //getalltarjetacontrolbybuidfecha?buId=1&taCoFecha=31-12-2016
    @GET(ConstantesRestApi.URL_TARJETA_CONTROL)
    Call<List<TarjetaControl>> getTarjetaControl(@Query("buId") int buId,@Query("taCoFecha") String taCoFecha);

    @POST(ConstantesRestApi.URL_TARJETA_CONTROL2+"savemovil")
    Call<Integer> updateTarjetaControl(@Body TarjetaControl tarjetaControl);

    @GET("/rest/tarjetacontroldetalle/tacoid/{taCoId}")
    Call<List<TarjetaControlDetalle>> getTarjetaControlDetalle(@Path("taCoId") int taCoId);//

    @POST(ConstantesRestApi.URL_TARJETA_CONTROL_DETALLE+"saveonemovil")
    Call<Integer> updateTarjetaControlDetalle(@Body TarjetaControlDetalle tarjetaControlDetalle);//
    @POST(ConstantesRestApi.URL_TARJETA_CONTROL_DETALLE+"saveallmovil")
    Call<List<TarjetaControlDetalle>> updateTarjetaControlDetalles(@Body List<TarjetaControlDetalle> tarjetaControlDetalle);//
    //PuntoCOntrol
    @GET(ConstantesRestApi.URL_PUNTO_CONTROL+"{puCoId}")
    Call<PuntoControl> getPuntoControl(@Path("puCoId") int puCoId);

    @GET(ConstantesRestApi.URL_PUNTO_CONTROL_DETALLE+"{puCoId}")
    Call<List<PuntoControlDetalle>> getPuntoControlDetalle(@Path("puCoId") int puCoId);//
    //Ruta
    @GET(ConstantesRestApi.URL_RUTA+"{ruId}")
    Call<Ruta> getRuta(@Path("ruId") int ruId);

    @GET(ConstantesRestApi.URL_RUTA_DETALLE+"{ruId}")
    Call<List<RutaDetalle>> getRutaDetalle(@Path("ruId") int ruId);//
    //Telefono
    @GET(ConstantesRestApi.URL_TELEFONO+"{teId}")
    Call<Telefono> getTelefono(@Path("teId") int teId);

    @GET(ConstantesRestApi.URL_TELEFONO_IMEI)
    Call<List<TelefonoImei>> getAllTelefonoImei( @Query("teImei") String teImei);

    //Georeferencia
    @POST(ConstantesRestApi.URL_GEOREFERENCIA+"saveone")
    Call<Boolean> saveGeoreferenciaOne(@Body Georeferencia georeferencia);

    @POST(ConstantesRestApi.URL_GEOREFERENCIA+"save")
    Call<Boolean> saveGeoreferencia(@Body List<Georeferencia> georeferencias);//
}
