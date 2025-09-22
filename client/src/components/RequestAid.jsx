import React, { useState } from "react";
import { create } from "../api/relief/relief";

const RequestAid = ({ isOpen, setOpen }) => {
  const [location, setLocation] = useState({ latitude: null, longitude: null });
  const [warning, setWarning] = useState("");
  const [verify, setVerify] = useState(false);

  const [name, setName] = useState("");
  const [poc, setPoc] = useState("");
  const [description, setDescription] = useState("");

  const [otp, setOtp] = useState(null);

  if (!isOpen) return null;

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (pos) => {
          setLocation({
            latitude: pos.coords.latitude,
            longitude: pos.coords.longitude,
          });
        },
        (err) => {
          alert("Unable to retrieve location. Please allow location access.");
          console.log(err);
          return;
        }
      );
      const payload = {
        name: name,
        poc: poc,
        longitude: location.longitude,
        latitude: location.latitude,
        description: description,
      };
      console.log("[AID_REQ] Payload: ", payload);

      const res = await create(payload);
      alert(res.data); 
      if (res.success) {
        setOpen(prev => !prev);
      } 
    }
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center">
      <div className="absolute inset-0 bg-black opacity-40"></div>
      <div
        className="relative bg-white rounded-lg shadow-lg p-8 w-full max-w-md z-10"
        style={{ borderTop: "6px solid #33A1E0" }}
      >
        <button
          className="absolute top-3 right-3 text-gray-400 hover:text-gray-700"
          onClick={() => setOpen(false)}
          aria-label="Close"
        >
          <span className="fa fa-times" />
        </button>
        <h2 className="text-2xl font-bold mb-6 text-[#33A1E0] text-center">
          Request Aid
        </h2>
        <form className="flex flex-col gap-3" onSubmit={handleSubmit}>
          <input
            type="text"
            name="name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            placeholder="Your Name"
            className="border border-gray-300 rounded px-4 py-2 focus:outline-none focus:border-[#33A1E0]"
            required
          />
          <input
            type="number"
            name="poc"
            value={poc}
            onChange={(e) => setPoc(e.target.value)}
            placeholder="Contact Number"
            className="border border-gray-300 rounded px-4 py-2 focus:outline-none focus:border-[#33A1E0]"
            required
          />
          <textarea
            name="description"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            placeholder="Describe your problem"
            className="border border-gray-300 rounded px-4 py-2 focus:outline-none focus:border-[#33A1E0] min-h-[80px]"
            required
          />
          <p className="text-sm text-red-500 p-0 m-0">{warning}</p>
          {verify ? (
            <input
              type="number"
              name="otp"
              value={otp}
              onChange={(e) => setOtp(e.target.value)}
              placeholder="Enter 6-digit OTP"
              className="border border-gray-300 rounded px-4 py-2 focus:outline-none focus:border-[#33A1E0]"
              required
            />
          ) : (
            ""
          )}
          {!verify ? (
            <button
              type="submit"
              className="bg-[#33A1E0] text-white font-semibold py-2 rounded hover:bg-[#2381b0]"
            >
              Submit Request
            </button>
          ) : (
            <button
              type="submit"
              className="bg-[#33A1E0] text-white font-semibold py-2 rounded hover:bg-[#2381b0]"
            >
              Submit OTP
            </button>
          )}
        </form>
      </div>
    </div>
  );
};

export default RequestAid;
