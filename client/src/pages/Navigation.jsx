import React, { useEffect, useRef, useState } from "react";
import { useParams } from "react-router-dom";
import ReliefMenu from "../components/menu/ReliefMenu.jsx";
import AssignedMenu from "../components/menu/AssignedMenu.jsx";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import {
  getAssignedReliefs,
  getLiveLocation,
} from "../api/provider/provider.js";
import Map from "../components/maps/Map.jsx";

const Navigation = () => {
  const { id } = useParams();
  const [user, setUser] = useState(null);
  const [hqId, setHqId] = useState(null);
  const stompWSRef = useRef(null);

  const [reliefs, setReliefs] = useState([
    {
      uid: 1,
      criticality: "HIGH",
      lat: "30.7046",
      lon: "76.7179",
      description:
        "Building collapsed, people trapped inside, need urgent rescue.",
      poc: "9876543210",
      name: "Rajesh Kumar",
    },
    {
      uid: 2,
      criticality: "MODERATE",
      lat: "28.7041",
      lon: "77.1025",
      description:
        "No electricity or water for 2 days, family with children needs supplies.",
      poc: "9123456780",
      name: "Anita Sharma",
    },
    {
      uid: 3,
      criticality: "BASIC",
      lat: "19.0760",
      lon: "72.8777",
      description:
        "Flooding on ground floor, need evacuation support if water level rises.",
      poc: "9012345678",
      name: "Kaju Vaju",
    },
    {
      uid: 4,
      criticality: "HIGH",
      lat: "15.2993",
      lon: "74.1240",
      description:
        "Severe landslide, people injured and stuck, need immediate medical help.",
      poc: "9823456712",
      name: "Lakshmi Rao",
    },
    {
      uid: 5,
      criticality: "MODERATE",
      lat: "22.5726",
      lon: "88.3639",
      description:
        "Elderly person with breathing issues, medicines finished, need urgent delivery.",
      poc: "7001234567",
      name: "Sandeep Ghosh",
    },
  ]);
  const [assigned, setAssigned] = useState([
    {
      name: "hello",
      uid: 1,
      description: "hello",
      criticality: "MODERATE",
      longitude: "20.3636391",
      latitude: "85.8152384",
      poc: "0987654321",
    },
    {
      name: "hello",
      uid: 2,
      description: "hello",
      criticality: "MODERATE",
      longitude: "20.3536391",
      latitude: "85.7152384",
      poc: "0987654321",
    },
    {
      name: "hello 2",
      uid: 3,
      description: "hello world",
      criticality: "MODERATE",
      longitude: "20.373321",
      latitude: "85.8452456",
      poc: "1234567890",
    },
  ]);

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
          console.log("STOMP [REQ] '/navigation': ", response);
          if (response.shareCredentials) {
            if (response.hq === hqId) sendCurrentState(response.reqId);
          } else {
            setReliefs((prev) => [response, ...prev]);
          }
        });
        stompClient.subscribe(`/navigation/${id}`, async (res) => {
          const response = await JSON.parse(res.body);
          console.log("STOMP [ASSIGN] '/navigation/{id}': ", response);
          setAssigned((prev) => [...prev, response]);
        });
      },
      onStompError: (frame) => {
        console.log("Error: ", frame.body);
      },
    });

    stompClient.activate();
    stompWSRef.current = stompClient;

    console.log("Id: ", id);
    setUser(window.localStorage.getItem("user"));
    setHqId(window.localStorage.getItem("hq"));
    return () => {
      stompClient.deactivate();
    };
  }, []);

  useEffect(() => {
    const fetchAssigned = async () => {
      const res = await getAssignedReliefs(window.localStorage.getItem("user"));
      if (res.success && res.data?.length > 0) setAssigned([...res.data]);
    };

    fetchAssigned();
  }, []);

  const sendCurrentState = async (reqId) => {
    let pos = getLiveLocation()
      .then((data) => {
        setCurrCoords(data);
      })
      .catch((e) =>
        alert(
          "Unable to retrieve location. Please allow location access and try again"
        )
      );

    const stompClient = stompWSRef.current;
    if (stompClient && stompClient.connected) {
      stompClient.publish({
        destination: `/app/reliefdata/${reqId}`,
        body: JSON.stringify({
          wsUid: id, // websocket UID
          reqId: reqId,
          user: user,
          latitude: pos.latitude,
          longitude: pos.longitude,
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
          <Map assigned={assigned} />
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
          <AssignedMenu data={assigned} />
        </div>
      </div>
      <br />
      <br />
    </div>
  );
};

export default Navigation;
