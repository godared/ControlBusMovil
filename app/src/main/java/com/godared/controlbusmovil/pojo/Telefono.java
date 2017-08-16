package com.godared.controlbusmovil.pojo;

/**
 * Created by ronald on 14/08/2017.
 */

public class Telefono {
    private int TeId;
    private int BuId;
    private String TeMarca;
    private String TeModelo;
    private String TeVersionAndroid;
    private int TeActivo;
    private String TeImei;
    private int UsId;
    private String UsFechaReg;
    public Telefono(){

    }
    public Telefono(int teId,int buId, String teMarca, String teModelo,String teVersionAndroid,
                    int teActivo, String teImei, int usId, String usFechaReg){
        this.TeId=teId;
        this.BuId=buId;
        this.TeMarca=teMarca;
        this.TeModelo=teModelo;
        this.TeVersionAndroid=teVersionAndroid;
        this.TeActivo=teActivo;
        this.TeImei=teImei;
        this.UsId=usId;
        this.UsFechaReg=usFechaReg;
    }

    public int getTeId() {
        return TeId;
    }

    public void setTeId(int teId) {
        TeId = teId;
    }

    public int getBuId() {
        return BuId;
    }

    public void setBuId(int buId) {
        BuId = buId;
    }

    public String getTeMarca() {
        return TeMarca;
    }

    public void setTeMarca(String teMarca) {
        TeMarca = teMarca;
    }

    public String getTeModelo() {
        return TeModelo;
    }

    public void setTeModelo(String teModelo) {
        TeModelo = teModelo;
    }

    public String getTeVersionAndroid() {
        return TeVersionAndroid;
    }

    public void setTeVersionAndroid(String teVersionAndroid) {
        TeVersionAndroid = teVersionAndroid;
    }

    public int getTeActivo() {
        return TeActivo;
    }

    public void setTeActivo(int teActivo) {
        TeActivo = teActivo;
    }

    public String getTeImei() {
        return TeImei;
    }

    public void setTeImei(String teImei) {
        TeImei = teImei;
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
