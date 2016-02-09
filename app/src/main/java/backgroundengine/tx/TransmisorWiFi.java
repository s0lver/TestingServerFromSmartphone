package backgroundengine.tx;

import android.content.Context;
import android.net.ConnectivityManager;

public class TransmisorWiFi extends MedioTransmision {

    public TransmisorWiFi(Context contexto, String targetURL, ListenerTransmision listenerTransmision) {
        super(contexto, targetURL, listenerTransmision);
    }

    @Override
    protected boolean confirmarTipoRed() {
        activeNetwork = administradorConexion.getActiveNetworkInfo();
        return activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
    }

    @Override
    protected void switchTipoRed() {
        administradorConexion = (ConnectivityManager)contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
        administradorConexion.setNetworkPreference(ConnectivityManager.TYPE_WIFI);
    }
}
