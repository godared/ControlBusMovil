package com.godared.controlbusmovil.pojo;

/**
 * Created by ronald on 14/08/2017.
 */

public class RutaDetalle {
    private int RuDeId;
    private int RuId;
    private int RuDeOrden;
    private double RuDeLatitud;
    private double RuDeLongitud;
    private int UsId;
    private String UsFechaReg;
    public RutaDetalle(){

    }
    public RutaDetalle(int ruDeId,int ruId,int ruDeOrden,double ruDeLatitud,double ruDeLongitud,
                       int usId, String usFechaReg){
        this.RuDeId=ruDeId;
        this.RuId=ruId;
        this.RuDeOrden=ruDeOrden;
        this.RuDeLatitud=ruDeLatitud;
        this.RuDeLongitud=ruDeLongitud;
        this.UsId=usId;
        this.UsFechaReg=usFechaReg;
    }

    public int getRuDeId() {
        return RuDeId;
    }

    public void setRuDeId(int ruDeId) {
        RuDeId = ruDeId;
    }

    public int getRuId() {
        return RuId;
    }

    public void setRuId(int ruId) {
        RuId = ruId;
    }

    public int getRuDeOrden() {
        return RuDeOrden;
    }

    public void setRuDeOrden(int ruDeOrden) {
        RuDeOrden = ruDeOrden;
    }

    public double getRuDeLatitud() {
        return RuDeLatitud;
    }

    public void setRuDeLatitud(double ruDeLatitud) {
        RuDeLatitud = ruDeLatitud;
    }

    public double getRuDeLongitud() {
        return RuDeLongitud;
    }

    public void setRuDeLongitud(double ruDeLongitud) {
        RuDeLongitud = ruDeLongitud;
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
}
