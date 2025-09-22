import axios from "axios";

const base_url = process.env.BASE_URL;

const url = {
  createReq: "request/create",
  getAllReq: "request/getstatus/all/",
  getRelief: "request/getrelief/",
  getStatus: "request/getstatus/",
};

export async function create(payload) {
  const path = base_url + url.createReq;
  return axios
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

export async function getAllRequests(contact) {
  const path = base_url + url.getAllReq + contact;
  return axios
    .get(path)
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

export async function getRelief(id) {
  const path = base_url + url.getRelief + id;
  return axios
    .get(path)
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

export async function getStatus(id) {
  const path = base_url + url.getStatus + id;
  return axios
    .get(path)
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
