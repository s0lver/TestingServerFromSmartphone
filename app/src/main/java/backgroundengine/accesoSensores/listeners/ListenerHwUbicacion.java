package backgroundengine.accesoSensores.listeners;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import backgroundengine.accesoSensores.administradores.AdministradorUbicacion;
import backgroundengine.accesoSensores.registros.Registro;

public class ListenerHwUbicacion implements LocationListener {
    private AdministradorUbicacion administrador;
    public ListenerHwUbicacion(AdministradorUbicacion administrador) {
        this.administrador = administrador;
    }

    @Override
    public void onLocationChanged(Location location) {
        Registro registro = administrador
                .getTraductor().traducir(location);

        administrador.lecturaObtenida(registro);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
