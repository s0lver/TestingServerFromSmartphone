package backgroundengine.bateria;

import android.os.BatteryManager;
import backgroundengine.accesoSensores.registros.Traducible;

public class EstatusBateria implements Traducible {
    private double nivel;
    private double escala;
    private boolean isBateriaPresente;
    private String tecnologia;
    private int voltaje;
    private int estatus;
    private int conectadaA;
    private int salud;
    private int temperatura;

    public EstatusBateria(double nivel, double escala, boolean isBateriaPresente, String tecnologia, int voltaje, int estatus, int conectadaA, int salud, int temperatura) {
        this.nivel = nivel;
        this.escala = escala;
        this.isBateriaPresente = isBateriaPresente;
        this.tecnologia = tecnologia;
        this.voltaje = voltaje;
        this.estatus = estatus;
        this.conectadaA = conectadaA;
        this.salud = salud;
        this.temperatura = temperatura;
    }

    public double getNivel() {
        return nivel;
    }

    public double getEscala() {
        return escala;
    }

    public boolean isBateriaPresente() {
        return isBateriaPresente;
    }

    public String getIsBateriaPresenteComoString() {
        return isBateriaPresente() ? "Yes" : "No";
    }

    public String getTecnologia() {
        return tecnologia;
    }

    public int getVoltaje() {
        return voltaje;
    }

    public int getEstatus() {
        return estatus;
    }

    public String getEstatusComoString() {
        switch (getEstatus()) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                return "charging";
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                return "discharging";
            case BatteryManager.BATTERY_STATUS_FULL:
                return "full";
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                return "not-charging";
            //case BatteryManager.BATTERY_STATUS_UNKNOWN:
            default:
                return "unknown";
        }
    }

    public int getConectadaA() {
        return conectadaA;
    }

    public String getConectadaAComoString() {
        switch (getConectadaA()) {
            case BatteryManager.BATTERY_PLUGGED_USB:
                return "USB";
            case BatteryManager.BATTERY_PLUGGED_AC:
                return "AC";
            default:
                return "unplugged";
        }
    }

    public int getSalud() {
        return salud;
    }

    public String getSaludComoString() {
        switch (getSalud()) {
            case BatteryManager.BATTERY_HEALTH_DEAD:
                return "dead";
            case BatteryManager.BATTERY_HEALTH_GOOD:
                return "good";
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                return "over-voltage";
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                return "over-heat";
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                return "unspecified-failure";
            //case BatteryManager.BATTERY_HEALTH_UNKNOWN:
            default:
                return "unknown";
        }
    }

    public int getTemperatura() {
        return temperatura;
    }

    @Override
    public String toCSV() {
        StringBuilder sb = new StringBuilder();
        sb.append(getNivel())
                .append(",")
                .append(getVoltaje())
                .append(",")
                .append(getEstatusComoString())
                .append(",")
                .append(getTemperatura())
                .append(",")
                .append(getConectadaAComoString());

        return sb.toString();
    }
}
