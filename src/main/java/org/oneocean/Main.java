package org.oneocean;

import org.oneocean.ship.VesselsDb;

public class Main {
    public static void main(String[] args) {
        VesselsDb vessels = new VesselsDb("./TestData.json");

        Server server = new Server(vessels);

        server.startServer();
    }
}