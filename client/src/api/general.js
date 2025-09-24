import axios, { AxiosHeaders } from "axios";

const url = {
  login: "user/login",
  logout: "user/logout",
  verify: "user/verified",
};

const base_url = import.meta.env.VITE_BASE_URL;

export async function login(payload) {
  const path = base_url + url.login;
  return await axios
    .post(path, payload)
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

export async function verify() {
  const headers = {
    Authorization: `Bearer ${window.localStorage.getItem("token")}`,
    "Content-Type": "application/json",
  };
  const path = base_url + url.verify;
  return await axios
    .get(path, { headers })
    .then((res) => {
      return {
        success: true,
        data: res.data,
      };
    })
    .catch((err) => {
      const errMsg = err.response ? err.response.data : err.message;
      console.log("Err Msg: ", errMsg);
      console.log(err);
      return {
        success: false,
        data: errMsg,
      };
    });
}

export async function logout() {
  const headers = {
    Authorization: `Bearer ${window.localStorage.getItem("token")}`,
    "Content-Type": "application/json",
  };
  const path = base_url + url.logout;
  return await axios
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
