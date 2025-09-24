import React from "react";

const ReliefMenu = ({ data }) => {
  return (
    <div className="border-2 h-[98vh] border-amber-200 bg-gray-100 p-4 rounded-md z-10">
      <div className="bg-white flex flex-col gap-3 p-3 rounded-md overflow-y-scroll h-full">
        <p className="font-bold text-xl text-amber-600 sticky top-0 bg-white w-auto z-10 p-3">
          Relief Required
        </p>
        {data.map((data) => {
          return (
            <div key={data.uid}
              className={`bg-amber-50 border-t-4 rounded-b text-teal-900 px-3 py-2 shadow-md w-[98%] self-center ${
                data.criticality === "VERY_HIGH"
                  ? "border-red-700"
                  : data.criticality === "HIGH"
                  ? "border-orange-400"
                  : data.criticality === "MODERATE"
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
                  <p className="text-sm">{data.description}</p>
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
