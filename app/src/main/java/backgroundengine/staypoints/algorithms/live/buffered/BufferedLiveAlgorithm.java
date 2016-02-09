package backgroundengine.staypoints.algorithms.live.buffered;

import android.location.Location;
import backgroundengine.staypoints.StayPoint;
import backgroundengine.staypoints.algorithms.live.LiveAlgorithm;

import java.util.ArrayList;

public abstract class BufferedLiveAlgorithm extends LiveAlgorithm {
    protected ArrayList<Location> list;

    public BufferedLiveAlgorithm(long minTimeThreshold, double distanceThreshold) {
        super(minTimeThreshold, distanceThreshold);
        this.list = new ArrayList<Location>();
    }

    @Override
    public StayPoint processFix(Location fix) {
        list.add(fix);
        int size = list.size();
        if (size == 1) {
            return null;
        }
        else{
            return processLive();
        }
    }

    @Override
    public StayPoint processLastPart() {
        int n = list.size();
        if (n==0) {
            return null;
        }
        StayPoint sp = StayPoint.createStayPoint(list, 0, n - 1);
        list.clear();
        return sp;
    }

    protected void cleanList(Location pj) {
        list = new ArrayList<Location>();
        list.add(pj);
    }
}
