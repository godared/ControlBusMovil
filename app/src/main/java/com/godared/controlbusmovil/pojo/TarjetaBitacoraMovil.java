package com.godared.controlbusmovil.pojo;

/**
 * Created by ronald on 22/05/2017.
 */

public class TarjetaBitacoraMovil {
    private int TaCoId;
    private int TaBiMoRemotoId; //Codigo de registro del server
    private int TaBiMoEnviado; //indicador de si esta enviado
    private int TaBiMoActivo; //indicador de cual tarjeta esta activo
    private int TaBiMoFinalDetalle; //indicador si se ha finalizado el detalle(0:no finalizado, 1:finalizado por estar completo el detalle, 2: finalizado por la fuerza

    public int getTaCoId() {
        return TaCoId;
    }

    public void setTaCoId(int taCoId) {
        TaCoId = taCoId;
    }

    public int getTaBiMoRemotoId() {
        return TaBiMoRemotoId;
    }

    public void setTaBiMoRemotoId(int taBiMoRemotoId) {
        TaBiMoRemotoId = taBiMoRemotoId;
    }

    public int getTaBiMoEnviado() {
        return TaBiMoEnviado;
    }

    public void setTaBiMoEnviado(int taBiMoEnviado) {
        TaBiMoEnviado = taBiMoEnviado;
    }

    public int getTaBiMoActivo() {
        return TaBiMoActivo;
    }

    public void setTaBiMoActivo(int taBiMoActivo) {
        TaBiMoActivo = taBiMoActivo;
    }

    public int getTaBiMoFinalDetalle() {
        return TaBiMoFinalDetalle;
    }

    public void setTaBiMoFinalDetalle(int taBiMoFinalDetalle) {
        TaBiMoFinalDetalle = taBiMoFinalDetalle;
    }
}
