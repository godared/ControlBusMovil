package com.godared.controlbusmovil.pojo;

import java.util.Date;

/**
 * Created by ronald on 12/04/2017.
 */

public class PuntoControl {
    private int PuCoId;
    private int RuId;
    private String PuCoTiempoBus;
    private String PuCoClase;
    private int  UsId;
    private String UsFechaReg;
    private String PuCoDescripcion;
    public PuntoControl(){}
    public PuntoControl(int puCoId, int ruId, String puCoTiempoBus, String puCoClase, int usId, String usFechaReg, String puCoDescripcion) {
        PuCoId = puCoId;
        RuId = ruId;
        PuCoTiempoBus = puCoTiempoBus;
        PuCoClase = puCoClase;
        UsId = usId;
        UsFechaReg = usFechaReg;
        PuCoDescripcion=puCoDescripcion;
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

    public String getPuCoTiempoBus() {
        return PuCoTiempoBus;
    }

    public void setPuCoTiempoBus(String puCoTiempoBus) {
        PuCoTiempoBus = puCoTiempoBus;
    }

    public String getPuCoClase() {
        return PuCoClase;
    }

    public void setPuCoClase(String puCoClase) {
        PuCoClase = puCoClase;
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

    public String getPuCoDescripcion() {
        return PuCoDescripcion;
    }

    public void setPuCoDescripcion(String puCoDescripcion) {
        PuCoDescripcion = puCoDescripcion;
    }
}
