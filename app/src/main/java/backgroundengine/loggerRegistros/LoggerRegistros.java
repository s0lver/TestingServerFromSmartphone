package backgroundengine.loggerRegistros;

import android.os.Environment;
import backgroundengine.accesoSensores.registros.Registro;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class LoggerRegistros {
    private String pathArchivo;

    public LoggerRegistros(String nombreDirectorio, String nombreArchivo) {
        String pathSeparator = "/";
        String rutaAlmacenamiento = new StringBuilder()
                .append(Environment.getExternalStorageDirectory().getAbsolutePath())
                .append(pathSeparator)
                .append(nombreDirectorio)
                .append(pathSeparator)
                .toString();

        File jerarquiaDirectorios = new File(rutaAlmacenamiento);
        if (!jerarquiaDirectorios.exists()) {
            jerarquiaDirectorios.mkdirs();
        }

        this.pathArchivo = new StringBuilder(rutaAlmacenamiento)
                .append(pathSeparator)
                .append(nombreArchivo)
                .toString();
    }

    public void escribirRegistro(Registro registro){
        try {
            FileWriter archivoSalida = new FileWriter(pathArchivo, true);
            PrintWriter printerArchivo = new PrintWriter(archivoSalida);
            printerArchivo.println(registro.toCSV());
            printerArchivo.flush();
            printerArchivo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void escribirRegistros(ArrayList<Registro> registros) {
        try {
            FileWriter archivoSalida = new FileWriter(pathArchivo, true);
            PrintWriter printerArchivo = new PrintWriter(archivoSalida);
            for (Registro registro : registros){
                printerArchivo.println(registro.toCSV());
            }
            printerArchivo.flush();
            printerArchivo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void escribirCadena(String cadena){
        FileWriter archivoSalida = null;
        try {
            archivoSalida = new FileWriter(pathArchivo, true);
            PrintWriter printerArchivo = new PrintWriter(archivoSalida);
            printerArchivo.println(cadena);
            printerArchivo.flush();
            printerArchivo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
