package backgroundengine.accesoSensores.alarmas;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import backgroundengine.servicios.ServicioCentral;

public class AlarmaBase extends BroadcastReceiver {
    public AlarmaBase(){}

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(this.getClass().getSimpleName(), "onReceive()");
        String idCalendarizable = intent.getStringExtra("id");

        Intent i = new Intent(context, ServicioCentral.class);
        ServicioCentral.ElBinder binder = (ServicioCentral.ElBinder) peekService(context, i);

        if (binder == null) {
            Log.e(this.getClass().getSimpleName(), "No se pudo enlazar con el Servicio");
        }else {
            ServicioCentral servicioCentral = binder.getService();
            servicioCentral.alarmaRecibida(idCalendarizable);
        }
    }
}
