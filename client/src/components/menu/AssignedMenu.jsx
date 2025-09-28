import React from "react";
import ReliefinfoCard from "../infoCards/ReliefinfoCard";

const AssignedMenu = ({ data }) => {
  return (
    <div className="w-full border-2 h-48 border-[#33A1E0] bg-gray-100 p-4 rounded-md overflow-x-scroll overflow-y-hidden">
      <div className="flex gap-2 p-1">
        {data.map((req) => {
          return (
            <ReliefinfoCard req={req} nav={true} key={req.uid} />
          );
        })}
      </div>
    </div>
  );
};

export default AssignedMenu;
