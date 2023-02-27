package org.oneocean;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.resource.PathResourceManager;
import io.undertow.util.Headers;
import org.json.JSONArray;
import org.json.JSONObject;
import org.oneocean.geo.LocationPoint;
import org.oneocean.ship.Alarm;
import org.oneocean.ship.VesselsDb;

import java.nio.file.Paths;
import java.util.Deque;
import java.util.List;

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
                                .get("/alerts", new HttpHandler() {
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
                        .addPrefixPath("/index.html", resource(new PathResourceManager(Paths.get("src/main/resources/frontend/index.html"), 100))
                                .setDirectoryListingEnabled(false))
                ).build();

        server.start();
    }

    private JSONArray getProximityAlarms() {
        List<Alarm> alarms = vessels.getAlarms();
        JSONArray result = new JSONArray();

        for (Alarm alarm: alarms) {
            result.put(alarm.toJson());
        }

        return result;
    }

    private JSONObject getVesselGpsTrack(Deque<String> vessel) {
        JSONObject result = new JSONObject();
        String veselId = vessel.getFirst();
        result.put("name", veselId);
        JSONArray data = new JSONArray();

        for (LocationPoint entry: vessels.vesselsData.get(veselId).getPositions()) {
            data.put(entry.toJson());
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

    private JSONArray buildVesselList() {
        return new JSONArray(vessels.vesselsData.keySet());
    }
}
