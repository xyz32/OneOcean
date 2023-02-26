package test.oneocean.geo;

public class GeoNode {
    public static final double EARTH_RADIUS_M = 6378137;
    public static final double EARTH_RADIUS_KM = EARTH_RADIUS_M / 1000;

    public double yKm;
    public double xKm;

    public GeoNode(double yKm, double xKm) {
        this.yKm = yKm;
        this.xKm = xKm;
    }

    public double distanceToKm(GeoNode toNode) {
        double x = this.yKm - toNode.yKm;
        double y = this.xKm - toNode.xKm;
        return Math.sqrt(x * x + y * y);
    }

    // calculate geographical distance. account for earth curvature.
    public double sphericalDistanceToM(GeoNode toNode) {
        double dLat = Math.toRadians(toNode.yKm - this.yKm);
        double dLon = Math.toRadians(toNode.xKm - this.xKm);

        double lat1 = Math.toRadians(this.yKm);
        double lat2 = Math.toRadians(toNode.yKm);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_M * c;
    }
}
