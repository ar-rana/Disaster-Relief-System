import React from "react";
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
  ,
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

const ReliefMenu = () => {
  return (
    <div className="border-2 h-[98vh] border-amber-200 bg-gray-100 p-4 rounded-md z-10">
      <div className="bg-white flex flex-col gap-3 p-3 rounded-md overflow-y-scroll h-full">
        <p className="font-bold text-xl text-amber-600 sticky top-0 bg-white w-auto z-10 p-3">
          Relief Required
        </p>
        {data.map((data) => {
          return (
            <div
              className={`bg-amber-50 border-t-4 rounded-b text-teal-900 px-3 py-2 shadow-md w-[98%] self-center ${
                data.criticality === "very_high"
                  ? "border-red-700"
                  : data.criticality === "high"
                  ? "border-orange-400"
                  : data.criticality === "moderate"
                  ? "border-amber-300"
                  : "border-blue-400"
              }`}
            >
              <div className="flex">
                <div>
                  <p className="font-bold">{data.name}</p>
                  <p className="font-semibold text-sm underline">
                    Criticality: {data.criticality}
                  </p>
                  <p className="text-sm">{data.desc}</p>
                </div>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default ReliefMenu;
