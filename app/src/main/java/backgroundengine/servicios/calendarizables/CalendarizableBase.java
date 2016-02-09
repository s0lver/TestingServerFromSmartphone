package backgroundengine.servicios.calendarizables;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import backgroundengine.accesoSensores.administradores.AccesoSensor;
import backgroundengine.accesoSensores.administradores.AdministradorBase;
import backgroundengine.accesoSensores.administradores.LecturasSensor;
import backgroundengine.accesoSensores.alarmas.timers.TimerTaskTimeoutLecturas;
import backgroundengine.accesoSensores.dutycyclers.DutyCyclerBase;
import backgroundengine.accesoSensores.registros.Registro;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;

public abstract class CalendarizableBase implements AccesoSensor, LecturasSensor {
    protected DutyCyclerBase dutyCyclerBase;

    private Intent intentAlarma;
    protected boolean wakeLockActivo;

    protected PowerManager.WakeLock wakeLock;
    protected ArrayList<Registro> historicoLecturas;
    private long msSiguienteLectura;
    protected AdministradorBase administrador;
    protected AlarmManager adminAlarmas;
    protected TimerTaskTimeoutLecturas ttTimeoutLecturas;

    protected Timer ttimeout;
    public CalendarizableBase(DutyCyclerBase dutyCyclerBase, Intent intentAlarma){
        this.dutyCyclerBase = dutyCyclerBase;
        this.historicoLecturas = new ArrayList<Registro>();
        this.intentAlarma = intentAlarma;
        this.adminAlarmas = (AlarmManager) dutyCyclerBase.getContexto().getSystemService(Context.ALARM_SERVICE);
        PowerManager ae = (PowerManager) dutyCyclerBase.getContexto().getSystemService(Context.POWER_SERVICE);
        wakeLock = ae.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "WL " + getClass().getSimpleName() + System.currentTimeMillis());
        this.wakeLockActivo = false;
    }

    protected abstract void recortarHistorico();

    public abstract void timeoutLecturas();
    protected Calendar obtenerTiempoSiguienteLectura() {
        Calendar calendario = Calendar.getInstance();
        calendario.setTimeInMillis(System.currentTimeMillis());

        msSiguienteLectura = dutyCyclerBase.getPolitica()
                .obtenerMsSiguienteLectura(historicoLecturas, msSiguienteLectura);

        calendario.add(Calendar.MILLISECOND, (int) msSiguienteLectura);
        return calendario;
    }

    protected void adquirirWakeLock() {
        if (!wakeLockActivo) {
            wakeLock.acquire();
            wakeLockActivo = true;
            Log.v(this.getClass().getSimpleName(), "WL adquirido");
        }
        else {
            Log.w(this.getClass().getSimpleName(), "Llamada a adquirir WL cuando ya había sido adquirido");
        }
    }

    protected void liberarWakeLock() {
        if (wakeLockActivo) {
            wakeLock.release();
            wakeLockActivo = false;
            Log.v(this.getClass().getSimpleName(), "WL liberado");
        }
        else {
            Log.w(this.getClass().getSimpleName(), "Llamada a liberar WL cuando ya estaba libre");
        }
    }

    protected void cancelarAlarma(){
        Context contexto = dutyCyclerBase.getContexto();
        PendingIntent sender = PendingIntent.getBroadcast(contexto, 0, intentAlarma, 0);
        this.adminAlarmas.cancel(sender);
        Log.d(this.getClass().getSimpleName(), "Alarma " + this.getClass().getSimpleName() + " cancelada");
    }

    @Override
    public void lecturaObtenida(Registro registro) {
        synchronized (historicoLecturas) {
            historicoLecturas.add(registro);
            LecturasSensor listenerCapaSuperior = dutyCyclerBase.getListenerCapaSuperior();
            if (listenerCapaSuperior != null) {
                listenerCapaSuperior.lecturaObtenida(registro);
            }else {
                advertirListenerSuperiorNulo();
            }
        }
    }

    protected void advertirListenerSuperiorNulo() {
        Log.w(this.getClass().getSimpleName(), "Cuidado, el listener superior es nulo");
    }

    @Override
    public void lecturasFinalizadas(ArrayList<Registro> registros) {
        synchronized (historicoLecturas) {
            LecturasSensor listenerCapaSuperior = dutyCyclerBase.getListenerCapaSuperior();
            if (listenerCapaSuperior != null) {
                listenerCapaSuperior.lecturasFinalizadas(registros);
            }
            else{
                advertirListenerSuperiorNulo();
            }
        }
    }

    protected void calendarizarProximaLectura(Calendar calendario) {
        Context contexto  = dutyCyclerBase.getContexto();
        PendingIntent intentPendiente = PendingIntent.getBroadcast(contexto, 0, intentAlarma, 0);

        adminAlarmas.set(AlarmManager.RTC_WAKEUP, calendario.getTimeInMillis(), intentPendiente);

        Log.d(this.getClass().getSimpleName(), String.format("La alarma %s ha sido establecida dentro de %s ms",
                this.getClass().getSimpleName(), msSiguienteLectura));
    }

    public void procesarLecturas() {
        Calendar calendario = obtenerTiempoSiguienteLectura();
        calendarizarProximaLectura(calendario);
        recortarHistorico();
        liberarWakeLock();
    }

    protected void destruirTimers(){
        ttTimeoutLecturas.cancel();
        ttimeout.cancel();
        ttimeout.purge();
    }

    public DutyCyclerBase getDutyCyclerBase() {
        return dutyCyclerBase;
    }
}
