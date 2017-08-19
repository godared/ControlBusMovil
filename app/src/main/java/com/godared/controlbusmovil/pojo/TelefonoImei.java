package com.godared.controlbusmovil.pojo;

/**
 * Created by ronald on 18/08/2017.
 */

public class TelefonoImei {
    private int TeId;
    private int BuId;
    private int EmId;
    private String SuEmRSocial;
    private String BuPlaca;
    private String TeMarca;
    private String TeImei;
    private String EmConsorcio;
    public TelefonoImei(){

    }
    public TelefonoImei(int teId,int buId, int emId, String suEmRSocial,
                        String buPlaca, String teMarca,String teImei, String emConsorcio){
        this.TeId=teId;
        this.BuId=buId;
        this.EmId=emId;
        this.SuEmRSocial=suEmRSocial;
        this.BuPlaca=buPlaca;
        this.TeMarca=teMarca;
        this.TeImei=teImei;
        this.EmConsorcio=emConsorcio;

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

    public int getEmId() {
        return EmId;
    }

    public void setEmId(int emId) {
        EmId = emId;
    }

    public String getSuEmRSocial() {
        return SuEmRSocial;
    }

    public void setSuEmRSocial(String suEmRSocial) {
        SuEmRSocial = suEmRSocial;
    }

    public String getBuPlaca() {
        return BuPlaca;
    }

    public void setBuPlaca(String buPlaca) {
        BuPlaca = buPlaca;
    }

    public String getTeMarca() {
        return TeMarca;
    }

    public void setTeMarca(String teMarca) {
        TeMarca = teMarca;
    }

    public String getTeImei() {
        return TeImei;
    }

    public void setTeImei(String teImei) {
        TeImei = teImei;
    }

    public String getEmConsorcio() {
        return EmConsorcio;
    }

    public void setEmConsorcio(String emConsorcio) {
        EmConsorcio = emConsorcio;
    }
}
