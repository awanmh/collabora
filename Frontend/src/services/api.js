// src/services/api.js

import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8081/api/api', // PASTIKAN SAMA DENGAN YANG DI POSTMAN
  headers: {
    'Content-Type': 'application/json',
  },
});

// Interceptor untuk menyertakan token jika ada
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export default api;
