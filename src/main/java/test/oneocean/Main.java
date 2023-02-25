package test.oneocean;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import test.oneocean.ship.Vessel;

public class Main {
    public static void main(String[] args) {
        VesselsDb vessels = new VesselsDb("./TestData.json");

        Server server = new Server(vessels);

        server.startServer();
    }
}