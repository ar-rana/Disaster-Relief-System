import React, { useMemo, useRef, useState } from "react";
import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";
import "leaflet/dist/leaflet.css";

const Map = () => {
  const [map, setMap] = useState(null)

  const [zoom, setZoom] = useState(12);
  const [home, setHome] = useState([28.613, 77.209]);

  const returnToHome = () => {
    map.setView(home, zoom);
  };

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
          attribution="© OpenStreetMap contributors"
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />
        <Marker className="fa fa-home" position={[home[0], home[1]]}>
          <Popup>
            A pretty CSS3 popup. <br /> Easily customizable.
          </Popup>
        </Marker>
      </MapContainer>
    ),
    []
  );

  return (
    <div className="h-[100%] w-[100%] p-0">
      <div className="flex m-0.5">
        <button
          className="px-4 bg-amber-200 border rounded-sm font-semibold active:border-white"
          onClick={returnToHome}
        >
          Reset (Return to home)
        </button>
      </div>
      {DisplayMap}
    </div>
  );
};

export default Map;
