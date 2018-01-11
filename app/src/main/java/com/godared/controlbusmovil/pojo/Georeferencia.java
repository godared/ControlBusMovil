package com.godared.controlbusmovil.pojo;

/**
 * Created by ronald on 14/08/2017.
 */

public class Georeferencia {
    private int GeId;
    private int TaCoId;
    private double GeLatitud;
    private double GeLongitud;
    private String GeFechaHora;
    private int UsId;
    private String UsFechaReg;
    private int GeOrden;
    private Boolean GeEnviadoMovil;

    public Georeferencia(){

    }
    public Georeferencia(int geId, int taCoId, double geLatitud, double geLongitud, String geFechaHora,
                         int usId, String usFechaReg, int geOrden, Boolean geEnviadoMovil){
        this.GeId=geId;
        this.TaCoId=taCoId;
        this.GeLatitud=geLatitud;
        this.GeLongitud=geLongitud;
        this.GeFechaHora=geFechaHora;
        this.UsId=usId;
        this.UsFechaReg=usFechaReg;
        this.GeOrden=geOrden;
        this.GeEnviadoMovil=geEnviadoMovil;
    }

    public int getGeId() {
        return GeId;
    }

    public void setGeId(int geId) {
        GeId = geId;
    }

    public int getTaCoId() {
        return TaCoId;
    }

    public void setTaCoId(int taCoId) {
        TaCoId = taCoId;
    }

    public double getGeLatitud() {
        return GeLatitud;
    }

    public void setGeLatitud(double geLatitud) {
        GeLatitud = geLatitud;
    }

    public double getGeLongitud() {
        return GeLongitud;
    }

    public void setGeLongitud(double geLongitud) {
        GeLongitud = geLongitud;
    }

    public String getGeFechaHora() {
        return GeFechaHora;
    }

    public void setGeFechaHora(String geFechaHora) {
        GeFechaHora = geFechaHora;
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

    public int getGeOrden() {
        return GeOrden;
    }

    public void setGeOrden(int geOrden) {
        GeOrden = geOrden;
    }

    public Boolean getGeEnviadoMovil() {
        return GeEnviadoMovil;
    }

    public void setGeEnviadoMovil(Boolean geEnviadoMovil) {
        GeEnviadoMovil = geEnviadoMovil;
    }
}
