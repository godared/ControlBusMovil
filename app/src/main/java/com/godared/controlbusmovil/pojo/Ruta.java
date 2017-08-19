package com.godared.controlbusmovil.pojo;

/**
 * Created by ronald on 14/08/2017.
 */

public class Ruta {
    private int RuId;
    private	int EmId;
    private String RuDescripcion;
    private String RuFechaCreacion;
    private String RuRegMunicipal;
    private float RuKilometro;
    private Boolean RuActivo;
    private int UsId;
    private String UsFechaReg;
    public Ruta(){

    }
    public Ruta(int ruId,int emId,String ruDescripcion, String ruFechaCreacion, String ruRegMunicipal,
                float ruKilometro, Boolean ruActivo, int usId, String usFechaReg){
        this.RuId=ruId;
        this.EmId=emId;
        this.RuDescripcion=ruDescripcion;
        this.RuFechaCreacion=ruFechaCreacion;
        this.RuRegMunicipal=ruRegMunicipal;
        this.RuKilometro=ruKilometro;
        this.RuActivo=ruActivo;
        this.UsId=usId;
        this.UsFechaReg=usFechaReg;

    }

    public int getRuId() {
        return RuId;
    }

    public void setRuId(int ruId) {
        RuId = ruId;
    }

    public int getEmId() {
        return EmId;
    }

    public void setEmId(int emId) {
        EmId = emId;
    }

    public String getRuDescripcion() {
        return RuDescripcion;
    }

    public void setRuDescripcion(String ruDescripcion) {
        RuDescripcion = ruDescripcion;
    }

    public String getRuFechaCreacion() {
        return RuFechaCreacion;
    }

    public void setRuFechaCreacion(String ruFechaCreacion) {
        RuFechaCreacion = ruFechaCreacion;
    }

    public String getRuRegMunicipal() {
        return RuRegMunicipal;
    }

    public void setRuRegMunicipal(String ruRegMunicipal) {
        RuRegMunicipal = ruRegMunicipal;
    }

    public float getRuKilometro() {
        return RuKilometro;
    }

    public void setRuKilometro(float ruKilometro) {
        RuKilometro = ruKilometro;
    }

    public Boolean getRuActivo() {
        return RuActivo;
    }

    public void setRuActivo(Boolean ruActivo) {
        RuActivo = ruActivo;
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
