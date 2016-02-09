package backgroundengine.servicios;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import backgroundengine.accesoSensores.administradores.AccesoSensor;
import backgroundengine.accesoSensores.alarmas.AlarmaBase;
import backgroundengine.accesoSensores.dutycyclers.DutyCyclerBase;
import backgroundengine.accesoSensores.dutycyclers.DutyCyclerSensorInercial;
import backgroundengine.accesoSensores.dutycyclers.DutyCyclerSensorUbicacion;
import backgroundengine.servicios.calendarizables.CalendarizableBase;
import backgroundengine.servicios.calendarizables.CalendarizableInercial;
import backgroundengine.servicios.calendarizables.CalendarizableUbicacion;
import tamps.cinvestav.s0lver.testingserverfromsmartphone.app.R;

import java.util.Hashtable;

public class ServicioCentral extends Service {
    private int NOTIFICATION_ID = 4645875;

    private final IBinder binder = new ElBinder();
    private Hashtable<String, CalendarizableBase> calendarizables;

    @Override
    public void onCreate() {
        super.onCreate();
        this.calendarizables = new Hashtable<String, CalendarizableBase>();
        Log.d(getSimpleName(), "onCreate");
    }

    private void mostrarNotificacionServicio() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Notification note = new Notification(R.mipmap.ic_launcher, "Servicio Middleware",
                System.currentTimeMillis());
        // Intent i=new Intent(this, FakePlayer.class);
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        note.setLatestEventInfo(this, "Aviso", "Servicio iniciado", pi);
        note.flags |= Notification.FLAG_NO_CLEAR;
        startForeground(NOTIFICATION_ID, note);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(getSimpleName(), "onStartCommand()");
        mostrarNotificacionServicio();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (CalendarizableBase calendarizable : calendarizables.values()) {
            desregistrarDutyCycler(calendarizable.getDutyCyclerBase());
        }
        Log.d(getSimpleName(), "onDestroy()");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(getSimpleName(), "Llamada a onBind()");
        return binder;
    }

    public class ElBinder extends Binder {

        public ServicioCentral getService() {
            return ServicioCentral.this;
        }
    }

    public AccesoSensor registrarDutyCycler(DutyCyclerBase dutyCycler) {
        Intent intentAlarma = new Intent(getApplicationContext(), AlarmaBase.class);
        intentAlarma.putExtra("id", dutyCycler.getIdCalendarizable());

        CalendarizableBase c = mapearDutyCycler(dutyCycler, intentAlarma);

        Log.i(getSimpleName(), "Registrando un dutycycler con id = " + dutyCycler.getIdCalendarizable());
        this.calendarizables.put(dutyCycler.getIdCalendarizable(), c);
        return c;
    }

    public void desregistrarDutyCycler(DutyCyclerBase dutyCycler) {
        CalendarizableBase c = calendarizables.get(dutyCycler.getIdCalendarizable());
        if (c == null) {
            Log.e(getSimpleName(), "No se encuentra un idCalendarizable = " + dutyCycler.getIdCalendarizable());
        }
        else {
            c.detenerLecturas();
            calendarizables.remove(dutyCycler.getIdCalendarizable());
            Log.i(getSimpleName(), "Desregistrando un dutycycler con id =" + dutyCycler.getIdCalendarizable());
        }
    }

    public void alarmaRecibida(String idCalendarizable) {
        CalendarizableBase c = calendarizables.get(idCalendarizable);
        if (c == null) {
            Log.e(getSimpleName(), "No se encuentra un idCalendarizable = " + idCalendarizable);
        }
        else {
            Log.i(getSimpleName(), "Se recibió la alarma del dutycycler con id = " + idCalendarizable);
            c.iniciarLecturas();
        }
    }

    private CalendarizableBase mapearDutyCycler(DutyCyclerBase dutyCycler, Intent intentAlarma) {
        CalendarizableBase c = null;
        if (dutyCycler instanceof DutyCyclerSensorUbicacion) {
            c = new CalendarizableUbicacion(dutyCycler, intentAlarma);
        } else if (dutyCycler instanceof DutyCyclerSensorInercial) {
            c = new CalendarizableInercial(dutyCycler, intentAlarma);
        }else {
            throw new RuntimeException("El tipo del dutycycler no es soportado por el Servicio.");
        }
        return c;
    }

    private String getSimpleName() {
        return this.getClass().getSimpleName();
    }
}
