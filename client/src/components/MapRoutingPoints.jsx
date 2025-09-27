import React from "react";
import { Polyline } from "react-leaflet";

const MapRoutingPoints = ({ pos }) => {
  return (
    <Polyline
      positions={[
        [20.3636391, 85.8152384],
        [20.3536391, 85.7152384],
        [20.373321, 85.8452456],
        // can add more [long, lat points]
      ]}
      color="#3287C2"
    />
  );
};

export default MapRoutingPoints;
