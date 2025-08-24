import React from "react";
import logo from "../../public/mountain_logo.svg";
import nav_wave from "../assets/nav_wave2.svg";

const Navbar = () => {
return (
    <div className="fixed top-0 flex w-full h-12 bg-[#33A1E0] z-1001">
        <div className="p-2 h-full w-1/2 flex gap-2 items-center">
            <img className="h-[90%] rounded-sm" src={logo} alt="logo" />
            <p className="font-bold text-white">Disaster Relief System</p>
        </div>
        <div className="p-2 h-full w-1/2 flex gap-2 items-center justify-end">
            <a
                className="px-4 py-1 bg-white text-[#33A1E0] font-bold rounded-md hover:bg-[#33A1E0] hover:text-white transition"
                href="#"
            >
                Login
            </a>
            <button className="px-4 py-1 cursor-pointer bg-white text-[#33A1E0] font-bold rounded-md hover:bg-[#33A1E0] hover:text-white transition">
                Request Relief
            </button>
        </div>
        <img className="fixed top-[-10] w-full z-[-1]" src={nav_wave} alt="nav" />
    </div>
);
};

export default Navbar;
