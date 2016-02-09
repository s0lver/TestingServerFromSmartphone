package backgroundengine.tx;

import android.content.Context;
import android.net.ConnectivityManager;

public class TransmisorMobile extends MedioTransmision {
    public TransmisorMobile(Context contexto, String targetURL, ListenerTransmision listenerTransmision) {
        super(contexto, targetURL, listenerTransmision);
    }

    @Override
    protected boolean confirmarTipoRed() {
        activeNetwork = administradorConexion.getActiveNetworkInfo();
        return activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    @Override
    protected void switchTipoRed() {
        administradorConexion = (ConnectivityManager)contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
        administradorConexion.setNetworkPreference(ConnectivityManager.TYPE_MOBILE);
    }
}
