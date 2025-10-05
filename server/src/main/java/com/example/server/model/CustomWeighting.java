package com.example.server.model;

import java.util.Set;

import com.graphhopper.GraphHopper;
import com.graphhopper.routing.weighting.Weighting;
import com.graphhopper.util.EdgeIteratorState;
import com.graphhopper.util.PMap;

public class CustomWeighting implements Weighting {

    GraphHopper graphHopper;
    Weighting baseWeighting;
    Set<Integer> blockedIds;

    public CustomWeighting(GraphHopper graphHopper, Set<Integer> blockedIds) {
        this.graphHopper = graphHopper;
        this.blockedIds = blockedIds;
        baseWeighting = graphHopper.createWeighting(
                graphHopper.getProfile("small-truck"),
                new PMap()
        );
    }

    @Override
    public double calcMinWeightPerDistance() {
        return baseWeighting.calcMinWeightPerDistance();
    }

    @Override
    public double calcEdgeWeight(EdgeIteratorState edge, boolean b) {
        int edgeId = edge.getEdge();
        if (blockedIds.contains(edgeId)) {
            return Double.POSITIVE_INFINITY;
        }
        return baseWeighting.calcEdgeWeight(edge, b);
    }

    @Override
    public long calcEdgeMillis(EdgeIteratorState edge, boolean b) {
        return baseWeighting.calcEdgeMillis(edge, b);
    }

    @Override
    public double calcTurnWeight(int i, int i1, int i2) {
        return baseWeighting.calcTurnWeight(i, i1, i2);
    }

    @Override
    public long calcTurnMillis(int i, int i1, int i2) {
        return baseWeighting.calcTurnMillis(i, i1, i2);
    }

    @Override
    public boolean hasTurnCosts() {
        return baseWeighting.hasTurnCosts();
    }

    @Override
    public String getName() {
        return "blocked_roads_weighting";
    }
}
