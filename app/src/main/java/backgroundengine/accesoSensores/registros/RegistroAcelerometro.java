package backgroundengine.accesoSensores.registros;

import java.util.Date;

public final class RegistroAcelerometro extends Registro {
    private float x;
    private float y;
    private float z;
    private Date timestamp;

    public RegistroAcelerometro(float x, float y, float z, Date timestamp) {
        super(timestamp);
        this.setX(x);
        this.setY(y);
        this.setZ(z);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public String toString() {
        return String.format(
                "X: %f, Y: %f, Z: %f, TS: %s",
                this.getX(), this.getY(), this.getZ(), this.getTimestamp().toString());
    }

    @Override
    public String toCSV() {
        return new StringBuilder()
                .append(getX())
                .append(',')
                .append(getY())
                .append(',')
                .append(getZ())
                .append(',')
                .append(getTimestamp())
                .toString();
    }
}
