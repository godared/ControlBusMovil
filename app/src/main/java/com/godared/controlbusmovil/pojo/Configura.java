package com.godared.controlbusmovil.pojo;

import java.util.Date;

/**
 * Created by Ronald on 21/03/2018.
 */

public class Configura {
    private int CoId;
    private int EmId;
    private int CoPeriodo;
    private int CoNroMaxVueltas;
    private String CoLogo;
    private int CoCountMovilTaCo;
    private int CoCountMovilTaCoDe;
    private String CoMembreReporte;
    private int UsId;
    private Date UsFechaReg;
    private int CoSiId;
    private Date CoTiempoActual;
    public Configura(){

    }
    public Configura(int coId,int emId,int coPeriodo,int coNroMaxVueltas,String coLogo,int coCountMovilTaCo,
                     int coCountMovilTaCoDe,String coMembreReporte,int usId,Date usFechaReg,int coSiId,Date coTiempoActual){
        this.CoId=coId;
        this.EmId=emId;
        this.CoPeriodo=coPeriodo;
        this.CoNroMaxVueltas=coNroMaxVueltas;
        this.CoLogo=coLogo;
        this.CoCountMovilTaCo=coCountMovilTaCo;
        this.CoCountMovilTaCoDe=coCountMovilTaCoDe;
        this.CoMembreReporte=coMembreReporte;
        this.UsId=usId;
        this.UsFechaReg=usFechaReg;
        this.CoSiId=coSiId;
        this.CoTiempoActual=coTiempoActual;

    }

    public int getCoId() {
        return CoId;
    }

    public void setCoId(int coId) {
        CoId = coId;
    }

    public int getEmId() {
        return EmId;
    }

    public void setEmId(int emId) {
        EmId = emId;
    }

    public int getCoPeriodo() {
        return CoPeriodo;
    }

    public void setCoPeriodo(int coPeriodo) {
        CoPeriodo = coPeriodo;
    }

    public int getCoNroMaxVueltas() {
        return CoNroMaxVueltas;
    }

    public void setCoNroMaxVueltas(int coNroMaxVueltas) {
        CoNroMaxVueltas = coNroMaxVueltas;
    }

    public String getCoLogo() {
        return CoLogo;
    }

    public void setCoLogo(String coLogo) {
        CoLogo = coLogo;
    }

    public int getCoCountMovilTaCo() {
        return CoCountMovilTaCo;
    }

    public void setCoCountMovilTaCo(int coCountMovilTaCo) {
        CoCountMovilTaCo = coCountMovilTaCo;
    }

    public int getCoCountMovilTaCoDe() {
        return CoCountMovilTaCoDe;
    }

    public void setCoCountMovilTaCoDe(int coCountMovilTaCoDe) {
        CoCountMovilTaCoDe = coCountMovilTaCoDe;
    }

    public String getCoMembreReporte() {
        return CoMembreReporte;
    }

    public void setCoMembreReporte(String coMembreReporte) {
        CoMembreReporte = coMembreReporte;
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

    public int getCoSiId() {
        return CoSiId;
    }

    public void setCoSiId(int coSiId) {
        CoSiId = coSiId;
    }

    public Date getCoTiempoActual() {
        return CoTiempoActual;
    }

    public void setCoTiempoActual(Date coTiempoActual) {
        CoTiempoActual = coTiempoActual;
    }
}
