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
  const path = base_url + url.addUser;
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

export async function addAdmin(payload) {
  const path = base_url + url.addAdmin;
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

export async function allocateResources(payload) {
  const path = base_url + url.allocateResources;
  return axios
    .put(path, payload)
    .then((res) => res.data)
    .catch((err) => {
      if (err.response) {
        return err.response.data;
      }
      console.log(err);
      return err.message;
    });
}

export async function trasferProvider(payload) {
  const path = base_url + url.trasferProvider;
  return axios
    .put(path, payload)
    .then((res) => res.data)
    .catch((err) => {
      if (err.response) {
        return err.response.data;
      }
      console.log(err);
      return err.message;
    });
}

export async function changePass(payload) {
  const path = base_url + url.changePass;
  return axios
    .put(path, payload)
    .then((res) => res.data)
    .catch((err) => {
      if (err.response) {
        return err.response.data;
      }
      console.log(err);
      return err.message;
    });
}
