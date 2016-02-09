package backgroundengine.bateria;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class VerificadorEstatusBateria {
    private Context contexto;

    public VerificadorEstatusBateria(Context contexto) {
        this.contexto = contexto;
    }

    public static final String STRING_BATT_LEVEL = "level";
    public static final String STRING_BATT_SCALE = "scale";
    public static final String STRING_BATT_PRESENT = "present";
    public static final String STRING_BATT_TECH = "technology";
    public static final String STRING_BATT_VOLTAGE = "voltage";
    public static final String STRING_BATT_STATUS = "status";
    public static final String STRING_BATT_PLUGGED = "plugged";
    public static final String STRING_BATT_HEALTH = "health";
    public static final String STRING_BATT_TEMPERATURE = "temperature";

    public EstatusBateria obtenerEstadoBateria() {
        Intent intentBateria = contexto.registerReceiver(null,
                new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int nivelRaw = intentBateria.getIntExtra(STRING_BATT_LEVEL, -1);
        double escala = intentBateria.getIntExtra(STRING_BATT_SCALE, -1);
        double nivel = -1;
        if (nivelRaw >= 0 && escala > 0) {
            nivel = nivelRaw / escala;
        }

        boolean estaPresente = intentBateria.getBooleanExtra(STRING_BATT_PRESENT, false);
        String tecnologia = intentBateria.getStringExtra(STRING_BATT_TECH);
        int voltaje = intentBateria.getIntExtra(STRING_BATT_VOLTAGE, 0);
        int estatus = intentBateria.getIntExtra(STRING_BATT_STATUS, 0);
        int conectadaA = intentBateria.getIntExtra(STRING_BATT_PLUGGED, -1);
        int salud = intentBateria.getIntExtra(STRING_BATT_HEALTH, 0);
        int temperatura = intentBateria.getIntExtra(STRING_BATT_TEMPERATURE, 0);

        EstatusBateria estatusBateria = new
                EstatusBateria(nivel, escala, estaPresente, tecnologia, voltaje, estatus, conectadaA, salud, temperatura);

        return estatusBateria;
    }
}
