package backgroundengine.accesoSensores.registros;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class RegistroUbicacion extends Registro {
    private double latitud;
    private double longitud;
    private double altitud;

    private double precision;

    public double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }

    private double velocidad;

    private boolean lecturaObtenida;

    public RegistroUbicacion(Date timestamp, double latitud, double longitud, double altitud, double precision, double velocidad, boolean lecturaObtenida) {
        super(timestamp);
        this.setLatitud(latitud);
        this.setLongitud(longitud);
        this.setAltitud(altitud);
        this.setPrecision(precision);
        this.setVelocidad(velocidad);
        this.setLecturaObtenida(lecturaObtenida);
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getAltitud() {
        return altitud;
    }

    public void setAltitud(double altitud) {
        this.altitud = altitud;
    }

    public double getPrecision() {
        return precision;
    }

    public void setPrecision(double precision) {
        this.precision = precision;
    }

    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
        return String.format(
                "Lat: %f, Long: %f, Prec: %f, Alt: %f, Vel: %f, ts: %s",
                getLatitud(),
                getLongitud(),
                getPrecision(),
                getAltitud(),
                getVelocidad(),
                sdf.format(getTimestamp())
        );
    }

    public boolean isLecturaObtenida() {
        return lecturaObtenida;
    }

    public void setLecturaObtenida(boolean lecturaObtenida) {
        this.lecturaObtenida = lecturaObtenida;
    }

    @Override
    public String toCSV() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
        return new StringBuilder()
                .append(isLecturaObtenida() ? "Si" : "No")
                .append(',')
                .append(getLatitud())
                .append(',')
                .append(getLongitud())
                .append(',')
                .append(getAltitud())
                .append(',')
                .append(getPrecision())
                .append(',')
                .append(getVelocidad())
                .append(',')
                .append(sdf.format(getTimestamp()))
                .toString();
    }

    public static RegistroUbicacion crearUbicacionNoDisponible(){
        Date fechaAhora = new Date(System.currentTimeMillis());
        RegistroUbicacion ubicacionFalsa = new RegistroUbicacion(fechaAhora, 0, 0, 0, 0, 0, false);
        return ubicacionFalsa;
    }

    public Location construirLocation() {
        Location fix = new Location("Custom");
        fix.setAccuracy((float) getPrecision());
        fix.setAltitude(0);
        fix.setLatitude(getLatitud());
        fix.setLongitude(getLongitud());
        fix.setTime(getTimestamp().getTime());
        return fix;
    }
}
