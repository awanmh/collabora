// src/services/taskService.js
import api from './api'; // Asumsi Anda punya file konfigurasi axios terpusat

/**
 * Mengambil semua tugas berdasarkan ID proyek.
 * @param {number} projectId
 */
export const getTasksByProject = (projectId) => {
  return api.get(`/tasks/project/${projectId}`);
};

/**
 * Membuat tugas baru.
 * @param {object} taskData - Data tugas baru, contoh: { title, description, projectId, assigneeId }
 */
export const createTask = (taskData) => {
  return api.post('/tasks', taskData);
};

/**
 * Mengupdate tugas yang sudah ada.
 * @param {number} taskId
 * @param {object} updateData - Data yang akan diupdate
 */
export const updateTask = (taskId, updateData) => {
  return api.put(`/tasks/${taskId}`, updateData);
};

/**
 * Menghapus tugas.
 * @param {number} taskId
 */
export const deleteTask = (taskId) => {
  return api.delete(`/tasks/${taskId}`);
};
