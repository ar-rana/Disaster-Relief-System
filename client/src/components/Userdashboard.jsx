import React from "react";
import ReliefinfoCard from "./ReliefinfoCard";

const UserDashboard = ({
  submitHandlerForNumber,
  submitHandlerForId,
  phone,
  setPhone,
  reliefReq,
  reliefId,
  setReliefId,
}) => {
    // BOTH THESE divs are using the same 'reliefReq' DATA change that when the actual data comes
  return (
    <div className="flex flex-col md:flex-row justify-center gap-4">
      <div className="flex-2 border-2 border-gray-400 flex flex-col p-4 rounded-sm">
        <h2 className="font-bold text-[#33A1E0] text-xl">Query all Requests</h2>
        <form
          className="flex w-full gap-2 mt-2"
          onSubmit={submitHandlerForNumber}
        >
          <input
            type="text"
            placeholder="enter your contact number"
            value={phone}
            onChange={(e) => setPhone(e.target.value)}
            required
            className="flex-1 px-4 py-2 rounded-l-lg border border-gray-300 focus:outline-none focus:border-[#33A1E0] bg-white text-gray-800 text-base shadow-sm"
          />
          <button
            type="submit"
            className="px-4 py-2 rounded-r-lg bg-[#33A1E0] text-white font-semibold flex items-center gap-2 hover:bg-[#2381b0] transition border border-[#33A1E0]"
            aria-label="Search"
          >
            <i className="fa fa-search mr-1" />
            Search
          </button>
        </form>
        <div className="max-h-[28rem] overflow-y-scroll p-4 mt-1.5 space-y-2">
          {reliefReq.map((req) => {
            return <ReliefinfoCard key={req.id} req={req} />;
          })}
        </div>
      </div>
      <div className="flex-2 border-2 border-gray-400 flex flex-col p-4 rounded-sm">
        <h2 className="font-bold text-[#33A1E0] text-xl">
          Search Relief Request by Id (Detail search)
        </h2>
        <form className="flex w-full gap-2 mt-2" onSubmit={submitHandlerForId}>
          <input
            type="text"
            placeholder="enter relief request ID"
            value={reliefId}
            onChange={(e) => setReliefId(e.target.value)}
            required
            className="flex-1 px-4 py-2 rounded-l-lg border border-gray-300 focus:outline-none focus:border-[#33A1E0] bg-white text-gray-800 text-base shadow-sm"
          />
          <button
            type="submit"
            className="px-4 py-2 rounded-r-lg bg-[#33A1E0] text-white font-semibold flex items-center gap-2 hover:bg-[#2381b0] transition border border-[#33A1E0]"
            aria-label="Search"
          >
            <i className="fa fa-search mr-1" />
            Search
          </button>
        </form>
        <div className="max-h-[28rem] overflow-y-scroll p-4 mt-1.5 space-y-2">
          {reliefReq.map((req) => {
            return <ReliefinfoCard key={req.id} req={req} />;
          })}
        </div>
      </div>
    </div>
  );
};

export default UserDashboard;
