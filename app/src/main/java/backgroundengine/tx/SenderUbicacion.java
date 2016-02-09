package backgroundengine.tx;

import android.content.Context;
import android.util.Log;
import backgroundengine.accesoSensores.registros.RegistroUbicacion;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class SenderUbicacion {
    private MedioTransmision medioTransmision;

    public SenderUbicacion(Context contexto, ListenerTransmision listenerTransmision, String UrlDestino, int tipoTransmisor) {
        instanciarMedioTransmision(contexto, UrlDestino, tipoTransmisor, listenerTransmision);
    }

    public void enviarUbicacion(RegistroUbicacion registroUbicacion) {
        ArrayList<NameValuePair> parametros = crearParametrosPeticion(registroUbicacion);
        medioTransmision.transmit(parametros);
    }

    public void enviarSolicitudUltimaParte(){
        ArrayList<NameValuePair> parametros = new ArrayList<NameValuePair>();
        parametros.add(new BasicNameValuePair("lastPart", "1"));
        medioTransmision.transmit(parametros);
    }

    public void enviarSolicitudCreacionTrayectoria(long minTime, long maxTime, double minDistance){
        ArrayList<NameValuePair> parametros = new ArrayList<NameValuePair>();
        parametros.add(new BasicNameValuePair("createTrajectory", "1"));
        parametros.add(new BasicNameValuePair("minTime", String.valueOf(minTime)));
        parametros.add(new BasicNameValuePair("maxTime", String.valueOf(maxTime)));
        parametros.add(new BasicNameValuePair("minDistance", String.valueOf(minDistance)));
        medioTransmision.transmit(parametros);
    }

    private ArrayList<NameValuePair> crearParametrosPeticion(RegistroUbicacion registroUbicacion) {
        ArrayList<NameValuePair> parametros = new ArrayList<NameValuePair>();
        parametros.add(new BasicNameValuePair("latitude", String.valueOf(registroUbicacion.getLatitud())));
        parametros.add(new BasicNameValuePair("longitude", String.valueOf(registroUbicacion.getLongitud())));
        parametros.add(new BasicNameValuePair("altitude", String.valueOf(registroUbicacion.getLongitud())));
        parametros.add(new BasicNameValuePair(("timestamp"), String.valueOf(registroUbicacion.getTimestamp())));
        Log.i("SenderUbicacion", String.valueOf(registroUbicacion.getTimestamp()));
        return parametros;
    }

    private void instanciarMedioTransmision(Context contexto, String urlDestino, int tipoTransmisor, ListenerTransmision
                                            listenerTransmision) {
        switch (tipoTransmisor){
            case MedioTransmision.TRANSMISOR_WIFI:
                this.medioTransmision = new TransmisorWiFi(contexto, urlDestino, listenerTransmision);
                break;
            case MedioTransmision.TRANSMISOR_MOBILE:
                this.medioTransmision = new TransmisorMobile(contexto, urlDestino, listenerTransmision);
                break;
            default:
                throw new RuntimeException("Medio de transmisión no reconocido");
        }
    }
}
