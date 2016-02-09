package backgroundengine.accesoSensores.traductores;

import android.location.Location;
import backgroundengine.accesoSensores.registros.Registro;
import backgroundengine.accesoSensores.registros.RegistroUbicacion;

import java.util.Date;

public class TraductorRegistroUbicacion implements TraductorUbicacion {

    @Override
    public Registro traducir(Location ubicacion) {
        Registro registroUbicacion = new RegistroUbicacion(
                new Date(),
                ubicacion.getLatitude(),
                ubicacion.getLongitude(),
                ubicacion.getAltitude(),
                ubicacion.getAccuracy(),
                ubicacion.getSpeed(),
                true
        );
        return registroUbicacion;
    }

}
