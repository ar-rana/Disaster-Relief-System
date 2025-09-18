import React, { useState } from "react";
import LoginAndRegForm from "./LoginAndRegForm";

const AdminDashboard = () => {
  const [provider, setProvider] = useState("");
  const [newhq, setNewhq] = useState("");
  const [warning1, setWarning1] = useState("");

  const [uid, setUid] = useState("");

  const [adminContact, setAdminContact] = useState("");
  const [adminUsername, setUsername] = useState("");
  const [adminPassword, setPassword] = useState("");

  const registerHandler = (e) => {
    e.preventDefault();
  };

  const transferProvider = (e) => {
    e.preventDefault();
  };

  const removeUser = (e) => {
    e.preventDefault();
  };

  const addAdmin = (e) => {
    e.preventDefault();
  };

  return (
    <>
      <div className="flex flex-col md:flex-row justify-center gap-4">
        <div className="flex-2">
          <LoginAndRegForm signUp={true} formHandler={registerHandler} />
        </div>
        <div className="flex-2 border-2 border-gray-400 flex flex-col p-4 rounded-sm gap-2">
          <div className="border-2 border-[#33A1E0] p-4 rounded-md h-1/2">
            <h2 className="font-bold text-[#33A1E0] text-md">
              Transfer a provider
            </h2>
            <form
              className="flex flex-col w-full gap-2 mt-2"
              onSubmit={transferProvider}
            >
              <input
                type="text"
                placeholder="Enter provider contact number"
                value={provider}
                onChange={(e) => setProvider(e.target.value)}
                required
                className="flex-1 px-2 py-1 rounded-l-lg border border-gray-300 focus:outline-none focus:border-[#33A1E0] bg-white text-gray-800 text-base shadow-sm"
              />
              <input
                type="text"
                placeholder="Enter new headquarter ID"
                value={newhq}
                onChange={(e) => setNewhq(e.target.value)}
                required
                className="flex-1 px-2 py-1 rounded-l-lg border border-gray-300 focus:outline-none focus:border-[#33A1E0] bg-white text-gray-800 text-base shadow-sm"
              />
              <button
                type="submit"
                className="px-2 py-1 rounded-lg bg-[#33A1E0] text-white font-semibold flex items-center gap-2 hover:bg-[#2381b0] transition border border-[#33A1E0]"
              >
                Submit
              </button>
            </form>
          </div>
          <div className="border-2 border-[#33A1E0] p-4 rounded-md h-1/2">
            <h2 className="font-bold text-[#33A1E0] text-md">Additional resource allocation</h2>
            <form
              className="flex flex-col w-full gap-2 mt-2"
              onSubmit={removeUser}
            >
              <input
                type="text"
                placeholder="Enter new resource allocation"
                value={uid}
                onChange={(e) => setUid(e.target.value)}
                required
                className="flex-1 px-2 py-1 rounded-l-lg border border-gray-300 focus:outline-none focus:border-[#33A1E0] bg-white text-gray-800 text-base shadow-sm"
              />
              <button
                type="submit"
                className="px-2 py-1 rounded-lg bg-[#33A1E0] text-white font-semibold flex items-center gap-2 hover:bg-[#2381b0] transition border border-[#33A1E0]"
              >
                Submit
              </button>
            </form>
          </div>
          <div className="border-2 border-[#33A1E0] p-4 rounded-md h-1/2">
            <h2 className="font-bold text-[#33A1E0] text-md">
              Add new admin to HQ
            </h2>
            <form
              className="flex flex-col w-full gap-2 mt-2"
              onSubmit={addAdmin}
            >
              <div className="flex gap-1">
                <input
                  type="number"
                  placeholder="Enter admin contact number"
                  value={adminContact}
                  onChange={(e) => setAdminContact(e.target.value)}
                  required
                  className="flex-1 px-2 py-1 rounded-lg border border-gray-300 focus:outline-none focus:border-[#33A1E0] bg-white text-gray-800 text-base shadow-sm"
                />
                <input
                  type="text"
                  placeholder="Enter username"
                  value={adminUsername}
                  onChange={(e) => setUsername(e.target.value)}
                  required
                  className="flex-1 px-2 py-1 rounded-lg border border-gray-300 focus:outline-none focus:border-[#33A1E0] bg-white text-gray-800 text-base shadow-sm"
                />
              </div>
              <input
                type="text"
                placeholder="Enter password"
                value={adminPassword}
                onChange={(e) => setPassword(e.target.value)}
                required
                className="flex-1 px-2 py-1 rounded-lg border border-gray-300 focus:outline-none focus:border-[#33A1E0] bg-white text-gray-800 text-base shadow-sm"
              />
              <button
                type="submit"
                className="px-2 py-1 rounded-lg bg-[#33A1E0] text-white font-semibold flex items-center gap-2 hover:bg-[#2381b0] transition border border-[#33A1E0]"
              >
                Submit
              </button>
            </form>
          </div>
          <p className="text-sm font-normal text-red-600 p-0 m-0">{warning1}</p>
        </div>
      </div>
      <br />
      <br />
      <h2 className="font-bold text-[#33A1E0] text-md m-0">Change Password</h2>
      <form className="flex flex-col w-full gap-2 mt-2" onSubmit="">
        <input
          type="text"
          placeholder="Enter changed password"
          value=""
          // onChange={(e) => setUid(e.target.value)}
          required
          className="flex-1 px-2 py-1 rounded-l-lg border border-gray-300 focus:outline-none focus:border-[#33A1E0] bg-white text-gray-800 text-base shadow-sm"
        />
        <button
          type="submit"
          className="px-2 py-1 rounded-lg bg-[#33A1E0] text-white font-semibold flex items-center gap-2 hover:bg-[#2381b0] transition border border-[#33A1E0]"
        >
          Submit
        </button>
      </form>
    </>
  );
};

export default AdminDashboard;
