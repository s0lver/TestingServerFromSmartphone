package backgroundengine.accesoSensores.enums;

import android.location.LocationManager;

public enum TipoProveedorUbicacion {
    WIFI(LocationManager.NETWORK_PROVIDER, "Redes inalámbricas"),
    GPS(LocationManager.GPS_PROVIDER, "GPS");

    private String descripcion;
    private String tipoInterno;

    TipoProveedorUbicacion(String tipoInterno, String descripcion) {
        setTipoInterno(tipoInterno);
        setDescripcion(descripcion);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoInterno() {
        return tipoInterno;
    }

    public void setTipoInterno(String tipoInterno) {
        this.tipoInterno = tipoInterno;
    }
}
