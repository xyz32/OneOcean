package test.oneocean;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.json.JSONObject;

import java.util.Deque;

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
                        )
//                        .addPrefixPath("/", resource(new ClassPathResourceManager(ChatServer.class.getClassLoader(), ChatServer.class.getPackage()))
//                                .addWelcomeFiles("index.html"))
                ).build();

        server.start();
    }

    private JSONObject getVesselGpsTrack(Deque<String> vessel) {
        JSONObject result = new JSONObject();
        String veselId = vessel.getFirst();
        result.put("name", veselId);
        result.put("gpsTrack", "");
        return result;
    }

    private JSONObject getVesselAverageSpeed(Deque<String> vessel) {
        JSONObject result = new JSONObject();
        String veselId = vessel.getFirst();
        result.put("name", veselId);
        result.put("averageSpeedKph", vessels.vessels.get(veselId).getAverageSpeed());
        return result;
    }

    private JSONObject getVesselDistanceTraveled(Deque<String> vessel) {
        JSONObject result = new JSONObject();
        String veselId = vessel.getFirst();
        result.put("name", veselId);
        result.put("distanceTraveledKm", vessels.vessels.get(veselId).getDistanceTraveledKm());
        return result;
    }

    private JSONObject buildVesselList() {
        JSONObject result = new JSONObject();
        result.put("name", vessels.vessels.keySet());
        return result;
    }
}
