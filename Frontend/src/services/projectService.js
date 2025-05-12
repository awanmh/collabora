import api from "./api";

export const getProjects = () => api.get("/projects");
export const createProject = (projectData) => api.post("/projects", projectData);
export const getTasks = (projectId) => api.get(`/tasks/project/${projectId}`);
export const createTask = (taskData) => api.post("/tasks", taskData);