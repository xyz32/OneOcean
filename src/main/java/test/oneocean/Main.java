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
        List<Vessel> vessels = new ArrayList<>();
        try {
            InputStream fileIn = new FileInputStream("./TestData.json");
            JSONTokener tokener = new JSONTokener(fileIn);
            JSONObject object = new JSONObject(tokener);

            JSONArray vesselsData = object.getJSONArray("vessels");
            for (int i = 0; i < vesselsData.length(); ++i) {
                vessels.add(new Vessel(vesselsData.getJSONObject(i)));

                System.out.println("Average speed: " + vessels.get(i).getAverageSpeed() + " km/h");
                System.out.println("Distance traveled: " + vessels.get(i).getDistanceTraveledM()/1000 + " km");
                System.out.println("Time traveled: " + (vessels.get(i).getTravelTime().getSeconds() / 3600d + " h"));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}