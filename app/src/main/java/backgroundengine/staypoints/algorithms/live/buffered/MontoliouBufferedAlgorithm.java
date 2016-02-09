package backgroundengine.staypoints.algorithms.live.buffered;

import android.location.Location;
import android.util.Log;
import backgroundengine.staypoints.StayPoint;

public class MontoliouBufferedAlgorithm extends BufferedLiveAlgorithm {

    private long maxTimeThreshold;

    public MontoliouBufferedAlgorithm(long maxTimeThreshold, long minTimeThreshold, double distanceThreshold) {
        super(minTimeThreshold,distanceThreshold);
        this.maxTimeThreshold = maxTimeThreshold;
    }

    @Override
    protected StayPoint processLive() {
        int n = list.size();

        Location pi = list.get(0);
        Location pj = list.get(n - 1);
        Location pjMinus = list.get(n - 2);

        long timespan = timeDifference(pjMinus, pj);
        if (timespan > maxTimeThreshold) {
            cleanList(pj);
            return null;
        }

        double distance = pi.distanceTo(pj);

        if (distance > distanceThreshold) {
            timespan = timeDifference(pi, pj);

            if (timespan > minTimeThreshold) {
                Log.i(this.getClass().getSimpleName(), "Going to create a staypoint with a list of ");
                StayPoint sp = StayPoint.createStayPoint(list, 0, n - 1);
                cleanList(pj);
                return sp;
            }
            cleanList(pj);
        }

        return null;
    }

    public long getMaxTimeThreshold() {
        return maxTimeThreshold;
    }
}
