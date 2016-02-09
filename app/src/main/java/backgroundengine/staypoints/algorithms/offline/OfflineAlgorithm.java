package backgroundengine.staypoints.algorithms.offline;

import android.location.Location;
import backgroundengine.staypoints.StayPoint;

import java.util.ArrayList;

public abstract class OfflineAlgorithm {
    protected long minTimeTreshold;
    protected double distanceTreshold;
    protected boolean verbose;
    protected ArrayList<Location> gpsFixes;

    public OfflineAlgorithm(ArrayList<Location> gpsFixes, long minTimeTreshold, double distanceTreshold, boolean verbose) {
        this.gpsFixes = gpsFixes;
        this.minTimeTreshold = minTimeTreshold;
        this.distanceTreshold = distanceTreshold;
        this.verbose = verbose;
    }

    public abstract ArrayList<StayPoint> extractStayPoints();

    protected static long timeDifference(Location p, Location q) {
        if (p == null || q == null) {
            throw new RuntimeException("No se puede calcular la diferencia de tiempos, una de las ubicaciones en null");
        }
        long timespan = q.getTime() - p.getTime();
        return timespan;
    }
}
