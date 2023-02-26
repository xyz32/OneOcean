package test.oneocean.geo;

import org.json.JSONObject;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class LocationPoint {
    public static final String TIME_STAMP = "timestamp";
    public static final String COORD_X = "x";
    public static final String COORD_Y = "y";
    public final GeoNode location;
    public Instant dateTime;

    public LocationPoint (JSONObject locationObject) {
        this.location = new GeoNode(locationObject.optDouble(COORD_X), locationObject.optDouble(COORD_Y));
        TemporalAccessor temporalAccessor = DateTimeFormatter.ISO_ZONED_DATE_TIME.parse(locationObject.optString(TIME_STAMP));
        this.dateTime = Instant.from(temporalAccessor);
    }

    public JSONObject toJson() {
        JSONObject gpsTrack = new JSONObject();
        gpsTrack.put("xKm", this.location.xKm);
        gpsTrack.put("yKm", this.location.yKm);
        gpsTrack.put("timestamp", this.dateTime.toString());
        return gpsTrack;
    }
}
