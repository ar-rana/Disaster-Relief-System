import React, { useState } from "react";
import Navbar from "../components/Navbar.jsx";
import UserDashboard from "../components/UserDashboard.jsx";
import AdminDashboard from "../components/AdminDashboard.jsx";

const data = [
  {
    id: 0,
    criticality: "basic",
    lat: "27.99",
    lon: "77.456",
    desc: "just testing the systems",
    POC: "1234567890",
    name: "arrthjujn",
    status: "true",
  },
  {
    id: 1,
    criticality: "high",
    lat: "28.43",
    lon: "77.1234",
    desc: "my god im dead, save im dead, save me save me save me save me save me save me save me save me saveim dead, save me save me save me save me save me save me save me save me saveim dead, save me save me save me save me save me save me save me save me saveim dead, save me save me save me save me save me save me save me save me saveme save me save me save me save me save me save me save me save mesave me",
    POC: "0987654321",
    name: "kaliyug",
    status: "false",
  },
  {
    id: 2,
    criticality: "high",
    lat: "28.3132",
    lon: "77.351",
    desc: "my god he is dead",
    POC: "78905238",
    name: "street dog",
    status: "false",
  },
  {
    id: 2,
    criticality: "high",
    lat: "28.3132",
    lon: "77.351",
    desc: "my god he is dead",
    POC: "78905238",
    name: "street dog",
    status: "false",
  },
];

const Dashboard = ({ admin }) => {
  const [phone, setPhone] = useState("");
  const [reliefId, setReliefId] = useState("");

  const [reliefReq, setReliefReq] = useState(data);

  const submitHandlerForNumber = () => {};
  const submitHandlerForId = () => {};

  return (
    <>
      <Navbar />
      <br />
      <div className="bg-gray-50 h-auto w-[90%] ml-auto mr-auto p-4 pt-[5%] space-y-8">
        <UserDashboard
          submitHandlerForNumber={submitHandlerForNumber}
          submitHandlerForId={submitHandlerForId}
          phone={phone}
          setPhone={setPhone}
        // 'reliefReq' DATA being used for both ID and number change that when the actual data comes
          reliefReq={reliefReq}
          reliefId={reliefId}
          setReliefId={setReliefId}
        />
        <br />
        <AdminDashboard />
      </div>
    </>
  );
};

export default Dashboard;
