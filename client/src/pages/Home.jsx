import React from "react";
import Navbar from "../components/Navbar";
import Questionaries from "../components/Questionaries";
import logo from "../assets/mountain_logo.svg";

const Home = () => {
  return (
    <>
    <Navbar />
    <br />
      <div className="min-h-screen bg-gray-50 flex flex-col">
        <div className="flex flex-col items-center justify-center py-16 bg-white shadow-sm">
          <img
            src={logo}
            alt="Disaster Relief Logo"
            className="w-20 mb-6"
          />
          <h1 className="text-4xl font-bold mb-2 text-gray-800">
            Disaster Relief System
          </h1>
          <p className="text-gray-600 text-lg text-center max-w-xl">
            Sending help where its needed, fast and efficiently.
          </p>
        </div>

        <div className="py-10 px-4 max-w-2xl mx-auto">
          <h2 className="text-2xl font-semibold mb-3 text-gray-700">About</h2>
          <p className="text-gray-600">
            Our platform streamlines disaster relief by connecting the central
            resources, and affected communities. We focus on speed,
            and efficient distribution of resources using complex algorithms and 
            integrating AI to ensure help reaches where it's needed most.
          </p>
        </div>

        <Questionaries />

        <footer className="mt-auto py-4 text-center text-white text-sm bg-[#33A1E0] border-t">
          Disaster Relief System. Providing aid efficiently.
        </footer>
      </div>
    </>
  );
};

export default Home;
