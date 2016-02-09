package backgroundengine.accesoSensores.registros;

import java.util.Date;

public abstract class Registro implements Traducible {
    protected Date timestamp;

    public Registro(Date timestamp) {
        this.setTimestamp(timestamp);
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
