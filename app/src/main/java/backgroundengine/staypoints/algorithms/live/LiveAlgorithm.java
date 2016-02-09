package backgroundengine.staypoints.algorithms.live;

import android.location.Location;
import backgroundengine.staypoints.StayPoint;

public abstract class LiveAlgorithm {

    protected double distanceThreshold;

    protected long minTimeThreshold;
    public LiveAlgorithm(long minTimeThreshold, double distanceThreshold) {
        this.minTimeThreshold = minTimeThreshold;
        this.distanceThreshold = distanceThreshold;
    }

    public abstract StayPoint processFix(Location fix);

    protected abstract StayPoint processLive();

    public abstract StayPoint processLastPart();

    protected static long timeDifference(Location p, Location q) {
        if (p == null || q == null) {
            throw new RuntimeException("No se puede calcular la diferencia de tiempos, una de las ubicaciones en null");
        }
        long timespan = q.getTime() - p.getTime();
        return timespan;
    }

    public double getDistanceThreshold() {
        return distanceThreshold;
    }

    public long getMinTimeThreshold() {
        return minTimeThreshold;
    }
}
