package test.oneocean.ship;

import org.junit.Test;

import static org.junit.Assert.*;

public class VesselTest {

    @Test
    public void getVesselInfo() {
        String vesselData = "{" +
                "\"name\": \"Vessel 1\"," +
                "\"positions\": [{" +
                "\"x\": 5," +
                "\"y\": 5," +
                "\"timestamp\": \"2020-01-01T07:40Z\"" +
                "}, {" +
                "\"x\": 9," +
                "\"y\": 9," +
                "\"timestamp\": \"2020-01-01T07:55Z\"" +
                "}, {" +
                "\"x\": 15," +
                "\"y\": 11," +
                "\"timestamp\": \"2020-01-01T08:25Z\"" +
                "}, {" +
                "\"x\": 22," +
                "\"y\": 14," +
                "\"timestamp\": \"2020-01-01T08:50Z\"" +
                "}, {" +
                "\"x\": 29," +
                "\"y\": 16," +
                "\"timestamp\": \"2020-01-01T09:06Z\"" +
                "}, {" +
                "\"x\": 35," +
                "\"y\": 17," +
                "\"timestamp\": \"2020-01-01T09:24Z\"" +
                "}, {" +
                "\"x\": 41," +
                "\"y\": 20," +
                "\"timestamp\": \"2020-01-01T09:45Z\"" +
                "}, {" +
                "\"x\": 48," +
                "\"y\": 23," +
                "\"timestamp\": \"2020-01-01T10:13Z\"" +
                "}]" +
                "}";
        Vessel testVessel = new Vessel(vesselData);

        assertEquals("Average speed", 18.542757699464733, testVessel.getAverageSpeed(), 0.1);
        assertEquals("Distance traveled", 47.284032133635066, testVessel.getDistanceTraveledKm(), 0.1);
        assertEquals("Vessel to json", "{\"yKm\":5,\"xKm\":5,\"timestamp\":\"2020-01-01T07:40:00Z\"}", testVessel.getPositions().get(0).toJson().toString());
    }
}