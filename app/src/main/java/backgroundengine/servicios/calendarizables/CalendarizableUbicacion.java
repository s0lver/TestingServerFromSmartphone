package backgroundengine.servicios.calendarizables;

import android.content.Intent;
import android.util.Log;
import backgroundengine.accesoSensores.administradores.BuilderAdministradorUbicacion;
import backgroundengine.accesoSensores.alarmas.timers.TimerTaskTimeoutLecturas;
import backgroundengine.accesoSensores.dutycyclers.DutyCyclerBase;
import backgroundengine.accesoSensores.dutycyclers.DutyCyclerSensorUbicacion;
import backgroundengine.accesoSensores.registros.Registro;
import backgroundengine.accesoSensores.registros.RegistroUbicacion;
import backgroundengine.accesoSensores.traductores.TraductorRegistroUbicacion;

import java.util.Timer;

public class CalendarizableUbicacion extends CalendarizableBase {
    public CalendarizableUbicacion(DutyCyclerBase dutyCyclerBase, Intent intentAlarma) {
        super(dutyCyclerBase, intentAlarma);
        DutyCyclerSensorUbicacion dcu = (DutyCyclerSensorUbicacion) this.dutyCyclerBase;
        this.administrador = BuilderAdministradorUbicacion.contexto(dcu.getContexto())
                .proveedorUbicacion(dcu.getTipoProveedorUbicacion())
                .traductor(new TraductorRegistroUbicacion())
                .listenerSuperior(this)
                .build();
    }

    protected void recortarHistorico() {
        if (historicoLecturas.size() > 5) {
            historicoLecturas.remove(0);
        }
    }

    @Override
    public void iniciarLecturas() {
        ttTimeoutLecturas = new TimerTaskTimeoutLecturas(this);
        ttimeout = new Timer();
        // :S May be not the best idea, but it will work
        DutyCyclerSensorUbicacion dcu = (DutyCyclerSensorUbicacion) this.dutyCyclerBase;
        adquirirWakeLock();
        administrador.iniciarLecturas();
        ttimeout.schedule(ttTimeoutLecturas, dcu.getMsTimeout());
    }

    @Override
    public void detenerLecturas() {
        administrador.detenerLecturas();
        destruirTimers();
        cancelarAlarma();
        liberarWakeLock();
    }

    @Override
    public void lecturaObtenida(Registro registro) {
        super.lecturaObtenida(registro);
        detenerLecturas();

        synchronized (historicoLecturas) {
            procesarLecturas();
        }
    }

    @Override
    public void timeoutLecturas() {
        Log.w(this.getClass().getSimpleName(), "No se pudo obtener una lectura de ubicación");
        RegistroUbicacion lecturaFalsa = RegistroUbicacion.crearUbicacionNoDisponible();
        lecturaObtenida(lecturaFalsa);
    }

}
