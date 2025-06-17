import { useState, useEffect } from "react";
import { useAuth } from "../../contexts/AuthContext";
import { getProjects, createProject } from "../../services/projectService";
import TaskCard from "./TaskCard";
import CreateProjectModal from "../Modals/CreateProjectModal";
import { toast } from "react-toastify";
import { motion } from "framer-motion";
import { Link } from "react-router-dom";

const ProjectBoard = () => {
  const [projects, setProjects] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const { user } = useAuth();

  useEffect(() => {
  const fetchProjects = async () => {
    try {
      const response = await getProjects();
      console.log("Projects fetched:", response.data); // ✅ log responsenya
      setProjects(response.data);
    } catch (error) {
      console.error("Fetch error:", error); // ✅ tampilkan kesalahan
      toast.error("Failed to fetch projects");
    }
  };
  fetchProjects();
}, []);


  const handleCreateProject = async (projectData) => {
    try {
      const response = await createProject(projectData);
      setProjects([...projects, response.data]);
      toast.success("Project created successfully!");
      setIsModalOpen(false);
    } catch (error) {
      toast.error(error.response?.data?.message || "Failed to create project");
    }
  };

  return (
    <motion.div
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      className="min-h-screen bg-gray-900 p-6 text-white"
    >
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-3xl font-bold text-yellow-400">Project Board</h1>
        <button
          onClick={() => setIsModalOpen(true)}
          className="bg-yellow-400 text-gray-900 py-2 px-4 rounded font-semibold hover:bg-yellow-500 transition"
        >
          + Create Project
        </button>
      </div>

      {projects.length === 0 ? (
        <p className="text-gray-400">No projects available. Create one to get started!</p>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          {projects.map((project) => (
            <div key={project.id} className="bg-gray-800 p-5 rounded-xl shadow-lg">
              <h2 className="text-xl font-semibold text-yellow-400 mb-2">
                 <Link to={`/projects/${project.id}`} className="hover:underline">
                        {project.title}</Link></h2>
              <p className="text-gray-300 mb-4">{project.description}</p>
              <div className="space-y-4">
                {project.tasks?.length > 0 ? (
                  project.tasks.map((task) => (
                    <TaskCard key={task.id} task={task} />
                  ))
                ) : (
                  <p className="text-sm text-gray-500">No tasks yet</p>
                )}
              </div>
            </div>
          ))}
        </div>
      )}

      <CreateProjectModal
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        onSubmit={handleCreateProject}
      />
    </motion.div>
  );
};

export default ProjectBoard;
