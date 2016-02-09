package backgroundengine.accesoSensores.listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import backgroundengine.accesoSensores.administradores.AdministradorSensor;
import backgroundengine.accesoSensores.registros.Registro;

public class ListenerHwSensor implements SensorEventListener {
    private AdministradorSensor administradorSensor;

    public ListenerHwSensor(AdministradorSensor administradorSensor) {
        this.administradorSensor = administradorSensor;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Registro registro = administradorSensor.getTraductor()
                .traducir(sensorEvent);

        administradorSensor.lecturaObtenida(registro);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
