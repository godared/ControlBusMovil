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

    private int PrId;
    private int TiSaId;
    private String TaCoAsignado;
    private int TaCoTipoHoraSalida;
    private int ReDiDeId;
    private int TaCoFinish;
    private int TaCoMultiple;
    private int TaCoCodEnvioMovil;
    private int TaCoCountMultiple;
    private int CoId;

    public TarjetaControl(){

    }
    public TarjetaControl(int taCoId, int puCoId, int ruId, int buId, String taCoFecha,
                          String taCoHoraSalida, float taCoCuota, int usId, String usFechaReg,
                          int taCoNroVuelta,int prId,int tiSaId,String taCoAsignado,
                          int taCoTipoHoraSalida,int reDiDeId,int taCoFinish,int taCoMultiple,
                          int taCoCodEnvioMovil,int taCoCountMultiple,int coId) {
        TaCoId = taCoId;
        PuCoId = puCoId;
        RuId = ruId;
        BuId = buId;
        TaCoFecha = taCoFecha;
        TaCoHoraSalida = taCoHoraSalida;
        TaCoCuota = taCoCuota;
        UsId = usId;
        UsFechaReg = usFechaReg;
        TaCoNroVuelta=taCoNroVuelta;
        PrId=prId;
        TiSaId=tiSaId;
        TaCoAsignado=taCoAsignado;
        TaCoTipoHoraSalida=taCoTipoHoraSalida;
        ReDiDeId=reDiDeId;
        TaCoFinish=taCoFinish;
        TaCoMultiple=taCoMultiple;
        TaCoCodEnvioMovil=taCoCodEnvioMovil;
        TaCoCountMultiple=taCoCountMultiple;
        CoId=coId;
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

    public int getPrId() {
        return PrId;
    }

    public void setPrId(int prId) {
        PrId = prId;
    }

    public int getTiSaId() {
        return TiSaId;
    }

    public void setTiSaId(int tiSaId) {
        TiSaId = tiSaId;
    }

    public String getTaCoAsignado() {
        return TaCoAsignado;
    }

    public void setTaCoAsignado(String taCoAsignado) {
        TaCoAsignado = taCoAsignado;
    }

    public int getTaCoTipoHoraSalida() {
        return TaCoTipoHoraSalida;
    }

    public void setTaCoTipoHoraSalida(int taCoTipoHoraSalida) {
        TaCoTipoHoraSalida = taCoTipoHoraSalida;
    }

    public int getReDiDeId() {
        return ReDiDeId;
    }

    public void setReDiDeId(int reDiDeId) {
        ReDiDeId = reDiDeId;
    }

    public int getTaCoFinish() {
        return TaCoFinish;
    }

    public void setTaCoFinish(int taCoFinish) {
        TaCoFinish = taCoFinish;
    }

    public int getTaCoMultiple() {
        return TaCoMultiple;
    }

    public void setTaCoMultiple(int taCoMultiple) {
        TaCoMultiple = taCoMultiple;
    }

    public int getTaCoCodEnvioMovil() {
        return TaCoCodEnvioMovil;
    }

    public void setTaCoCodEnvioMovil(int taCoCodEnvioMovil) {
        TaCoCodEnvioMovil = taCoCodEnvioMovil;
    }

    public int getTaCoCountMultiple() {
        return TaCoCountMultiple;
    }

    public void setTaCoCountMultiple(int taCoCountMultiple) {
        TaCoCountMultiple = taCoCountMultiple;
    }

    public int getCoId() {
        return CoId;
    }

    public void setCoId(int coId) {
        CoId = coId;
    }
}
