package tools;

import backgroundengine.accesoSensores.registros.RegistroUbicacion;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class SmartphoneFixesFileReader{
    private final int LATITUDE = 1;
    private final int LONGITUDE = 2;
    private final int TIMESTAMP = 6;

    protected String path;
    // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);

    public SmartphoneFixesFileReader(String path) {
        this.path = path;
    }

    protected RegistroUbicacion processLine(String line) {
        RegistroUbicacion fix = null;
        String[] slices = line.split(",");
        if (slices[0].equals("Si")) {
            try {
                fix = new RegistroUbicacion(
                        simpleDateFormat.parse(slices[TIMESTAMP]),
                        Double.valueOf(slices[LATITUDE]),
                        Double.valueOf(slices[LONGITUDE]), 0, 0, 0, true);

            } catch (ParseException e) {
                e.printStackTrace();
                throw new RuntimeException("I couldn't parse the date, and I hate checked exceptions");
            }
        }
        return fix;
    }

    public ArrayList<RegistroUbicacion> readFile() {
        ArrayList<RegistroUbicacion> gpsFixes = new ArrayList<RegistroUbicacion>();
        Scanner scanner;

        try {
            scanner = new Scanner(new FileReader(path));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("I couldn't open the file. Additionally I hate checked exceptions");
        }

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            RegistroUbicacion gpsFix = processLine(line);
            if (gpsFix != null)
                gpsFixes.add(gpsFix);
        }

        scanner.close();
        return gpsFixes;
    }
}