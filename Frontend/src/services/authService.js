// src/services/authService.js
import api from "./api";

export const login = (credentials) => api.post("/auth/login", credentials);
export const register = (userData) => api.post("/auth/register", userData);

// Tambahkan fungsi ini
export const getUserProfile = (userId) => api.get(`/users/${userId}`);
