package backgroundengine.accesoSensores.dutycyclers;

import android.content.Context;
import backgroundengine.accesoSensores.administradores.LecturasSensor;
import backgroundengine.accesoSensores.politicas.IPolitica;

public abstract class DutyCyclerBase{
    protected String idCalendarizable;

    protected Context contexto;
    protected IPolitica politica;
    protected LecturasSensor listenerCapaSuperior;

    protected DutyCyclerBase(Context contexto, IPolitica politica, LecturasSensor listenerCapaSuperior,
                             String idCalendarizable) {
        this.contexto = contexto;
        this.politica = politica;
        this.listenerCapaSuperior = listenerCapaSuperior;
        this.idCalendarizable = idCalendarizable;
    }

    public Context getContexto() {
        return contexto;
    }

    public IPolitica getPolitica() {
        return politica;
    }

    public LecturasSensor getListenerCapaSuperior() {
        return listenerCapaSuperior;
    }

    public String getIdCalendarizable() {
        return idCalendarizable;
    }
}
