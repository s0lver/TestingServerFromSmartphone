package backgroundengine.accesoSensores.registros;

import java.util.Date;

public final class RegistroLuminosidad extends Registro {
    private float luminosidad;

    public RegistroLuminosidad(float luminosidad, Date timestamp) {
        super(timestamp);
        this.setLuminosidad(luminosidad);
    }

    public float getLuminosidad() {
        return luminosidad;
    }

    public void setLuminosidad(float luminosidad) {
        this.luminosidad = luminosidad;
    }

    @Override
    public String toCSV() {
        return new StringBuilder()
                .append(getLuminosidad())
                .append(',')
                .append(getTimestamp())
                .toString();
    }

    public String toString() {
        return String.format("Luminosidad: %f, timestamp: %s", getLuminosidad(), timestamp.toString());
    }
}
