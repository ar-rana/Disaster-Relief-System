import axios from "axios";

const base_url = import.meta.env.VITE_BASE_URL;

const url = {
  resolveRelief: "provider/resolveRelief",
  rejectRelief: "provider/rejectRelief",
  getAssignedReliefs: "provider/get/assignments/",
  roadBlock: "provider/report/blockedpath",
  route: "provider/route",
};

export async function resolveRelief(payload) {
  const headers = {
    Authorization: `Bearer ${window.localStorage.getItem("token")}`,
    "Content-Type": "application/json",
  };
  const path = base_url + url.resolveRelief;
  return axios
    .post(path, payload, { headers })
    .then((res) => {
      return {
        success: true,
        data: res.data,
      };
    })
    .catch((err) => {
      const errMsg = err.response ? err.response.data : err.message;
      console.log(errMsg);
      console.log(err);
      return {
        success: false,
        data: errMsg,
      };
    });
}

export async function rejectRelief(payload) {
  const headers = {
    Authorization: `Bearer ${window.localStorage.getItem("token")}`,
    "Content-Type": "application/json",
  };
  const path = base_url + url.rejectRelief;
  return axios
    .post(path, payload, { headers })
    .then((res) => {
      return {
        success: true,
        data: res.data,
      };
    })
    .catch((err) => {
      const errMsg = err.response ? err.response.data : err.message;
      console.log(errMsg);
      console.log(err);
      return {
        success: false,
        data: errMsg,
      };
    });
}

export async function getAssignedReliefs(username) {
  const headers = {
    Authorization: `Bearer ${window.localStorage.getItem("token")}`,
    "Content-Type": "application/json",
  };
  const path = base_url + url.getAssignedReliefs + username;
  return axios
    .get(path, { headers })
    .then((res) => {
      return {
        success: true,
        data: res.data,
      };
    })
    .catch((err) => {
      const errMsg = err.response ? err.response.data : err.message;
      console.log(errMsg);
      console.log(err);
      return {
        success: false,
        data: errMsg,
      };
    });
}

export async function roadBlocked() {
  const headers = {
    Authorization: `Bearer ${window.localStorage.getItem("token")}`,
    "Content-Type": "application/json",
  };
  const reqData = getLiveLocation();
  const payload = JSON.stringify({
    latitude: reqData.latitude,
    longitude: reqData.longitude
  });
  const path = base_url + url.roadBlock;
  return axios
    .post(path, payload, { headers })
    .then((res) => {
      return {
        success: true,
        data: res.data,
      };
    })
    .catch((err) => {
      const errMsg = err.response ? err.response.data : err.message;
      console.log(errMsg);
      console.log(err);
      return {
        success: false,
        data: errMsg,
      };
    });
}

export async function moveToLocation(reqId) {
  const headers = {
    Authorization: `Bearer ${window.localStorage.getItem("token")}`,
    "Content-Type": "application/json",
  };
  const reqData = getLiveLocation();
  const payload = JSON.stringify({
    latitude: reqData.latitude,
    longitude: reqData.longitude,
    reqId: reqId
  });
  const path = base_url + url.moveToLocation;
  return axios
    .post(path, payload, { headers })
    .then((res) => {
      return {
        success: true,
        data: res.data,
      };
    })
    .catch((err) => {
      const errMsg = err.response ? err.response.data : err.message;
      console.log(errMsg);
      console.log(err);
      return {
        success: false,
        data: errMsg,
      };
    });
}

export async function getLiveLocation() {
  const pos = await new Promise((resolve, reject) => {
    navigator.geolocation.getCurrentPosition(
      (position) => resolve(position),
      (error) => reject(error),
      { enableHighAccuracy: true, timeout: 10000 }
    );
  });

  const latitude = pos.coords.latitude;
  const longitude = pos.coords.longitude;
  return [latitude, longitude]
}
