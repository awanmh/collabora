// src/services/userService.js
import api from './api';

export const getUserById = async (userId) => {
  try {
    // Sesuaikan endpoint API untuk mengambil detail user berdasarkan ID
    // Contoh: GET /api/users/{userId}
    const response = await api.get(`/users/${userId}`);
    return response.data; // Asumsi ini mengembalikan objek user yang memiliki 'username'
  } catch (error) {
    console.error(`Error fetching user with ID ${userId}:`, error);
    throw error;
  }
};