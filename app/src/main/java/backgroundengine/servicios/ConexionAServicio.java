package backgroundengine.servicios;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

class ConexionAServicio implements ServiceConnection {

    private ServicioCentral miServicio = null;

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        ServicioCentral.ElBinder b = (ServicioCentral.ElBinder) iBinder;
        miServicio = b.getService();

        Log.d("ConexionAServicio", "Conectado al servicio");
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        this.miServicio = null;
        Log.d("ConexionAServicio", "Desconectado del servicio");
    }

    public ServicioCentral obtenerConexionAServicio() {
        if (miServicio == null) {
            throw new RuntimeException("NULL, la conexión al Servicio no ha sido realizada");
        }
        return this.miServicio;
    }
}
