package backgroundengine.accesoSensores.dutycyclers;

import android.content.Context;
import backgroundengine.accesoSensores.administradores.LecturasSensor;
import backgroundengine.accesoSensores.enums.TipoProveedorUbicacion;
import backgroundengine.accesoSensores.politicas.IPolitica;

public class DutyCyclerSensorUbicacion extends DutyCyclerBase {
    private int msTimeout;
    private TipoProveedorUbicacion tipoProveedorUbicacion;

    public DutyCyclerSensorUbicacion(Context contexto, IPolitica politica, LecturasSensor listenerLecturas,
                                     TipoProveedorUbicacion tipoProveedorUbicacion, int msTimeout,
                                     String idCalendarizable) {
        super(contexto, politica, listenerLecturas, idCalendarizable);
        this.msTimeout = msTimeout;
        this.tipoProveedorUbicacion = tipoProveedorUbicacion;
    }

    public int getMsTimeout() {
        return msTimeout;
    }

    public TipoProveedorUbicacion getTipoProveedorUbicacion() {
        return tipoProveedorUbicacion;
    }
}
