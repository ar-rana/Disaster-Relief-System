import React, { useState } from "react";

const ProviderMenu = ({ isOpen, setOpen, rqId, reject }) => {
  const [warning, setWarning] = useState(null);

  const [description, setDescription] = useState("");
  const [images, setImages] = useState([]);

  const convertToBase64 = (e) => {
    const file = e.target.files[0];
    if (!file) return;

    const reader = new FileReader();
    reader.readAsDataURL(file);

    reader.onload = () => {
      const base64 = reader.result;
      // const ext = file.name.substring(file.name.lastIndexOf("."));
      console.log("Image base64: ", base64);

      setImages((prev) => [...prev, base64]);
    };

    reader.onerror = (error) => {
      console.error("Error converting file:", error);
    };
  };

  const resolve = async () => {
    if (!description) setWarning("Description is required");
    if (images.length === 0) setWarning("Images are required");
    const payload = {
      reliefId: rqId,
      desc: description,
      images: images,
    };
    const data = await resolveRelief(payload);
    if (data.success) {
      setImages([]);
      setDescription("");
      setOpen(false);
    }
  };

  const dismiss = async () => {
    if (!description) setWarning("Description is required");
    const payload = {
      reliefId: rqId,
      desc: description,
    };
    const data = await rejectRelief(payload);
    if (data.success) {
      setDescription("");
      setOpen(false);
    }
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-[1000] flex items-center justify-center">
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
        <h2 className={`text-2xl font-bold mb-6 ${reject ? "text-[#e09b33]" : "text-[#33A1E0]"} text-center`}>
          {reject ? "Reject AID" : "Resolve AID"}
        </h2>
        <form className="flex flex-col gap-3">
          <input
            type="file"
            name="Photo"
            onChange={convertToBase64}
            placeholder="Upload or Click Photo"
            className="border border-gray-300 rounded px-4 py-2 focus:outline-none focus:border-[#33A1E0]"
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
          {reject ? (
          <button
            onClick={dismiss}
            className="bg-[#e09b33] text-white font-semibold py-2 rounded hover:bg-[#b06e23]"
          >
            Reject AID
          </button>
          ) : (
          <button
            onClick={resolve}
            className="bg-[#33A1E0] text-white font-semibold py-2 rounded hover:bg-[#2381b0]"
          >
            Resolve AID
          </button>
          )}
        </form>
      </div>
    </div>
  );
};

export default ProviderMenu;
