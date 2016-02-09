package backgroundengine.accesoSensores.administradores;

import backgroundengine.accesoSensores.registros.Registro;

import java.util.ArrayList;

public interface LecturasSensor {
    public void lecturaObtenida(Registro registro);

    public void lecturasFinalizadas(ArrayList<Registro> registros);
}
