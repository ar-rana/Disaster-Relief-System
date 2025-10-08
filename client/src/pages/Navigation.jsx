import React, { useEffect, useRef, useState } from "react";
import { useParams } from "react-router-dom";
import ReliefMenu from "../components/menu/ReliefMenu.jsx";
import AssignedMenu from "../components/menu/AssignedMenu.jsx";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import {
  getAssignedReliefs,
  getLiveLocation,
  moveToLocation,
} from "../api/provider/provider.js";
import Map from "../components/maps/Map.jsx";

const Navigation = () => {
  const { id } = useParams();
  const [user, setUser] = useState(null);
  const [hqId, setHqId] = useState(null);
  const stompWSRef = useRef(null);

  const [position, setPosition] = useState([[]]);

  const [reliefs, setReliefs] = useState([]);
  const [assigned, setAssigned] = useState([]);

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

  const moveHere = async (reqId) => {
    const data = await moveToLocation(reqId);
    if (data.success) {
      data.data.map((points) => {
        setPosition((prev) => {
          [
            ...prev,
            [parseFloat(points.longitude), parseFloat(points.longitude)],
          ];
        });
      });
    } else {
      alert("Failed to fetch location");
    }
  };

  return (
    <div className="w-full h-auto flex flex-col ml-auto mr-auto gap-1 bg-slate-700">
      <div className="w-[93%] h-[100vh] flex gap-4 ml-auto mr-auto">
        <div className="flex-[3] border-2 m-2">
          <Map assigned={assigned} pos={position} />
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
          <AssignedMenu data={assigned} handleRouting={moveHere} />
        </div>
      </div>
      <br />
      <br />
    </div>
  );
};

export default Navigation;
