package backgroundengine.accesoSensores.administradores;

import android.content.Context;
import backgroundengine.accesoSensores.traductores.Traductor;

public abstract class AdministradorBase implements AccesoSensor, LecturasSensor {
    protected Context contexto;
    protected boolean lecturaActiva;
    protected LecturasSensor listenerCapaSuperior;
    private Traductor traductor;

    public Context getContexto() {
        return contexto;
    }

    public LecturasSensor getListenerCapaSuperior() {
        return listenerCapaSuperior;
    }

    public void setListenerCapaSuperior(LecturasSensor listenerCapaSuperior) {
        this.listenerCapaSuperior = listenerCapaSuperior;
    }

    public Traductor getTraductor() {
        return traductor;
    }

    public void setTraductor(Traductor traductor) {
        this.traductor = traductor;
    }
}
