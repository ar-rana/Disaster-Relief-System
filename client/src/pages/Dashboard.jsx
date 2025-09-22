import React, { useState } from "react";
import Navbar from "../components/Navbar.jsx";
import UserDashboard from "../components/UserDashboard.jsx";
import AdminDashboard from "../components/AdminDashboard.jsx";
import { getRelief, getStatus } from "../api/relief/relief.js";

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

  // TO-DO
  // send this to UserDashboard and use the data inside in "Detail search" section
  const [reliefDetail, setReliefDetail] = useState({
    reliefReq: null,
    reliefDetail: [],
  });

  const getAllRequests = async (e) => {
    const res = await getAllRequests(phone);
    if (res.success) {
      setReliefReq(res.data);
    } else {
      alert("Some error occured, while fetching Relief Requests");
    }
  };

  const submitHandlerForReliefId = async (e) => {
    const res01 = await getRelief(reliefId);
    if (res01.success) {
      setReliefDetail((prev) => ({
        ...prev,
        reliefReq: res01.data,
      }));
    }
    const res02 = await getStatus(reliefId);
    if (res02.success) {
      setReliefDetail((prev) => ({
        ...prev,
        reliefDetail: res02.data,
      }));
    }

    if (!res01.success || !res02.success) {
      alert("no relief request for this ID");
    }
  };

  return (
    <>
      <Navbar />
      <br />
      <div className="bg-gray-50 h-auto w-[90%] ml-auto mr-auto p-4 pt-[5%] space-y-8">
        <UserDashboard
          submitHandlerForNumber={getAllRequests}
          submitHandlerForId={submitHandlerForReliefId}
          phone={phone}
          setPhone={setPhone}
          reliefReq={reliefReq}
          reliefId={reliefId}
          setReliefId={setReliefId}
        />
        <br />
        {admin ? <AdminDashboard /> : ""}
      </div>
    </>
  );
};

export default Dashboard;
