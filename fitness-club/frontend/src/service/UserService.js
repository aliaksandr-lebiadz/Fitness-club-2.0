import axios from "axios";

const USERS_URL = "/users";

export const userService = {
  getAll,
  deleteById,
  updateById,
  create,
  getById,
  login
};

function getAll() {
  return axios.get(USERS_URL);
}

function deleteById(id) {
  return axios.delete(USERS_URL + "/" + id);
}

function updateById(id, user) {
  return axios.patch(USERS_URL + "/" + id, user);
}

function create(user) {
  return axios.post(USERS_URL, user);
}

function getById(id) {
  return axios.get(USERS_URL + "/" + id);
}

function login(email, password) {
  return axios.post("/authenticator", { email, password });
}
