package test.oneocean;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.resource.PathResourceManager;
import io.undertow.util.Headers;
import org.json.JSONArray;
import org.json.JSONObject;
import test.oneocean.geo.LocationPoint;

import java.nio.file.Paths;
import java.util.Deque;

import static io.undertow.Handlers.resource;

public class Server {

    private final VesselsDb vessels;

    public Server(VesselsDb vessels) {
        this.vessels = vessels;
    }

    public void startServer() {
        Undertow server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(Handlers.path()
                        .addPrefixPath("/api", Handlers.routing()
                                .get("/vessels", new HttpHandler() {
                                    @Override
                                    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {
                                        httpServerExchange.getResponseHeaders()
                                                .put(Headers.CONTENT_TYPE, "application/json");

                                        httpServerExchange.getResponseSender()
                                                .send(buildVesselList().toString());
                                    }
                                })
                                .get("/{vessel}/averageSpeed", new HttpHandler() {
                                    @Override
                                    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {
                                        httpServerExchange.getResponseHeaders()
                                                .put(Headers.CONTENT_TYPE, "application/json");

                                        httpServerExchange.getResponseSender()
                                                .send(getVesselAverageSpeed(httpServerExchange.getQueryParameters().get("vessel")).toString());
                                    }
                                })
                                .get("/{vessel}/distanceTraveled", new HttpHandler() {
                                    @Override
                                    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {
                                        httpServerExchange.getResponseHeaders()
                                                .put(Headers.CONTENT_TYPE, "application/json");

                                        httpServerExchange.getResponseSender()
                                                .send(getVesselDistanceTraveled(httpServerExchange.getQueryParameters().get("vessel")).toString());
                                    }
                                })
                                .get("/{vessel}/gpsTrack", new HttpHandler() {
                                    @Override
                                    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {
                                        httpServerExchange.getResponseHeaders()
                                                .put(Headers.CONTENT_TYPE, "application/json");

                                        httpServerExchange.getResponseSender()
                                                .send(getVesselGpsTrack(httpServerExchange.getQueryParameters().get("vessel")).toString());
                                    }
                                })
                                .get("/alarms", new HttpHandler() {
                                    @Override
                                    public void handleRequest(HttpServerExchange httpServerExchange) throws Exception {
                                        httpServerExchange.getResponseHeaders()
                                                .put(Headers.CONTENT_TYPE, "application/json");

                                        httpServerExchange.getResponseSender()
                                                .send(getProximityAlarms().toString());
                                    }
                                })
                        )
                        .addPrefixPath("/docs", resource(new PathResourceManager(Paths.get("target/generated-sources/openapi/index.html"), 100))
                                .setDirectoryListingEnabled(false))
                ).build();

        server.start();
    }

    private JSONArray getProximityAlarms() {
        vessels.getAlarms();
        return new JSONArray();
    }

    private JSONObject getVesselGpsTrack(Deque<String> vessel) {
        JSONObject result = new JSONObject();
        String veselId = vessel.getFirst();
        result.put("name", veselId);
        JSONArray data = new JSONArray();

        for (LocationPoint entry: vessels.vesselsData.get(veselId).getPositions()) {
            JSONObject gpsTrack = new JSONObject();
            JSONObject gpsLoc = new JSONObject();
            gpsLoc.put("decimalLatitude", entry.location.decimalLatitude);
            gpsLoc.put("decimalLongitude", entry.location.decimalLongitude);
            gpsTrack.put(entry.dateTime.toString(), gpsLoc);
            data.put(gpsTrack);
        }

        result.put("gpsTrack", data);
        return result;
    }

    private JSONObject getVesselAverageSpeed(Deque<String> vessel) {
        JSONObject result = new JSONObject();
        String veselId = vessel.getFirst();
        result.put("name", veselId);
        result.put("averageSpeedKph", vessels.vesselsData.get(veselId).getAverageSpeed());
        return result;
    }

    private JSONObject getVesselDistanceTraveled(Deque<String> vessel) {
        JSONObject result = new JSONObject();
        String veselId = vessel.getFirst();
        result.put("name", veselId);
        result.put("distanceTraveledKm", vessels.vesselsData.get(veselId).getDistanceTraveledKm());
        return result;
    }

    private JSONObject buildVesselList() {
        JSONObject result = new JSONObject();
        result.put("name", vessels.vesselsData.keySet());
        return result;
    }
}
