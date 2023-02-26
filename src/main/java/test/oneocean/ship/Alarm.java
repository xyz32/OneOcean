package test.oneocean.ship;

import test.oneocean.geo.GeoNode;

import java.time.Instant;

public class Alarm {
    public GeoNode intersection;
    public String[] vessels;
    public Instant timestamp;

    public Alarm(GeoNode intersection, Vessel vessel1, Vessel vessel2, Instant timestamp) {
        this.intersection = intersection;
        this.vessels = new String[] {vessel1.name, vessel2.name};
        this.timestamp = timestamp;
    }
}
