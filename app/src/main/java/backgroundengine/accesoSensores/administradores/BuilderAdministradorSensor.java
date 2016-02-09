package backgroundengine.accesoSensores.administradores;
import android.content.Context;
import android.hardware.SensorManager;
import backgroundengine.accesoSensores.enums.TipoSensor;
import backgroundengine.accesoSensores.listeners.ListenerHwSensor;
import backgroundengine.accesoSensores.traductores.TraductorSensor;


public final class BuilderAdministradorSensor {
    public static ITipoSensor contexto(Context contexto) {
        return new Builder(contexto);
    }

    public interface ITipoSensor {
        IFrecuencia tipoSensor(TipoSensor tipoSensor);

    }
    public interface IFrecuencia {
        ITraductor frecuencia(int frecuencia);
    }

    public interface ITraductor{
        IListenerSuperior traductor(TraductorSensor traductor);
    }

    public interface IListenerSuperior{
        IBuild listenerSuperior(LecturasSensor lecturasSensor);
    }

    public interface IBuild {
        AdministradorSensor build();
    }

    private static class Builder implements ITipoSensor, IFrecuencia, ITraductor, IListenerSuperior, IBuild {

        private final AdministradorSensor administrador = new AdministradorSensor();

        public Builder(Context contexto) {
            administrador.contexto = contexto;
        }

        @Override
        public IFrecuencia tipoSensor(TipoSensor tipoSensor) {
            administrador.setTipoSensor(tipoSensor);
            return this;
        }

        @Override
        public ITraductor frecuencia(int frecuencia) {
            administrador.setFrecuenciaMuestreo(frecuencia);
            return this;
        }

        @Override
        public IListenerSuperior traductor(TraductorSensor traductor) {
            administrador.setTraductor(traductor);
            return this;
        }

        @Override
        public IBuild listenerSuperior(LecturasSensor lecturasSensor) {
            administrador.setListenerCapaSuperior(lecturasSensor);
            return this;
        }

        @Override
        public AdministradorSensor build() {
            verificarValores();

            administrador.setSensorManager(
                    (SensorManager) administrador.getContexto().getSystemService(Context.SENSOR_SERVICE));

            administrador.setSensor(
                    administrador.getSensorManager().getDefaultSensor(
                            administrador.getTipoSensor().getTipoInterno()));

            administrador.setListenerHwSensor(new ListenerHwSensor(administrador));

            return administrador;
        }

        private void verificarValores() {
            if (administrador.getTipoSensor() == null)
                throw new RuntimeException("El tipo de sensor no ha sido especificado");
            if (administrador.getTraductor() == null) {
                throw new RuntimeException("El traductor no ha sido especificado");
            }
            if (administrador.getFrecuenciaMuestreo()<= 0) {
                throw new RuntimeException(String.format(
                        "La frecuencia de muestreo %d no es válida",
                        administrador.getFrecuenciaMuestreo()));
            }
        }
    }
}
