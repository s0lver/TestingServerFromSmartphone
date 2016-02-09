package backgroundengine.servicios.calendarizables;

import android.content.Intent;
import backgroundengine.accesoSensores.administradores.BuilderAdministradorSensor;
import backgroundengine.accesoSensores.alarmas.timers.TimerTaskTimeoutLecturas;
import backgroundengine.accesoSensores.dutycyclers.DutyCyclerBase;
import backgroundengine.accesoSensores.dutycyclers.DutyCyclerSensorInercial;
import backgroundengine.accesoSensores.registros.Registro;
import backgroundengine.accesoSensores.traductores.TraductorRegistroAcelerometro;

import java.util.ArrayList;
import java.util.Timer;

public class CalendarizableInercial extends CalendarizableBase {
    public CalendarizableInercial(DutyCyclerBase dutyCyclerBase, Intent intentAlarma) {
        super(dutyCyclerBase, intentAlarma);

        DutyCyclerSensorInercial dcsi = (DutyCyclerSensorInercial) this.dutyCyclerBase;

        this.administrador = BuilderAdministradorSensor.contexto(dcsi.getContexto())
                .tipoSensor(dcsi.getTipoSensor())
                .frecuencia(dcsi.getFrecuenciaMuestreo())
                .traductor(new TraductorRegistroAcelerometro())
                .listenerSuperior(this)
                .build();
    }

    @Override
    protected void recortarHistorico() {
        historicoLecturas = new ArrayList<Registro>();
    }

    @Override
    protected void destruirTimers() {

    }

    @Override
    public void timeoutLecturas() {
        lecturasFinalizadas(historicoLecturas);
        detenerLecturas();
        procesarLecturas();
    }

    @Override
    public void iniciarLecturas() {
        DutyCyclerSensorInercial dcsi = (DutyCyclerSensorInercial) this.dutyCyclerBase;

        ttTimeoutLecturas = new TimerTaskTimeoutLecturas(this);
        ttimeout = new Timer();

        adquirirWakeLock();
        administrador.iniciarLecturas();
        ttimeout.schedule(ttTimeoutLecturas, dcsi.getMsDuracionLecturas());
    }

    @Override
    public void detenerLecturas() {
        administrador.detenerLecturas();
        destruirTimers();
        cancelarAlarma();
        liberarWakeLock();
    }
}
