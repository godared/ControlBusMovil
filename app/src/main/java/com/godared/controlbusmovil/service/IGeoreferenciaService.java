package com.godared.controlbusmovil.service;

import com.godared.controlbusmovil.dao.BaseDatos;
import com.godared.controlbusmovil.pojo.Georeferencia;
import java.util.List;

/**
 * Created by ronald on 06/12/2017.
 */

public interface IGeoreferenciaService {
    void SaveGeoreferenciaRest(Georeferencia georeferencia);
    void SaveGeoreferenciaRest(List<Georeferencia> georeferencias);
    void InsertarGeoreferenciaBD(BaseDatos baseDatos, Georeferencia georeferencia);
    void ActualizarGeoreferenciaBD(BaseDatos baseDatos, Georeferencia georeferencia);
    int GetCountGeoreferenciadByTaCo(int taCoId);
    Georeferencia GetLastGeoreferenciaByTaCo(int taCoId);

}
