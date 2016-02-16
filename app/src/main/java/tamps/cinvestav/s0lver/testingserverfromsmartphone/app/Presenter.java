package tamps.cinvestav.s0lver.testingserverfromsmartphone.app;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import backgroundengine.accesoSensores.registros.RegistroUbicacion;
import backgroundengine.staypoints.StayPoint;
import backgroundengine.tx.ListenerTransmision;
import backgroundengine.tx.MedioTransmision;
import backgroundengine.tx.SenderUbicacion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class Presenter {
    private MainActivity mainActivity;
    private SenderUbicacion locationSender;
    private final String TARGET_URL = "http://amsterdam.tamps.cinvestav.mx/~rperez/local-poi/uploadFixMontoliou.php";

    private final static String TRAJECTORY_CREATED = "trajOk";
    private final static String STAY_POINT_NOT_FOUND = "-1";

    private final static int ONE_SECOND = 1000;
    private final static int ONE_MINUTE = 60 * ONE_SECOND;

    private final static int LATITUDE = 0;
    private final static int LONGITUDE = 1;
    private final static int ARRIVAL_TIME = 2;
    private final static int DEPARTURE_TIME = 3;
    private final static int AMOUNT_FIXES_INVOLVED = 4;

    public Presenter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        Context context = mainActivity.getApplicationContext();
        this.locationSender = new SenderUbicacion(context, createTransmissionListener(), TARGET_URL, MedioTransmision.TRANSMISOR_WIFI);
    }

    public void createTrajectory(){
        Log.i(this.getClass().getSimpleName(), "Before sending");
        this.locationSender.enviarSolicitudCreacionTrayectoria(10 * ONE_MINUTE, 60 * ONE_MINUTE, 50);
    }

    public void processFix() {

    }

    public void processFixes() {
        ArrayList<RegistroUbicacion> gpsFixes = mainActivity.getGpsFixes();
        for (RegistroUbicacion gpsFix : gpsFixes) {
            locationSender.enviarUbicacion(gpsFix);
        }
        locationSender.enviarSolicitudUltimaParte();
    }

    private ListenerTransmision createTransmissionListener(){
        return new ListenerTransmision() {
            @Override
            public void respuestaObtenida(String response) {
                if (TRAJECTORY_CREATED.equals(response)) {
                    Toast.makeText(mainActivity.getApplicationContext(), "Trajectory created @ server", Toast.LENGTH_SHORT).show();
                } else if (STAY_POINT_NOT_FOUND.equals(response)) {
                    Log.i(this.getClass().getSimpleName(), "Server has not found a stay point");
                } else {
                    try{
                        StayPoint stayPoint = translateResponseToStayPoint(response);
                        mainActivity.updateStayPointsList(stayPoint);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Log.w(this.getClass().getSimpleName(), "I couldn't parse it as a stay point: " + response);
                    }
                }
            }
        };
    }

    private StayPoint translateResponseToStayPoint(String string) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
        String[] slices = string.split(",");
        float latitude = Float.valueOf(slices[LATITUDE]);
        float longitude = Float.valueOf(slices[LONGITUDE]);
        Date arrivalTime = simpleDateFormat.parse(slices[ARRIVAL_TIME]);
        Date departureTime = simpleDateFormat.parse(slices[DEPARTURE_TIME]);
        int fixesInvolved = Integer.valueOf(slices[AMOUNT_FIXES_INVOLVED]);

        return new StayPoint(latitude, longitude, arrivalTime, departureTime, fixesInvolved);
    }
}
