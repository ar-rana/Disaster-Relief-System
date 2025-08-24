import React from 'react';
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
    desc: "my god im dead, save im dead, save me save me save me save me save me save me save me save me saveim dead, save me save me save me save me save me save me save me save me saveim dead, save me save me save me save me save me save me save me save me saveim dead, save me save me save me save me save me save me save me save me saveme save me save me save me save me save me save me save me save mesave me",
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
  },,
  {
    id: 1,
    lat: "28.43",
    lon: "77.1234",
    desc: "my god im dead, save im dead, save me save me save me save me save me save me save me save me saveim dead, save me save me save me save me save me save me save me save me saveim dead, save me save me save me save me save me save me save me save me saveim dead, save me save me save me save me save me save me save me save me saveme save me save me save me save me save me save me save me save mesave me",
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
  {
    id: 1,
    lat: "28.43",
    lon: "77.1234",
    desc: "my god im dead, save im dead, save me save me save me save me save me save me save me save me saveim dead, save me save me save me save me save me save me save me save me saveim dead, save me save me save me save me save me save me save me save me saveim dead, save me save me save me save me save me save me save me save me saveme save me save me save me save me save me save me save me save mesave me",
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

const AssignedMenu = () => {
  return (
    <div className="max-w-[85%] border-2 h-44 border-teal-200 bg-gray-100 p-4 rounded-md overflow-x-scroll overflow-y-hidden">
      <div className="flex gap-2 p-1">
        {data.map((data) => {
          return (
            <div class="bg-teal-50 border-t-4 border-teal-200 rounded-b text-teal-900 p-3 shadow-md w-3xs">
              <div class="flex">
                <div>
                  <p title={data.desc} class="font-bold">{data.name} | Id: {data.id}</p>
                  <p class="text-sm font-medium">Contact: {data.POC}</p>
                  <p title={data.desc} class="text-sm truncate overflow-hidden w-[240px]">{data.desc}</p>
                  <button className='bg-teal-300 font-medium px-2 rounded-lg mt-2'>Move Here</button>
                </div>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  )
}

export default AssignedMenu
