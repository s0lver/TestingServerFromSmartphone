package backgroundengine.accesoSensores.politicas;

import android.location.Location;
import android.util.Log;
import backgroundengine.accesoSensores.registros.Registro;
import backgroundengine.accesoSensores.registros.RegistroUbicacion;

import java.util.ArrayList;

public class PoliticaFSMDoblar implements IPolitica {
    private long intervaloMinimo;
    private long intervaloMaximo;
    private long intervaloActual;
    private float umbralMovimiento;

    public PoliticaFSMDoblar(long intervaloMinimo, long intervaloMaximo, float umbralMovimiento) {
        this.intervaloMinimo = intervaloMinimo;
        this.intervaloMaximo = intervaloMaximo;
        this.umbralMovimiento = umbralMovimiento;

        intervaloActual = intervaloMinimo;
    }

    @Override
    public long obtenerMsSiguienteLectura(ArrayList<Registro> listaLecturas, long ultimoIntervaloUtilizado) {
        long nuevoIntervalo = intervaloActual;
        int size = listaLecturas.size();
        if (size == 1) {
            nuevoIntervalo = intervaloMinimo;
        } else {
            RegistroUbicacion reciente = (RegistroUbicacion) listaLecturas.get(size - 1);
            RegistroUbicacion anterior = (RegistroUbicacion) listaLecturas.get(size - 2);

            if (anterior.isLecturaObtenida()){
                // Normal policy behavior
                if (reciente.isLecturaObtenida()){
                    Location ultimo = reciente.construirLocation();
                    Location penultimo = anterior.construirLocation();

                    Log.i(this.getClass().getSimpleName(), "Ultimo " + ultimo.toString());
                    Log.i(this.getClass().getSimpleName(), "Penultimo " + penultimo.toString());
                    float distancia = ultimo.distanceTo(penultimo);
                    Log.i(this.getClass().getSimpleName(), "Distancia entre fixes " + distancia);

                    if (distancia > umbralMovimiento) {
                        nuevoIntervalo = intervaloMinimo;
                        Log.i(this.getClass().getSimpleName(), "Regresando a intervalo inicial " + intervaloMinimo);
                    } else {
                        nuevoIntervalo = getNuevoIntervalo();
                    }
                }
                //else{ If the newer reading is not known, We keep the current reading frequency }
            }else{
                if (reciente.isLecturaObtenida()) {
                    nuevoIntervalo = intervaloMinimo;
                }else{
                    Log.i(this.getClass().getSimpleName(), "Los dos GPS fixes son desconocidos");
                    nuevoIntervalo = getNuevoIntervalo();
                }
            }
        }
        intervaloActual = nuevoIntervalo;
        return nuevoIntervalo;
    }

    private long getNuevoIntervalo() {
        long nuevoIntervalo;
        nuevoIntervalo = intervaloActual * 2;
        Log.i(this.getClass().getSimpleName(), "Doblando " + nuevoIntervalo);
        if (nuevoIntervalo > intervaloMaximo) {
            nuevoIntervalo = intervaloMaximo;
            Log.i(this.getClass().getSimpleName(), "Acotando a intervalo máximo " + nuevoIntervalo);
        }
        return nuevoIntervalo;
    }
}