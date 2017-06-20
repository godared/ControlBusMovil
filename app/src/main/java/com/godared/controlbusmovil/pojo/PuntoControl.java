package com.godared.controlbusmovil.pojo;

import java.util.Date;

/**
 * Created by ronald on 12/04/2017.
 */

public class PuntoControl {
    private int PuCoId;
    private int RuId;
    private Date PuCoTiempoBus;
    private String PuCoClase;
    private int  UsId;
    private Date UsFechaReg;

    public PuntoControl(int puCoId, int ruId, Date puCoTiempoBus, String puCoClase, int usId, Date usFechaReg) {
        PuCoId = puCoId;
        RuId = ruId;
        PuCoTiempoBus = puCoTiempoBus;
        PuCoClase = puCoClase;
        UsId = usId;
        UsFechaReg = usFechaReg;
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

    public Date getPuCoTiempoBus() {
        return PuCoTiempoBus;
    }

    public void setPuCoTiempoBus(Date puCoTiempoBus) {
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

    public Date getUsFechaReg() {
        return UsFechaReg;
    }

    public void setUsFechaReg(Date usFechaReg) {
        UsFechaReg = usFechaReg;
    }
}
