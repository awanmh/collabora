import api from "./api";

export const getProjects = () => api.get("/projects");
export const createProject = (projectData) => api.post("/projects", projectData);
export const getTasks = (projectId) => api.get(`/tasks/project/${projectId}`);
export const createTask = (taskData) => api.post("/tasks", taskData);
export const getProjectById = (id) => api.get(`/projects/${id}`);
export const getAllStudents = () => api.get("/members/students");
export const getAllLecturers = () => api.get("/members/lecturers");
