import React from "react";

const ReliefinfoCard = ({ req }) => {
  return (
    <div class="bg-white border-t-4 border-[#33A1E0] rounded-b text-teal-900 p-3 shadow-md w-full">
      <div class="flex">
        <div>
          <p title={req.desc} class="font-bold">
            {req.name} | Id: {req.id}
          </p>
          <p class="text-sm font-bold">Contact: {req.POC}</p>
          <p className="font-semibold text-sm">
            Criticality: {req.criticality} | Status:{" "}
            {req.status === "true" ? "Completed" : "Incomplete"}
          </p>
          <p title={req.desc} class="text-sm overflow-hidden">
            {req.desc}
          </p>
        </div>
      </div>
    </div>
  );
};

export default ReliefinfoCard;
