package backgroundengine.staypoints.algorithms.live.sigma;

import android.location.Location;
import backgroundengine.staypoints.StayPoint;
import backgroundengine.staypoints.algorithms.live.LiveAlgorithm;

import java.util.Date;

public abstract class SigmaLiveAlgorithm extends LiveAlgorithm {
    protected Location pi, pj, pjMinus;
    protected int amountFixes;
    protected double sigmaLatitude;
    protected double sigmaLongitude;
    protected Date arrivalTime, departureTime;

    public SigmaLiveAlgorithm(long minTimeThreshold, double distanceThreshold) {
        super(minTimeThreshold, distanceThreshold);

        this.amountFixes = 0;
        this.sigmaLatitude = 0;
        this.sigmaLongitude = 0;
    }

    public StayPoint processFix(Location fix) {
        includeFix(fix);
        if (amountFixes == 1) {
            pi = fix;
            arrivalTime = new Date(fix.getTime());
            return null;
        }
        else if (amountFixes == 2) {
            pjMinus = pi;
            pj = fix;
        }
        else {
            pjMinus = pj;
            pj = fix;
        }
        return processLive();
    }

    protected void includeFix(Location fix) {
        amountFixes++;
        departureTime = new Date(fix.getTime());
        sigmaLatitude += fix.getLatitude();
        sigmaLongitude += fix.getLongitude();
    }

    /***
     * pj is the new pi
     */
    protected void resetAccumulated() {
        amountFixes = 1;
        sigmaLatitude = pj.getLatitude();
        sigmaLongitude = pj.getLongitude();
        arrivalTime = new Date(pj.getTime());
        departureTime = new Date(pj.getTime());

        pi = pj;
        pj = null;
        pjMinus = null;
    }
}
