package backgroundengine.accesoSensores.administradores;

import android.content.Context;
import android.location.LocationManager;
import backgroundengine.accesoSensores.enums.TipoProveedorUbicacion;
import backgroundengine.accesoSensores.listeners.ListenerHwUbicacion;
import backgroundengine.accesoSensores.traductores.TraductorUbicacion;

public final class BuilderAdministradorUbicacion {
    public static IProveedorUbicacion contexto(Context contexto) {
        return new Builder(contexto);
    }

    public interface IProveedorUbicacion {
        ITraductor proveedorUbicacion(TipoProveedorUbicacion tipoProveedorUbicacion);
    }

    public interface ITraductor{
        IListenerSuperior traductor(TraductorUbicacion traductor);
    }

    public interface IListenerSuperior{
        IBuild listenerSuperior(LecturasSensor lecturasSensor);
    }

    public interface IBuild {
        AdministradorUbicacion build();
    }

    private static class Builder implements IProveedorUbicacion, ITraductor, IListenerSuperior, IBuild {

        private AdministradorUbicacion administrador = new AdministradorUbicacion();

        public Builder(Context contexto) {
            administrador.contexto = contexto;
        }

        @Override
        public ITraductor proveedorUbicacion(TipoProveedorUbicacion tipoProveedor) {
            administrador.setTipoProveedorUbicacion(tipoProveedor);
            return this;
        }

        @Override
        public IListenerSuperior traductor(TraductorUbicacion traductor) {
            administrador.setTraductor(traductor);
            return this;
        }

        @Override
        public IBuild listenerSuperior(LecturasSensor lecturasSensor) {
            administrador.setListenerCapaSuperior(lecturasSensor);
            return this;
        }

        @Override
        public AdministradorUbicacion build() {
            verificarValores();

            administrador.setManagerUbicacion((LocationManager)
                    administrador.getContexto().getSystemService(Context.LOCATION_SERVICE));

            administrador.setListenerHwUbicacion(new ListenerHwUbicacion(administrador));

            return administrador;
        }

        private void verificarValores() {
            if (administrador.getTipoProveedorUbicacion() == null)
                throw new RuntimeException("El tipo de proveedor de ubicación no ha sido especificado");
            if (administrador.getTraductor() == null) {
                throw new RuntimeException("El traductor no ha sido especificado");
            }
        }
    }
}
