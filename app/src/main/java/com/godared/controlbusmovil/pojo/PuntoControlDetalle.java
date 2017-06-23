package com.godared.controlbusmovil.pojo;

import java.util.Date;

/**
 * Created by ronald on 12/04/2017.
 */

public class PuntoControlDetalle {
    private int PuCoDeId;
    private int PuCoId;
    private double PuCoDeLatitud;
    private double PuCoDeLongitud;
    private String PuCoDeDescripcion;
    private String PuCoDeHora;
    private int UsId;
    private String UsFechaReg;
    private int PuCoDeOrden;

    public PuntoControlDetalle(int puCoDeId, int puCoId, double puCoDeLatitud, double puCoDeLongitud,
                               String puCoDeDescripcion, String puCoDeHora, int usId, String usFechaReg,
                               int puCoDeOrden) {
        PuCoDeId = puCoDeId;
        PuCoId = puCoId;
        PuCoDeLatitud = puCoDeLatitud;
        PuCoDeLongitud = puCoDeLongitud;
        PuCoDeDescripcion = puCoDeDescripcion;
        PuCoDeHora = puCoDeHora;
        UsId = usId;
        UsFechaReg = usFechaReg;
        PuCoDeOrden = puCoDeOrden;
    }

    public int getPuCoDeId() {
        return PuCoDeId;
    }

    public void setPuCoDeId(int puCoDeId) {
        PuCoDeId = puCoDeId;
    }

    public int getPuCoId() {
        return PuCoId;
    }

    public void setPuCoId(int puCoId) {
        PuCoId = puCoId;
    }

    public double getPuCoDeLatitud() {
        return PuCoDeLatitud;
    }

    public void setPuCoDeLatitud(double puCoDeLatitud) {
        PuCoDeLatitud = puCoDeLatitud;
    }

    public double getPuCoDeLongitud() {
        return PuCoDeLongitud;
    }

    public void setPuCoDeLongitud(double puCoDeLongitud) {
        PuCoDeLongitud = puCoDeLongitud;
    }

    public String getPuCoDeDescripcion() {
        return PuCoDeDescripcion;
    }

    public void setPuCoDeDescripcion(String puCoDeDescripcion) {
        PuCoDeDescripcion = puCoDeDescripcion;
    }

    public String getPuCoDeHora() {
        return PuCoDeHora;
    }

    public void setPuCoDeHora(String puCoDeHora) {
        PuCoDeHora = puCoDeHora;
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

    public int getPuCoDeOrden() {
        return PuCoDeOrden;
    }

    public void setPuCoDeOrden(int puCoDeOrden) {
        PuCoDeOrden = puCoDeOrden;
    }
}
