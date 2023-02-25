package test.oneocean;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import test.oneocean.ship.Vessel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class VesselsDb {
    Map<String, Vessel> vessels = new HashMap<>();

    public VesselsDb(String source) {
        try {
            InputStream fileIn = new FileInputStream(source);
            JSONTokener tokener = new JSONTokener(fileIn);
            JSONObject object = new JSONObject(tokener);

            JSONArray vesselsData = object.getJSONArray("vessels");
            for (int i = 0; i < vesselsData.length(); ++i) {
                Vessel vessel = new Vessel(vesselsData.getJSONObject(i));
                vessels.put(vessel.getName(), vessel);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
