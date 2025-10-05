import React from "react";
import { Polyline } from "react-leaflet";

const MapRoutingPoints = ({ pos = [[]] }) => {
  return (
    <Polyline
      positions={pos}
      pathOptions={{
        color: "red",
        weight: 2,
      }}
    />
  );
};

export default MapRoutingPoints;
