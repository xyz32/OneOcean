package test.oneocean.geo;

public class GeoNode {
    public static final double EARTH_RADIUS_M = 6378137;
    public static final double EARTH_RADIUS_KM = EARTH_RADIUS_M / 1000;

    public double decimalLatitude;
    public double decimalLongitude;

    public GeoNode(double decimalLatitude, double decimalLongitude) {
        this.decimalLatitude = decimalLatitude;
        this.decimalLongitude = decimalLongitude;
    }

    public double distanceToKm(GeoNode toNode) {
        double x = this.decimalLatitude - toNode.decimalLatitude;
        double y = this.decimalLongitude - toNode.decimalLongitude;
        return Math.sqrt(x * x + y * y);
    }

    // calculate geographical distance. account for earth curvature.
    public double sphericalDistanceToM(GeoNode toNode) {
        double dLat = Math.toRadians(toNode.decimalLatitude - this.decimalLatitude);
        double dLon = Math.toRadians(toNode.decimalLongitude - this.decimalLongitude);

        double lat1 = Math.toRadians(this.decimalLatitude);
        double lat2 = Math.toRadians(toNode.decimalLatitude);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_M * c;
    }
}
