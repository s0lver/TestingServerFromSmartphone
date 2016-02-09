package backgroundengine.accesoSensores.traductores;

import android.hardware.SensorEvent;
import backgroundengine.accesoSensores.registros.Registro;
import backgroundengine.accesoSensores.registros.RegistroLuminosidad;

import java.util.Date;

public final class TraductorRegistroLuminosidad implements TraductorSensor {

    @Override
    public Registro traducir(SensorEvent evento) {
        Registro registro = new RegistroLuminosidad(
                evento.values[0],
                new Date());

        return registro;
    }
}
