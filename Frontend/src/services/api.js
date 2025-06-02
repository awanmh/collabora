import axios from "axios";

const api = axios.create({
<<<<<<< HEAD
  baseURL: "http://localhost:8081/api/api",
=======
  baseURL: "http://localhost:8080/api",
>>>>>>> 93901ec70be462dda4fb40350dee95909f898e6e
  headers: {
    "Content-Type": "application/json",
  },
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;