import axios from "axios";

const base_url = process.env.BASE_URL;

const url = {
  resolveRelief: "provider/resolveRelief",
  rejectRelief: "provider/rejectRelief",
  getAssignedReliefs: "provider/get/assignments/",
};

export async function resolveRelief(payload) {
  const headers = {
    Authorization: window.localStorage.getItem("token"),
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
    Authorization: window.localStorage.getItem("token"),
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
    Authorization: window.localStorage.getItem("token"),
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
