import api from "./api";

// PROJECTS
export const getProjects = () => api.get("/projects");
export const createProject = (projectData) => api.post("/projects", projectData);
export const getProjectById = (id) => api.get(`/projects/${id}`);

// TASKS
export const getTasks = (projectId) => api.get(`/tasks/project/${projectId}`);
export const getTasksByProject = (projectId) => api.get(`/tasks/project/${projectId}`);
export const createTask = (taskData) => api.post("/tasks", taskData);

// SIMULASI CREATE TASK (opsional, hanya jika dibutuhkan untuk mock/testing)
/*
export const createTask = async (taskData) => {
  try {
    console.log("Mengirim data tugas ke backend (simulasi):", taskData);
    return new Promise(resolve => {
      setTimeout(() => {
        console.log("Tugas berhasil dibuat (simulasi):", taskData);
        resolve({ success: true, data: taskData });
      }, 500);
    });
  } catch (error) {
    console.error("Gagal membuat tugas (simulasi):", error.message);
    throw error;
  }
};
*/

// MEMBERS
export const getAllStudents = () => api.get("/members/students");
export const getAllLecturers = () => api.get("/members/lecturers");
export const addMemberToProject = (projectId, memberData) =>
  api.post(`/projects/${projectId}/members`, memberData);
