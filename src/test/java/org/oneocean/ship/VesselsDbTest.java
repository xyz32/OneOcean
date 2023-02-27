package org.oneocean.ship;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class VesselsDbTest {

    @Test
    public void getAlarms() {
        VesselsDb testDb = new VesselsDb("./src/test/resources/TestData.json");

        List<Alarm> result = testDb.getAlarms();
        assertEquals("Test alarms", "{\"location\":{\"yKm\":15.12,\"xKm\":25.92},\"vessels\":[\"Vessel 3\",\"Vessel 1\"],\"timestamp\":\"2020-01-01T08:49:00Z\"}", result.get(0).toJson().toString());
    }
}