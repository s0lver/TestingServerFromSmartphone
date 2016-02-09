package backgroundengine.accesoSensores.enums;

import android.hardware.Sensor;

public enum TipoSensor {
    ACELEROMETRO(Sensor.TYPE_ACCELEROMETER, "Acelerómetro"),
    GIROSCOPIO(Sensor.TYPE_GYROSCOPE, "Giroscopio"),
    LUMINOSIDAD(Sensor.TYPE_LIGHT, "Luminosidad"),
    BAROMETRO(Sensor.TYPE_PRESSURE, "Barómetro"),
    TEMPERATURA(Sensor.TYPE_AMBIENT_TEMPERATURE, "Temperatura"),
    PROXIMIDAD(Sensor.TYPE_PROXIMITY, "Proximidad");

    private String descripcion;
    private int tipoInterno;

    TipoSensor(int tipoInterno, String tipoSensor) {
        setTipoInterno(tipoInterno);
        setDescripcion(tipoSensor);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getTipoInterno() {
        return tipoInterno;
    }

    public void setTipoInterno(int tipoInterno) {
        this.tipoInterno = tipoInterno;
    }
}
