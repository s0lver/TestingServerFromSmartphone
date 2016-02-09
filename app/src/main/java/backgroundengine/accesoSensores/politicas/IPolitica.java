package backgroundengine.accesoSensores.politicas;

import backgroundengine.accesoSensores.registros.Registro;

import java.util.ArrayList;

public interface IPolitica {
    long obtenerMsSiguienteLectura(ArrayList<Registro> listaLecturas, long ultimoIntervaloUtilizado);
}
