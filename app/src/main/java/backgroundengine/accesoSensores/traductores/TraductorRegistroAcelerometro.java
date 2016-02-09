package backgroundengine.accesoSensores.traductores;

import android.hardware.SensorEvent;
import backgroundengine.accesoSensores.registros.Registro;
import backgroundengine.accesoSensores.registros.RegistroAcelerometro;

import java.util.Date;

public final class TraductorRegistroAcelerometro implements TraductorSensor {

    @Override
    public Registro traducir(SensorEvent evento) {
        Registro registro = new RegistroAcelerometro(
                evento.values[0],
                evento.values[1],
                evento.values[2],
                new Date());

        return registro;
    }

}
