package backgroundengine.accesoSensores.administradores;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;
import backgroundengine.accesoSensores.enums.TipoSensor;
import backgroundengine.accesoSensores.listeners.ListenerHwSensor;
import backgroundengine.accesoSensores.registros.Registro;

import java.util.ArrayList;

public class AdministradorSensor extends AdministradorBase {
    private ListenerHwSensor listenerHwSensor;
    private SensorManager sensorManager;
    private Sensor sensor;

    private TipoSensor tipoSensor;
    private int frecuenciaMuestreo;

    protected AdministradorSensor(){
        super();
    }

    @Override
    public void iniciarLecturas() {
        lecturaActiva = true;
        sensorManager.registerListener(listenerHwSensor, sensor, frecuenciaMuestreo);
        Log.v(this.getClass().getSimpleName(), "Lecturas iniciadas");
    }

    @Override
    public void detenerLecturas(){
        if (lecturaActiva){
            sensorManager.unregisterListener(listenerHwSensor);
            lecturaActiva = false;
            Log.v(this.getClass().getSimpleName(), "Lecturas detenidas");
        }
        else {
            Log.v(this.getClass().getSimpleName(), "No puedo detener, no estaba leyendo");
        }
    }

    @Override
    public void lecturaObtenida(Registro registro) {
        if (listenerCapaSuperior == null) {
            advertirListenerSuperiorNulo();
        } else {
            listenerCapaSuperior.lecturaObtenida(registro);
        }
        Log.v(this.getClass().getSimpleName(), "Lectura obtenida " + registro.toString());
    }

    @Override
    public void lecturasFinalizadas(ArrayList<Registro> registros) {
        if (listenerCapaSuperior == null) {
            advertirListenerSuperiorNulo();
        }else {
            listenerCapaSuperior.lecturasFinalizadas(registros);
        }
        Log.v(this.getClass().getSimpleName(), "Lecturas finalizadas");
    }

    private void advertirListenerSuperiorNulo() {
        Log.w(this.getClass().getSimpleName(), "Cuidado, el listener superior es nulo");
    }

    public TipoSensor getTipoSensor() {
        return tipoSensor;
    }

    public void setTipoSensor(TipoSensor tipoSensor) {
        this.tipoSensor = tipoSensor;
    }

    public int getFrecuenciaMuestreo() {
        return frecuenciaMuestreo;
    }

    public void setFrecuenciaMuestreo(int frecuenciaMuestreo) {
        this.frecuenciaMuestreo = frecuenciaMuestreo;
    }

    public SensorManager getSensorManager() {
        return sensorManager;
    }

    public void setSensorManager(SensorManager sensorManager) {
        this.sensorManager = sensorManager;
    }

    public ListenerHwSensor getListenerHwSensor() {
        return listenerHwSensor;
    }

    public void setListenerHwSensor(ListenerHwSensor listenerHwSensor) {
        this.listenerHwSensor = listenerHwSensor;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
