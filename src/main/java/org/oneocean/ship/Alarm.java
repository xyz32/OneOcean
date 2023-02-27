package org.oneocean.ship;

import org.json.JSONArray;
import org.json.JSONObject;
import org.oneocean.geo.GeoNode;

import java.time.Instant;

public class Alarm {
    public GeoNode intersection;
    public String[] vessels;
    public Instant timestamp;

    public Alarm(GeoNode intersection, Vessel vessel1, Vessel vessel2, Instant timestamp) {
        this.intersection = intersection;
        this.vessels = new String[] {vessel1.getName(), vessel2.getName()};
        this.timestamp = timestamp;
    }

    public JSONObject toJson() {
        JSONObject jsonAlarm = new JSONObject();
        JSONObject gpsLoc = new JSONObject();
        gpsLoc.put("xKm", this.intersection.xKm);
        gpsLoc.put("yKm", this.intersection.yKm);
        jsonAlarm.put("location", gpsLoc);
        jsonAlarm.put("vessels", new JSONArray(this.vessels));
        jsonAlarm.put("timestamp", this.timestamp.toString());

        return jsonAlarm;
    }
}
