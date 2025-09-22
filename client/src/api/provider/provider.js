import axios from "axios";

const base_url = process.env.BASE_URL;

const url = {
  resolveRelief: "provider/resolveRelief",
  rejectRelief: "provider/rejectRelief",
  getAssignedReliefs: "provider/get/assignments/",
};

export async function resolveRelief(payload) {
  const path = base_url + url.resolveRelief;
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

export async function rejectRelief(payload) {
  const path = base_url + url.rejectRelief;
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

export async function getAssignedReliefs(username) {
  const path = base_url + url.getAssignedReliefs + username;
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
