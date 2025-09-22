import axios, { AxiosHeaders } from "axios";

const url = {
  login: "user/login",
  logout: "user/logout",
  verify: "user/verify",
};

const base_url = import.meta.env.VITE_BASE_URL;

export async function login(payload) {
  const path = base_url + url.login;
  return await axios
    .post(path, payload)
    .then((res) => res.data)
    .catch((err) => {
      if (err.response) {
        return err.response.data;
      }
      console.log(err);
      return err.message;
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
    .then((res) => res.data)
    .catch((err) => {
      if (err.response) {
        return err.response.data;
      }
      console.log(err);
      return err.message;
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
    .then((res) => res.data)
    .catch((err) => {
      if (err.response) {
        return err.response.data;
      }
      console.log(err);
      throw err;
    });
}
