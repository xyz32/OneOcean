package test.oneocean.geo;

import org.json.JSONObject;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class LocationPoint {
    public static final String TIME_STAMP = "timestamp";
    public static final String LATITUDE = "y";
    public static final String LONGITUDE = "x";
    public final GeoNode location;
    public Instant dateTime;

    public LocationPoint (JSONObject locationObject) {
        this.location = new GeoNode(locationObject.optDouble(LATITUDE), locationObject.optDouble(LONGITUDE));
        TemporalAccessor temporalAccessor = DateTimeFormatter.ISO_ZONED_DATE_TIME.parse(locationObject.optString(TIME_STAMP));
        this.dateTime = Instant.from(temporalAccessor);
    }
}
