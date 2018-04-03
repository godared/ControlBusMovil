package com.godared.controlbusmovil.pojo;

/**
 * Created by ronald on 02/04/2018.
 */

public class AlertaIncidencia {
    private int AlInId;
    private int EmId;
    private String AlInFecha;
    private String AlInDescripcion;
    private Boolean AlInTipo;
    private double AlInLatitud;
    private double AlInLongitud;
    private int UsId;
    private String UsFechaReg;
    private int TaCoId;

    public AlertaIncidencia() {
    }

    public AlertaIncidencia(int alInId, int emId, String alInFecha, String alInDescripcion,
                            Boolean alInTipo, double alInLatitud, double alInLongitud, int usId,
                            String usFechaReg, int taCoId) {
        AlInId = alInId;
        EmId = emId;
        AlInFecha = alInFecha;
        AlInDescripcion = alInDescripcion;
        AlInTipo = alInTipo;
        AlInLatitud = alInLatitud;
        AlInLongitud = alInLongitud;
        UsId = usId;
        UsFechaReg = usFechaReg;
        TaCoId = taCoId;
    }

    public int getAlInId() {
        return AlInId;
    }

    public void setAlInId(int alInId) {
        AlInId = alInId;
    }

    public int getEmId() {
        return EmId;
    }

    public void setEmId(int emId) {
        EmId = emId;
    }

    public String getAlInFecha() {
        return AlInFecha;
    }

    public void setAlInFecha(String alInFecha) {
        AlInFecha = alInFecha;
    }

    public String getAlInDescripcion() {
        return AlInDescripcion;
    }

    public void setAlInDescripcion(String alInDescripcion) {
        AlInDescripcion = alInDescripcion;
    }

    public Boolean getAlInTipo() {
        return AlInTipo;
    }

    public void setAlInTipo(Boolean alInTipo) {
        AlInTipo = alInTipo;
    }

    public double getAlInLatitud() {
        return AlInLatitud;
    }

    public void setAlInLatitud(double alInLatitud) {
        AlInLatitud = alInLatitud;
    }

    public double getAlInLongitud() {
        return AlInLongitud;
    }

    public void setAlInLongitud(double alInLongitud) {
        AlInLongitud = alInLongitud;
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

    public int getTaCoId() {
        return TaCoId;
    }

    public void setTaCoId(int taCoId) {
        TaCoId = taCoId;
    }
}
