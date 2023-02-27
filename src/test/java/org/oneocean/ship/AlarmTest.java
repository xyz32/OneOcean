package org.oneocean.ship;

import org.junit.Test;
import org.oneocean.geo.GeoNode;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import static org.junit.Assert.*;

public class AlarmTest {

    @Test
    public void toJson() {
        TemporalAccessor temporalAccessor = DateTimeFormatter.ISO_ZONED_DATE_TIME.parse("2020-01-01T07:40Z");

        Alarm testAlarm = new Alarm(new GeoNode(1, 1)
                , new Vessel("{\"name\":\"Vessel 1\",\"positions\":[{\"x\":5,\"y\":5,\"timestamp\":\"2020-01-01T07:40Z\"}]}")
                , new Vessel("{\"name\":\"Vessel 2\",\"positions\":[{\"x\":5,\"y\":5,\"timestamp\":\"2020-01-01T07:40Z\"}]}")
                , Instant.from(temporalAccessor));

        assertEquals("Alarm to json", "{\"location\":{\"yKm\":1,\"xKm\":1},\"vessels\":[\"Vessel 1\",\"Vessel 2\"],\"timestamp\":\"2020-01-01T07:40:00Z\"}", testAlarm.toJson().toString());
    }
}