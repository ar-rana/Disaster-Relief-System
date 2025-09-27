import React, { useEffect, useRef, useState } from "react";
import { useParams } from "react-router-dom";
import ReliefMenu from "../components/ReliefMenu.jsx";
import AssignedMenu from "../components/AssignedMenu.jsx";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import { getAssignedReliefs } from "../api/provider/provider.js";
import Map from "../components/Map.jsx";

const Navigation = () => {
  const { id } = useParams();
  const stompWSRef = useRef(null);

  const [reliefs, setReliefs] = useState([]);
  const [assigned, setAssigned] = useState([{
    name: "hello",
    uid: 1,
    description: "hello",
    criticality: "MODERATE",
    longitude: "20.3636391",
    latitude: "85.8152384",
    poc: "0987654321"
  },{
    name: "hello",
    uid: 2,
    description: "hello",
    criticality: "MODERATE",
    longitude: "20.3536391",
    latitude: "85.7152384",
    poc: "0987654321"
  },{
    name: "hello 2",
    uid: 3,
    description: "hello world",
    criticality: "MODERATE",
    longitude: "20.373321",
    latitude: "85.8452456",
    poc: "1234567890"
  }]);

  useEffect(() => {
    const socket = new SockJS("http://localhost:8080/ws");
    const stompClient = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
      debug: (str) => {
        console.log(str);
      },
      onConnect: () => {
        console.log("Connected to STOMP");
        stompClient.subscribe(`/navigation`, async (res) => {
          const response = await JSON.parse(res.body);
          console.log("STOMP [REQ]: ", response);
          setReliefs(prev => [response, ...prev]);
        });
        stompClient.subscribe(`/navigation/${id}`, async (res) => {
          const response = await JSON.parse(res.body);
          console.log("STOMP [ASSIGN]: ", response);
          setAssigned(prev => [...prev, response]);
        });
      },
      onStompError: (frame) => {
        console.log("Error: ", frame.body);
      },
    });

    stompClient.activate();
    stompWSRef.current = stompClient;

    console.log("Id: ", id);
    return () => {
      stompClient.deactivate();
    };
  }, []);

  useEffect(() => {
    const fetchAssigned = async () => {
      const res = await getAssignedReliefs(window.localStorage.getItem("user"));
      if (res.success && res.data?.length > 0) setAssigned([...res.data]);
    }

    fetchAssigned();
  }, [])

  const sendCurrentState = async () => {
    let pos;
    try {
      pos = await new Promise((resolve, reject) => {
        navigator.geolocation.getCurrentPosition(
          (position) => resolve(position),
          (error) => reject(error),
          { enableHighAccuracy: true, timeout: 10000 }
        );
      });
    } catch (err) {
      alert("Unable to retrieve location. Please allow location access and try again");
      return;
    }
    const stompClient = stompWSRef.current;
    if (stompClient && stompClient.connected) {
      stompClient.publish({
        destination: `/app/reliefdata/${id}`,
        body: JSON.stringify({
          wsUid: id, // websocket UID
          latitude: pos.coords.latitude,
          longitude: pos.coords.longitude,
        }),
      });
    } else {
      console.log("Not Connected to WS!");
    }
  };

  return (
    <div className="w-full h-auto flex flex-col ml-auto mr-auto gap-1 bg-slate-700">
      <div className="w-[93%] h-[100vh] flex gap-4 ml-auto mr-auto">
        <div className="flex-[3] border-2 m-2">
          <Map assigned={assigned}/>
        </div>
        <div className="flex-[1.25] self-center">
          <ReliefMenu data={reliefs} />
        </div>
      </div>
      <div className="w-[93%] flex flex-col ml-auto mr-auto">
        <p className="text-2xl font-bold text-[#33A1E0] m-2">
          Assigned Relief Points
        </p>
        <div className="self-center w-full flex">
          <AssignedMenu data={assigned}/>
        </div>
      </div>
      <br />
      <br />
    </div>
  );
};

export default Navigation;
