package com.godared.controlbusmovil.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by ronald on 12/04/2017.
 */

public class TarjetaControlDetalle {
    @SerializedName("TaCoDeId")
    @Expose
    private int TaCoDeId;
    @SerializedName("TaCoId")
    @Expose
    private int TaCoId;
    @SerializedName("PuCoDeId")
    @Expose
    private int PuCoDeId;
    @SerializedName("TaCoDeFecha")
    @Expose
    private String TaCoDeFecha;
    @SerializedName("TaCoDeHora")
    @Expose
    private String TaCoDeHora;
    @SerializedName("TaCoDeLatitud")
    @Expose
    private double TaCoDeLatitud;
    @SerializedName("TaCoDeLongitud")
    @Expose
    private double TaCoDeLongitud;
    @SerializedName("TaCoDeTiempo")
    @Expose
    private String TaCoDeTiempo;
    @SerializedName("TaCoDeDescripcion")
    @Expose
    private String TaCoDeDescripcion;
    @SerializedName("UsId")
    @Expose
    private int  UsId;
    @SerializedName("UsFechaReg")
    @Expose
    private String UsFechaReg;
    @SerializedName("TaCoDeCodEnvioMovil")
    @Expose
    private int TaCoDeCodEnvioMovil;

    public TarjetaControlDetalle(){

    }
    public TarjetaControlDetalle(int taCoDeId, int taCoId, int puCoDeId, String taCoDeFecha,
                                 String taCoDeHora, double taCoDeLatitud, double taCoDeLongitud,
                                 String taCoDeTiempo, String taCoDeDescripcion, int usId, String usFechaReg,
                                 int taCoDeCodEnvioMovil ) {
        TaCoDeId = taCoDeId;
        TaCoId = taCoId;
        PuCoDeId = puCoDeId;
        TaCoDeFecha = taCoDeFecha;
        TaCoDeHora = taCoDeHora;
        TaCoDeLatitud = taCoDeLatitud;
        TaCoDeLongitud = taCoDeLongitud;
        TaCoDeTiempo = taCoDeTiempo;
        TaCoDeDescripcion = taCoDeDescripcion;
        UsId = usId;
        UsFechaReg = usFechaReg;
        TaCoDeCodEnvioMovil=taCoDeCodEnvioMovil;
    }

    public int getTaCoDeId() {
        return TaCoDeId;
    }

    public void setTaCoDeId(int taCoDeId) {
        TaCoDeId = taCoDeId;
    }

    public int getTaCoId() {
        return TaCoId;
    }

    public void setTaCoId(int taCoId) {
        TaCoId = taCoId;
    }

    public int getPuCoDeId() {
        return PuCoDeId;
    }

    public void setPuCoDeId(int puCoDeId) {
        PuCoDeId = puCoDeId;
    }

    public String getTaCoDeFecha() {
        return TaCoDeFecha;
    }

    public void setTaCoDeFecha(String taCoDeFecha) {
        TaCoDeFecha = taCoDeFecha;
    }

    public String getTaCoDeHora() {
        return TaCoDeHora;
    }

    public void setTaCoDeHora(String taCoDeHora) {
        TaCoDeHora = taCoDeHora;
    }

    public double getTaCoDeLatitud() {
        return TaCoDeLatitud;
    }

    public void setTaCoDeLatitud(double taCoDeLatitud) {
        TaCoDeLatitud = taCoDeLatitud;
    }

    public double getTaCoDeLongitud() {
        return TaCoDeLongitud;
    }

    public void setTaCoDeLongitud(double taCoDeLongitud) {
        TaCoDeLongitud = taCoDeLongitud;
    }

    public String getTaCoDeTiempo() {
        return TaCoDeTiempo;
    }

    public void setTaCoDeTiempo(String taCoDeTiempo) {
        TaCoDeTiempo = taCoDeTiempo;
    }

    public String getTaCoDeDescripcion() {
        return TaCoDeDescripcion;
    }

    public void setTaCoDeDescripcion(String taCoDeDescripcion) {
        TaCoDeDescripcion = taCoDeDescripcion;
    }

    public int getUsId() {
        return UsId;
    }

    public void setUsId(int usId) {
        UsId = usId;
    }

    public String getUsFechaReg() {
        return UsFechaReg;
    }

    public void setUsFechaReg(String usFechaReg) {
        UsFechaReg = usFechaReg;
    }

    public int getTaCoDeCodEnvioMovil() {
        return TaCoDeCodEnvioMovil;
    }

    public void setTaCoDeCodEnvioMovil(int taCoDeCodEnvioMovil) {
        TaCoDeCodEnvioMovil = taCoDeCodEnvioMovil;
    }
}
