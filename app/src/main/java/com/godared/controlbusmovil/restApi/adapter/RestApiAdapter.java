package com.godared.controlbusmovil.restApi.adapter;

import com.godared.controlbusmovil.restApi.ConstantesRestApi;
import com.godared.controlbusmovil.restApi.IEndpointApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ronald on 19/04/2017.
 */

public class RestApiAdapter {
    public IEndpointApi establecerConexionRestApi(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ConstantesRestApi.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create()) //Convertido usando GSON(deserializacion, obtener datos de JSON) para eso referenciamos  dentro de retrofit a GSON
                .build();
        return retrofit.create(IEndpointApi.class);
    }
}
