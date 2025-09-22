import axios from "axios";

const base_url = process.env.BASE_URL;

const url = {
  addUser: "admin/add/user",
  addAdmin: "admin/add/admin",
  allocateResources: "admin/update/hq/resources",
  trasferProvider: "admin/transfer/provider",
  changePass: "admin/alter/pass",
};

// super/create/hq
// super/add/admin

export async function addUser(payload) {
  const headers = {
    Authorization: window.localStorage.getItem("token"),
    "Content-Type": "application/json",
  };
  const path = base_url + url.addUser;
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

export async function addAdmin(payload) {
  const headers = {
    Authorization: window.localStorage.getItem("token"),
    "Content-Type": "application/json",
  };
  const path = base_url + url.addAdmin;
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

export async function allocateResources(payload) {
  const headers = {
    Authorization: window.localStorage.getItem("token"),
    "Content-Type": "application/json",
  };
  const path = base_url + url.allocateResources;
  return axios
    .put(path, payload, { headers })
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

export async function trasferProvider(payload) {
  const headers = {
    Authorization: window.localStorage.getItem("token"),
    "Content-Type": "application/json",
  };
  const path = base_url + url.trasferProvider;
  return axios
    .put(path, payload, { headers })
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

export async function changePass(payload) {
  const headers = {
    Authorization: window.localStorage.getItem("token"),
    "Content-Type": "application/json",
  };
  const path = base_url + url.changePass;
  return axios
    .put(path, payload, { headers })
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
