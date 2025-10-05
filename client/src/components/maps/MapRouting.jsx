import React, { useEffect } from "react";
import L from "leaflet";
import "leaflet-routing-machine";

const MapRouting = ({ map, waypoints }) => {
  useEffect(() => {
    if (!map || !waypoints) return;

    const routingControl = L.Routing.control({
      waypoints: waypoints.map(([lat, lng]) => L.latLng(lat, lng)),
      routeWhileDragging: true,
      show: false,
      draggableWaypoints: true,
      addWaypoints: true,
      createMarker: () => null,
      lineOptions: {
        styles: [
          { color: "blue", weight: 2, opacity: 0.6},
        ],
      },
    }).addTo(map);

    return () => map.removeControl(routingControl);
  }, [map, waypoints]);

  return null;
};

export default MapRouting;
