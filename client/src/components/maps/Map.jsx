import React, { useEffect, useMemo, useRef, useState } from "react";
import {
  MapContainer,
  TileLayer,
  Marker,
  Popup,
  CircleMarker,
  Tooltip,
} from "react-leaflet";
import "leaflet/dist/leaflet.css";
import MapRouting from "./MapRouting";
import MapRoutingPoints from "./MapRoutingPoints";
import hq_svg from "../../assets/hq_svg.svg";
import provider_svg from "../../assets/provider_svg.svg";
import { getLiveLocation, roadBlocked } from "../../api/provider/provider";

const Map = ({ assigned }) => {
  const [map, setMap] = useState(null);

  const [zoom, setZoom] = useState(13);
  const [home, setHome] = useState([20.2636391, 85.8252384]);

  const [currCoords, setCurrCoords] = useState([20.2636391, 85.8252384]);
  const [pos, setPos] = useState([]);
  const [waypoints, setWaypoints] = useState([
    [20.2636391, 85.8252384], // Start
    [20.3636391, 85.8152384], // Stop 1
    [20.3536391, 85.7152384], // Stop 2
    [20.373321, 85.8452456], // Destination
  ]);

  const headquarterIcon = L.icon({
    iconUrl: hq_svg,
    iconSize: [30, 30],
    iconAnchor: [16, 32],
    popupAnchor: [0, -32],
  });

  const providerIcon = L.icon({
    iconUrl: provider_svg,
    iconSize: [28, 28],
    iconAnchor: [16, 32],
    popupAnchor: [0, -32],
  });

  const returnToHome = () => {
    map.setView(home, zoom);
  };

  const whereAmI = () => {
    map.setView(currCoords, zoom);
  };

  const reportRoadBlock = async () => {
    const data = await roadBlocked();
    if (data.success) alert("We have reported the Road Block, thank you for you help.");
  };

  useEffect(() => {
    getLiveLocation()
      .then((data) => {
        setCurrCoords(data);
      })
      .catch((e) => alert("location is required"));
    const intervalId = setInterval(() => {
      getLiveLocation()
        .then((data) => {
          setCurrCoords(data);
        })
        .catch((e) => alert("location is required"));
    }, 1000);

    return () => clearInterval(intervalId);
  }, []);

  const DisplayMap = useMemo(
    () => (
      <MapContainer
        center={[home[0], home[1]]}
        zoom={zoom}
        scrollWheelZoom={true}
        ref={setMap}
        className="w-[100%] h-[95%]"
      >
        <TileLayer
          attribution="© OpenStreetMap"
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />
        <Marker icon={headquarterIcon} position={home}>
          <Popup>Headquarters</Popup>
        </Marker>
        <Marker icon={providerIcon} position={currCoords}>
          <Popup>You are here</Popup>
        </Marker>
        {assigned.map((point) => {
          return (
            <CircleMarker
              center={[point.longitude, point.latitude]}
              pathOptions={{ color: "red" }}
              radius={12}
            >
              <Tooltip>{point.uid}</Tooltip>
            </CircleMarker>
          );
        })}
        <MapRoutingPoints pos={pos} />
      </MapContainer>
    ),
    [currCoords, waypoints, assigned]
  );

  return (
    <div className="h-[100%] w-[100%] p-0 bg-white">
      <div className="flex p-0.5 gap-0.5">
        <button
          className="px-4 bg-amber-100 border rounded-sm font-semibold active:border-white"
          onClick={returnToHome}
        >
          Reset (Return to home)
        </button>
        <button
          className="px-4 bg-amber-100 border rounded-sm font-semibold active:border-white"
          onClick={whereAmI}
        >
          ME
        </button>
        <button
          className="px-4 bg-amber-100 border rounded-sm font-semibold active:border-white"
          onClick={reportRoadBlock}
        >
          Report Road Block
        </button>
      </div>
      {DisplayMap}
      {map && <MapRouting map={map} waypoints={waypoints} />}
    </div>
  );
};

export default Map;
