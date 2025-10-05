package com.example.server.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.server.model.CustomWeighting;
import com.example.server.model.DTOs.Coords;
import com.example.server.model.ReliefReq;
import com.example.server.service.redis.RedisListService;
import com.graphhopper.GraphHopper;
import com.graphhopper.config.CHProfile;
import com.graphhopper.config.Profile;
import com.graphhopper.json.Statement;
import com.graphhopper.routing.AStar;
import com.graphhopper.routing.Path;
import com.graphhopper.routing.RoutingAlgorithm;
import com.graphhopper.routing.querygraph.QueryGraph;
import com.graphhopper.routing.util.EdgeFilter;
import com.graphhopper.routing.util.TraversalMode;
import com.graphhopper.storage.index.LocationIndex;
import com.graphhopper.storage.index.Snap;
import com.graphhopper.util.CustomModel;
import com.graphhopper.util.PointList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoutesService {

    private RedisListService lists;
    private GraphHopper graphHopper;
    private ReliefService reliefService;

    public RoutesService() {
        graphHopper = new GraphHopper()
                .setOSMFile("src/main/resources/static/eastern-zone-251003.osm.pbf")
                .setGraphHopperLocation("target/heading-graph-cache")
                .setEncodedValuesString("car_access, road_access, car_average_speed")
                .setProfiles(new Profile("car")
                        .setCustomModel(new CustomModel()
                                .addToSpeed(Statement.If("true", Statement.Op.LIMIT, "car_average_speed"))
                                .addToPriority(Statement.If("!car_access", Statement.Op.MULTIPLY, "0")))
                );
        graphHopper.getCHPreparationHandler().setCHProfiles(new CHProfile("car"));
        graphHopper.importOrLoad();
        this.lists = new RedisListService();
        this.reliefService = new ReliefService();
    }

    public List<Coords> getShortestSafePath(String reqId, Coords fromCoords) {
        ReliefReq aid = reliefService.getReliefRequest(reqId);
        Set<Coords> set = lists.getBlockedCoords();
        if (set.isEmpty()) {
            return getPathFallback();
        }
        Set<Integer> blockedIds = new HashSet<>();

        LocationIndex locationIndex = graphHopper.getLocationIndex();
        for (Coords coords : set) {
            Snap close = locationIndex.findClosest(
                    coords.getLatitude(),
                    coords.getLongitude(),
                    EdgeFilter.ALL_EDGES
            );
            // Get the edge ID that corresponds to this location
            if (close.isValid()) {
                int edgeId = close.getClosestEdge().getEdge();
                log.info("[ROUTE_S] Blocking edge ID: {}", edgeId);
                blockedIds.add(edgeId);
            } else {
                log.info("[ROUTE_S] Invalid Snap");
            }
        }

        Snap to = locationIndex.findClosest(aid.getLatitude(), aid.getLongitude(), EdgeFilter.ALL_EDGES);
        Snap from = locationIndex.findClosest(fromCoords.getLatitude(), fromCoords.getLongitude(), EdgeFilter.ALL_EDGES);
        if (!to.isValid() || !from.isValid()) {
            log.error("[ROUTE_S] Invalid 'to' or 'from' location for ReliefReq: {}. from: {}, to: {}", aid.getUid(), from.isValid(), to.isValid());
            // FALLBACK ON FAILURE
            return getPathFallback();
        }

        QueryGraph queryGraph = QueryGraph.create(graphHopper.getBaseGraph(), from, to);
        CustomWeighting customWeighting = new CustomWeighting(graphHopper, blockedIds);
//        AlgorithmOptions algoOpts = new AlgorithmOptions()
//                .setAlgorithm(Parameters.Algorithms.ASTAR);
        RoutingAlgorithm algo = new AStar(queryGraph, customWeighting, TraversalMode.NODE_BASED);

        // final path
        Path path = algo.calcPath(from.getClosestNode(), to.getClosestNode());
        // hindi wala rasta
        List<Coords> rasta = new ArrayList<>();
        if (path.isFound()) {
            PointList pointList = path.calcPoints();

            for (int i = 0; i < pointList.size(); i++) {
                rasta.add(new Coords(
                        pointList.get(i).getLat(),
                        pointList.get(i).getLon()
                ));
            }
            log.info("[ROUTE_S] Route for AID: {} found from: {} to: {}", aid.getUid(), fromCoords, aid);
        } else {
            log.info("[ROUTE_S] Route for AID: {} NOT found from: {} to: {}. RESORT TO FALLBACK", aid.getUid(), from, to.getClosestNode());
            return getPathFallback();
        }

        return rasta;
    }

    private List<Coords> getPathFallback() {
        log.info("[ROUTE_S] Resorting to Fallback");
        return new ArrayList<>();
    }
}
