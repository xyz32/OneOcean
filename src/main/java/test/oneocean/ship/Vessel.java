package test.oneocean.ship;

import org.json.JSONArray;
import org.json.JSONObject;
import test.oneocean.geo.LocationPoint;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Vessel {
    public static final String VESSEL_NAME = "name";
    public static final String VESSEL_POSITIONS = "positions";
    String name;
    List<LocationPoint> positions = new ArrayList<>();

    public Vessel(JSONObject vesselInfo) {
        this.name = vesselInfo.optString(VESSEL_NAME);

        JSONArray jsonPos = vesselInfo.getJSONArray(VESSEL_POSITIONS);
        for (int i = 0; i < jsonPos.length(); ++i) {
            LocationPoint locationPoint = new LocationPoint(jsonPos.getJSONObject(i));
            positions.add(locationPoint);
        }
    }

    public double getAverageSpeed() {
        //convert distance to km before using it
        return (getDistanceTraveledM() / 1000) / (getTravelTime().getSeconds() / 3600d);
    }

    public Duration getTravelTime() {
        return Duration.between(positions.get(0).dateTime, positions.get(positions.size() - 1).dateTime);
    }

    public double getDistanceTraveledM() {
        double distance = 0;

        for (int i = 1; i < positions.size(); ++i) {
            distance = distance + positions.get(i-1).location.distanceToM(positions.get(i).location);
        }

        return distance;
    }
}
