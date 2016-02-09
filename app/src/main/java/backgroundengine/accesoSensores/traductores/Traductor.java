package backgroundengine.accesoSensores.traductores;

import backgroundengine.accesoSensores.registros.Registro;

public interface Traductor<T> {
    public Registro traducir(T elemento);
}
