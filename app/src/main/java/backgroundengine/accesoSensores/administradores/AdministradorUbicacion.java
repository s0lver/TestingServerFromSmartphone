package backgroundengine.accesoSensores.administradores;

import android.location.LocationManager;
import android.util.Log;
import backgroundengine.accesoSensores.enums.TipoProveedorUbicacion;
import backgroundengine.accesoSensores.listeners.ListenerHwUbicacion;
import backgroundengine.accesoSensores.registros.Registro;

import java.util.ArrayList;

public class AdministradorUbicacion extends AdministradorBase {
    private LocationManager managerUbicacion;
    private ListenerHwUbicacion listenerHwUbicacion;

    private TipoProveedorUbicacion tipoProveedorUbicacion;
    private boolean lecturaActiva;

    protected AdministradorUbicacion(){
        super();
    }

    @Override
    public void iniciarLecturas() {
        lecturaActiva = true;
        getManagerUbicacion().requestLocationUpdates(getTipoProveedorUbicacion().getTipoInterno(), 0, 0, getListenerHwUbicacion());
        Log.v("Admin", "Lecturas iniciadas");
    }

    @Override
    public void detenerLecturas() {
        if (lecturaActiva) {
            getManagerUbicacion().removeUpdates(getListenerHwUbicacion());
            lecturaActiva = false;
            Log.v("Admin", "Lectura ubicación detenidas");
        } else {
            Log.v("Admin", "No puedo detener lecturas ubicación, no estaba leyendo");
        }
    }

    @Override
    public void lecturaObtenida(Registro registro) {
        Log.v("Admin", "Lectura ubicación obtenida " + registro.toString());
        if (null != listenerCapaSuperior) {
            listenerCapaSuperior.lecturaObtenida(registro);
        }
    }

    @Override
    public void lecturasFinalizadas(ArrayList<Registro> registros) {
        Log.v("Admin", "Lecturas ubicación finalizadas");
        if (null != listenerCapaSuperior) {
            listenerCapaSuperior.lecturasFinalizadas(registros);
        }
    }

    public TipoProveedorUbicacion getTipoProveedorUbicacion() {
        return tipoProveedorUbicacion;
    }

    public void setTipoProveedorUbicacion(TipoProveedorUbicacion tipoProveedorUbicacion) {
        this.tipoProveedorUbicacion = tipoProveedorUbicacion;
    }

    public LocationManager getManagerUbicacion() {
        return managerUbicacion;
    }

    public void setManagerUbicacion(LocationManager managerUbicacion) {
        this.managerUbicacion = managerUbicacion;
    }

    public ListenerHwUbicacion getListenerHwUbicacion() {
        return listenerHwUbicacion;
    }

    public void setListenerHwUbicacion(ListenerHwUbicacion listenerHwUbicacion) {
        this.listenerHwUbicacion = listenerHwUbicacion;
    }
}
