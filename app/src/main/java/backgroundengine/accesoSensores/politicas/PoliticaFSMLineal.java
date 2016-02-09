package backgroundengine.accesoSensores.politicas;

import android.location.Location;
import android.util.Log;
import backgroundengine.accesoSensores.registros.Registro;
import backgroundengine.accesoSensores.registros.RegistroUbicacion;

import java.util.ArrayList;

public class PoliticaFSMLineal implements IPolitica {
    private float umbralMovimiento;
    private long[] arregloEstados;
    private int estadoActual;
    private int maxEstados;

    public PoliticaFSMLineal(float umbralMovimiento, long[] arregloEstados) {
        this.umbralMovimiento = umbralMovimiento;
        this.arregloEstados = arregloEstados;
        this.maxEstados = arregloEstados.length;
        this.estadoActual = 0;
    }

    @Override
    public long obtenerMsSiguienteLectura(ArrayList<Registro> listaLecturas, long ultimoIntervaloUtilizado) {
        int nuevoEstado = estadoActual;
        int size = listaLecturas.size();
        if (size == 1) {
            nuevoEstado = 0;
        } else {
            RegistroUbicacion reciente = (RegistroUbicacion) listaLecturas.get(size - 1);
            RegistroUbicacion anterior = (RegistroUbicacion) listaLecturas.get(size - 2);

            if (anterior.isLecturaObtenida()) {
                // Normal policy behavior
                if (reciente.isLecturaObtenida()) {
                    Location ultimo = reciente.construirLocation();
                    Location penultimo = anterior.construirLocation();

                    float distancia = ultimo.distanceTo(penultimo);
                    Log.i(this.getClass().getSimpleName(), "Distancia entre fixes " + distancia);
                    if (distancia > umbralMovimiento) {
                        nuevoEstado = 0;
                        Log.i(this.getClass().getSimpleName(), "Regresando a estado 0 " + arregloEstados[nuevoEstado]);
                    } else {
                        nuevoEstado = getNuevoEstado();
                    }
                }
                //else{ If the newer reading is not known, We keep the current reading frequency }
            } else {
                if (reciente.isLecturaObtenida()) {
                    nuevoEstado = 0;
                } else {
                    Log.i(this.getClass().getSimpleName(), "Los dos GPS fixes son desconocidos");
                    nuevoEstado = getNuevoEstado();
                }
            }
        }
        estadoActual = nuevoEstado;
        return arregloEstados[estadoActual];
    }

    private int getNuevoEstado() {
        int nuevoEstado;
        nuevoEstado = estadoActual + 1;
        Log.i(this.getClass().getSimpleName(), "Siguiente estado " + nuevoEstado);
        if (nuevoEstado >= maxEstados) {
            nuevoEstado = maxEstados - 1;
            Log.i(this.getClass().getSimpleName(), "Acotando a último estado " + nuevoEstado + " = " + arregloEstados[nuevoEstado]);
        }
        return nuevoEstado;
    }
}
