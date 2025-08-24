import React from "react";
import Map from "../components/Map.jsx";
import ReliefMenu from "../components/ReliefMenu.jsx";
import AssignedMenu from "../components/AssignedMenu.jsx";

const Navigation = () => {
  return (
    <div className="w-full h-auto flex flex-col ml-auto mr-auto gap-1 bg-slate-700">
      <div className="w-[93%] h-[100vh] flex gap-4 ml-auto mr-auto">
        <div className="flex-[3] border-2 m-2">
          <Map />
        </div>
        <div className="flex-[1.25] self-center">
          <ReliefMenu />
        </div>
      </div>
      <div className="w-[93%] flex flex-col ml-auto mr-auto">
        <p className="text-2xl font-bold text-teal-500 m-2">
          Assigned Relief Points
        </p>
        <div className="self-center w-full flex">
          <AssignedMenu />
        </div>
      </div>
      <br />
      <br />
    </div>
  );
};

export default Navigation;
