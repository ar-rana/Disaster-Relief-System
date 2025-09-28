import React, { useState } from "react";
import Navbar from "../components/Navbar.jsx";
import UserDashboard from "../components/dashboard/Userdashboard.jsx";
import AdminDashboard from "../components/dashboard/AdminDashboard.jsx";
import { getAllReliefRequests, getRelief, getStatus } from "../api/relief/relief.js";

const Dashboard = ({ admin }) => {
  const [phone, setPhone] = useState("");
  const [reliefId, setReliefId] = useState("");

  const [reliefReq, setReliefReq] = useState([]);

  // TO-DO
  // send this to UserDashboard and use the data inside in "Detail search" section
  const [reliefDetail, setReliefDetail] = useState({
    reliefReq: null,
    reliefStatus: null,
  });

  const getAllRequests = async (e) => {
    e.preventDefault();
    const res = await getAllReliefRequests(phone);
    if (res.success) {
      setReliefReq([...res.data]);
    } else {
      alert("Some error occured, while fetching Relief Requests");
    }
  };

  const submitHandlerForReliefId = async (e) => {
    e.preventDefault();
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
        reliefStatus: res02.data,
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
      <div className="bg-gray-50 min-h-[90vh] w-[90%] ml-auto mr-auto p-4 pt-[5%] space-y-8">
        <UserDashboard
          submitHandlerForNumber={getAllRequests}
          submitHandlerForId={submitHandlerForReliefId}
          phone={phone}
          setPhone={setPhone}
          reliefReq={reliefReq}
          reliefId={reliefId}
          reliefDetail={reliefDetail}
          setReliefId={setReliefId}
        />
        <br />
        {admin ? <AdminDashboard /> : ""}
      </div>
    </>
  );
};

export default Dashboard;
