package backgroundengine.accesoSensores.politicas;

import android.util.Log;
import backgroundengine.accesoSensores.registros.Registro;

import java.util.ArrayList;

public class PoliticaFija implements IPolitica {
    private long frecuenciaLecturas;

    public PoliticaFija(long frecuenciaLecturas) {
        this.frecuenciaLecturas = frecuenciaLecturas;
    }

    @Override
    public long obtenerMsSiguienteLectura(ArrayList<Registro> listaLecturas, long ultimoIntervaloUtilizado) {
        Log.i(this.getClass().getSimpleName(), "Frecuencia fija " + frecuenciaLecturas);
        return frecuenciaLecturas;
    }
}
