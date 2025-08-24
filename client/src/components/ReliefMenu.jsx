import React from "react";
28.613, 77.209;
const data = [
  {
    id: 0,
    lat: "27.99",
    lon: "77.456",
    desc: "just testing the systems",
    POC: "1234567890",
    name: "arrthjujn",
  },
  {
    id: 1,
    lat: "28.43",
    lon: "77.1234",
    desc: "my god im dead",
    POC: "0987654321",
    name: "kaliyug",
  },
  {
    id: 2,
    lat: "28.3132",
    lon: "77.351",
    desc: "my god he is dead",
    POC: "78905238",
    name: "street dog",
  },
];

const ReliefMenu = () => {
  return (
    <div className="max-w-[95%] border-2 h-[90%] border-amber-200 bg-gray-100 p-4 rounded-md overflow-y-scroll">
      <div className="bg-white flex flex-col gap-1">
        {data.map((data) => {
          return (
            <div class="bg-amber-50 border-t-4 border-amber-200 rounded-b text-teal-900 px-3 py-2 shadow-md">
              <div class="flex">
                <div>
                  <p class="font-bold">{data.name}</p>
                  <p class="text-sm">{data.desc}</p>
                </div>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default ReliefMenu;
