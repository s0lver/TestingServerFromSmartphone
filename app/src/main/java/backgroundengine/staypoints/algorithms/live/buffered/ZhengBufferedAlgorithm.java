package backgroundengine.staypoints.algorithms.live.buffered;

import android.location.Location;
import backgroundengine.staypoints.StayPoint;

public class ZhengBufferedAlgorithm extends BufferedLiveAlgorithm {

    public ZhengBufferedAlgorithm(long minTimeThreshold, double distanceThreshold) {
        super(minTimeThreshold,distanceThreshold);
    }

    @Override
    protected StayPoint processLive() {
        int n = list.size();

        Location pi = list.get(0);
        Location pj = list.get(n - 1);

        double distance = pi.distanceTo(pj);

        if (distance > distanceThreshold) {
            long timespan = timeDifference(pi, pj);

            if (timespan > minTimeThreshold) {
                StayPoint sp = StayPoint.createStayPoint(list, 0, n - 1);
                cleanList(pj);
                return sp;
            }
            cleanList(pj);
        }

        return null;
    }
}
