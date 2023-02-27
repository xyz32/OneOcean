package org.oneocean.geo;

import org.junit.Test;

import static org.junit.Assert.*;

public class GeoNodeTest {

    @Test
    public void distanceToKm() {
        GeoNode fromNode = new GeoNode(10, 10);
        GeoNode toNode = new GeoNode(12, 12);

        assertEquals("Distance to", 2.8284271247461903, fromNode.distanceToKm(toNode), 0.1);
    }
}