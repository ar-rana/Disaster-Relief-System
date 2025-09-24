import React from "react";

const ReliefinfoCard = ({ req, nav = false }) => {
  return (
    <div
      key={req.uid}
      className={`bg-white border-t-4 border-[#33A1E0] rounded-b text-teal-900 p-3 shadow-md ${
        nav ? "w-xs" : "w-full"
      }`}
    >
      <div className="flex">
        <div>
          <p title={req.description} className="font-bold">
            {req.name} | Id: {req.uid}
          </p>
          <p className="text-sm font-bold">Contact: {req.poc}</p>
          <p className="font-semibold text-sm">
            Criticality: {req.criticality}
          </p>
          <p
            title={req.desc}
            className={`text-sm overflow-hidden ${
              nav ? "truncate w-[285px]" : ""
            }`}
          >
            {req.description}
          </p>
          {nav ? (
            <div className="flex gap-2">
              <button className="bg-[#33A1E0] font-medium px-2 rounded-lg mt-2 text-white hover:opacity-70">
                Move Here
              </button>
              <button className="bg-gray-600 font-medium px-2 rounded-lg mt-2 text-white hover:opacity-70">
                Completed
              </button>
              <button className="bg-gray-600 font-medium px-2 rounded-lg mt-2 text-white hover:opacity-70">
                Dismiss
              </button>
            </div>
          ) : (
            ""
          )}
        </div>
      </div>
    </div>
  );
};

export default ReliefinfoCard;
