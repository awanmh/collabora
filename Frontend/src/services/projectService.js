import api from "./api";

export const getProjects = () => api.get("/projects");
export const createProject = (projectData) => api.post("/projects", projectData);
export const getTasks = (projectId) => api.get(`/tasks/project/${projectId}`);
export const getProjectById = (id) => api.get(`/projects/${id}`);
export const getAllStudents = () => api.get("/members/students");
export const getAllLecturers = () => api.get("/members/lecturers");

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

