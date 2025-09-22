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
    .then((res) => res.data)
    .catch((err) => {
      if (err.response) {
        return err.response.data;
      }
      console.log(err);
      return err.message;
    });
}

export async function getAllRequests(contact) {
  const path = base_url + url.getAllReq + contact;
  return axios
    .get(path)
    .then((res) => res.data)
    .catch((err) => {
      if (err.response) {
        return err.response.data;
      }
      console.log(err);
      return err.message;
    });
}

export async function getRelief(id) {
  const path = base_url + url.getRelief + id;
  return axios
    .get(path)
    .then((res) => res.data)
    .catch((err) => {
      if (err.response) {
        return err.response.data;
      }
      console.log(err);
      return err.message;
    });
}

export async function getStatus(id) {
  const path = base_url + url.getStatus + id;
  return axios
    .get(path)
    .then((res) => res.data)
    .catch((err) => {
      if (err.response) {
        return err.response.data;
      }
      console.log(err);
      return err.message;
    });
}