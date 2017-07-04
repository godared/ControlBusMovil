package com.godared.controlbusmovil.restApi;

import com.godared.controlbusmovil.pojo.PuntoControl;
import com.godared.controlbusmovil.pojo.PuntoControlDetalle;
import com.godared.controlbusmovil.pojo.TarjetaControl;
import com.godared.controlbusmovil.pojo.TarjetaControlDetalle;
import com.godared.controlbusmovil.restApi.model.TarjetaControlDetalleResponse;
import com.godared.controlbusmovil.restApi.model.TarjetaControlResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Ronald on 19/04/2017.
 */

public interface IEndpointApi {
    //getalltarjetacontrolbybuidfecha?buId=1&taCoFecha=31-12-2016
   @GET(ConstantesRestApi.URL_TARJETA_CONTROL)
   Call<List<TarjetaControl>> getTarjetaControl(@Query("buId") int buId,@Query("taCoFecha") String taCoFecha);

    @GET("/rest/tarjetacontroldetalle/tacoid/{taCoId}")
    Call<List<TarjetaControlDetalle>> getTarjetaControlDetalle(@Path("taCoId") int taCoId);//

    @GET(ConstantesRestApi.URL_PUNTO_CONTROL+"{puCoId}")
    Call<PuntoControl> getPuntoControl(@Path("puCoId") int puCoId);

    @GET(ConstantesRestApi.URL_PUNTO_CONTROL_DETALLE+"{puCoId}")
    Call<List<PuntoControlDetalle>> getPuntoControlDetalle(@Path("puCoId") int puCoId);//
}
