package com.godared.controlbusmovil.restApi;

/**
 * Created by Ronald on 19/04/2017.
 */

public final class ConstantesRestApi {
    public static final String ROOT_URL="http://controlbus.us-east-1.elasticbeanstalk.com";//"http://controlbus-ronaldmam.rhcloud.com";
    //http://localhost:8089/controlbus/rest/tarjetacontrol/getalltarjetacontrolbybuidfecha?buId=1&taCoFecha=26-12-2016
    public static final String URL_TARJETA_CONTROL="/rest/tarjetacontrol/getalltarjetacontrolbybuidfecha";
    public static final String URL_TARJETA_CONTROL2="/rest/tarjetacontrol/";
    public static final String URL_TARJETA_CONTROL_DETALLE="/rest/tarjetacontroldetalle/";
    public static final String URL_PUNTO_CONTROL="/rest/puntocontrol/";
    public static final String URL_PUNTO_CONTROL_DETALLE="/rest/puntocontroldetalle/pucoid/";
    public static final String URL_RUTA="/rest/ruta/";
    public static final String URL_RUTA_DETALLE="/rest/rutadetalle/ruid/";
    public static final String URL_TELEFONO="/rest/telefono/";
    public static final String URL_TELEFONO_IMEI="/rest/telefono/getalltelefonobyimei";
    public static final String URL_GEOREFERENCIA="/rest/georeferencia/";
    public static final String URL_CONFIGURA="/rest/configura/";
    public static final String URL_ALERTA_INCIDENCIA="/rest/alertaincidencia/";

}
