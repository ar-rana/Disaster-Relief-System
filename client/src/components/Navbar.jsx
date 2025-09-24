import React, { useState } from "react";
import logo from "../assets/mountain_logo.svg";
import nav_wave from "../assets/nav_wave2.svg";
import RequestAid from "./RequestAid";
import useVerify from "../hooks/useVerify";

const Navbar = () => {
  const { data, err } = useVerify();
  const [isOpen, setOpen] = useState(false);

  const handleLogout = () => {
    window.localStorage.removeItem("token");
    window.localStorage.removeItem("user");
    window.location.reload();
  }

  return (
    <div className="fixed top-0 flex w-full h-12 bg-[#33A1E0] z-1001">
      <div className="p-2 h-full w-1/2 flex gap-2 items-center">
        <a className="h-[95%]" href="/">
          <img className="h-full rounded-sm" src={logo} alt="logo" />
        </a>
        <p className="font-bold text-white">Disaster Relief System</p>
      </div>
      <div className="p-2 h-full w-1/2 flex gap-2 items-center justify-end">
        {!data ? (
          <a
            className="px-4 py-1 bg-white text-[#33A1E0] font-bold rounded-md hover:bg-[#33A1E0] hover:text-white"
            href="/login"
          >
            Login
          </a>
        ) : (
          <a
            className="px-4 py-1 bg-white text-[#33A1E0] font-bold rounded-md hover:bg-[#33A1E0] hover:text-white"
            onClick={(e) => handleLogout(e)}
          >
            Logout
          </a>
        )}
        <a
          className="px-4 py-1 bg-white text-[#33A1E0] font-bold rounded-md hover:bg-[#33A1E0] hover:text-white"
          href="/dashboard"
        >
          DashBoard
        </a>
        <button
          className="px-4 py-1 cursor-pointer bg-white text-[#33A1E0] font-bold rounded-md hover:bg-[#33A1E0] hover:text-white"
          onClick={() => setOpen((prev) => !prev)}
        >
          Request Relief
        </button>
      </div>
      <img className="fixed top-[0] w-full z-[-1]" src={nav_wave} alt="nav" />
      <RequestAid isOpen={isOpen} setOpen={setOpen} />
    </div>
  );
};

export default Navbar;
