package com.godared.controlbusmovil.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by ronald on 12/04/2017.
 */

public class TarjetaControl {
    @SerializedName("TaCoId")
    @Expose
    private int TaCoId;
    @SerializedName("PuCoId")
    @Expose
    private int PuCoId;
    @SerializedName("RuId")
    @Expose
    private int RuId;
    @SerializedName("BuId")
    @Expose
    private int BuId;
    @SerializedName("TaCoFecha")
    @Expose
    private String TaCoFecha;
    @SerializedName("TaCoHoraSalida")
    @Expose
    private String TaCoHoraSalida;
    @SerializedName("TaCoCuota")
    @Expose
    private float TaCoCuota;
    @SerializedName("UsId")
    @Expose
    private int UsId;
    @SerializedName("UsFechaReg")
    @Expose
    private String UsFechaReg;
    @SerializedName("TaCoNroVuelta")
    @Expose
    private int TaCoNroVuelta;

    public TarjetaControl(){

    }
    public TarjetaControl(int taCoId, int puCoId, int ruId, int buId, String taCoFecha,
                          String taCoHoraSalida, float taCoCuota, int usId, String usFechaReg) {
        TaCoId = taCoId;
        PuCoId = puCoId;
        RuId = ruId;
        BuId = buId;
        TaCoFecha = taCoFecha;
        TaCoHoraSalida = taCoHoraSalida;
        TaCoCuota = taCoCuota;
        UsId = usId;
        UsFechaReg = usFechaReg;
    }

    public int getTaCoId() {
        return TaCoId;
    }

    public void setTaCoId(int taCoId) {
        TaCoId = taCoId;
    }

    public int getPuCoId() {
        return PuCoId;
    }

    public void setPuCoId(int puCoId) {
        PuCoId = puCoId;
    }

    public int getRuId() {
        return RuId;
    }

    public void setRuId(int ruId) {
        RuId = ruId;
    }

    public int getBuId() {
        return BuId;
    }

    public void setBuId(int buId) {
        BuId = buId;
    }

    public String getTaCoFecha() {
        return TaCoFecha;
    }

    public void setTaCoFecha(String taCoFecha) {
        TaCoFecha = taCoFecha;
    }

    public String getTaCoHoraSalida() {
        return TaCoHoraSalida;
    }

    public void setTaCoHoraSalida(String taCoHoraSalida) {
        TaCoHoraSalida = taCoHoraSalida;
    }

    public float getTaCoCuota() {
        return TaCoCuota;
    }

    public void setTaCoCuota(float taCoCuota) {
        TaCoCuota = taCoCuota;
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

    public int getTaCoNroVuelta() {
        return TaCoNroVuelta;
    }

    public void setTaCoNroVuelta(int taCoNroVuelta) {
        TaCoNroVuelta = taCoNroVuelta;
    }
}
