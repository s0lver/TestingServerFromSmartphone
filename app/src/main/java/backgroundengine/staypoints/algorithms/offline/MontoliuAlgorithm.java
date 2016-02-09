package backgroundengine.staypoints.algorithms.offline;

import android.location.Location;
import backgroundengine.staypoints.StayPoint;

import java.util.ArrayList;

public class MontoliuAlgorithm extends OfflineAlgorithm {
    private long maxTimeTreshold;

    public MontoliuAlgorithm(ArrayList<Location> gpsFixes, long minTimeTreshold, long maxTimeTreshold, double distanceTreshold) {
        super(gpsFixes, minTimeTreshold, distanceTreshold, false);
        this.maxTimeTreshold = maxTimeTreshold;
    }

    @Override
    public ArrayList<StayPoint> extractStayPoints() {
        ArrayList<StayPoint> output = new ArrayList<StayPoint>();
        Location pi, pj, pjMinus;
        int i = 0, j = 0, n = gpsFixes.size();
        long timespan;
        double distance;
        boolean weAreDone = false;

        while (i<n) {
            if (weAreDone) {
                StayPoint sp = StayPoint.createStayPoint(gpsFixes, i, j);
                output.add(sp);
                break;
            }
            pi = gpsFixes.get(i);
            j = i + 1;
            while (j<n){
                pj = gpsFixes.get(j);

                // Here is the adaptation that Montoliu does
                pjMinus = gpsFixes.get(j - 1);
                timespan = timeDifference(pjMinus, pj);
                if (timespan > maxTimeTreshold) {
                    i = j;
                    break;
                }

                distance = pi.distanceTo(pj); //distance(pi, pj);
                if (distance > distanceTreshold) {
                    timespan = timeDifference(pi, pj);
                    // If the point is NOT within the interval, then we have moved out of the stay point
                    if (timespan > minTimeTreshold) {
                        StayPoint sp = StayPoint.createStayPoint(gpsFixes, i, j);
                        output.add(sp);
                        if (verbose) {
                            System.out.println("New SP generated. Fixes involved: ");
                            for (int k = i; k <= j; k++) {
                                System.out.println(gpsFixes.get(k));
                            }
                        }
                    }
                    i = j;
                    break;
                }
                j++;
                // If this increment finalises the iteration...
                if (j == n) {
                    // The we have to stop it
                    weAreDone = true;
                    j--;
                    break;
                }
            }
        }

        return output;
    }
}
