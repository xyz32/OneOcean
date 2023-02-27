package org.oneocean.ship;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.oneocean.geo.GeoNode;
import org.oneocean.geo.LocationPoint;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.Duration;
import java.util.*;

public class VesselsDb {
    public Map<String, Vessel> vesselsData = new HashMap<>();

    public VesselsDb(String source) {
        try {
            InputStream fileIn = new FileInputStream(source);
            JSONTokener tokener = new JSONTokener(fileIn);
            JSONObject object = new JSONObject(tokener);

            JSONArray vesselsData = object.getJSONArray("vessels");
            for (int i = 0; i < vesselsData.length(); ++i) {
                Vessel vessel = new Vessel(vesselsData.getJSONObject(i));
                this.vesselsData.put(vessel.getName(), vessel);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Alarm> getAlarms() {
        Vessel[] mapValues = vesselsData.values().toArray(new Vessel[0]);
        List<Alarm> result = new ArrayList<>();
        for (int i = 0; i < vesselsData.size()-1; ++i) {
            for (int j = i+1; j < vesselsData.size(); ++j) {
                List<Double[]> intersections = computeIntersection(mapValues[i].getPositions(), mapValues[j].getPositions());
                if (!intersections.isEmpty()) {
                    computeAlarms(intersections, mapValues[i], mapValues[j], result);
                }
            }
        }
        return result;
    }

    private void computeAlarms(List<Double[]> intersections, Vessel vessel1, Vessel vessel2, List<Alarm> result) {
        for (Double[] intersection: intersections) {
            if (Duration.between(vessel1.getPositions().get(intersection[2].intValue()).dateTime, vessel2.getPositions().get(intersection[2].intValue()).dateTime)
                    .toHours() < 1) {
                result.add(new Alarm(new GeoNode(intersection[0], intersection[1]), vessel1, vessel2, vessel1.getPositions().get(intersection[2].intValue()).dateTime));
            }
        }
    }

    private List<Double[]> computeIntersection(List<LocationPoint> track1, List<LocationPoint> track2) {
        List<Double[]> result = new ArrayList<>();
        for (int i = 0; i < track1.size() - 1; ++i) {
            for (int j = 0; j < track2.size() - 1; ++j) {
                GeoNode track1Point1 = track1.get(i).location;
                GeoNode track1Point2 = track1.get(i + 1).location;

                GeoNode track2Point1 = track2.get(j).location;
                GeoNode track2Point2 = track2.get(j + 1).location;

                Double[] intersect = getSegSegIntersection(track1Point1.xKm, track1Point1.yKm,
                        track1Point2.xKm, track1Point2.yKm,
                        track2Point1.xKm, track2Point1.yKm,
                        track2Point2.xKm, track2Point2.yKm);

                if (intersect != null) {
                    result.add(new Double[]{intersect[0], intersect[1], (double) i, (double) j}); //get the segments IDs as well to make it easier to calculate the timestamp.
                }
            }
        }

        return result;
    }

    private Double[] getSegSegIntersection(double x1, double y1,
                                           double x2, double y2,
                                           double x3, double y3,
                                           double x4, double y4) {
        double bx = x2 - x1;
        double by = y2 - y1;
        double dx = x4 - x3;
        double dy = y4 - y3;

        double bDotDPerp = bx * dy - by * dx;

        if (bDotDPerp == 0) {
            return null;
        }

        double cx = x3 - x1;
        double cy = y3 - y1;

        double t = (cx * dy - cy * dx) / bDotDPerp;

        if (t < 0 || t > 1) {
            return null;
        }

        double u = (cx * by - cy * bx) / bDotDPerp;

        if (u < 0 || u > 1) {
            return null;
        }

        return new Double[] {x1 + t * bx, y1 + t * by};
    }
}
