package backgroundengine.tx;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class MedioTransmision {
    protected final String USER_AGENT = "Mozilla/5.0";
    public static final String NO_CONECTADO = "NO_CONECTADO";
    public static final String RED_NO_COMPATIBLE = "RED_NO_COMPATIBLE";
    public static final int TRANSMISOR_WIFI = 0;
    public static final int TRANSMISOR_MOBILE = 1;


    protected URL targetURL;
    protected Context contexto;
    protected ConnectivityManager administradorConexion;
    protected NetworkInfo activeNetwork;

    private ListenerTransmision listenerTransmision;

    protected MedioTransmision(Context contexto, String targetURL, ListenerTransmision listenerTransmision) {
        this.contexto = contexto;
        this.listenerTransmision = listenerTransmision;
        try {
            this.targetURL = new URL(targetURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void transmit(ArrayList<NameValuePair> parametros) {
        NameValuePair[] arregloParametros = parametros.toArray(new NameValuePair[parametros.size()]);
        new EnvioAsincrono().execute(arregloParametros);
    }

    protected abstract boolean confirmarTipoRed();

    protected abstract void switchTipoRed();

    protected String obtenerRespuesta(HttpResponse respuesta) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(respuesta.getEntity().getContent()));

        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        return sb.toString();
    }

    class EnvioAsincrono extends AsyncTask<NameValuePair, Void, String> {

        @Override
        protected String doInBackground(NameValuePair... arregloParametros) {
            Log.i(this.getClass().getSimpleName(), "before switching");
            switchTipoRed();
            Log.i(this.getClass().getSimpleName(), "after switching");
            ArrayList<NameValuePair> parametros =
                    new ArrayList<NameValuePair>(Arrays.asList(arregloParametros));
            HttpClient clienteHttp = new DefaultHttpClient();
            HttpPost post = new HttpPost(String.valueOf(targetURL));
            post.setHeader("User-Agent", USER_AGENT);
            Log.i(this.getClass().getSimpleName(), "I have " + parametros.size() + " parameters");
            try {
                post.setEntity(new UrlEncodedFormEntity(parametros));
                if (confirmarTipoRed()) {
                    Log.i(this.getClass().getSimpleName(), "Network type confirmed");
                    if (activeNetwork.isConnected()) {
                        Log.i(this.getClass().getSimpleName(), "Network is connected");
                        HttpResponse respuestaHttp = clienteHttp.execute(post);
                        int statusRespuesta = respuestaHttp.getStatusLine().getStatusCode();
                        Log.i(this.getClass().getSimpleName(), "Status respuesta: " + statusRespuesta);
                        String respuesta = obtenerRespuesta(respuestaHttp);
                        Log.i(this.getClass().getSimpleName(), "Respuesta: " + respuesta);
                        return respuesta;
                    } else {
                        Log.w(this.getClass().getSimpleName(), "Interfaz de red no conectada");
                        return NO_CONECTADO;
                    }
                } else {
                    Log.w(this.getClass().getSimpleName(), "No existen interfaces de red disponibles");
                    return RED_NO_COMPATIBLE;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                throw new RuntimeException("No se pudo realizar el encoding", e);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Ocurrió un error al realizar el POST");
            }
        }

        @Override
        protected void onPostExecute(String s) {
            listenerTransmision.respuestaObtenida(s);
        }
    }
}
