package backgroundengine.accesoSensores.dutycyclers;

import android.content.Context;
import backgroundengine.accesoSensores.administradores.LecturasSensor;
import backgroundengine.accesoSensores.enums.TipoSensor;
import backgroundengine.accesoSensores.politicas.IPolitica;

public final class DutyCyclerSensorInercial extends DutyCyclerBase {
    private int frecuenciaMuestreo;
    private long msDuracionLecturas;
    private TipoSensor tipoSensor;

    public DutyCyclerSensorInercial(Context contexto, IPolitica politica, TipoSensor tipoSensor,
                                    int frecuenciaMuestreo, long msDuracionLecturas,
                                    LecturasSensor listenerLecturas, String idCalendarizable) {
        super(contexto, politica, listenerLecturas, idCalendarizable);
        this.frecuenciaMuestreo = frecuenciaMuestreo;
        this.msDuracionLecturas = msDuracionLecturas;
        this.tipoSensor = tipoSensor;
    }

    public int getFrecuenciaMuestreo() {
        return frecuenciaMuestreo;
    }

    public long getMsDuracionLecturas() {
        return msDuracionLecturas;
    }

    public TipoSensor getTipoSensor() {
        return tipoSensor;
    }
}
