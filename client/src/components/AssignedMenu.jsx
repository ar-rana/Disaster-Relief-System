import React from "react";
import ReliefinfoCard from "./ReliefinfoCard";
const data = [
  {
    id: 0,
    criticality: "basic",
    lat: "27.99",
    lon: "77.456",
    desc: "just testing the systems",
    POC: "1234567890",
    name: "arrthjujn",
  },
  {
    id: 1,
    criticality: "high",
    lat: "28.43",
    lon: "77.1234",
    desc: "my god im dead, save im dead, save me save me save me save me save me save me save me save me saveim dead, save me save me save me save me save me save me save me save me saveim dead, save me save me save me save me save me save me save me save me saveim dead, save me save me save me save me save me save me save me save me saveme save me save me save me save me save me save me save me save mesave me",
    POC: "0987654321",
    name: "kaliyug",
  },
  {
    id: 2,
    criticality: "high",
    lat: "28.3132",
    lon: "77.351",
    desc: "my god he is dead",
    POC: "78905238",
    name: "street dog",
  },
  {
    id: 1,
    criticality: "high",
    lat: "28.43",
    lon: "77.1234",
    desc: "my god im dead, save im dead, save me save me save me save me save me save me save me save me saveim dead, save me save me save me save me save me save me save me save me saveim dead, save me save me save me save me save me save me save me save me saveim dead, save me save me save me save me save me save me save me save me saveme save me save me save me save me save me save me save me save mesave me",
    POC: "0987654321",
    name: "kaliyug",
  },
  {
    id: 2,
    criticality: "very_high",
    lat: "28.3132",
    lon: "77.351",
    desc: "my god he is dead",
    POC: "78905238",
    name: "street dog",
  },
  {
    id: 1,
    criticality: "high",
    lat: "28.43",
    lon: "77.1234",
    desc: "my god im dead, save im dead, save me save me save me save me save me save me save me save me saveim dead, save me save me save me save me save me save me save me save me saveim dead, save me save me save me save me save me save me save me save me saveim dead, save me save me save me save me save me save me save me save me saveme save me save me save me save me save me save me save me save mesave me",
    POC: "0987654321",
    name: "kaliyug",
  },
  {
    id: 2,
    criticality: "moderate",
    lat: "28.3132",
    lon: "77.351",
    desc: "my god he is dead",
    POC: "78905238",
    name: "street dog",
  },
];

const AssignedMenu = () => {
  return (
    <div className="w-full border-2 h-48 border-[#33A1E0] bg-gray-100 p-4 rounded-md overflow-x-scroll overflow-y-hidden">
      <div className="flex gap-2 p-1">
        {data.map((data) => {
          return (
            <ReliefinfoCard req={data} nav={true} />
          );
        })}
      </div>
    </div>
  );
};

export default AssignedMenu;
